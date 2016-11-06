import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class SOM {

    private static String map1 = "/Users/didrikpa/IdeaProjects/it3105/project-three/maps/western_sahara.txt";
    private static String map2 = "/Users/didrikpa/IdeaProjects/it3105/project-three/maps/uruguay.txt";
    private static String map3 = "/Users/didrikpa/IdeaProjects/it3105/project-three/maps/qatar.txt";
    private static String map4 = "/Users/didrikpa/IdeaProjects/it3105/project-three/maps/djibouti.txt";

    private int delta = 100000;
    private double radius = 26.0;
    private double learningRate = 0.7;

    private double minX;
    private double maxX;
    private double minY;
    private double maxY;

    private ArrayList<Vector> cities;
    private ArrayList<Vector> neurons;


    public SOM(String filePath) throws IOException {
        this.cities = readMapFile(filePath);
        int numberOfNeurons = (int) Math.floor(cities.size()*3);
        neurons = new ArrayList<>();
        for (int i = 0; i < numberOfNeurons; i++) {
            this.neurons.add(new Vector(Math.random(), Math.random()));
        }
    }

    private double normalizeNumber(double number, double max, double min) {
        return (number - min) / (max - min);
    }

    private double calculateEuclideanDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void updateWeights(Vector weight, double alpha, double theta, Vector cityWeight) {
        weight.setxValue(weight.getxValue() + theta * alpha * (cityWeight.getxValue() - weight.getxValue()));
        weight.setyValue(weight.getyValue() + theta * alpha * (cityWeight.getyValue() - weight.getyValue()));
    }

    private double alpha(int stage, String decayRate) {
        double learningRate = this.learningRate;
        if (decayRate.equals("linear")) {
            learningRate *= 0.7;
        } else if (decayRate.equals("exponential")) {
            learningRate = this.learningRate * Math.pow(Math.E, -0.001 * stage);
        }
        return learningRate;

    }

    private double theta(int indexOfBestMatching, int indexOfCurrent, int stage, String decayRate) {
        double radius = this.radius;
        if (decayRate.equals("linear")) {
            radius *= 0.7;
        } else if (decayRate.equals("exponential")) {
            radius = this.radius * Math.pow(Math.E, -0.01 * stage);
        }
        double numb = Math.abs(indexOfCurrent - indexOfBestMatching);
        if (neurons.size() - numb < numb) {
            numb = neurons.size() - numb;
        }
        if (numb <= radius) {
            return 1;
        } else
            return 0;
    }

    private double calculateDistance() {
        double distance = 0;
        for (int i = 0; i < neurons.size(); i++) {
            if (i == neurons.size() - 1) {
                distance += distanceBetweenTwoNeurons(neurons.get(i), neurons.get(0));
            } else {
                distance += distanceBetweenTwoNeurons(neurons.get(i), neurons.get(i + 1));
            }
        }
        return distance;
    }

    private double distanceBetweenTwoNeurons(Vector n1, Vector n2) {
        return Math.sqrt(Math.pow(denormalizeNumber(n2.getxValue(), minX, maxX) - denormalizeNumber(n1.getxValue(), minX, maxX), 2) + Math.pow(denormalizeNumber(n2.getyValue(), minY, maxY) - denormalizeNumber(n1.getyValue(), minY, maxY), 2));
    }

    private double denormalizeNumber(double number, double min, double max) {
        return number * (max - min) - min;
    }


    private ArrayList<Vector> readMapFile(String filePath) throws IOException {
        ArrayList<Vector> cities = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        String nextReadLineAsString = bufferedReader.readLine();
        boolean collectFromFile = false;
        while (nextReadLineAsString != null) {
            if (nextReadLineAsString.equals("EOF")) {
                collectFromFile = false;
            }
            if (collectFromFile) {
                cities.add(new Vector(Double.parseDouble(nextReadLineAsString.split(" ")[1]), Double.parseDouble(nextReadLineAsString.split(" ")[2])));
            }
            if (nextReadLineAsString.equals("NODE_COORD_SECTION")) {
                collectFromFile = true;
            }
            nextReadLineAsString = bufferedReader.readLine();
        }
        Collections.sort(cities, (o1, o2) -> Double.compare(o1.getxValue(), o2.getxValue()));
        this.minX = cities.get(0).getxValue();

        Collections.sort(cities, (o1, o2) -> Double.compare(o1.getyValue(), o2.getyValue()));
        this.minY = cities.get(0).getyValue();

        Collections.sort(cities, (o1, o2) -> Double.compare(o2.getxValue(), o1.getxValue()));
        this.maxX = cities.get(0).getxValue();

        Collections.sort(cities, (o1, o2) -> Double.compare(o2.getyValue(), o1.getyValue()));
        this.maxY = cities.get(0).getyValue();

        for (Vector vector : cities) {
            vector.setxValue(normalizeNumber(vector.getxValue(), maxX, minX));
            vector.setyValue(normalizeNumber(vector.getyValue(), maxY, minY));
        }
        return cities;
    }

    private void runAlgorithm() {
        DrawMap drawMap = new DrawMap();
        drawMap.drawScatterMap(cities, neurons);
        for (int i = 0; i < delta; i++) {
            double bestNeuron = 1000;
            int bestNeuronIndex = 0;
            int cityIndex = (int) Math.floor(Math.random() * cities.size());
            for (int k = 0; k < neurons.size(); k++) {
                double ced = calculateEuclideanDistance(neurons.get(k).getxValue(), cities.get(cityIndex).getxValue(), neurons.get(k).getyValue(), cities.get(cityIndex).getyValue());
                if (ced < bestNeuron) {
                    bestNeuron = ced;
                    bestNeuronIndex = k;
                }
            }
            for (int l = 0; l < neurons.size(); l++) {
                updateWeights(neurons.get(l), alpha(i, "exponential"), theta(bestNeuronIndex, l, i, "exponential"), cities.get(cityIndex));
            }
        }
        double distance = calculateDistance();
        System.out.println(distance);
        drawMap.drawScatterMap(cities, neurons);
    }

    public static void main(String[] args) throws IOException {
        SOM som = new SOM(map1);
        som.runAlgorithm();
    }
}
