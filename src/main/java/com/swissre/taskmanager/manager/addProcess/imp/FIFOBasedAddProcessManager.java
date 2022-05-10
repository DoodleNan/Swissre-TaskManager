package com.swissre.taskmanager.manager.addProcess.imp;

import com.swissre.taskmanager.manager.ProcessCreationManager;
import com.swissre.taskmanager.manager.ProcessKillerManager;
import com.swissre.taskmanager.manager.addProcess.AddProcessManager;
import com.swissre.taskmanager.model.Process;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;

/**
 * Add a process following FIFO rule:
 * accept all new processes through the add() method,
 * killing and removing from the TM list the oldest one (First-In, First- Out) when the max size is reached
 */
@Slf4j
@AllArgsConstructor
public class FIFOBasedAddProcessManager implements AddProcessManager {
    ProcessCreationManager processCreationManager;
    ProcessKillerManager processKillerManager;

    public FIFOBasedAddProcessManager() {
        this.processCreationManager = new ProcessCreationManager();
        this.processKillerManager = new ProcessKillerManager();
    }
    @Override
    public Process addProcess(List<Process> liveProcesses, Process process, int capacity) {
        if(liveProcesses.size() == capacity) {
            // Sort process by time, the smallest timestamp is the oldest one
            liveProcesses.sort(new Comparator<Process>() {
                @Override
                public int compare(Process o1, Process o2) {
                    return Long.compare(o1.getCreationTimestamp(), o2.getCreationTimestamp());
                }
            });

            // Task with oldest timestamp
            processCreationManager.createProcess(process);
            liveProcesses.add(process);
            Process oldestProcess = liveProcesses.get(0);
            processKillerManager.killProcess(liveProcesses, oldestProcess.getPID());
            liveProcesses.remove(oldestProcess);
        }else{
            log.info(processCreationManager.toString());
            processCreationManager.createProcess(process);
            liveProcesses.add(process);
        }

        return process;
    }

}
