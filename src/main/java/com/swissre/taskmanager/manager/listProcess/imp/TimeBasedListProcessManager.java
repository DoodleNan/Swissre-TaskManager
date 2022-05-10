package com.swissre.taskmanager.manager.listProcess.imp;

import com.swissre.taskmanager.manager.listProcess.ListProcessManager;
import com.swissre.taskmanager.model.Process;

import java.util.Comparator;
import java.util.List;

/**
 * TimeBasedListProcessManager,  sort the liveProcesses under a TaskManager based on Timestamp when the process is created, in ascend order
 */
public class TimeBasedListProcessManager implements ListProcessManager {
    @Override
    public List<Process> listProcess(List<Process> liveProcesses) {
        liveProcesses.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Long.compare(o1.getCreationTimestamp(), o2.getCreationTimestamp());
            }
        });

        return liveProcesses;
    }
}
