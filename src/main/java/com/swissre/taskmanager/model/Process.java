package com.swissre.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data model for a process
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Process {
    int PID;
    String priority;
    Long creationTimestamp;
    String[] command;
    boolean isLive;

    public Process(String priority, String[] command){
        this.priority = priority;
        this.command = command;
    }

    public Process(int PID, boolean isLive) {
        this.PID = PID;
        this.isLive = isLive;
    }
}
