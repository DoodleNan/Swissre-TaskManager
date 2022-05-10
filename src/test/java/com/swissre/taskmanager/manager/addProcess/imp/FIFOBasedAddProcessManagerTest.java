package com.swissre.taskmanager.manager.addProcess.imp;

import com.swissre.taskmanager.exception.ProcessCreationException;
import com.swissre.taskmanager.manager.ProcessCreationManager;
import com.swissre.taskmanager.manager.ProcessKillerManager;
import com.swissre.taskmanager.model.Process;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class FIFOBasedAddProcessManagerTest {
    private List<Process> liveProcesses;
    private int capacity = 2;

    @Mock
    Process mockProcess;
    @Mock
    ProcessCreationManager mockProcessCreationManager;
    @Mock
    ProcessKillerManager mockProcessKillerManager;

    private FIFOBasedAddProcessManager fifoBasedAddProcessManager;

    @BeforeEach
    public void init() {
        liveProcesses = new ArrayList<>();
        MockitoAnnotations.initMocks(this);
        fifoBasedAddProcessManager = new FIFOBasedAddProcessManager(mockProcessCreationManager, mockProcessKillerManager);
    }
    @Test
    public void testAddProcess_notFull() {
        doNothing().when(mockProcessCreationManager).createProcess(any());

        fifoBasedAddProcessManager.addProcess(liveProcesses, mockProcess, capacity);

        assertEquals(1, liveProcesses.size());
    }

    @Test void testAddProcess_fullAdd() {
        doNothing().when(mockProcessCreationManager).createProcess(any());
        doNothing().when(mockProcessKillerManager).killProcess(anyList(), anyInt());

        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        fifoBasedAddProcessManager.addProcess(liveProcesses, mockProcess, capacity);

        assertEquals(capacity, liveProcesses.size());
    }

    @Test void testAddProcess_exception() {
        doThrow(ProcessCreationException.class).when(mockProcessCreationManager).createProcess(any());

        assertThrows(ProcessCreationException.class, () -> fifoBasedAddProcessManager.addProcess(liveProcesses, mockProcess, capacity));

    }

}
