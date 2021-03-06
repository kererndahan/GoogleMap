import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import static com.aventstack.extentreports.Status.INFO;
import static junit.framework.TestCase.assertTrue;


public class Sanity {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static Helper myHelper = new Helper();
    private static DBActions myDB = new DBActions();
    private static GMapRest myrest = new GMapRest();
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;


    @BeforeTest
    public void startReport() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/STMExtentReport.html");
        // Create an object of Extent Reports
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "SoftwareTestingMaterial");
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("User Name", "Keren Dahan");
        htmlReporter.config().setDocumentTitle("Test Of Google Map! ");
        // Name of the report
        htmlReporter.config().setReportName("GoogleMapReport ");
        // Dark Theme
        htmlReporter.config().setTheme(Theme.DARK);
        htmlReporter.loadXMLConfig(System.getProperty("user.dir") + "/test-output/extent-config.xml");

    }
    //This method is to capture the screenshot and return the path of the screenshot.
    public static String getScreenShot(WebDriver driver, String screenshotName) throws IOException {
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        // after execution, you could see a folder "FailedTestsScreenshots" under src folder
        String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination);
        return destination;
    }


    @Test
    public void sanity() throws Exception {
        int randomNum = 10 + (int) (Math.random() * 1000);
        /***************Browse to google map**********************************/
        System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");

        String myjson= myrest.GetJsonLine();
        myrest.parse(myjson);
        logger = extent.createTest("To Test get Json");
        logger.createNode("Before get lat lng from REST");

        //Assert.assertEquals(driver.getTitle(),"Google");
        String lat = Double.toString(myrest.getLat());
        String lng = Double.toString(myrest.getLng());
        String searchtext = myrest.getSearch();
        String mySearch = lat + "," + lng ;
        String myURL = "https://www.google.com/maps";
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(myURL);

     WebElement searchbox=  driver.findElement(By.id("searchboxinput"));
        Actions builder = new Actions(driver);
        Actions actions = builder;
        actions.moveToElement(searchbox);
        actions.click();
        actions.sendKeys(mySearch);
     actions.sendKeys(Keys.ENTER);
        logger.createNode("After search for cordinates");


     Action seriesOfActions = actions.build();
     seriesOfActions.perform() ;
     WebElement searchresult = driver.findElement(By.cssSelector("#pane div:nth-child(5) span.widget-pane-link"));
     String actualString = searchresult.getText();
     System.out.println(actualString);
     assertTrue(actualString.contains(searchtext));

     //System.out.println(result.getText());
 }

    public static void setDriverAccordingToBrowser(String browser,String URL){
        switch (browser) {
            case "Chrome":
                System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                break;
            case "FireFox":
                System.setProperty("webdriver.gecko.driver", ".\\Drivers\\geckodriver-v0.24.0-win64");
                driver = new FirefoxDriver();
//                ((FirefoxDriver)driver).getKeyboard().pressKey(Keys.F11);
                break;
            case "Edge": //Fix so will ajust to edge
                System.setProperty("webdriver.edge.driver", ".\\Drivers\\Edge\\MicrosoftWebDriver.exe");
                driver = new EdgeDriver();
                break;
            default:
                System.setProperty("webdriver.edge.driver", ".\\Drivers\\chromedriver.exe");
                driver = new ChromeDriver();
                driver.manage().window().maximize();
                  break;
        }
        driver.get(URL);
    }

    @AfterMethod
    public void getResult(ITestResult result) throws Exception{
        if(result.getStatus() == ITestResult.FAILURE){
            //MarkupHelper is used to display the output in different colors
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
            //To capture screenshot path and store the path of the screenshot in the string "screenshotPath"
            //We do pass the path captured by this method in to the extent reports using "logger.addScreenCapture" method.
            //String Scrnshot=TakeScreenshot.captuerScreenshot(driver,"TestCaseFailed");
            String screenshotPath = getScreenShot(driver, result.getName());
            //To add it in the extent report
            logger.fail("Test Case Failed Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
        }
        else if(result.getStatus() == ITestResult.SKIP){
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
        }
        else if(result.getStatus() == ITestResult.SUCCESS)
        {
            String screenshotPath = getScreenShot(driver, result.getName());
            //To add it in the extent report
            logger.log(Status.PASS,"Test Case Passes Snapshot is below " + logger.addScreenCaptureFromPath(screenshotPath));
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" Test Case PASSED", ExtentColor.GREEN));
        }
        driver.quit();
    }

    @AfterTest
    public void endReport() {
        extent.flush();
    }
}

