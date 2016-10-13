import java.util.Objects;

class HelpFunctions {


    boolean checkForThreats(Board board) {
        return checkForThreatsVertically(board) || checkForThreatsDiagonally(board);
    }

    boolean checkForThreatsByPosition(Board board, int xValue, int yValue) {
        return checkForThreatsVerticallyByPosition(board, yValue) || checkForThreatsDiagonallyByPosition(board, xValue, yValue);
    }

    private boolean checkIfSamePosition(int x1Value, int y1Value, int x2Value, int y2Value) {
        return x1Value == x2Value && y1Value == y2Value;
    }

    private boolean checkForThreatsVertically(Board board) {
        for (int i = 0; i < board.getBoardSize(); i++) {
            int queen = board.getBoard().get(i);
            board.removeQueen(i);
            if (board.getBoard().contains(queen) && queen != 0) {
                board.setQueen(i, queen);
                return true;
            }
            board.setQueen(i, queen);

        }
        return false;
    }

    private boolean checkForThreatsVerticallyByPosition(Board board, int yValue) {
        return board.getBoard().contains(yValue);
    }

    private boolean checkForThreatsDiagonally(Board board) {
        for (int i = 0; i < board.getBoardSize() - 1; i++) {
            for (int j = i; j < board.getBoardSize() - 1; j++) {
                if (board.getBoard().get(i) > 0 && board.getBoard().get(j + 1) > 0) {
                    int deltaRow = j + 1 - i;
                    int deltaCol = board.getBoard().get(j + 1) - board.getBoard().get(i);
                    if (Math.abs(deltaCol) == Math.abs(deltaRow)) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    private boolean checkForThreatsDiagonallyByPosition(Board board, int xValue, int yValue) {
        for (int i = 0; i < board.getBoardSize(); i++) {
            if (!checkIfSamePosition(xValue, yValue, i, board.getBoard().get(i)) && board.getBoard().get(i) != 0) {
                int deltaRow = xValue - i;
                int deltaCol = yValue - board.getBoard().get(i);
                if (Math.abs(deltaCol) == Math.abs(deltaRow)) {
                    return true;
                }
            }
        }
        return false;
    }

    int checkNumberOfThreats(Board board) {
        int numberOfThreats = 0;
        for (int i = 0; i < board.getBoardSize() - 1; i++) {
            for (int j = i; j < board.getBoardSize() - 1; j++) {
                if (board.getBoard().get(i) > 0 && board.getBoard().get(j + 1) > 0) {
                    int deltaRow = j + 1 - i;
                    int deltaCol = board.getBoard().get(j + 1) - board.getBoard().get(i);
                    if (Math.abs(deltaCol) == Math.abs(deltaRow)) {
                        numberOfThreats += 1;
                    }
                }
            }
            for (int j = i + 1; j < board.getBoardSize(); j++) {
                if (Objects.equals(board.getBoard().get(i), board.getBoard().get(j))) {
                    numberOfThreats += 1;
                }
            }
        }
        return numberOfThreats;
    }

    int getNumberOfQueensInSameRow(Board board) {
        Board board1 = board.copyBoard();
        int numbs = 0;
        int counter = 0;
        for (Integer queen : board1.getBoard()) {
            int queen1 = queen;
            board1.removeQueen(counter);
            if (board1.getBoard().contains(queen1)) {
                numbs += 1;
            }
            counter += 1;
        }
        return numbs;
    }

}
