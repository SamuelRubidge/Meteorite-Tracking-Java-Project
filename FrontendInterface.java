import java.util.Scanner;

public interface FrontendInterface {

    public void openApp();

    public void userPromptMain(Scanner userMethodChoice);

    public void loadDataFile(Scanner data);

    public void highestMassMeteoriteList();

    public void meteoriteFinder(double lowerThreshold, double higherThreshold);

    public void exitApp();

}
