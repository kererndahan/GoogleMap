import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;


public class Sanity {
    public static WebDriver driver;
    public static WebDriverWait wait;
    private static Helper myHelper = new Helper();
    private static DBActions myDB = new DBActions();


    @Rule
    public TestName name = new TestName();
    // report location & details
//    public static ExtentReports extent = new ExtentReports(".//report.html", false);
//    public static ExtentTest test = extent.startTest("Buy me! ", "Keren's Automation project");


    @BeforeClass
    public static void beforeMyClass() throws SQLException, ClassNotFoundException {
        String browserType = myHelper.getData("browser");
        String myURL= myDB.getaInfo("URL");
        String mySearch= myDB.getaInfo("mySearch");

//        String mysearch =
        setDriverAccordingToBrowser(browserType,myURL);// (driver,browserType);
        myHelper.openURL(myURL,driver);
                wait = new WebDriverWait(driver, 30);
//        extent.addSystemInfo("Environment","Production");
//        test.log(LogStatus.INFO, "Selected Browser", browserType);
     /* After driver & Wait are Intialized in Helper can use Class constractors *////

    }
 @Test
    public void sanity() throws Exception {
        int randomNum = 10 + (int) (Math.random() * 1000);
        String email = "kerenstore07+" + Integer.toString(randomNum) + "@gmail.com";
        //test.log(LogStatus.INFO, "Logging you in....");
        /***************Browse to google map**********************************/
 System.setProperty("webdriver.chrome.driver", ".\\Drivers\\chromedriver.exe");
     String myURL= myDB.getaInfo("URL");
     String mySearch= myDB.getaInfo("mySearch");
 driver = new ChromeDriver();
     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
     driver.get(myURL);
     driver.findElement(By.id("searchboxinput")).sendKeys(mySearch);
     driver.findElement(By.cssSelector("div.suggest-left-cell")).click();


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
//    @AfterClass
//    public static void afterClass() {
//        test.log(LogStatus.INFO,"Test Completed", Sanity.test.addScreenCapture(takeScreenShot("done",driver)));
//        driver.quit();
//        extent.endTest(test);
//        extent.flush();
//    }
}
