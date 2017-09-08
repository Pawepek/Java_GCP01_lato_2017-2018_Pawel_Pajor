package com.company;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException{
        final Logger[] loggers = new Logger[]
                {
                        new ConsoleLogger(),

                };
            List<String> urls = new ArrayList<>();
            for(int i=1;i<=5;i++){
                urls.add("http://pretoryjoe.pdg.pl/st"+i+".txt");
            }
            try {
                ParallelLogger parallelLogger = new ParallelLogger();
                Monitor monitor = new Monitor(urls, 5);
                monitor.addStudentAddedListener((st,th)->parallelLogger.log("Thread"+th + " Added: ",st));
                monitor.addStudentRemovedListener((st, th)->parallelLogger.log("Thread"+th + " Removed: ",st));
                Thread th = new Thread(monitor);
                th.start();
                Thread.sleep(3000);
                monitor.cancel();
            }catch(MonitorException ex){
                ex.printStackTrace();
            }

    }
}
