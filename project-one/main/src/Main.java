import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which task do you want to test?");
        int task = scanner.nextInt();
        int problemSize = 8;
        if (task > 1) {
            System.out.println("How big do you want the board to be?");
            problemSize = scanner.nextInt();
        }
        System.out.println("Please fill in the starting position of the queens");
        ArrayList<Integer> positions = new ArrayList<>();
        while (scanner.hasNext()) {
            int checker = scanner.nextInt();
            if (checker != 0) {
                positions.add(checker);
            } else break;
        }

        long startTime = System.currentTimeMillis();
        Board board = new Board(problemSize, positions);
        if (task == 1 || task == 2) {
            Backtracking backtracking = new Backtracking();
            backtracking.backtrack(positions.size(), task, board);
            if (task == 2 && !backtracking.getBoardSolutions().isEmpty()) {
                backtracking.printBoards();
            }
        } else if (task == 3) {
            SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing();
            simulatedAnnealing.simulatedAnnealing(board);
        } else if (task == 4) {
            TabuSearch tabuSearch = new TabuSearch();
            tabuSearch.tabuSearch(board);
        } else if (task == 5) {
            GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
            geneticAlgorithm.geneticAlgorithm(board);
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Time in milliseconds: " + totalTime);
    }
}
