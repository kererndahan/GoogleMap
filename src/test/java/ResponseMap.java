import com.google.gson.annotations.SerializedName;

public class ResponseMap {
    @SerializedName("results")
    private ResponseMap.Results results[];

    private static class Results {
        @SerializedName("geometry")
        public ResponseMap.geometry geometry;
    }

    private static class geometry {
        @SerializedName("location")
        public ResponseMap.location location;
    }
    private static class location {
        @SerializedName("lat")
        public String lat;
        public String lng;

    }


    public String getlat() {
        return results[0].geometry.location.lat;
    }
    public String getlng() {
        return results[0].geometry.location.lng;

    }
}
