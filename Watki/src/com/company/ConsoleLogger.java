package com.company;

/**
 * Created by pnosek on 2017-03-20.
 */
public class ConsoleLogger implements Logger{
    public void log(String status, Student student){
        System.out.println(status + " " + student.getMark() + " " + student.getFirstName() + " " + student.getLastName() + " " + student.getAge());
    }
    @Override
    public void log(String status) {
        System.out.println(status);
    }

    @Override
    public void log(Student student) {
        System.out.println(student.getMark()+ " " + student.getFirstName()+ " " + student.getLastName()+ " " + student.getAge());
    }
}