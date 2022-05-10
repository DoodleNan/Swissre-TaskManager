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

public class KillAllKillProcessManagerTest {
    List<Process> liveProcesses;
    @Mock
    private ProcessKillerManager mockProcessKillerManager;

    @Mock
    private Process mockProcess;

    private KillAllKillProcessManager killAllKillProcessManager;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        liveProcesses = new ArrayList<>();
        liveProcesses.add(mockProcess);
        killAllKillProcessManager = new KillAllKillProcessManager(mockProcessKillerManager);
    }

    @Test
    public void testKillAllProcess_succeed() {
        doNothing().when(mockProcessKillerManager).killProcess(anyList(), anyInt());

        killAllKillProcessManager.killAllProcess(liveProcesses);

        verify(mockProcessKillerManager, times(1)).killProcess(anyList(), anyInt());
    }

    @Test
    public void testKillAllProcess_failure() {
        doThrow(ProcessKillerException.class).when(mockProcessKillerManager).killProcess(anyList(), anyInt());

        assertThrows(ProcessKillerException.class, () -> killAllKillProcessManager.killAllProcess(liveProcesses));
    }
}
