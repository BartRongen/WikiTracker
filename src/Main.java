import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        WikiTracker tracker = new WikiTracker("Willem-Alexander of the Netherlands", "Buffalo buffalo Buffalo buffalo buffalo buffalo Buffalo buffalo");
        tracker.getRoute();
    }
}
