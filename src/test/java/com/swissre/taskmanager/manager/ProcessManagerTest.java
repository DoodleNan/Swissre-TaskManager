package com.swissre.taskmanager.manager;

import com.swissre.taskmanager.model.Process;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessManagerTest {
    private Process validProcess = new Process("high", new String[]{"java", "--version"});
    private ProcessManager processManager = new ProcessManager(Arrays.asList(validProcess, validProcess));

    @Test
    public void testGetLiveProcesses() {
        List<Process> liveProcesses = processManager.getLiveProcesses();
        assertTrue(liveProcesses.contains(validProcess));
        assertEquals(2, liveProcesses.size());
        assertTrue(liveProcesses.get(0).equals(validProcess));
        assertTrue(liveProcesses.get(1).equals(validProcess));
    }
}
