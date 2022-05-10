package com.swissre.taskmanager.manager.killProcess;

import com.swissre.taskmanager.manager.killProcess.imp.KillAllKillProcessManager;
import com.swissre.taskmanager.manager.killProcess.imp.KillGroupProcessManager;
import com.swissre.taskmanager.manager.killProcess.imp.KillSingleProcessManager;

/**
 * KillProcessManagerFactory, responsible for creating KillAllKillProcessManager based on user input
 */
public class KillProcessManagerFactory {
    public KillAllKillProcessManager getKillAllKillProcessManager() {
        return new KillAllKillProcessManager();
    }

    public KillSingleProcessManager getKillSingleProcessManager() {
        return new KillSingleProcessManager();
    }

    public KillGroupProcessManager getKillGroupProcessManager() {
        return new KillGroupProcessManager();
    }
}
