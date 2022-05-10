package com.swissre.taskmanager.manager.listProcess.imp;

import com.swissre.taskmanager.manager.listProcess.ListProcessManager;
import com.swissre.taskmanager.model.Process;

import java.util.Comparator;
import java.util.List;

/**
 * PIDBasedListProcessManager, sort the liveProcesses under a TaskManager based on PID, in ascend order
 */
public class PIDBasedListProcessManager implements ListProcessManager {
    @Override
    public List<Process> listProcess(List<Process> liveProcesses) {
        liveProcesses.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Integer.compare(o1.getPID(), o2.getPID());
            }
        });

        return liveProcesses;
    }
}
