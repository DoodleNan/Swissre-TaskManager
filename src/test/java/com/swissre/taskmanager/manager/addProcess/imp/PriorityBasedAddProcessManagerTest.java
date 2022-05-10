package com.swissre.taskmanager.manager.addProcess.imp;

import com.swissre.taskmanager.exception.ProcessCreationException;
import com.swissre.taskmanager.manager.ProcessCreationManager;
import com.swissre.taskmanager.manager.ProcessKillerManager;
import com.swissre.taskmanager.model.Priority;
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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PriorityBasedAddProcessManagerTest {
    private List<Process> liveProcesses;
    private int capacity = 2;

    @Mock
    Process mockProcess;
    @Mock
    ProcessCreationManager mockProcessCreationManager;
    @Mock
    ProcessKillerManager mockProcessKillerManager;

    private PriorityBasedAddProcessManager priorityBasedAddProcessManager;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @BeforeEach
    public void init() {
        liveProcesses = new ArrayList<>();
        MockitoAnnotations.initMocks(this);
        priorityBasedAddProcessManager = new PriorityBasedAddProcessManager(mockProcessCreationManager, mockProcessKillerManager);
    }

    @Test
    public void testAddProcess_notFull() {
        doNothing().when(mockProcessCreationManager).createProcess(any());

        priorityBasedAddProcessManager.addProcess(liveProcesses, mockProcess, capacity);

        assertEquals(1, liveProcesses.size());
    }

    @Test void testAddProcess_fullNotAdd() {
        when(mockProcess.getPriority()).thenReturn("low");
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        priorityBasedAddProcessManager.addProcess(liveProcesses, mockProcess, capacity);

        assertEquals(capacity, liveProcesses.size());
    }

    @Test
    public void testAddProcess_fullAdd() {
        doNothing().when(mockProcessCreationManager).createProcess(any());
        doNothing().when(mockProcessKillerManager).killProcess(anyList(), anyInt());
        when(mockProcess.getPriority()).thenReturn("low");
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);

        Process newProcess = new Process("high", new String[]{"dummy-command"});
        priorityBasedAddProcessManager.addProcess(liveProcesses, newProcess, capacity);

        assertEquals(capacity, liveProcesses.size());
        liveProcesses.sort(new Comparator<Process>() {
            @Override
            public int compare(Process o1, Process o2) {
                return Integer.compare(
                        Priority.valueOf(o1.getPriority().toUpperCase(Locale.ROOT)).getIntValue(),
                        Priority.valueOf(o2.getPriority().toUpperCase(Locale.ROOT)).getIntValue());
            }
        });

        assertEquals("high", liveProcesses.get(0).getPriority());
        assertEquals("low", liveProcesses.get(1).getPriority());
    }

    @Test void testAddProcess_exception() {
        doThrow(ProcessCreationException.class).when(mockProcessCreationManager).createProcess(any());

        assertThrows(ProcessCreationException.class, () -> priorityBasedAddProcessManager.addProcess(liveProcesses, mockProcess, capacity));

    }
}
