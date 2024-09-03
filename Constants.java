package utils;

import java.util.Arrays;

public class Constants {
    public static int NO_OF_TASKS = 200; // number of Cloudlets vary from 50, 100, 200
    public static int NO_OF_DATA_CENTERS = 20; // number of Datacenters;

    public static String baseDir = ".";

    public static String matrixDir = "input-matrices";
    public static String VMMipsFile = "input-VMMips";

    public static String inputDir = "inputs";
    public static String inputDirFT = "inputsFT";
    public static int POPULATION_SIZE = 100; // Number of Particles.
    public static int MAX_ITERATION = 1000; // Number of Iterations

    public static double QoSX = 2;

    public static double[] getLB(){
        double[] lower_bound = new double[Constants.NO_OF_TASKS];
        Arrays.fill(lower_bound, 0);

        return lower_bound;
    }

    public static double[] getUB(){
        double[] upper_bound = new double[Constants.NO_OF_TASKS];
        Arrays.fill(upper_bound, Constants.NO_OF_DATA_CENTERS-1);

        return upper_bound;
    }
}
