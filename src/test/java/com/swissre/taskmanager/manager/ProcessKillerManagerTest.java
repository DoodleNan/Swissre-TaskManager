package com.swissre.taskmanager.manager;

import com.swissre.taskmanager.model.Process;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessKillerManagerTest {
    private ProcessKillerManager processKillerManager = new ProcessKillerManager();
    private ProcessCreationManager processCreationManager = new ProcessCreationManager();

    private Process validProcess = new Process("low", new String[]{"java", "--version"});
    private Process invalidProcess = new Process(1, true);

    private List<Process> liveProcesses = new ArrayList<>();

    @BeforeEach
    public void init() {
        processCreationManager.createProcess(validProcess);
        liveProcesses.add(validProcess);
//        liveProcesses.add(validProcess);
    }
    @Test
    public void testKillProcess_succeed() {
        processKillerManager.killProcess(liveProcesses, validProcess.getPID());
        liveProcesses.add(invalidProcess);
        assertEquals(1, liveProcesses.size());
    }

    @Test
    public void testKillProcess_pid_not_found() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> processKillerManager.killProcess(liveProcesses, invalidProcess.getPID()));
        assertTrue(exception.getMessage().contains("Can not kill the process as Task Manager doesn't contain this process"));
    }

//    @Test
//    public void testKillProcess_kill_operation_failed() {
//        liveProcesses.add(invalidProcess);
//        processKillerManager.killProcess(liveProcesses, validProcess.getPID());
//        Throwable exception = assertThrows(ProcessKillerException.class, () -> processKillerManager.killProcess(liveProcesses, validProcess.getPID()));
//        assertTrue(exception.getMessage().contains("Can not kill the process as Task Manager doesn't contain this process"));
//    }

    @AfterEach
    public void tearDown(){
        liveProcesses = null;
    }
}
