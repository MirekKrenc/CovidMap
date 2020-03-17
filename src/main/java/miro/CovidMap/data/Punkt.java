package miro.CovidMap.data;

public class Punkt {

    private double latitude;
    private double longitude;
    private int dailyGrowth;
    private int confirmed;
    private String country;

    public String getMessage() {
        return message;
    }

    private String message;

    public Punkt() {
    }

    public Punkt(double latitude, double longitude, int dailyGrowth, int confirmed, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.dailyGrowth = dailyGrowth;
        this.confirmed = confirmed;
        this.country = country;
        this.message = this.country + " " + confirmed + ", dobowy przyrost:" + this.dailyGrowth;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getDailyGrowth() {
        return dailyGrowth;
    }

    public void setDailyGrowth(int dailyGrowth) {
        this.dailyGrowth = dailyGrowth;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Punkt{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", dailyGrowth=" + dailyGrowth +
                ", message='" + confirmed + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
