import java.util.List;
import java.io.File;

public interface BackendInterface {

    boolean initialize(String data);


    List<Meteorite> getMeteoritesWithMaxMass();


    List<Meteorite> getMeteoritesByMassRange(double minMass, double maxMass);

}

