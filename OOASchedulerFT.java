package MetaheuristicsNew.OOA;

import MetaheuristicsNew.MetaheuristicSchedulerFT;

import java.util.List;

public class OOASchedulerFT extends MetaheuristicSchedulerFT {
    public static List<List<?>> main(String[] args){
        return runFT(new OOAFT(), "OOA Fault tolerant");
    }
}
