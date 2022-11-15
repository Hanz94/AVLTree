import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    public static void readInputFile(String filepath){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filepath));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void decodeCommands(String command){
        String[] cmd = command.split("\\(");
        switch(cmd[0]) {
            case "Initialize":
                break;
            case "Insert":
                // code block
                break;
            case "Delete":
                // code block
                break;
            case "Search":
                // code block
                break;
            default:
                // code block
                throw new RuntimeException(cmd[0] + " is not supported, only Initialize, Insert, Delete and Search are supported.");
        }

    }


}
