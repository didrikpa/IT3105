import java.util.ArrayList;

class Backtracking {

    private ArrayList<Board> boardSolutions = new ArrayList<>();
    private int numberOfBacktracks;
    private HelpFunctions helpFunctions;
    private long startTime;


    Backtracking() {
        this.helpFunctions = new HelpFunctions();
        this.numberOfBacktracks = 0;
        this.startTime = System.currentTimeMillis();
    }

    void printBoards() {
        //boardSolutions.forEach(Board::printBoard);
        System.out.println("Number of solutions: " + boardSolutions.size());
    }

    ArrayList<Board> getBoardSolutions() {
        return this.boardSolutions;
    }

    boolean backtrack(int numberOfQueensPlaced, int task, Board board) {
        //board.printBoard();
        if (numberOfBacktracks == 0) {
            if (helpFunctions.checkForThreats(board)) {
                return false;
            }
        }
        numberOfBacktracks += 1;
        if (numberOfQueensPlaced == board.getBoard().size()) {
            if (task == 1) {
                return true;
            } else if (task == 2) {
                Board newBoard = board.copyBoard();
                boardSolutions.add(newBoard);
            }
        } else {
            ArrayList<Integer> validMoves = new ArrayList<>();
            for (int i = 0; i < board.getBoardSize(); i++) {
                if (!helpFunctions.checkForThreatsByPosition(board, numberOfQueensPlaced, i + 1)) {
                    validMoves.add(i);
                }
            }
            if (validMoves.size() > 0) {
                for (Integer validMove : validMoves) {
                    board.setQueen(numberOfQueensPlaced, validMove + 1);
                    if (backtrack(numberOfQueensPlaced + 1, task, board)) {
                        return true;
                    } else {
                        board.removeQueen(numberOfQueensPlaced);
                    }
                }
            }
        }
        if (boardSolutions.size() == 100000){
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Time in milliseconds: " + totalTime);
        }
        return false;
    }
}