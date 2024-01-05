import java.util.Scanner;



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

public class FrontendClass {
    public boolean running;
    private BackendClass backend;
    private Scanner input;

    public FrontendClass(BackendClass backend, Scanner input) {
        this.backend = backend;
        this.input = input;
    }

    public void openApp() {
        running = true;
        System.out.println("Welcome to Meteorite Finder.");
        userPromptMain();
    }
    
    public void userPromptMain() {
        while (running) {
            System.out.print(
                    "Choose an option. \n 1. Load data \n 2. List meteorites of max mass \n 3. List meteorites within a mass range \n 4. Exit \n");

            int choice = input.nextInt();
            input.nextLine();

            if (choice == 1) {
                loadDataFile();
            } else if (choice == 2) {
                highestMassMeteoriteList();
            } else if (choice == 3) {
                System.out.println("Enter minimum meteorite mass.");
                double min = input.nextDouble();
                System.out.println("Enter maximum meteorite mass.");
                double max = input.nextDouble();
                meteoriteFinder(min, max);
            } else if (choice == 4) {
                exitApp();
                break;
            }
        }
    }

    public void loadDataFile() {
        System.out.print("Enter file path. \n");
        String path = input.nextLine();
        boolean loadStatus = backend.initialize(path);
        if (loadStatus == true) {
            System.out.println("Data loaded sucessfully");
        } else {
            System.out.println("Data loaded unsucessfully");
        }

    }

    public void highestMassMeteoriteList() {
        System.out.println("Meteorites with the highest mass: ");
        List<Meteorite> meteoritesWithMaxMass = backend.getMeteoritesWithMaxMass();
        for (Meteorite meteorite : meteoritesWithMaxMass) {
            System.out.println(meteorite.getName());
        }
        System.out.println();
    }

    public void meteoriteFinder(double lowerThreshold, double higherThreshold) {
        System.out.println("Meteorites within the given mass: ");
        List<Meteorite> meteoritesWithinMass = backend.getMeteoritesByMassRange(lowerThreshold,
                higherThreshold);
        for (Meteorite meteorite : meteoritesWithinMass) {
            System.out.println(meteorite.getName());
        }
        System.out.println();
    }

    public void exitApp() {
        running = false;
    }

    public static void main(String[] args) {
        new FrontendClass(new BackendClass(new IterableMultiKeyRBT<>()), new Scanner(System.in))
                .openApp();

    }

}
