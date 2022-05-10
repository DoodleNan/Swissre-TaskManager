package com.swissre.taskmanager.manager;

import com.swissre.taskmanager.exception.ProcessKillerException;
import com.swissre.taskmanager.model.Process;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * Kill a process if liveProcess under TaskManager contains it.
 */
@Slf4j
public class ProcessKillerManager {
    public void killProcess(List<Process> liveProcesses, int PID) {
        try {
            boolean found = false;
            for(Process process : liveProcesses) {
                if(process.isLive() && process.getPID() == PID) {
                    ProcessBuilder pb = new ProcessBuilder("kill", String.valueOf(PID));
                    pb.start();
                    liveProcesses.remove(process);
                    found = true;
                    log.info("Killed process with PID {}" , PID);
                    break;
                }
            }
            if(!found) {
                throw new IllegalArgumentException("Can not kill the process as Task Manager doesn't contain this process");
            }
        } catch (IOException ex) {
            log.error("Exception kill a process. Client input for process PID: {}", PID);
            throw new ProcessKillerException(ex.getMessage());
        }

    }
}
