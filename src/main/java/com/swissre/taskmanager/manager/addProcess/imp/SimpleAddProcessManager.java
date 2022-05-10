package com.swissre.taskmanager.manager.addProcess.imp;

import com.swissre.taskmanager.manager.ProcessCreationManager;
import com.swissre.taskmanager.manager.addProcess.AddProcessManager;
import com.swissre.taskmanager.model.Process;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Add a process following FIFO rule:
 * The task manager should have a prefixed maximum capacity,
 * so it can not have more than a certain number of running processes within itself.
 * This value is defined at build time.
 * The add(process) method in TM is used for it.
 * The default behaviour is that we can accept new processes till when there is capacity inside the Task Manager,
 * otherwise we won’t accept any new process.
 */

@Component
@Slf4j
@AllArgsConstructor
public class SimpleAddProcessManager implements AddProcessManager {
    ProcessCreationManager processCreationManager;

    public SimpleAddProcessManager() {
        processCreationManager = new ProcessCreationManager();
    }

    @Override
    public Process addProcess(List<Process> liveProcesses, Process process, int capacity) {

        if(liveProcesses.size() < capacity) {
            processCreationManager.createProcess(process);
            liveProcesses.add(process);
        }

        return process;
    }
}
