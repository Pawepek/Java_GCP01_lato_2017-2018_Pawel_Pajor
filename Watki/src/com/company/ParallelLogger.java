package com.company;

public class ParallelLogger implements Logger {
    final Logger[] loggers = new Logger[]
            {
                    new ConsoleLogger()
            };
    @Override
    public void log(String status, Student student) {
        synchronized (this){
            for(Logger logger : loggers)
                logger.log(status,student);
            notifyAll();
        }
    }

    @Override
    public void log(String status) {

    }

    @Override
    public void log(Student student) {

    }
}
