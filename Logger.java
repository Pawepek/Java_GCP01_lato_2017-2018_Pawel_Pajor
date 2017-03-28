package example;

import javax.mail.MessagingException;
import java.io.Console;


public interface Logger {
    public void log(String status, Student student);
    void log(String status, int iteration);
    void log(Student student);
    public void log(String status);

}



