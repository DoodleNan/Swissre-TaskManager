package com.swissre.taskmanager.model;

public enum Priority {
    HIGH("0"),
    MEDIUM("1"),
    LOW("2");

    private String value;

    Priority(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public int getIntValue() {
        return Integer.parseInt(this.value);
    }

}
