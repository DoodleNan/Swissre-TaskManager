package com.swissre.taskmanager.manager.listProcess.imp;

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

import static junit.framework.TestCase.assertTrue;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TimeBasedListProcessManagerTest {
    private TimeBasedListProcessManager timeBasedListProcessManager;
    List<Process> liveProcesses;

    @Mock
    private Process mockProcess;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        timeBasedListProcessManager = new TimeBasedListProcessManager();
        liveProcesses = new ArrayList<>();
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
    }
    @Test
    public void testListProcess() {
        timeBasedListProcessManager.listProcess(liveProcesses);
        boolean timeSorted = true;
        for(int i = 1; i < liveProcesses.size(); i++) {
            if(liveProcesses.get(i).getCreationTimestamp() < liveProcesses.get(i-1).getCreationTimestamp()){
                timeSorted = false;
                break;
            }
        }

        assertTrue(timeSorted);
    }
}
