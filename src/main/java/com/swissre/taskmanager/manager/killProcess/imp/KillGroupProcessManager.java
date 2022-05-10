package com.swissre.taskmanager.manager.killProcess.imp;

import com.swissre.taskmanager.manager.ProcessKillerManager;
import com.swissre.taskmanager.model.Process;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * KillGroupProcessManager to kill all processes with a given PGID, and remove from TaskManager list.
 */
@Slf4j
@AllArgsConstructor
public class KillGroupProcessManager {
    private ProcessKillerManager processKillerManager;

    public KillGroupProcessManager() {
        this.processKillerManager = new ProcessKillerManager();
    }

    public List<Process> killGroupProcess(List<Process> liveProcesses, int PGID) {
        processKillerManager.killProcess(liveProcesses, PGID);

        return liveProcesses;
    }
}
