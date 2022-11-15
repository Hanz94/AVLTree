import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        AVLTree avlTree = null;
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new FileReader("input.txt"));
            writer = new BufferedWriter(new FileWriter("output.txt", false));
            String line = reader.readLine();
            while (line != null) {
                avlTree = decodeAndRunCommands(line, avlTree, writer);
                line = reader.readLine();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AVLTree decodeAndRunCommands(String command, AVLTree avlTree, BufferedWriter writer) throws IOException {
        String[] cmd = command.split("[\\(\\)]");
        switch(cmd[0]) {
            case "Initialize":
                avlTree = AVLTree.initialize();
            break;
            case "Insert":
                avlTree.insert(Integer.parseInt(cmd[1]));
                break;
            case "Delete":
                avlTree.delete(Integer.parseInt(cmd[1]));
                break;
            case "Search":
                String[] range = cmd[1].split(",");
                if(range.length > 1){
                    List<Integer> results = avlTree.searchRange(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
                    if (!results.isEmpty()){
                        writer.write(results.toString().replaceAll("[\\[\\] ]", ""));
                    }else {
                        writer.write("NULL");
                    }
                }else {
                    Integer result = avlTree.search(Integer.parseInt(cmd[1]));
                    if(result != null){
                        writer.write(result.toString());
                    }else {
                        writer.write("NULL");
                    }
                }
                writer.newLine();
                break;
            default:
                throw new RuntimeException(cmd[0] + " is not supported, only Initialize, Insert, Delete and Search are supported.");
        }
        return avlTree;
    }

    public static void testAVLTree(){
        AVLTree testAvlTree = AVLTree.initialize();
        testAvlTree.insert(4);
        testAvlTree.insert(3);
        testAvlTree.insert(5);
        testAvlTree.insert(1);
        testAvlTree.insert(10);
        System.out.println(testAvlTree);

        testAvlTree.insert(20);
        System.out.println(testAvlTree);

        testAvlTree.insert(30);
        System.out.println(testAvlTree);

        testAvlTree.delete(3);
        System.out.println(testAvlTree);

        testAvlTree.delete(4);
        System.out.println(testAvlTree);

        testAvlTree.insert(4);
        System.out.println(testAvlTree);

        System.out.println(testAvlTree.search(10));
        System.out.println(testAvlTree.search(11));

        System.out.println(testAvlTree.searchRange(2, 6));
        System.out.println(testAvlTree.searchRange(10, 20));
    }

}
