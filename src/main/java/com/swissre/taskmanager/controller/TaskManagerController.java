package com.swissre.taskmanager.controller;

import com.swissre.taskmanager.exception.ProcessCreationException;
import com.swissre.taskmanager.exception.ProcessKillerException;
import com.swissre.taskmanager.service.TaskManagerService;
import lombok.extern.slf4j.Slf4j;
import com.swissre.taskmanager.model.Process;

import javax.inject.Inject;
import javax.ws.rs.PathParam;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * SpringBoot controller
 */
@RestController
@Slf4j
public class TaskManagerController {

    @Value("${queue.capacity}")
    private String capacity;

    @Inject
    private TaskManagerService taskManagerService;

    /**
     * Add a process with strategy to be chosen by client. strategy can be simple add, priority based or time based.
     *
     * @param process process input, which will contains priority and command to be executed
     * @param strategy strategy can be simple add, priority based or time based.
     * @return A message to client the execution results of the add operation
     */
    @RequestMapping(method = RequestMethod.POST, path = "/addProcess")
    public String addProcess(@RequestBody Process process, @PathParam("strategy") String strategy) {
        long start = System.currentTimeMillis();
        Process newProcess = taskManagerService.addProcess(process, strategy, Integer.parseInt(capacity));
        long duration = (System.currentTimeMillis() - start);

        log.info("Add a process in {} ms", duration);
        if(newProcess.isLive()) {
            return "Successfully added a new process: " + newProcess.toString();
        }else{
            return String.format("Unable to add the new process because capacity %s is full.", capacity);
        }
    }

    /**
     * List all processes maintained by task manager, the sorting strategy is specified by user.
     *
     * @param strategy strategy can be sort by time, by pid and priority, all in asending order
     * @return Sorted list of current live processes.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/listProcesses")
    public List<Process> listProcesses(@PathParam("strategy") String strategy) {
        long start = System.currentTimeMillis();
        List<Process> processes = taskManagerService.listProcesses(strategy);
        long duration = (System.currentTimeMillis() - start);
        log.info("List all processes in {} ms", duration);

        return processes;
    }

    /**
     * Kill a process given the PID
     *
     * @param PID The uniq identifier to a process that needs to be killed
     * @return A message to client about the process killing execution results. If PID is not found, IllegalArgumentException will be thrown.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/killProcess")
    public String killProcess(@PathParam("PID") int PID) {
        long start = System.currentTimeMillis();

        taskManagerService.killProcess(PID);
        long duration = (System.currentTimeMillis() - start);
        log.info("Kill a process with PID {} in {} ms", PID, duration);

        return String.format("Successfully kill process with PID %s", PID);
    }

    /**
     * Kill all processes maintained by Task Manager
     *
     * @return A message to client about the process killing execution results.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/killAllProcesses")
    public String killAllProcess() {
        long start = System.currentTimeMillis();
        taskManagerService.killAllProcess();
        long duration = (System.currentTimeMillis() - start);
        log.info("Kill all process in {} ms", duration);

        return String.format("Killed all processes successfully");
    }

    /**
     * Kill all Processes with a given Group id.
     *
     * @param PGID the unique identifier for application to kill processes
     * @return A message to client about the process killing execution results.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/killGroupProcesses")
    public String killGroupProcesses(@PathParam("PGID") int PGID) {
        long start = System.currentTimeMillis();
        taskManagerService.killGroupProcess(PGID);
        long duration = (System.currentTimeMillis() - start);
        log.info("Kill all group processes in {} ms", duration);

        return String.format("Killed group processes successfully with GPID %s.", PGID);
    }

    /**
     * Exception handling for better user experience
     * @param ex Exception thrown from application
     * @return HTTP response
     */
    @ExceptionHandler(ProcessCreationException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> handleProcessCreationException(ProcessCreationException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ProcessKillerException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<String> handleProcessKillerException(ProcessKillerException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(ex.getMessage());
    }

}
