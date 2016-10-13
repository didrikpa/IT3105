
import java.util.ArrayList;

class Board {

    private ArrayList<Integer> board;
    private double normalizedFitnessValue;

    Board(int size, ArrayList<Integer> position) {
        board = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (i < position.size()) {
                board.add(position.get(i));
            } else {
                board.add(0);
            }
        }
        normalizedFitnessValue = 0;
    }

    double getNormalizedFitnessValue() {
        return normalizedFitnessValue;
    }

    ArrayList<Integer> getBoard() {
        return this.board;
    }

    void setQueen(int xValue, int yValue) {
        board.set(xValue, yValue);
    }

    void removeQueen(int xValue) {
        board.set(xValue, 0);
    }

    int getBoardSize() {
        return board.size();
    }

    Board copyBoard() {
        Board board2 = new Board(board.size(), new ArrayList<>());
        for (int i = 0; i < board.size(); i++) {
            board2.setQueen(i, board.get(i));
        }
        return board2;
    }

    ArrayList<ArrayList<String>> printBoard() {
        System.out.println(board);
        ArrayList<ArrayList<String>> boardRep = new ArrayList<>();
        for (int i = 0; i < board.size(); i++) {
            boardRep.add(new ArrayList<>());
            for (int j = 0; j < board.size(); j++) {
                boardRep.get(i).add(".");
            }
        }
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) != 0) {
                boardRep.get(i).set(board.get(i) - 1, "Q");
            }
        }
        for (int i = 0; i < board.size(); i++) {
            System.out.print("|");
            for (int j = 0; j < board.size(); j++) {
                System.out.print(boardRep.get(i).get(j));
            }
            System.out.println("|");
        }
        return boardRep;
    }

    public String toString() {
        String str = "[";
        for (int i = 0; i < board.size(); i++) {
            int queen = board.get(i);
            if (i <= board.size()-2){
                str += queen + ", ";
            }
            else{
                str += queen;
            }
        }
        str += "]";
        return str;
    }

}