import java.util.*;
import java.io.*;

public class CheckedExceptions {
    public byte[] readFile(String fileName) throws IOException {
        var file = new File(fileName);
        var is = new FileInputStream(file); // throws IOException
        return is.readAllBytes(); // throws IOException
    }

    public void catchIt() {
        try { var bytes = readFile("file.txt"); }
        catch (IOException exc) { /* Handle error  */ }
    }

    // Caller must catch IOException
    public void declareIt() throws IOException {
        var bytes = readFile("file.txt");
    }
}
