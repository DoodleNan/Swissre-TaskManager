package com.swissre.taskmanager.manager;

import com.swissre.taskmanager.exception.ProcessCreationException;
import com.swissre.taskmanager.model.Process;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Create a process based on user input - command, update the process data with timestamp and PID.
 */
@Slf4j
public class ProcessCreationManager {
    public void createProcess(Process process) {
        try {
            ProcessBuilder pb = new ProcessBuilder(process.getCommand());
            java.lang.Process javaProcess = pb.start();
            process.setCreationTimestamp(System.currentTimeMillis());
            // TODO: I only implemented UNIX like system process creation.
            // Ref: https://stackoverflow.com/questions/4750470/how-to-get-pid-of-process-ive-just-started-within-java-program
            if (javaProcess.getClass().getName().equals("java.lang.UNIXProcess")) {
                Field f = javaProcess.getClass().getDeclaredField("pid");
                f.setAccessible(true);
                int pid = f.getInt(javaProcess);
                f.setAccessible(false);
                process.setPID(pid);
                process.setLive(true);
            }
        } catch (IOException | NoSuchFieldException | IllegalAccessException ex) {
            log.error("Exception create a new process. Client input process metadata: " + process.toString());
            throw new ProcessCreationException(ex.getMessage());
        }
    }
}
