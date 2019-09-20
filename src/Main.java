import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        WikiTracker tracker = new WikiTracker("Willem-Alexander of the Netherlands", "Buffalo buffalo Buffalo buffalo buffalo buffalo Buffalo buffalo");
        //WikiTracker tracker = new WikiTracker("procrastination", "thesis");
        tracker.getRoute();
    }
}
