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
public class PIDBasedListProcessManagerTest {
    private PIDBasedListProcessManager pidBasedListProcessManager;
    List<Process> liveProcesses;

    @Mock
    private Process mockProcess;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        pidBasedListProcessManager = new PIDBasedListProcessManager();
        liveProcesses = new ArrayList<>();
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
        liveProcesses.add(mockProcess);
    }
    @Test
    public void testListProcess() {
        pidBasedListProcessManager.listProcess(liveProcesses);
        boolean pidSorted = true;
        for(int i = 1; i < liveProcesses.size(); i++) {
            if(liveProcesses.get(i).getPID() < liveProcesses.get(i-1).getPID()){
                pidSorted = false;
                break;
            }
        }

        assertTrue(pidSorted);
    }
}
