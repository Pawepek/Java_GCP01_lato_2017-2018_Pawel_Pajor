package com.company;

/**
 * Created by pnosek on 2017-03-20.
 */
public interface Logger {
    public void log(String status, Student student);
    public void log(String status);
    public void log(Student student);
}
