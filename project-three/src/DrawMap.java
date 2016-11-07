import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.util.ArrayList;

class DrawMap {

    void drawScatterMap(ArrayList<Vector> stateOfCities, ArrayList<Vector> neurons) {
        XYChart chart = new XYChartBuilder().width(500).height(500).title("TSP").xAxisTitle("X").yAxisTitle("Y").build();

        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setChartTitleVisible(false);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.getStyler().setMarkerSize(8);

        ArrayList<Double> xValuesInNeurons = new ArrayList<>();
        ArrayList<Double> yValuesInNeurons = new ArrayList<>();

        ArrayList<Double> xValuesInCities = new ArrayList<>();
        ArrayList<Double> yValuesInCities = new ArrayList<>();

        for (Vector neuron1 : neurons) {
            xValuesInNeurons.add(neuron1.getxValue());
            yValuesInNeurons.add(neuron1.getyValue());
        }
        xValuesInNeurons.add(neurons.get(0).getxValue());
        yValuesInNeurons.add(neurons.get(0).getyValue());

        for (Vector stateOfCity : stateOfCities) {
            xValuesInCities.add(stateOfCity.getxValue());
            yValuesInCities.add(stateOfCity.getyValue());
        }


        XYSeries neuron = chart.addSeries("Neurons", xValuesInNeurons, yValuesInNeurons);
        XYSeries cities = chart.addSeries("Cities", xValuesInCities, yValuesInCities);

        cities.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        neuron.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        cities.setMarker(SeriesMarkers.TRIANGLE_DOWN);


        new SwingWrapper(chart).displayChart();


    }


}
