import java.io.*;
import java.util.List;

/**
 * dtest
 */
public class AVLTree {

    public static void main(String[] args) {
        String filename = "input_file.txt";
        if (args.length > 0) {
            filename = args[0];
        }
        AVLBinaryTree avlBinaryTree = null;
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new FileReader(filename));
            writer = new BufferedWriter(new FileWriter("output_file.txt", false));
            String line = reader.readLine();
            while (line != null) {
                avlBinaryTree = decodeAndRunCommands(line, avlBinaryTree, writer);
                line = reader.readLine();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AVLBinaryTree decodeAndRunCommands(String command, AVLBinaryTree avlBinaryTree, BufferedWriter writer) throws IOException {
        String[] cmd = command.split("[\\(\\)]");
        switch(cmd[0]) {
            case "Initialize":
                avlBinaryTree = AVLBinaryTree.initialize();
            break;
            case "Insert":
                avlBinaryTree.insert(Integer.parseInt(cmd[1]));
                break;
            case "Delete":
                avlBinaryTree.delete(Integer.parseInt(cmd[1]));
                break;
            case "Search":
                String[] range = cmd[1].split(",");
                if(range.length > 1){
                    List<Integer> results = avlBinaryTree.searchRange(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
                    if (!results.isEmpty()){
                        writer.write(results.toString().replaceAll("[\\[\\] ]", ""));
                    }else {
                        writer.write("NULL");
                    }
                }else {
                    Integer result = avlBinaryTree.search(Integer.parseInt(cmd[1]));
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
        return avlBinaryTree;
    }

    public static void testAVLTree(){
        AVLBinaryTree testAvlBinaryTree = AVLBinaryTree.initialize();
        testAvlBinaryTree.insert(4);
        testAvlBinaryTree.insert(3);
        testAvlBinaryTree.insert(5);
        testAvlBinaryTree.insert(1);
        testAvlBinaryTree.insert(10);
        System.out.println(testAvlBinaryTree);

        testAvlBinaryTree.insert(20);
        System.out.println(testAvlBinaryTree);

        testAvlBinaryTree.insert(30);
        System.out.println(testAvlBinaryTree);

        testAvlBinaryTree.delete(3);
        System.out.println(testAvlBinaryTree);

        testAvlBinaryTree.delete(4);
        System.out.println(testAvlBinaryTree);

        testAvlBinaryTree.insert(4);
        System.out.println(testAvlBinaryTree);

        System.out.println(testAvlBinaryTree.search(10));
        System.out.println(testAvlBinaryTree.search(11));

        System.out.println(testAvlBinaryTree.searchRange(2, 6));
        System.out.println(testAvlBinaryTree.searchRange(10, 20));
    }

}
