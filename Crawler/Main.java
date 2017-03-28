package example;
import java.util.logging.Logger;
import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import java.io.IOException;
import java.util.logging.Level;





public class Main
{

    public static void main( String[] args ) throws IOException, CrawlerException, InterruptedException
    {
        try {
            Crawler cr = new Crawler();
            cr.setAdress(("C:\\Users\\elgha_000\\Desktop\\LAB03-scratch\\students.txt"));
            cr.run();
        } catch (MessagingException exception) {
            exception.printStackTrace();
        }

    }

}
