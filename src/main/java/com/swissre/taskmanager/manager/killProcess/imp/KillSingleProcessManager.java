package com.swissre.taskmanager.manager.killProcess.imp;

import com.swissre.taskmanager.manager.ProcessKillerManager;
import com.swissre.taskmanager.model.Process;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * KillSingleProcessManager kill a process given the PID from TaskManager, and remove from list
 */
@Slf4j
@AllArgsConstructor
public class KillSingleProcessManager {
    private ProcessKillerManager processKillerManager;
    public KillSingleProcessManager() {
        this.processKillerManager = new ProcessKillerManager();
    }

    public void killProcess(List<Process> liveProcesses, int PID) {
        processKillerManager.killProcess(liveProcesses, PID);
    }
}
