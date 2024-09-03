package utils;

import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.Vm;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class VMCreator {

    public static LinkedList<Vm> vms;
    public VMCreator(int userId, int vms, Datacenter datacenter) {
        //Creates a container to store VMs. This list is passed to the broker later
        LinkedList<Vm> list = new LinkedList<>();
        Random random = new Random();

        //VM Parameters
        long size = 10000; //image size (MB)
        int ram = 512; //vm memory (MB)
        int mips = random.nextInt(1001) + 1000;
        long bw = 1000;
        int pesNumber = 1; //number of cpus
        String vmm = "Xen"; //VMM name

        for (int i = 0; i < vms; i++) {
            Vm vm = new Vm(datacenter.getId(), userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared());
            list.add(vm);
        }
        VMCreator.vms = list;
    }

    public static LinkedList<Vm> getVms() {
        return vms;
    }

    public static Vm getVmById(int id){
        return vms.get(id);
    }
}
