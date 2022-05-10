package com.swissre.taskmanager.service;

import com.swissre.taskmanager.manager.ProcessManager;
import com.swissre.taskmanager.manager.addProcess.AddProcessManagerFactory;
import com.swissre.taskmanager.manager.addProcess.AddProcessManager;
import com.swissre.taskmanager.manager.killProcess.KillProcessManagerFactory;
import com.swissre.taskmanager.manager.killProcess.imp.KillAllKillProcessManager;
import com.swissre.taskmanager.manager.killProcess.imp.KillGroupProcessManager;
import com.swissre.taskmanager.manager.killProcess.imp.KillSingleProcessManager;
import com.swissre.taskmanager.manager.listProcess.ListProcessManager;
import com.swissre.taskmanager.manager.listProcess.ListProcessManagerFactory;
import com.swissre.taskmanager.model.Process;
import com.swissre.taskmanager.model.ProcessAddStrategy;
import com.swissre.taskmanager.model.ProcessListStrategy;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

/**
 * Service component to handle real business logics
 */
@Service
public class TaskManagerService {
    private ProcessManager processManager;

    @Inject
    public TaskManagerService(ProcessManager processManager) {

        this.processManager = processManager;
    }

    public Process addProcess(Process process, String strategy, int capacity){
        ProcessAddStrategy processAddStrategy = ProcessAddStrategy.valueOf(strategy.toUpperCase(Locale.ROOT));
        AddProcessManager addProcessManager = new AddProcessManagerFactory().getAddProcessManager(processAddStrategy);

        return addProcessManager.addProcess(processManager.getLiveProcesses(), process, capacity);

    }

    public List<Process> listProcesses(String strategy) {
        ProcessListStrategy processListStrategy = ProcessListStrategy.valueOf(strategy.toUpperCase(Locale.ROOT));
        ListProcessManager listProcessManager = new ListProcessManagerFactory().getListProcessManager(processListStrategy);

        return listProcessManager.listProcess(processManager.getLiveProcesses());
    }

    public void killProcess(int PID) {
        KillSingleProcessManager killSingleProcessManager = new KillProcessManagerFactory().getKillSingleProcessManager();
        killSingleProcessManager.killProcess(processManager.getLiveProcesses(), PID);
    }

    public void killAllProcess() {
        KillAllKillProcessManager killAllProcessManager = new KillProcessManagerFactory().getKillAllKillProcessManager();
        killAllProcessManager.killAllProcess(processManager.getLiveProcesses());
    }

    public void killGroupProcess(int PGID) {
        KillGroupProcessManager killGroupProcessManager = new KillProcessManagerFactory().getKillGroupProcessManager();
        killGroupProcessManager.killGroupProcess(processManager.getLiveProcesses(), PGID);
    }

}
