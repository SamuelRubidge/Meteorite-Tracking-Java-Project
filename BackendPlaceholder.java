import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.util.List;

public class BackendPlaceholder { 
    private ArrayList values;

    public BackendPlaceholder() {
        values = new ArrayList();
    }

    public boolean insertSingleKey(MeteoriteImpl key) {
        values.add(key);
        return true;
    }

    public int numKeys() {
        return values.size();
    }

    public Iterator<Double> iterator() {
        return values.iterator();
    }

    public void setIterationStartPoint(Comparable<Double> startPoint) {
        if (values == null) 
            return;

        Double startMass = (Double) startPoint;

        Iterator<Double> iterator = values.iterator();
        while (iterator.hasNext()) {
            Double meteorite = iterator.next();
            Double massInGrams = meteorite.doubleValue();

            if (massInGrams.compareTo(startMass) >= 0) {
                iterator = values.iterator();
                break;
            }
        }
    }
}
