package com.swissre.taskmanager.manager.addProcess.imp;

import com.swissre.taskmanager.exception.ProcessCreationException;
import com.swissre.taskmanager.manager.ProcessCreationManager;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class SimpleAddProcessManagerTest {
    private List<Process> liveProcesses;
    private int capacity = 2;

    @Mock
    Process mockProcess;
    @Mock
    ProcessCreationManager mockProcessCreationManager;

    private SimpleAddProcessManager simpleAddProcessManager;

    @BeforeEach
    public void init() {
        liveProcesses = new ArrayList<>();
        MockitoAnnotations.initMocks(this);
        simpleAddProcessManager = new SimpleAddProcessManager(mockProcessCreationManager);
    }
    @Test
    public void testAddProcess_notFull() {
        doNothing().when(mockProcessCreationManager).createProcess(any());

        simpleAddProcessManager.addProcess(liveProcesses, mockProcess, capacity);
        verify(mockProcessCreationManager, times(1)).createProcess(any());
        assertEquals(1, liveProcesses.size());
    }

    @Test void testAddProcess_fullAdd() {
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        simpleAddProcessManager.addProcess(liveProcesses, mockProcess, capacity);

        assertEquals(capacity, liveProcesses.size());
        verify(mockProcessCreationManager, times(0)).createProcess(any());
    }

    @Test void testAddProcess_exception() {
        doThrow(ProcessCreationException.class).when(mockProcessCreationManager).createProcess(any());

        assertThrows(ProcessCreationException.class, () -> simpleAddProcessManager.addProcess(liveProcesses, mockProcess, capacity));

    }
}
