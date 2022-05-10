package com.swissre.taskmanager.manager.addProcess.imp;

import com.swissre.taskmanager.manager.ProcessCreationManager;
import com.swissre.taskmanager.manager.ProcessKillerManager;
import com.swissre.taskmanager.manager.addProcess.AddProcessManager;
import com.swissre.taskmanager.model.Priority;
import com.swissre.taskmanager.model.Process;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Add a process following Priority rule:
 * every call to the add() method, when the max size is reached, should result into an evaluation:
 * if the new process passed in the add() call has a higher priority compared to any of the existing one,
 * we remove the lowest priority that is the oldest, otherwise we skip it.
 */
@Slf4j
@AllArgsConstructor
public class PriorityBasedAddProcessManager implements AddProcessManager {
    ProcessCreationManager processCreationManager;
    ProcessKillerManager processKillerManager;

    public PriorityBasedAddProcessManager() {
        this.processCreationManager = new ProcessCreationManager();
        this.processKillerManager = new ProcessKillerManager();
    }
    @Override
    public Process addProcess(List<Process> liveProcesses, Process process, int capacity) {
        if(liveProcesses.size() == capacity) {
            // Sort process by priority, the top(the biggest value) is the least priority
            liveProcesses.sort(new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    return Integer.compare(
                            Priority.valueOf(o2.getPriority().toUpperCase(Locale.ROOT)).getIntValue(),
                            Priority.valueOf(o1.getPriority().toUpperCase(Locale.ROOT)).getIntValue());
                }
            });

            // Task with lowestPriority
            Process lowestPriorityProcess = liveProcesses.get(0);
            if(Priority.valueOf(lowestPriorityProcess.getPriority().toUpperCase(Locale.ROOT)).getIntValue() >
               Priority.valueOf(process.getPriority().toUpperCase(Locale.ROOT)).getIntValue()) {
                processCreationManager.createProcess(process);
                liveProcesses.add(process);

                processKillerManager.killProcess(liveProcesses, lowestPriorityProcess.getPID());
                liveProcesses.remove(lowestPriorityProcess);
            }
        }else{
            processCreationManager.createProcess(process);
            liveProcesses.add(process);
        }

        return process;
    }

}
