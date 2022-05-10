package com.swissre.taskmanager.manager;

import com.swissre.taskmanager.model.Process;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

/**
 * ProcessManager component to hold TaskManager live processes.
 */
@Named
public class ProcessManager {

    private List<Process> liveProcesses = new ArrayList<>();

    public ProcessManager(List<Process> liveProcesses) {
        this.liveProcesses = liveProcesses;
    }

    public List<Process> getLiveProcesses() {
        return this.liveProcesses;
    }
}
