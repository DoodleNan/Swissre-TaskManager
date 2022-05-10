package com.swissre.taskmanager.manager.listProcess.imp;

import com.swissre.taskmanager.manager.listProcess.ListProcessManager;
import com.swissre.taskmanager.model.Priority;
import com.swissre.taskmanager.model.Process;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * PriorityBasedListProcessManager, sort the liveProcesses under a TaskManager based on priority, in ascending order(top priorities come first).
 * We assume lower value represents higher priority
 */
public class PriorityBasedListProcessManager implements ListProcessManager {
    @Override
    public List<Process> listProcess(List<Process> liveProcesses) {
        liveProcesses.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Integer.compare(Priority.valueOf(o1.getPriority().toUpperCase(Locale.ROOT)).getIntValue(),
                        Priority.valueOf(o2.getPriority().toUpperCase(Locale.ROOT)).getIntValue());
            }
        });

        return liveProcesses;
    }
}
