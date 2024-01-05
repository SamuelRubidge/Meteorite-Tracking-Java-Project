import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BackendClass extends BackendPlaceholder implements BackendInterface {

    private IterableMultiKeyRBT<Meteorite> dataCollection;

    public BackendClass() {}

    public BackendClass(String dataFilePath) {
        initialize(dataFilePath);
    }

    public BackendClass(IterableMultiKeyRBT<Meteorite> dataCollection) {
        this.dataCollection = dataCollection;
    }

    public boolean initialize(String data) {
        File file = new File(data);
        if (!file.exists() || !file.isFile()) {
            System.err.println("Invalid file: " + file.getAbsolutePath());
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean dataInserted = false;
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                lineCount++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length < 11) {
                    System.err.println("Skipping invalid line at line " + lineCount + ": " + line);
                    continue;
                }
                if (fields.length == 11) {
                    try {
                        String name = fields[0];
                        double latitude = Double.parseDouble(fields[7]);
                        double longitude = Double.parseDouble(fields[8]);
                        boolean fall;
                        if(fields[5].equals("Fell"))
                            fall = true;
                        else
                            fall = false;
                        double massInGrams = Double.parseDouble(fields[4]);
                        dataCollection.insertSingleKey(new MeteoriteImpl(name, latitude, longitude, fall, massInGrams));
                        dataInserted = true;
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Error parsing data at line " + lineCount + ": " + line);
                    }
                    
                }
                if (fields.length == 12) {
                    try {
                        String name = fields[0];
                        double latitude = Double.parseDouble(fields[8]);
                        double longitude = Double.parseDouble(fields[9]);
                        boolean fall;
                        if(fields[6].equals("Fell"))
                            fall = true;
                        else
                            fall = false;
                        double massInGrams = Double.parseDouble(fields[5]);
                        dataCollection.insertSingleKey(new MeteoriteImpl(name, latitude, longitude, fall, massInGrams));
                        dataInserted = true;
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                        System.err.println("Error parsing data at line " + lineCount + ": " + line);
                    }
                }
            }

            return dataInserted;
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return false;
        }

    }

    public List<Meteorite> getMeteoritesWithMaxMass() {
        List<Meteorite> meteoritesWithMaxMass = new ArrayList<>();

        double maxMass = Double.MIN_VALUE;
        for (Meteorite meteorite : dataCollection) {
            if (meteorite.getMassInGrams() > maxMass) {
                maxMass = meteorite.getMassInGrams();
            }
        }
        for (Meteorite meteorite : dataCollection) {
            if (meteorite.getMassInGrams() == maxMass) {
                meteoritesWithMaxMass.add(new MeteoriteImpl(meteorite.getName(), meteorite.getLatitude(),
                        meteorite.getLongitude(), meteorite.getFall(), meteorite.getMassInGrams()));
            }
        }
        return meteoritesWithMaxMass;
    }

    public List<Meteorite> getMeteoritesByMassRange(double minMass, double maxMass) {
        List<Meteorite> meteoritesByMassRange = new ArrayList<>();

        for (Meteorite meteorite : dataCollection) {
            if (meteorite.getMassInGrams() >= minMass && meteorite.getMassInGrams() <= maxMass) {
                meteoritesByMassRange.add(new MeteoriteImpl(meteorite.getName(), meteorite.getLatitude(),
                        meteorite.getLongitude(), meteorite.getFall(), meteorite.getMassInGrams()));
            }
        }
        return meteoritesByMassRange;
    }
}
