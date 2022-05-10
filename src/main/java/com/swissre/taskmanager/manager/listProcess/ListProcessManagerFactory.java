package com.swissre.taskmanager.manager.listProcess;

import com.swissre.taskmanager.manager.listProcess.imp.PIDBasedListProcessManager;
import com.swissre.taskmanager.manager.listProcess.imp.PriorityBasedListProcessManager;
import com.swissre.taskmanager.manager.listProcess.imp.TimeBasedListProcessManager;
import com.swissre.taskmanager.model.ProcessListStrategy;

/**
 * ListProcessManagerFactory, responsible for creating ListProcessManager object
 */
public class ListProcessManagerFactory {
    public ListProcessManager getListProcessManager(ProcessListStrategy processListStrategy) {
        switch (processListStrategy) {
            case TIME: return new TimeBasedListProcessManager();
            case PID: return new PIDBasedListProcessManager();
            case PRIORITY: return new PriorityBasedListProcessManager();
            default: throw new IllegalArgumentException("not valid processListStrategy");
        }
    }
}
