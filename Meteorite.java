public interface Meteorite extends Comparable<Meteorite> {
    
    String getName();
    public void setName(String name);

    double getLatitude();
    public void setLatitude(double latitude);

    double getLongitude();
    public void setLongitude(double longitude);

    boolean getFall();

    public void setFall(boolean fall);

    double getMassInGrams();

    public void setMassInGrams(double massInGrams);

  

} 




