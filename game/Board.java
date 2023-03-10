package game;
import java.util.Stack;

public class Board implements Comparable<Board> {

    private int[] board[];
    private int size;
    private int depth;
    private int zeroX, zeroY;
    private static int[] finalBoard[];
    private Board parent;

    /*--------------------------------------------- */

    public Board(int[][] b, int s) {
        size = s;
        board = new int[size][size];
        finalBoard = new int[size][size];

        // Insert board setup into Board object
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                board[i][j] = b[i][j];
                if (board[i][j] == 0) {
                    zeroY = i;
                    zeroX = j;
                }
            }
        }

        parent = null;
    }

    /*--------------------------------------------- */

    public int[][] getBoard() {

        int[] gBoard[] = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gBoard[i][j] = board[i][j];
            }
        }

        return gBoard;
    }

    /*--------------------------------------------- */

    public int getSize() {
        return size;
    }

    /*--------------------------------------------- */

    public void setParent(Board p) {
        parent = p;
    }

    public Board getParent() {
        return parent;
    }

    /*--------------------------------------------- */

    public void setDepth(int p) {
        depth = p;
    }

    public int getDepth() {
        return depth;
    }

    /*--------------------------------------------- */

    public static void setFinalBoard(int[][] b, int s) {
        finalBoard = new int[s][s];
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                finalBoard[i][j] = b[i][j];
            }
        }
    }

    /*--------------------------------------------- */

    public String printBoard() {

        String s = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                // this if is meant to align the output correctly

                if (board[i][j] <= 9) {
                    s += board[i][j] + "  ";
                } else {
                    s += board[i][j] + " ";
                }
            }
            s += "\n";
        }
        return s;
    }

    /*--------------------------------------------- */

    public String printFinalBoard() {

        String s = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                // this if is meant to align the output correctly

                if (finalBoard[i][j] <= 9) {
                    s += finalBoard[i][j] + "  ";
                } else {
                    s += finalBoard[i][j] + " ";
                }
            }
            s += "\n";
        }
        return s;
    }

    /*--------------------------------------------- */

    public boolean isFinished() {
        if (board[size - 1][size - 1] != 0)
            return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == size - 1 && j == size - 1)
                    continue;
                if (board[i][j] != (i * size + j + 1))
                    return false;
            }
        }
        return true;
    }

    /*--------------------------------------------- */

    public boolean isSolvable()
    {
        return(isSolvable(finalBoard) == isSolvable(board));
    }

    public boolean isSolvable(int[][] br) {

        int[] arr = new int[size * size];
        int inv_count = 0;
        int z = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (br[i][j] == 0) {
                    z = size-i;
                }
                arr[i * size + j] = br[i][j];
            }
        }

        for (int i = 0; i < size * size - 1; i++) {
            for (int j = i + 1; j < size * size; j++) {
                
                if (arr[j] != 0 && arr[i] != 0 && arr[i] > arr[j]) {
                    inv_count++;
                }
            }
        }
        if(size % 2 == 1 && inv_count % 2 == 0)
            return true;
        else{
            if((inv_count % 2 == 0) == (z % 2 == 1))
            return true;
        else
            return false;
        }
        
    }

    /*--------------------------------------------- */

    public Board move(Moves m) {
        int x = zeroX + m.dx;
        int y = zeroY + m.dy;

        if (x < 0 || x > size - 1 || y < 0 || y > size - 1)
            return null; // If invalid

        int[] newBoard[] = getBoard();
        newBoard[zeroY][zeroX] = newBoard[y][x];
        newBoard[y][x] = 0;

        Board newState = new Board(newBoard, size);

        return newState;

    }

    /*--------------------------------------------- */

    public static void printSolution(Stack<Board> stack) {

        if (stack == null) {
            System.out.println("No solution found.");
            return;
        } else {
            int count = -1; // -1 because the initial state is not counted as a move.
            System.out.println("\nSolution Found!\nInitial Position:");
            while (!stack.empty()) {
                Board g = stack.pop();
                System.out.println(g.printBoard());
                count++;
            }
            System.out.println("Number of moves: " + (count));
        }
    }

    /*--------------------------------------------- */

    public static Stack<Board> rewind(Board c) {
        Stack<Board> solution = new Stack<Board>();
        while (c != null) {
            solution.push(c);
            c = c.getParent();
        }
        return solution;
    }

    /*--------------------------------------------- */

    public String getSequence() {
        String v = "";
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                v += board[i][j] + "_";
        return v;
    }

    public int compareTo(Board b) {
        return getSequence().compareTo(b.getSequence());
    }

}
