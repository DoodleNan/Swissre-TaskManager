package com.swissre.taskmanager.manager.addProcess;

import com.swissre.taskmanager.model.Process;
import java.util.List;

/**
 * Interface to add a process. It can be simple/priority/fifo based.
 */
public interface AddProcessManager {
    Process addProcess(List<Process> liveProcesses, Process process, int capacity);
}
