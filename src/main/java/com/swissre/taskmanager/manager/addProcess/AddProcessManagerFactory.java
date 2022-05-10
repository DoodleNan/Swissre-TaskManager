package com.swissre.taskmanager.manager.addProcess;

import com.swissre.taskmanager.manager.addProcess.imp.FIFOBasedAddProcessManager;
import com.swissre.taskmanager.manager.addProcess.imp.PriorityBasedAddProcessManager;
import com.swissre.taskmanager.manager.addProcess.imp.SimpleAddProcessManager;
import com.swissre.taskmanager.model.ProcessAddStrategy;

/**
 * AddProcessManager factory, responsible for creating AddProcessManager object
 */
public class AddProcessManagerFactory {
    public AddProcessManager getAddProcessManager(ProcessAddStrategy processAddStrategy) {
        switch (processAddStrategy) {
            case FIFO: return new FIFOBasedAddProcessManager();
            case SIMPLE: return new SimpleAddProcessManager();
            case PRIORITY: return new PriorityBasedAddProcessManager();
            default: throw new IllegalArgumentException("not valid processAddStrategy");
        }
    }
}
