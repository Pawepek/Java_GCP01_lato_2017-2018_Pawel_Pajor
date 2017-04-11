import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.*;

import javafx.scene.input.KeyCharacterCombination;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.TextArea;

import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;


public class Main extends Application {

    int group[]= new int[6];



    public static void Main ( String [] args)throws IOException,CrawlerException {
        Crawler cr = new Crawler();
        cr.setAdress(("C:\\Users\\elgha_000\\Desktop\\LAB03-scratch\\students.txt"));
        cr.run();
System.out.println("xD");
        launch(args);

    }

    public void start(Stage primaryStage) throws IOException,CrawlerException {
        primaryStage.setTitle("GUI zajecia 4");







        CustomTabPane root = new CustomTabPane();

        root.setPrefHeight(400);
        root.setPrefWidth(500);

        Scene scene = new Scene(root);


        File plik= new File("C:\\Users\\elgha_000\\Desktop\\LAB03-scratch\\students.txt");
        List<Student> resultStudents;

        resultStudents=StudentsParser.parse(plik);







        final long timestamp = new Date().getTime();

        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        final int minutes = cal.get(Calendar.MINUTE);
        final String timeString =
                new SimpleDateFormat("HH:mm:ss:SSS").format(cal.getTime());



ListView<Student> LV= new ListView<Student>();
        ObservableList<Student> items =FXCollections.observableArrayList (resultStudents);
        LV.setItems(items);

        LV.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>(){

        @Override
public ListCell<Student> call(ListView<Student> p) {

ListCell<Student> cell = new ListCell<Student>(){

       @Override
protected void updateItem(Student t, boolean bln) {
           super.updateItem(t, bln);
           if (t != null) {
               setText(timeString + "\t Age: " + t.getAge() + "\t Mark: " + t.getMark()+"\t First Name: " + t.getFirstName()+"\t Last Name: "+t.getLastName());
           }
       }

        };

return cell;
}
});









        final CategoryAxis xAxis = new CategoryAxis();
final NumberAxis yAxis = new NumberAxis();
final BarChart<String,Number> barChart =new BarChart<>(xAxis,yAxis);
barChart.setCategoryGap(0);
barChart.setBarGap(0);

xAxis.setLabel("Mark");
yAxis.setLabel("Count");

XYChart.Series series1 = new XYChart.Series();
series1.setName("Histogram");



/*
        NumberFormat format = new DecimalFormat("");
        yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return (object.intValue()+"");

            }

            @Override
            public Number fromString(String string) {
                return 0;
            }
        });
*/
        yAxis.setAutoRanging(false);
        yAxis.setTickUnit(1);
        yAxis.setMinorTickVisible(false);
yAxis.setUpperBound(5);
        //yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis){ @Override public String toString(Number object){ return String.format("", object); } });
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis));



prepareData();
series1.getData().add(new XYChart.Data("2.0", group[0]));
series1.getData().add(new XYChart.Data("3.0", group[1]));
series1.getData().add(new XYChart.Data("3.5", group[2]));
series1.getData().add(new XYChart.Data("4.0", group[3]));
series1.getData().add(new XYChart.Data("4.5", group[4]));
series1.getData().add(new XYChart.Data("5", group[5]));
barChart.getData().add(series1);









        ObservableList<Student> StudentList=FXCollections.observableArrayList(resultStudents);











        TableView table= new TableView();

        table.setEditable(true);

        TableColumn markCol = new TableColumn("Mark");
        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn lastNameCol = new TableColumn("Last Name");
        TableColumn ageCol= new TableColumn("Age");
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName"));
        lastNameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("lastName"));
markCol.setCellValueFactory(new PropertyValueFactory<Student,String>("mark"));
ageCol.setCellValueFactory(new PropertyValueFactory<Student,String>("age"));

table.setItems(StudentList);


        table.getColumns().addAll(markCol,firstNameCol, lastNameCol,ageCol );






        TabPane tabPane = new TabPane();
        Tab tab = new Tab();
        tab.setText("Students");
        tab.setContent(new Rectangle(500, 400, Color.LIGHTSTEELBLUE));
        tab.setContent(table);














        Tab tab2 = new Tab();
        tab2.setText("Log");
        tab2.setContent(new Rectangle(500, 400, Color.LIGHTSTEELBLUE));
        tab2.setContent(LV);

        Tab tab3 = new Tab();
        tab3.setText("Histogram");
        tab3.setContent(new Rectangle(500, 400, Color.LIGHTSTEELBLUE));
        tab3.setContent(barChart);

        tabPane.getTabs().addAll(tab,tab2,tab3);







        MenuBar menuBar = new MenuBar();


        Menu menuFile = new Menu("Program");
        Menu menuFile2 = new Menu("About");


        MenuItem menuitem= new MenuItem("Close");
        menuitem.setAccelerator(KeyCharacterCombination.keyCombination("Ctrl+C"));

        menuFile.getItems().add(menuitem);
         MenuItem menuitem2= new MenuItem("About Author");
        menuitem2.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("GUI by Pawel Pajor");
            alert.setContentText("Author: Pawel Pajor!");

            alert.showAndWait();
        });

        menuitem.setOnAction(e -> System.exit(0));
        menuFile2.getItems().add(menuitem2);


        menuBar.getMenus().addAll(menuFile,menuFile2);


        VBox vbox = new VBox(1);

        vbox.getChildren().addAll(menuBar,tabPane);

        ((CustomTabPane)scene.getRoot()).getChildren().addAll(vbox);






        primaryStage.setScene(scene);
        // Pokaż
        primaryStage.show();


    }
    public void prepareData() throws IOException{
        File plik= new File("C:\\Users\\elgha_000\\Desktop\\LAB03-scratch\\students.txt");
        List<Student> resultStudents;

        resultStudents=StudentsParser.parse(plik);

        for(Student student: resultStudents)
        {
            if(student.getMark()==2.0)
                group[0]++;
            if(student.getMark()==3.0)
                group[1]++;
            if(student.getMark()==3.5)
                group[2]++;
            if(student.getMark()==4.0)
                group[3]++;
            if(student.getMark()==4.5)
                group[4]++;
            if(student.getMark()==5.0)
                group[5]++;

        }
    }
}

