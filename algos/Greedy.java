package algos;
import java.util.Set;
import java.util.TreeSet;

import game.Moves;
import game.Board;

import java.util.PriorityQueue;
import java.util.Stack;
import java.util.Comparator;

public class Greedy {
    private int size;
    private Board root;
    private int[][] finalState;
    private int heur;
    private Set<Board> explored;
    private PriorityQueue<Board> PQ = new PriorityQueue<>(Comparator.comparingInt(this::heuristic));

    public int heuristic(Board a) {
        int[] board[] = a.getBoard();
        int count = 0;
        if (heur == 1) {
            // sum of squares in the wrong place
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] != finalState[i][j])
                        count++;
                }
            }
            return count;
        } else {
            // manhattan distance
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] != finalState[i][j]) {
                        for (int k = 0; k < size; k++) {
                            for (int l = 0; l < size; l++) {
                                if (board[i][j] == finalState[k][l]) {
                                    count += Math.abs(i - k) + Math.abs(j - l);
                                }
                            }
                        }
                    }
                }
            }

            return count;
        }
    }

    public Greedy(Board initBoard, int h, int[][] fs) {
        size = initBoard.getSize();
        root = new Board(initBoard.getBoard(), initBoard.getSize());
        finalState = fs;
        heur = h;
    }

    public Stack<Board> solve() {
        explored = new TreeSet<Board>();

        if (root.isFinished())
            return Board.rewind(root);

        PQ.offer(root);

        System.out.println("Beginning Greedy Search\n");

        while (!PQ.isEmpty()) {
            Board currState = PQ.remove();

            for (Moves move : Moves.values()) {
                Board newNode = new Board(currState.getBoard(), size);
                newNode = newNode.move(move);

                if (newNode == null)
                    continue;

                newNode.setParent(currState);

                if (newNode.isFinished())
                    return Board.rewind(newNode);

                if (!explored.contains(newNode) && !PQ.contains(newNode)) {
                    explored.add(newNode);
                    PQ.offer(newNode);
                }

            }

        }
        return null;
    }
}