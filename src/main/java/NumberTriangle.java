import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This is the provided NumberTriangle class to be used in this coding task.
 *
 * Note: This is like a tree, but some nodes in the structure have two parents.
 *
 * The structure is shown below. Observe that the parents of e are b and c, whereas
 * d and f each only have one parent. Each row is complete and will never be missing
 * a node. So each row has one more NumberTriangle object than the row above it.
 *
 *                  a
 *                b   c
 *              d   e   f
 *            h   i   j   k
 *
 * Also note that this data structure is minimally defined and is only intended to
 * be constructed using the loadTriangle method, which you will implement
 * in this file. We have not included any code to enforce the structure noted above,
 * and you don't have to write any either.
 *
 *
 * See NumberTriangleTest.java for a few basic test cases.
 *
 * Extra: If you decide to solve the Project Euler problems (see main),
 *        feel free to add extra methods to this class. Just make sure that your
 *        code still compiles and runs so that we can run the tests on your code.
 *
 */
public class NumberTriangle {

    private int root;

    private NumberTriangle left;
    private NumberTriangle right;

    public NumberTriangle(int root) {
        this.root = root;
    }

    public void setLeft(NumberTriangle left) {
        this.left = left;
    }


    public void setRight(NumberTriangle right) {
        this.right = right;
    }

    public int getRoot() {
        return root;
    }


    /**
     * [not for credit]
     * Set the root of this NumberTriangle to be the max path sum
     * of this NumberTriangle, as defined in Project Euler problem 18.
     * After this method is called, this NumberTriangle should be a leaf.
     *
     * Hint: think recursively and use the idea of partial tracing from first year :)
     *
     * Note: a NumberTriangle contains at least one value.
     */
    public void maxSumPath() {
        // for fun [not for credit]:
    }


    public boolean isLeaf() {
        return right == null && left == null;
    }


    /**
     * Follow path through this NumberTriangle structure ('l' = left; 'r' = right) and
     * return the root value at the end of the path. An empty string will return
     * the root of the NumberTriangle.
     *
     * You can decide if you want to use a recursive or an iterative approach in your solution.

Read the provided NumberTriange.java file to understand the definition of a number triangle and what methods and variables we have defined to represent the structure.
     *
     * You can assume that:
     *      the length of path is less than the height of this NumberTriangle structure.
     *      each character in the string is either 'l' or 'r'
     *
     * @param path the path to follow through this NumberTriangle
     * @return the root value at the location indicated by path
     *
     */
    public int retrieve(String path) {
        if (Objects.equals(path, "")) {
            return root;
        } else {
            NumberTriangle currentTree = this;
            System.out.println(root);

            for (int i = 0; i < path.length(); i++) {
                char c = path.charAt(i);

                if (c == 'l') {
                    currentTree = currentTree.left;
                } else if (c == 'r') {
                    currentTree = currentTree.right;
                }
            }
            return currentTree.getRoot();
        }
    }

    /** Read in the NumberTriangle structure from a file.
     *
     * You may assume that it is a valid format with a height of at least 1,
     * so there is at least one line with a number on it to start the file.
     *
     * See resources/input_tree.txt for an example NumberTriangle format.
     *
     * @param fname the file to load the NumberTriangle structure from
     * @return the topmost NumberTriangle object in the NumberTriangle structure read from the specified file
     * @throws IOException may naturally occur if an issue reading the file occurs
     */
    public static NumberTriangle loadTriangle(String fname) throws IOException {
        // open the file and get a BufferedReader object whose methods
        // are more convenient to work with when reading the file contents.
        InputStream inputStream = NumberTriangle.class.getClassLoader().getResourceAsStream(fname);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        ArrayList<ArrayList<Integer>> triangleNums = new ArrayList<>();

        // will need to return the top of the NumberTriangle,
        // so might want a variable for that.
        NumberTriangle top = null;

        String line = br.readLine();
        while (line != null) {
            ArrayList<Integer> triangleRow = new ArrayList<>();
            String[] split = line.split(" ");
            for (String num : split) {
                triangleRow.add(Integer.parseInt(num));
            }
            if (triangleRow.size() == 1) {
                top = new NumberTriangle(triangleRow.get(0));
            }
            triangleNums.add(triangleRow);

            //read the next line
            line = br.readLine();
        }
        br.close();
        assert top != null;
        top.generateTriangle(triangleNums);
        return top;
    }

    // Recursive Helper
    public void generateTriangle(ArrayList<ArrayList<Integer>> triangleNums) {

        ArrayList<NumberTriangle> currLayer = new ArrayList<NumberTriangle>();
        currLayer.add(this);

        for (int rowNum = 0; rowNum < triangleNums.size() - 1; rowNum++) {
            ArrayList<Integer> currRow = triangleNums.get(rowNum);
            int rowSize = currRow.size();

            ArrayList<Integer> nextRow = triangleNums.get(rowNum + 1);
            NumberTriangle holdover = null;

            ArrayList<NumberTriangle> newCurrLayer = new ArrayList<NumberTriangle>();

            for (int i = 0; i < rowSize; i++) {
                // Get current
                NumberTriangle rootTriangle = currLayer.get(i);

                // Generate left
                NumberTriangle leftTriangle;
                if (i == 0) {
                    leftTriangle = new NumberTriangle(nextRow.get(i));
                } else {
                    leftTriangle = holdover;
                }

                // Generate right
                NumberTriangle rightTriangle = new NumberTriangle(nextRow.get(i + 1));

                // Set left, right
                rootTriangle.setLeft(leftTriangle);
                rootTriangle.setRight(rightTriangle);

                if (i < rowSize - 1) {
                    holdover = rightTriangle;
                }

                if (i == 0) {
                    newCurrLayer.add(leftTriangle);
                }
                newCurrLayer.add(rightTriangle);
            }

            // Replace currLayer
            currLayer.clear();
            currLayer.addAll(newCurrLayer);
        }
    }

    public static void main(String[] args) throws IOException {

        NumberTriangle mt = NumberTriangle.loadTriangle("input_tree.txt");

        // [not for credit]
        // you can implement NumberTriangle's maxPathSum method if you want to try to solve
        // Problem 18 from project Euler [not for credit]
        mt.maxSumPath();
        System.out.println(mt.getRoot());
    }
}
