package socialmedia;

/**
 * Class for storing coordinates as longitude and latitude.
 */
public class Coordinate {
    private double longitude;
    private double latitude;

    /**
     * Returns the longitude for coordinate.
     * @return longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude for the coordinate.
     * @param longitude new longitude
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Returns the latitude for coordinate.
     * @return latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude for the coordinate.
     * @param latitude new latitude
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
