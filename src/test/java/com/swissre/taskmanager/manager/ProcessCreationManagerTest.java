package com.swissre.taskmanager.manager;

import com.swissre.taskmanager.exception.ProcessCreationException;
import com.swissre.taskmanager.model.Process;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProcessCreationManagerTest {
    private ProcessCreationManager processCreationManager = new ProcessCreationManager();
    private Process validProcess = new Process("low", new String[]{"java", "--version"});
    private Process invalidProcess = new Process("high", new String[]{"random-command"});

    @Test
    public void testCreateProcess_succeed() {
        processCreationManager.createProcess(validProcess);

        assertNotNull(validProcess);
        assertTrue(validProcess.isLive());
        assertNotNull(validProcess.getCreationTimestamp());
    }

    @Test()
    public void testCreateProcess_failed() {
        Throwable exception = assertThrows(ProcessCreationException.class, () -> processCreationManager.createProcess(invalidProcess));
        assertTrue(exception.getMessage().contains(HttpStatus.NOT_ACCEPTABLE.name()));
        assertNotNull(invalidProcess);
        assertTrue(!invalidProcess.isLive());
        assertTrue(null == invalidProcess.getCreationTimestamp());
    }
}
