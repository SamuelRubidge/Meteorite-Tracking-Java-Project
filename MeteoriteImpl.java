import java.util.Collection;

public class MeteoriteImpl implements Comparable<Meteorite>, Meteorite {

    private String name;
    private double latitude;
    private double longitude;
    private boolean fall;
    private double massInGrams;

    public MeteoriteImpl(String name, double latitude, double longitude, boolean fall, double massInGrams) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.fall = fall;
        this.massInGrams = massInGrams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean getFall() {
        return fall;
    }

    public void setFall(boolean fall) {
        this.fall = fall;
    }

    public double getMassInGrams() {
        return massInGrams;
    }

    public void setMassInGrams(double massInGrams) {
        this.massInGrams = massInGrams;
    }

    public int compareTo(Meteorite o) {
        if(o.getName().equals(this.name) && o.getLatitude() == this.latitude &&
        o.getLongitude() == this.getLongitude() && o.getFall() == this.fall &&
        o.getMassInGrams() == this.getMassInGrams()) {
            return 1;
        } else {
            return 0;
        }
    }
}

