package com.company;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Monitor implements Runnable{
    private List<String> urls = new ArrayList<>();
    private int threadCount;
    private List<StListener> studentAddedEventList = new ArrayList<>();
    private List<StListener> studentRemovedEventList = new ArrayList<>();
    private boolean lopped = true;
    private ExecutorService executorService;

    Monitor(List<String> urls, int threadCount) throws MonitorException{
        this.urls = urls;
        this.threadCount = threadCount;
        if(this.urls.size() < this.threadCount)
            throw new MonitorException("Za malo linkow");
    }


    public void addStudentAddedListener(StListener listener){
        this.studentAddedEventList.add(listener);
    }
    public void addStudentRemovedListener(StListener listener){
        this.studentRemovedEventList.add(listener);
    }

    public synchronized void cancel(){
        this.lopped = false;
        notifyAll();

    }

    public synchronized void run(){
        executorService = Executors.newFixedThreadPool(this.threadCount);
        for (String url :
                this.urls) {
            Crawler crawler = new Crawler(url);
            crawler.addStudentAddedListener((st, th)-> {
                for(StListener el : studentAddedEventList)
                    el.handled(st,th);
            });
            crawler.addStudentRemovedListener((st, th)-> {
                for(StListener el : studentRemovedEventList)
                    el.handled(st,th);
            });
            executorService.execute(crawler);
        }

        while(lopped){
            try{
                wait();
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
        }
        System.out.println("MONITOR STOPPED");
        Iterator it = executorService.shutdownNow().iterator();
        while(it.hasNext())
            ((Crawler)it.next()).postCancel();
    }


}
