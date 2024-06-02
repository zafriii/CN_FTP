import java.io.File;
import java.util.ArrayList;

public class ListFiles {

  public static void main(String[] args) {
    String directoryPath = "files";
    File directory = new File(directoryPath);


    if (directory.exists() && directory.isDirectory()) {

      ArrayList<String> filenames = new ArrayList<>();
      File[] files = directory.listFiles();

      for (File file : files) {
        filenames.add(file.getName());
      }

    } else {
      System.out.println("Error: Directory not found or not readable.");
    }
  }
}
