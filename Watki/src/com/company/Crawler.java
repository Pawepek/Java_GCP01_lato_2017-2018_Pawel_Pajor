package com.company;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Crawler implements Runnable{
    private List<IterationListener> iterationStartedListeners = new ArrayList<>();
    private List<IterationListener> iterationFinishedListeners = new ArrayList<>();
    private List<StListener> studentAddedListeners = new ArrayList<>();
    private List<StListener> studentNotModifiedListeners = new ArrayList<>();
    private List<StListener> studentRemovedListeners = new ArrayList<>();
    private String url = null;
    private List<Student> students = new ArrayList<>();
    private List<Student> newStudents = new ArrayList<>();

    public boolean lopped = true;

    public synchronized void postCancel(){
        this.lopped = false;
    }

    public enum OrderMode
    {
        MARK,
        FIRST_NAME,
        LAST_NAME,
        AGE;
    }
    public enum ExtremumMode
    {
        MAX,
        MIN
    }

    public List<Student> extractStudents(OrderMode orderMode){
        Comparator<Student> comparator;
        switch(orderMode){
            case AGE:
                System.out.println("POSORTOWANO PO WIEKU");
                comparator = (Student o1, Student o2) -> Integer.compare(o1.getAge(), o2.getAge());
            break;
            case FIRST_NAME:
                System.out.println("POSORTOWANO PO IMIENIU");
                comparator = (Student o1, Student o2) -> o1.getFirstName().compareTo(o2.getFirstName());
            break;
            case LAST_NAME:
                System.out.println("POSORTOWANO PO NAZWISKU");
                comparator = (Student o1, Student o2) -> o1.getLastName().compareTo(o2.getLastName());
            break;
            case MARK:
                System.out.println("POSORTOWANO PO OCENIE");
                comparator = (Student o1, Student o2) -> Double.compare(o1.getMark(), o2.getMark());
            break;
            default:
                System.out.println("POSORTOWANO PO NAZWISKU");
                comparator = (Student o1, Student o2) -> o1.getLastName().compareTo(o2.getLastName());
            break;
        }
        Collections.sort(this.students, comparator);
        return this.students;
    }

    public double extractMark(ExtremumMode mode){
        Comparator<Student> comparator = (Student o1, Student o2) -> Double.compare(o1.getMark(), o2.getMark());
        if(mode == ExtremumMode.MAX)
            return Collections.max(this.students, comparator).getMark();
        return Collections.min(this.students, comparator).getMark();
    }
    public int extractAge(ExtremumMode mode){
        Comparator<Student> comparator = (Student o1, Student o2) -> Integer.compare(o1.getAge(), o2.getAge());
        if(mode == ExtremumMode.MAX)
            return Collections.max(this.students, comparator).getAge();
        return Collections.min(this.students, comparator).getAge();
    }

    Crawler(String url){
        this.url = url;
    }
    Crawler(){}

    public void addEventIterationStartedListener( IterationListener itListener){
        iterationStartedListeners.add(itListener);
    }

    public void removeEvent(IterationListener itListener){
        iterationStartedListeners.remove(itListener);
    }

    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (CrawlerException e) {
            e.printStackTrace();
        }
    }

    public synchronized void start() throws IOException, InterruptedException, CrawlerException{
        int iter = 0;

            if(this.url == null)
                throw new CrawlerException("Nie ustawiono sciezki");
            if (Thread.currentThread().isInterrupted()) {
                System.out.println("Crawler" +Thread.currentThread().getName() + " canceled");
                return;
            }
            try {

                while (this.lopped) {
                    iter++;
                    for (IterationListener el : iterationStartedListeners) {
                        el.handled(iter);
                    }
                    //TODO : zaimplementowac 4 kroki z scenariusza - pobieranie, parsowanie, wykrywania, info o zmianach
                    if(this.url.contains("http://"))
                        this.newStudents = StudentsParser.parse(new URL(this.url));
                    else
                        this.newStudents = StudentsParser.parse(new File(this.url));
                    int i = 0;
                    for (Student newStudent : this.newStudents) {

                        if(this.students.contains(this.newStudents.get(i))){
                            for (StListener sl : this.studentNotModifiedListeners)
                                sl.handled(newStudent, "");
                        }else{
                            this.students.add(newStudent);
                            for (StListener sl : this.studentAddedListeners)
                                sl.handled(newStudent,"");
                        }
                        i++;
                    }
                    List<Integer> removeIds = new ArrayList<Integer>();
                    i=0;
                    for (Student st : this.students) {
                        if(!this.newStudents.contains(st)){
                            for (StListener sl :
                                    this.studentRemovedListeners) {
                             sl.handled(st, "");
                            }
                            removeIds.add(i);
                            i++;
                        }
                    }
                    for (Integer no :
                            removeIds) {
                        this.students.remove(no);
                    }

                    removeIds.clear();
                    this.newStudents.clear();

                    List<Student> sts = this.extractStudents(OrderMode.LAST_NAME);
                    for (Student st :
                            sts) {
                        st.print();
                    }
                    System.out.println("Wiek <" + this.extractAge(ExtremumMode.MAX) + ", " + this.extractAge(ExtremumMode.MIN) + ">");
                    System.out.println("Ocena <" + this.extractMark(ExtremumMode.MAX) + ", " + this.extractMark(ExtremumMode.MIN) + ">");

                    for (IterationListener el : iterationFinishedListeners) {
                        el.handled(iter);
                    }
                    try {
                        Thread.sleep(10000);
                    }catch(InterruptedException ex){
                        System.out.println("Crawler" +Thread.currentThread().getName() + " stopped");
                        return;
                    }
                }
                System.out.print("Thread stopped");
            }catch(MalformedURLException ex){
                System.out.println(ex.getMessage());
            }catch(IOException ex){
                System.out.println(ex.getMessage());
            }

    }

    public void addEventIterationCompletedListener(IterationListener itListener){
        iterationFinishedListeners.add(itListener);
    }

    public void removeEventIterationCompletedListener(IterationListener itListener){
        iterationFinishedListeners.remove(itListener);
    }

    public void addStudentAddedListener(StListener student){
        studentAddedListeners.add(student);
    }

    public void addStudentNotModifiedListener(StListener student){
        studentNotModifiedListeners.add(student);
    }

    public void addStudentRemovedListener(StListener student){
        studentRemovedListeners.add(student);
    }

    public void removeStudentAddedListener(StListener student){
        studentAddedListeners.remove(student);
    }
    public void removeStudentNotModifiedListener(StListener student){ studentNotModifiedListeners.remove(student);}
    public void removeStudentRemovedListener(StListener student){
        studentRemovedListeners.remove(student);
    }

    public void setURL(String url){
        this.url = url;
    }

}
