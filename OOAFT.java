package MetaheuristicsNew.OOA;

import MetaheuristicsNew.MetaheuristicAlgorithmFT;
import MetaheuristicsNew.MetaheuristicFitnessFunction;
import MetaheuristicsNew.MetaheuristicTask;
import MetaheuristicsNew.MetaheuristicUtil;
import utils.Constants;
import utils.GenerateMatrices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class OOAFT implements MetaheuristicAlgorithmFT {

    private final int popSize;
    private final int maxIter;
    private final double[] lb;
    private final double[] ub;
    private int varSize;

    private final MetaheuristicFitnessFunction fitFunc;

    private final double[] deadlineMatrix;

    public OOAFT(){
        this.popSize = Constants.POPULATION_SIZE;
        this.maxIter = Constants.MAX_ITERATION;
        this.lb = Constants.getLB();
        this.ub = Constants.getUB();
        this.varSize = Constants.NO_OF_TASKS * 2; //for each task, there is a backup
        this.fitFunc = new MetaheuristicFitnessFunction();
        this.deadlineMatrix = GenerateMatrices.getDeadlineMatrix();
    }

    public ArrayList<MetaheuristicTask> runFT() {
        // Initialization
        double[][] X = new double[popSize][varSize];
        double[] fit = new double[popSize];

        Random random = new Random();

        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < varSize; j++) {
                X[i][j] = lb[0] + random.nextDouble() * (ub[0] - lb[0]);
            }
            fit[i] = fitFunc.evaluate(X[i]); // Calculate fitness for each agent
        }

        MetaheuristicTask[][] XTask = initializationTask(popSize, varSize, X);

        double[] bestPos = new double[varSize];
        double bestScore = Double.MAX_VALUE;
        int bestIndex = -1;

        // Main optimization loop
        for (int t = 0; t < maxIter; t++) {
            // Update the best proposed solution
            double fBest = Double.MAX_VALUE;
            int bLocation = -1;

            for (int i = 0; i < popSize; i++) {
                if (fit[i] < fBest) {
                    fBest = fit[i];
                    bLocation = i;
                }
            }

            if (t == 0 || fBest < bestScore) {
                bestScore = fBest;
                bestPos = X[bLocation].clone();
                bestIndex = bLocation;
            }

            for (int i = 0; i < popSize; i++) {
                // Phase 1: Position Identification and Hunting the Fish (Exploration)
                int[] fishPosition = new int[0];
                for (int j = 0; j < popSize; j++) {
                    if (fit[j] < fit[i]) {
                        int[] temp = new int[fishPosition.length + 1];
                        System.arraycopy(fishPosition, 0, temp, 0, fishPosition.length);
                        temp[fishPosition.length] = j;
                        fishPosition = temp;
                    }
                }

                double[] selectedFish;
                if (fishPosition.length == 0) {
                    selectedFish = bestPos.clone();
                } else {
                    if (random.nextDouble() < 0.5) {
                        selectedFish = bestPos.clone();
                    } else {
                        int k = random.nextInt(fishPosition.length);
                        selectedFish = X[fishPosition[k]].clone();
                    }
                }

                int I = random.nextInt(2) + 1;
                double[] XNewP1 = new double[varSize];

                for (int j = 0; j < varSize; j++) {
                    XNewP1[j] = X[i][j] + random.nextDouble() * (selectedFish[j] - I * X[i][j]);
                    XNewP1[j] = Math.max(XNewP1[j], lb[0]);
                    XNewP1[j] = Math.min(XNewP1[j], ub[0]);
                }

                double fitNewP1 = fitFunc.evaluate(XNewP1);

                if (fitNewP1 < fit[i]) {
                    X[i] = XNewP1.clone();
                    fit[i] = fitNewP1;
                }

                // Phase 2: Carrying the Fish to the Suitable Position (Exploitation)
                double[] XNewP2 = new double[varSize];

                for (int j = 0; j < varSize; j++) {
                    XNewP2[j] = X[i][j] + (lb[0] + random.nextDouble() * (ub[0] - lb[0])) / (t + 1);
                    XNewP2[j] = Math.max(XNewP2[j], lb[0]);
                    XNewP2[j] = Math.min(XNewP2[j], ub[0]);
                }

                double fitNewP2 = fitFunc.evaluate(XNewP2);

                if (fitNewP2 < fit[i]) {
                    X[i] = XNewP2.clone();
                    fit[i] = fitNewP2;
                }
            }

            for (int j = 0; j < popSize; j++) {
                //Working only on this Vulture (each vulture has varSize number of positions)
                MetaheuristicTask[] tasks = XTask[j];
                double[] position = X[j];
                //Update position of tasks according to position particle
                for (int k = 0; k < varSize; k++) {
                    MetaheuristicTask task = tasks[k];
                    task.setMapping((int) position[k]);
                }
                //Check if backup and primary tasks are not on the same VM (and move backup task to more powerful VM)
                for (int k = 0; k < varSize; k++) {
                    MetaheuristicTask task = tasks[k];
                    if(task.isPrimary() && !task.isBackupDropped() && task.getMapping() == task.getBackup().getMapping()){
                        MetaheuristicTask backupTask = task.getBackup();
                        int changedPosition = MetaheuristicUtil.getBetterVM((int) backupTask.getMapping());
                        backupTask.setMapping(changedPosition);
                        position[k+1] = changedPosition;
                    }
                }

                //Drop backup of tasks that didn't miss their deadline
                tasks = MetaheuristicUtil.setTaskDeadlineInfos(tasks);
                ArrayList<Integer> droppedBackups = new ArrayList<>();
                for (int k = 0; k < varSize; k++) {
                    MetaheuristicTask task = tasks[k];
                    if(task.isPrimary() && !task.isBackupDropped() && !task.isMissedDeadline()){
                        droppedBackups.add(k+1);
                    }
                }
                droppedBackups.sort(Collections.reverseOrder()); //Reverse it so we can delete from the end
                varSize -= droppedBackups.size();
                for (int k = 0; k < popSize; k++) {
                    MetaheuristicTask[] tas = XTask[k];
                    double[] po  = X[k];
                    for (Integer droppedBackup : droppedBackups) {
                        MetaheuristicTask pTask = tas[droppedBackup - 1];
                        pTask.setBackup(null);
                        pTask.setBackupDropped(true);
                        tas = MetaheuristicUtil.removeElementAtIndexMHTask(tas, droppedBackup);
                        po = MetaheuristicUtil.removeElementAtIndexDouble(po, droppedBackup);
                    }
                    XTask[k] = tas;
                    X[k] = po;
                }
            }

            System.arraycopy(X[bestIndex], 0, bestPos, 0, varSize);

        }

        MetaheuristicTask[] res = XTask[bestIndex];
        return new ArrayList<>(Arrays.asList(res));
    }

    private MetaheuristicTask[][] initializationTask(int popSize, int varSize, double[][] X){
        MetaheuristicTask[][] XTasks = new MetaheuristicTask[popSize][varSize];
        MetaheuristicTask pTask = null;

        for (int i = 0; i < popSize; i++) {
            for (int j = 0; j < varSize; j++) {
                int idx = (int) j/2;
                //Create primary and backup tasks (primary task is even, backup task is odd)
                if(j%2 == 0){
                    MetaheuristicTask primaryTask = new MetaheuristicTask(true, X[i][j], null, idx, deadlineMatrix[idx]);
                    pTask = primaryTask;
                    XTasks[i][j] = primaryTask;
                }else{
                    MetaheuristicTask backupTask = new MetaheuristicTask(false, X[i][j], pTask, idx, deadlineMatrix[idx]);
                    pTask.setBackup(backupTask);
                    XTasks[i][j] = backupTask;
                }
            }
        }

        return XTasks;
    }

}
