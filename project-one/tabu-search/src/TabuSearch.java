import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

class TabuSearch {

    private static int maxIterations = 10000;
    private static int maxSizeForTabuList = 50;
    private Random random;
    private HelpFunctions helpFunctions;

    TabuSearch() {
        this.random = new Random();
        this.helpFunctions = new HelpFunctions();

    }

    private ArrayList<Board> generateNeighbors(Board currentBoard) {
        ArrayList<Board> neighbors = new ArrayList<>();
        for (int i = 0; i < currentBoard.getBoardSize(); i++) {
            for (int j = 1; j < currentBoard.getBoardSize() / 3.5; j++) {
                if (currentBoard.getBoard().get(i) - j > 0) {
                    Board newBoard = currentBoard.copyBoard();
                    newBoard.setQueen(i, currentBoard.getBoard().get(i) - j);
                    neighbors.add(newBoard);
                }
                if (currentBoard.getBoard().get(i) + j < currentBoard.getBoardSize() + 1) {
                    Board newBoard = currentBoard.copyBoard();
                    newBoard.setQueen(i, currentBoard.getBoard().get(i) + j);
                    neighbors.add(newBoard);
                }
            }
            neighbors.add(swapToGenerateNeighbor(currentBoard));
        }
        return neighbors;
    }

    private Board swapToGenerateNeighbor(Board board) {
        Board newBoard = board.copyBoard();
        int firstIndex = random.nextInt(board.getBoardSize());
        int secondIndex = random.nextInt(board.getBoardSize());
        int queen = newBoard.getBoard().get(firstIndex);
        newBoard.setQueen(firstIndex, newBoard.getBoard().get(secondIndex));
        newBoard.setQueen(secondIndex, queen);
        return newBoard;
    }

    void tabuSearch(Board initialBoard) {
        Board currentBoard = initialBoard;
        ArrayList<String> tabuList = new ArrayList<>();
        HashSet<String> solutions = new HashSet<>();

        int iterations = 0;
        while (iterations < maxIterations) {
            currentBoard.printBoard();
            ArrayList<Board> neighbors = generateNeighbors(currentBoard);
            Board bestCandidate = new Board(initialBoard.getBoardSize(), new ArrayList<>());
            for (Board candidate : neighbors) {
                if ((!tabuList.contains(candidate.toString())) && (helpFunctions.checkNumberOfThreats(candidate) < helpFunctions.checkNumberOfThreats(bestCandidate))) {
                    bestCandidate = candidate;
                }
            }
            currentBoard = bestCandidate;

            tabuList.add(bestCandidate.toString());
            if (tabuList.size() > maxSizeForTabuList) {
                tabuList.remove(0);
            }
            if (helpFunctions.checkNumberOfThreats(bestCandidate) == 0) {
                solutions.add(bestCandidate.toString());
            }
            iterations += 1;
        }
        System.out.println("Solutions: " + solutions);
        System.out.println("Number of solutions: " + solutions.size());    }

}
