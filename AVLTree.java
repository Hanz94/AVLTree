import java.io.*;
import java.util.List;

/**
 * This program, contains a small <a href="https://en.wikipedia.org/wiki/AVL_tree">AVL Tree</a>.
 * AVLTree class is the entrypoint of the program, it handle input output file operations and decoding of the input file.
 */
public class AVLTree {

    /**
     * This is the main method of the program.
     * @param args takes one command line argument for input file, defaults to "input_file.txt" if not specified.
     * This method call decodeAndRunCommands() to decode the input file line by line run respective commands on AVL tree.
     * The final output is written to "output_file.txt".
     */
    public static void main(String[] args) {
        String filename = "input_file.txt";
        if (args.length > 0) {
            filename = args[0];
        }
        AVLBST avlBinaryTree = null;
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

    /**
     * Takes one command and run the respective operation on AVL tree. Supported commands are Initialize, Insert, Delete and Search.
     * @param command Command to run as a string ( eg -: Initialize(), Insert (60), Delete(23), Search(23), Search(2,10)).
     * @param avlBinaryTree The AVL tree before the operation (defaults to null at the start).
     * @param writer BufferedWriter representing the output file.
     * @return Returns the AVL tree after the operation.
     * @throws IOException Signals that an I/O exception of some sort has occurred regarding output file (eg -: output file is not writable)
     */
    public static AVLBST decodeAndRunCommands(String command, AVLBST avlBinaryTree, BufferedWriter writer) throws IOException {
        String[] cmd = command.split("[\\(\\)]");
        switch(cmd[0]) {
            case "Initialize":
                avlBinaryTree = AVLBST.initialize();
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

    /**
     * This method contains test cases to evaluate the implementation. Not called by the main program by default.
     */
    public static void testAVLTree(){
        AVLBST testAvlBinaryTree = AVLBST.initialize();
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
