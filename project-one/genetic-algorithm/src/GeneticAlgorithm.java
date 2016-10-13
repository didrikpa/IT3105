
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class GeneticAlgorithm {

    private static int maxPopulation = 50;
    private static int maxGenerations = 400;
    private static double mutationProbability = 0.3;
    private static double crossoverRatio = 0.5;

    private HelpFunctions helpFunctions;
    private Random random;


    GeneticAlgorithm() {
        this.random = new Random();
        this.helpFunctions = new HelpFunctions();
    }

    private ArrayList<Board> generateInititalPopulation(Board initialBoard) {
        ArrayList<Board> initialPopulation = new ArrayList<>();
        for (int i = 0; i < maxPopulation; i++) {
            initialPopulation.add(createNeighbor(initialBoard));
        }
        return initialPopulation;
    }
    private Board createNeighbor(Board board) {
        Board neighbor = board.copyBoard();
        if (helpFunctions.getNumberOfQueensInSameRow(neighbor) == 0) {
            for (int i = 0; i < 3; i++) {
                swapMutation(neighbor);
            }
            return neighbor;
        } else {
            while (helpFunctions.getNumberOfQueensInSameRow(neighbor) > 0) {
                ArrayList<Integer> missingQueens = new ArrayList<>();
                for (int i = 0; i < neighbor.getBoardSize(); i++) {
                    if (!neighbor.getBoard().contains(i + 1)) {
                        missingQueens.add(i + 1);
                    }
                }
                for (int i = 0; i < neighbor.getBoardSize(); i++) {
                    if (missingQueens.size() == 0) {
                        break;
                    }
                    int queen = neighbor.getBoard().get(i);
                    neighbor.removeQueen(i);
                    if (neighbor.getBoard().contains(queen)) {
                        int rand = random.nextInt(missingQueens.size());
                        neighbor.setQueen(i, missingQueens.get(rand));
                        missingQueens.remove(rand);
                    } else {
                        neighbor.setQueen(i, queen);
                    }
                }
            }
            swapMutationWithIndexes(neighbor, neighbor.getBoardSize() - 1, random.nextInt(neighbor.getBoardSize()));
        }
        return neighbor;
    }

    private void swapMutationWithIndexes(Board board, int firstIndex, int secondIndex) {
        int queen = board.getBoard().get(firstIndex);
        board.setQueen(firstIndex, board.getBoard().get(secondIndex));
        board.setQueen(secondIndex, queen);

    }

    private void swapMutation(Board board) {
        int firstIndex = random.nextInt(board.getBoardSize());
        int secondIndex = random.nextInt(board.getBoardSize());
        swapMutationWithIndexes(board, firstIndex, secondIndex);

    }

    private ArrayList<Board> selectionProcess(ArrayList<Board> currentPopulation) {
        Collections.sort(currentPopulation, (o1, o2) -> helpFunctions.checkNumberOfThreats(o1) - helpFunctions.checkNumberOfThreats(o2));
        ArrayList<Board> parents = new ArrayList<>();
        for (int i = 0; i < maxPopulation * 0.07; i++) {
            parents.add(currentPopulation.get(i));
        }
        for (int i = 0; i < maxPopulation * 0.03; i++) {
            parents.add(currentPopulation.get(random.nextInt(maxPopulation)));
        }
        return parents;
    }

    private ArrayList<Board> generateNextGeneration(ArrayList<Board> parents) {
        ArrayList<Board> newGeneration = new ArrayList<>();
        for (int i = 0; i < maxPopulation; i++) {
            int firstParentIndex = random.nextInt(parents.size());
            int secondParentIndex = random.nextInt(parents.size());
            while (secondParentIndex == firstParentIndex) {
                secondParentIndex = random.nextInt(parents.size());
            }
            newGeneration.add(generateChildFromParents(parents.get(firstParentIndex), parents.get(secondParentIndex)));
        }
        return newGeneration;
    }

    private Board generateChildFromParents(Board parent1, Board parent2) {
        Board child = new Board(parent1.getBoardSize(), new ArrayList<>());
        ArrayList<Integer> queens = new ArrayList<>();
        for (int i = 0; i < child.getBoardSize(); i++) {
            queens.add(i + 1);
        }
        double mutationRate = random.nextDouble();
        for (int i = 0; i < child.getBoardSize(); i++) {
            double rand = random.nextDouble();
            if (rand > crossoverRatio && (!child.getBoard().contains(parent2.getBoard().get(i)))) {
                child.setQueen(i, parent2.getBoard().get(i));
                queens.remove(queens.indexOf(parent2.getBoard().get(i)));
            } else if (rand <= crossoverRatio && (!child.getBoard().contains(parent1.getBoard().get(i)))) {
                child.setQueen(i, parent1.getBoard().get(i));
                queens.remove(queens.indexOf(parent1.getBoard().get(i)));
            } else {
                int r = random.nextInt(queens.size());
                child.setQueen(i, queens.get(r));
                queens.remove(r);
            }
        }
        if (mutationRate < mutationProbability) {
            for (int i = 0; i < 3; i++) {
                swapMutation(child);
            }
        }
        return child;
    }


    void geneticAlgorithm(Board initialBoard) {
        int generation = 0;
        ArrayList<Board> population = generateInititalPopulation(initialBoard);
        ArrayList<String> solutions = new ArrayList<>();
        while (generation < maxGenerations) {
            System.out.println("GENERATION " + generation);
            population.forEach(Board::printBoard);
            population = generateNextGeneration(selectionProcess(population));
            for (Board aPopulation : population) {
                if (!helpFunctions.checkForThreats(aPopulation) && !solutions.contains(aPopulation.toString())) {
                    System.out.println(aPopulation.toString());
                    solutions.add(aPopulation.toString());
                }
            }
            generation += 1;
        }
        System.out.println("Solutions: " + solutions);
        System.out.println("Number of solutions: " + solutions.size());
    }
}
