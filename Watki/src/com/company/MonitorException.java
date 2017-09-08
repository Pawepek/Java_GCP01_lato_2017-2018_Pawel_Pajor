package com.company;

public class MonitorException extends Exception{
    private String msg;

    public MonitorException(String message) {
        this.msg = message;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
