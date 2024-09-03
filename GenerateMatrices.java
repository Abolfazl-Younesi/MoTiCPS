package utils;


import java.io.*;
import java.util.Random;

public class GenerateMatrices {
    private static double[][] commMatrix, execMatrix;

    private static double[] deadlineMatrix;
    private static int[] VMMipsList;
    private File commFile = new File("CommunicationTimeMatrix.txt");
    private File execFile = new File("ExecutionTimeMatrix.txt");
    private File deadlineFile;

    private File VMMipsFile;

    public GenerateMatrices(String inputDir) {
        commMatrix = new double[Constants.NO_OF_TASKS][Constants.NO_OF_DATA_CENTERS];
        execMatrix = new double[Constants.NO_OF_TASKS][Constants.NO_OF_DATA_CENTERS];
        deadlineMatrix = new double[Constants.NO_OF_TASKS];
        VMMipsList = new int[Constants.NO_OF_DATA_CENTERS];
//        commFile = new File(Constants.baseDir + "/" + Constants.matrixDir + "/CommunicationTimeMatrix-task" + Constants.NO_OF_TASKS + "vm" + Constants.NO_OF_DATA_CENTERS + ".txt");
//        execFile = new File(Constants.baseDir + "/" + Constants.matrixDir + "/ExecutionTimeMatrix-task" + Constants.NO_OF_TASKS + "vm" + Constants.NO_OF_DATA_CENTERS + ".txt");
        commFile = new File(Constants.baseDir + "/" + inputDir + "/" + Constants.matrixDir + "/CommunicationTimeMatrix-task" + Constants.NO_OF_TASKS + "vm" + Constants.NO_OF_DATA_CENTERS + ".txt");
        execFile = new File(Constants.baseDir + "/" + inputDir  + "/" + Constants.matrixDir + "/ExecutionTimeMatrix-task" + Constants.NO_OF_TASKS + "vm" + Constants.NO_OF_DATA_CENTERS + ".txt");
        deadlineFile = new File(Constants.baseDir + "/" + inputDir  + "/" + Constants.matrixDir + "/DeadlineMatrix-task" + Constants.NO_OF_TASKS + "vm" + Constants.NO_OF_DATA_CENTERS + ".txt");
        VMMipsFile = new File(Constants.baseDir + "/" + inputDir + "/" + "VMMips" + ".txt");
        try {
            initDirectories(inputDir);
            initCostMatrix();
            initVMMips();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDirectories(String inputDir){
        String input = Constants.baseDir + "/" + inputDir;
        String inputMatrices = input +  "/" + Constants.matrixDir;
        java.io.File directoryInput = new java.io.File(input);
        java.io.File directoryInputMatrices = new java.io.File(inputMatrices);
        if (!directoryInput.exists()) {
            directoryInput.mkdir();
        }
        if (!directoryInputMatrices.exists()) {
            directoryInputMatrices.mkdir();
        }
    }

    private void initVMMips() throws IOException {
        System.out.println("Initializing VM Mips...");
        BufferedWriter VMBufferedWriter = new BufferedWriter(new FileWriter(VMMipsFile));
        Random random = new Random();

        for (int i = 0; i < Constants.NO_OF_DATA_CENTERS; i++) {
            int mips = random.nextInt(1001) + 1000;
            VMMipsList[i] = mips;
            VMBufferedWriter.write(String.valueOf(mips));
            VMBufferedWriter.write('\n');
        }
        VMBufferedWriter.close();
    }

    private void initCostMatrix() throws IOException {
        System.out.println("Initializing new Matrices...");
        BufferedWriter commBufferedWriter = new BufferedWriter(new FileWriter(commFile));
        BufferedWriter execBufferedWriter = new BufferedWriter(new FileWriter(execFile));
        BufferedWriter deadlineBufferedWriter = new BufferedWriter(new FileWriter(deadlineFile));
        Random random = new Random();

        for (int i = 0; i < Constants.NO_OF_TASKS; i++) {
            double avgExecTime = 0;
            for (int j = 0; j < Constants.NO_OF_DATA_CENTERS; j++) {
                double commTime = Math.random() * 600 + 20;
                double execTime = Math.random() * 500 + 10;
                commMatrix[i][j] = commTime;
                execMatrix[i][j] = execTime;
                avgExecTime += commTime + execTime;

                commBufferedWriter.write(String.valueOf(commTime) + ' ');
                execBufferedWriter.write(String.valueOf(execTime) + ' ');
//                deadlineBufferedWriter.write(String.valueOf(deadlineTime) + ' ');
            }
            double deadlineTime = (avgExecTime/Constants.NO_OF_DATA_CENTERS) * (random.nextInt(1, 4) + Math.random());
            deadlineMatrix[i] = deadlineTime;
            commBufferedWriter.write('\n');
            execBufferedWriter.write('\n');
            deadlineBufferedWriter.write('\n');
        }
        commBufferedWriter.close();
        execBufferedWriter.close();
        deadlineBufferedWriter.close();
    }

    private void readCostMatrix() throws IOException {
        System.out.println("Reading the Matrices...");
        BufferedReader commBufferedReader = new BufferedReader(new FileReader(commFile));

        int i = 0, j = 0;
        do {
            String line = commBufferedReader.readLine();
            for (String num : line.split(" ")) {
                commMatrix[i][j++] = new Double(num);
            }
            ++i;
            j = 0;
        } while (commBufferedReader.ready());


        BufferedReader execBufferedReader = new BufferedReader(new FileReader(execFile));

        i = j = 0;
        do {
            String line = execBufferedReader.readLine();
            for (String num : line.split(" ")) {
                execMatrix[i][j++] = new Double(num);
            }
            ++i;
            j = 0;
        } while (execBufferedReader.ready());
    }

    public static double[][] getCommMatrix() {
        return commMatrix;
    }

    public static double[][] getExecMatrix() {
        return execMatrix;
    }

    public static double[] getDeadlineMatrix() {
        return deadlineMatrix;
    }

    public static int[] getVMMipsList() {
        return VMMipsList;
    }
}
