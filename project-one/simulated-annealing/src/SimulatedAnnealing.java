
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class SimulatedAnnealing {

    private static double temperature = 37;

    private Random random;
    private HelpFunctions helpFunctions;

    public SimulatedAnnealing() {
        this.random = new Random();
        this.helpFunctions = new HelpFunctions();

    }

    private ArrayList<Board> generateNeighbors(Board initialBoard) {
        ArrayList<Board> neighbors = new ArrayList<>();
        for (int i = 0; i < initialBoard.getBoardSize(); i++) {
            for (int j = 1; j < initialBoard.getBoardSize() / 3.5
                    ; j++) {
                if (initialBoard.getBoard().get(i) - j > 0) {
                    Board newBoard = initialBoard.copyBoard();
                    newBoard.setQueen(i, initialBoard.getBoard().get(i) - j);
                    neighbors.add(newBoard);
                }
                if (initialBoard.getBoard().get(i) + j < initialBoard.getBoardSize() + 1) {
                    Board newBoard = initialBoard.copyBoard();
                    newBoard.setQueen(i, initialBoard.getBoard().get(i) + j);
                    neighbors.add(newBoard);
                }
            }
            neighbors.add(swap(initialBoard));
        }
        return neighbors;
    }

    private Board swap(Board board) {
        Board newBoard = board.copyBoard();
        int firstIndex = random.nextInt(board.getBoardSize());
        int secondIndex = random.nextInt(board.getBoardSize());
        int queen = newBoard.getBoard().get(firstIndex);
        newBoard.setQueen(firstIndex, newBoard.getBoard().get(secondIndex));
        newBoard.setQueen(secondIndex, queen);
        return newBoard;
    }

    private int evaluateBoard(Board board, int maxNumberOfThreats) {
        return maxNumberOfThreats - helpFunctions.checkNumberOfThreats(board);
    }

    private int countMaxNumberOfThreats(int boardSize) {
        int maxNumberOfThreats = 0;
        for (int i = 0; i < boardSize; i++) {
            maxNumberOfThreats += i;
        }
        return maxNumberOfThreats;
    }


    void simulatedAnnealing(Board initialBoard) {
        int maxNumberOfThreats = countMaxNumberOfThreats(initialBoard.getBoardSize());
        HashSet<String> solutions = new HashSet<>();
        int numberOfIterations = 0;
        while (numberOfIterations < 100000) {
            initialBoard.printBoard();
            ArrayList<Board> neighbors = generateNeighbors(initialBoard);
            Board boardToBeTested;
            boardToBeTested = neighbors.get(random.nextInt(neighbors.size()));
            int deltaEnergy = evaluateBoard(initialBoard, maxNumberOfThreats) - evaluateBoard(boardToBeTested, maxNumberOfThreats);
            if (deltaEnergy <= 0) {
                initialBoard = boardToBeTested;
            } else {
                double probability = Math.exp(-(deltaEnergy / (temperature)));
                if (probability > random.nextDouble()) {
                    initialBoard = boardToBeTested;
                }
            }
            if (helpFunctions.checkNumberOfThreats(initialBoard) == 0) {
                solutions.add(initialBoard.toString());
            }
            numberOfIterations += 1;
            temperature *= 0.9999;
        }
        System.out.println("Solutions: " + solutions);
        System.out.println("Number of solutions: " + solutions.size());
    }

}
