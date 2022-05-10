package com.swissre.taskmanager.manager.listProcess.imp;

import com.swissre.taskmanager.model.Priority;
import com.swissre.taskmanager.model.Process;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PriorityBasedListProcessManagerTest {
    private PriorityBasedListProcessManager priorityBasedListProcessManager;
    List<Process> liveProcesses;

    @Mock
    private Process mockProcess;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        priorityBasedListProcessManager = new PriorityBasedListProcessManager();
        liveProcesses = new ArrayList<>();
        when(mockProcess.getPriority()).thenReturn("low");
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
    }
    @Test
    public void testListProcess() {
        priorityBasedListProcessManager.listProcess(liveProcesses);
        boolean prioritySorted = true;
        for(int i = 1; i < liveProcesses.size(); i++) {
            if(Priority.valueOf(liveProcesses.get(i).getPriority().toUpperCase(Locale.ROOT)).getIntValue() <
               Priority.valueOf(liveProcesses.get(i-1).getPriority().toUpperCase(Locale.ROOT)).getIntValue()){
                prioritySorted = false;
                break;
            }
        }

        assertTrue(prioritySorted);
    }
}
