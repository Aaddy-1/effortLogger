package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import main.PlanningPokerApp;


public class WeightedAverageTest {
    PlanningPokerApp app = new PlanningPokerApp();

    @Test
    public void emptyLists() {
        List<Integer> weights = new ArrayList<Integer>(); 
        List<Double> times = new ArrayList<Double>();
        double result = app.calculateWeightedAverage(weights, times);
        assertEquals(0, result, 0);
    }

    @Test
    public void zeroLists() {
        List<Integer> weights = new ArrayList<Integer>(); 
        List<Double> times = new ArrayList<Double>();

        weights.add(0);
        weights.add(0);
        weights.add(0);

        times.add(0.0);
        times.add(0.0);
        times.add(0.0);

        double result = app.calculateWeightedAverage(weights, times);
        assertEquals(0, result, 0);
    }

    @Test
    public void positiveValues() {
        List<Integer> weights = new ArrayList<Integer>(); 
        List<Double> times = new ArrayList<Double>();

        weights.add(1);
        weights.add(2);
        weights.add(3);

        times.add(2.0);
        times.add(3.0);
        times.add(4.0);

        double result = app.calculateWeightedAverage(weights, times);

        assertEquals(3.3, result, 0.05);
    }

    @Test
    public void negativeValues() {
        List<Integer> weights = new ArrayList<Integer>(); 
        List<Double> times = new ArrayList<Double>();

        weights.add(-1);
        weights.add(-2);
        weights.add(-3);

        times.add(-2.0);
        times.add(-3.0);
        times.add(-4.0);

        double result = app.calculateWeightedAverage(weights, times);

        assertEquals(-1.0, result, 0.0);
    }
}
