package com.swissre.taskmanager.manager.listProcess;

import com.swissre.taskmanager.model.Process;

import java.util.List;

/**
 * ListProcessManager Interface to hold dynamic listProcess strategies.
 */
public interface ListProcessManager {
    List<Process> listProcess(List<Process> liveProcesses);
}
