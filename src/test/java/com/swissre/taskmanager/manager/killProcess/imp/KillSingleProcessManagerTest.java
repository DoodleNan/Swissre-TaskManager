package com.swissre.taskmanager.manager.killProcess.imp;

import com.swissre.taskmanager.exception.ProcessKillerException;
import com.swissre.taskmanager.manager.ProcessKillerManager;
import com.swissre.taskmanager.model.Process;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class KillSingleProcessManagerTest {
    private List<Process> liveProcesses;
    private int PID = 1234;

    @Mock
    private ProcessKillerManager mockProcessKillerManager;

    @Mock
    private Process mockProcess;

    private KillSingleProcessManager killSingleProcessManager;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        liveProcesses = new ArrayList<>();
        liveProcesses.add(mockProcess);
        killSingleProcessManager = new KillSingleProcessManager(mockProcessKillerManager);
    }

    @Test
    public void testKillAllProcess_succeed() {
        doNothing().when(mockProcessKillerManager).killProcess(anyList(), anyInt());

        killSingleProcessManager.killProcess(liveProcesses, PID);

        verify(mockProcessKillerManager, times(1)).killProcess(anyList(), anyInt());
    }

    @Test
    public void testKillAllProcess_failure() {
        doThrow(ProcessKillerException.class).when(mockProcessKillerManager).killProcess(anyList(), anyInt());

        assertThrows(ProcessKillerException.class, () -> killSingleProcessManager.killProcess(liveProcesses, PID));
    }
}
