import java.io.File;
import java.io.IOException;
import java.util.*;


public class Crawler {
    private final Logger[] loggers;

    public List<Student> resultStudents;
    public List<Student> previousStudents;
    private String adress;
    static int i = 0;

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


    public Crawler() {
        loggers = new Logger[] { new ConsoleLogger()} ;

        previousStudents = new ArrayList<>();
    }
    public void setAdress(String adress)

    {
        this.adress = adress;
    }

    public String getAdress(){
        return this.adress;
    }

    public void notifyAdded(List<Student> previousStudents,List<Student> resultStudents){
        Student student;
        if(previousStudents == null){
            for(Logger l : loggers){
                for(Student s : resultStudents){
                    l.log(" DODANO: ", s);
                }
            }
        }else
        {
            List<Student> tmp  = new ArrayList<>(resultStudents);
            List<Student> tmp2 = new ArrayList<>(previousStudents);
            tmp.removeAll(tmp2);
            ListIterator<Student> it = tmp.listIterator();
            while(it.hasNext()){
                student = it.next();
                for(Logger l : loggers){
                    l.log("DODANO: ", student);
                }
            }
        }

        notifyIteration();
        System.out.println("Przedzial ocen: <"+extractMark(resultStudents, ExtremumMode.MIN)+","+extractMark(resultStudents,ExtremumMode.MAX)+">");

        System.out.println("Przedzial wieku: <"+extractAge(resultStudents, ExtremumMode.MIN)+","+extractAge(resultStudents,ExtremumMode.MAX)+">\n");
        extractStudents(resultStudents,OrderMode.MARK);
    }
    public void notifyRemoved(List<Student> previousStudents, List<Student> resultStudents){
        Student student;
        if(resultStudents == null){
            for(Logger l : loggers){
                for(Student s : previousStudents){
                    l.log("USUNIETO: ", s);
                }
            }
        }else{
            List<Student> tmp  = new ArrayList<>(previousStudents);
            List<Student> tmp2 = new ArrayList<>(resultStudents);

            tmp.removeAll(tmp2);

            ListIterator<Student> it = tmp.listIterator();

            while(it.hasNext()){
                student = it.next();
                for(Logger l : loggers){
                    l.log("USUNIETO: ", student);
                }
            }
        }

        notifyIteration();
        System.out.println("Zakres wieku: <"+extractAge(resultStudents, ExtremumMode.MIN)+","+extractAge(resultStudents,ExtremumMode.MAX)+">\n");
        System.out.println("Zakres ocen: <"+extractMark(resultStudents, ExtremumMode.MIN)+","+extractMark(resultStudents,ExtremumMode.MAX)+">");
        extractStudents(resultStudents,OrderMode.MARK);
    }

    public void notifyUnchanged(){
        for(Logger l : loggers){
            l.log(" NIC NIE ZMIENIONO ");
        }
    }

    public void notifyIteration(){
        for(Logger l : loggers){
            l.log("ITERACJA: ", i);
        }
    }

    public List<Student> getResults(){
        return resultStudents;
    }

    public void print(List<Student> students){ //wyświetlanie
        for(Student s : students){
            System.out.println( s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge() );
        }
    }

    List<Student> extractStudents( List<Student> students, OrderMode mode ){// posortowani studenci
        List<Student> sortStudents = new ArrayList<>();
        switch(mode){
            case MARK:
                sortStudents = sortMark(students);

                System.out.println("\nPosortowana ze wzgledu na ocenę: \n");
                print(sortStudents);
                break;
            case FIRST_NAME:
                sortStudents = sortName(students);
                System.out.println("\nPosortowana ze wzgledu na imię: \n");
                print(sortStudents);
                break;
            case LAST_NAME:
                sortStudents = sortLastName(students);
                System.out.println("\nPosortowana ze wzgledu na nazwisko: \n");
                print(sortStudents);
                break;
            case AGE:
                sortStudents = sortAge(students);
                System.out.println("\nPosortowana ze wzgledu na wiek: \n");
                print(sortStudents);
                break;
            default:
                System.out.println("\nNiepoprawny status - nie da się posortować.");
        }
        return sortStudents;
    }
    double extractMark(List<Student> students, ExtremumMode mode ){// maksymalna lub minimalna ocena
        double mark = 0.0;
        switch(mode){
            case MAX:
                mark = maxMark(students);
                break;
            case MIN:
                mark = minMark(students);
                break;
            default:
                System.out.println("\nNiepoprawny status.");
        }
        return mark;
    }
    int extractAge(List<Student> students, ExtremumMode mode ){// maksymalny lub minimalny wiek
        int age = 0;
        switch(mode){
            case MAX:
                age = maxAge(students);
                break;
            case MIN:
                age = minAge(students);
                break;
            default:
                System.out.println("\nNiepoprawny status.");
        }

        return age;
    }

    public static List<Student> sortMark(List<Student> students){
        List<Student> resultStudents = new ArrayList<>();
        resultStudents = students;
        Collections.sort(resultStudents,
                (o1, o2) -> Double.compare(o1.getMark(), o2.getMark()));

        return resultStudents;
    }

    public static List<Student> sortName(List<Student> students){
        List<Student> resultStudents = new ArrayList<>();
        resultStudents = students;
        Collections.sort(resultStudents,
                (o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()));
        return resultStudents;
    }

    public static List<Student> sortLastName(List<Student> students){
        List<Student> resultStudents = new ArrayList<>();
        resultStudents = students;
        Collections.sort(resultStudents,
                (o1, o2) -> o1.getLastName().compareTo(o2.getLastName()));
        return resultStudents;
    }

    public static List<Student> sortAge(List<Student> students){
        List<Student> resultStudents = new ArrayList<>();
        resultStudents = students;
        Collections.sort(resultStudents,
                (o1, o2) -> Double.compare(o1.getMark(), o2.getMark()));
        return resultStudents;
    }

    public static double maxMark(List<Student> students){
        Student s =  Collections.max(students, (o1, o2) -> Double.compare(o1.getMark(), o2.getMark()));
        return s.getMark();
    }

    public static double minMark(List<Student> students){
        Student s = Collections.min(students, (o1, o2) -> Double.compare(o1.getMark(), o2.getMark()));
        return s.getMark();
    }

    public static int maxAge(List<Student> students){
        Student s = Collections.max(students, (o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
        return s.getAge();
    }

    public static int minAge(List<Student> students){
        Student s =  Collections.min(students, (o1, o2) -> Integer.compare(o1.getAge(), o2.getAge()));
        return s.getAge();
    }











    public void run() throws  CrawlerException, IOException

    {
        for(; ;){

            File plik = new File( this.getAdress());
            if(plik == null){
                throw new CrawlerException("Nieprawidlowa sciezka pliku");
            }
            resultStudents = new ArrayList<>();
            resultStudents = StudentsParser.parse(plik);
            i++;

            if(previousStudents == null){
                if(!resultStudents.isEmpty()){
                    notifyAdded(previousStudents, resultStudents);
                }
            }
            else if(previousStudents.size() > resultStudents.size()){
                notifyRemoved(previousStudents, resultStudents);

            }
            else if(previousStudents.size() < resultStudents.size()){
                notifyAdded(previousStudents, resultStudents);
            }
//else
            //{
            //notifyUnchanged();

            //}

            previousStudents = resultStudents;



            try {
                Thread.sleep(10000);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

}//class

