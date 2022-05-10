package com.swissre.taskmanager.manager.killProcess.imp;

import com.swissre.taskmanager.manager.ProcessKillerManager;
import com.swissre.taskmanager.model.Process;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * KillAllKillProcessManager to kill all processes under the Task Manager, and empty the TaskManager list
 */
@AllArgsConstructor
public class KillAllKillProcessManager {
    private ProcessKillerManager processKillerManager;

    public KillAllKillProcessManager() {
        this.processKillerManager = new ProcessKillerManager();
    }

    public void killAllProcess(List<Process> processList) {
        CopyOnWriteArrayList<Process> list = new CopyOnWriteArrayList<>(processList);
        for(Process process : list) {
            processKillerManager.killProcess(list, process.getPID());
        }

        processList.clear();
    }

}
