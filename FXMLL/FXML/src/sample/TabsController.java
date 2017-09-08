package sample;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Observable;


public class TabsController {

    private ObservableList<Student> studentsList;
    @FXML
    TableView<Student> tableView;
    @FXML
    TabPane tabPane;

    @FXML
    TableColumn<Student, Double> markColumn;
    @FXML
    TableColumn<Student, String> firstNameColumn;
    @FXML
    TableColumn<Student, String> lastNameColumn;
    @FXML
    TableColumn<Student, Integer> ageColumn;
    @FXML
    ListView logger;

    @FXML
    BarChart barChart;

    @FXML
    CategoryAxis catAxis;

    @FXML
    NumberAxis numAxis;

    private HashMap<Double, XYChart.Data<String, Integer>> values;
    private XYChart.Series<String, Integer> series = new XYChart.Series<>();

    private SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");

    private ObservableList<String> logs = FXCollections.observableArrayList();

    public ObservableList<Student> getStudentsList(ObservableList<Student> st){
        return this.studentsList;
    }

    @FXML
    public void initialize(){
        try {
            this.studentsList = FXCollections.observableArrayList(StudentsParser.parse(new URL("http://pretoryjoe.pdg.pl/st.txt")));
            markColumn.setCellValueFactory(cellData -> cellData.getValue().getMarkProp().asObject());
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().getFirstNameProp());
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().getLastNameProp());
            ageColumn.setCellValueFactory(cellData -> cellData.getValue().getAgeProp().asObject());

            tableView.setItems(this.studentsList);
            logger.setItems(this.logs);
            this.studentsList.addListener((ListChangeListener<Student>) e->{
                while(e.next()){
                    if(e.wasAdded()){
                        for (Student st :
                                e.getAddedSubList()) {
                            log("NEW", st);
                            updateAdded(st.getMark());
                        }
                    }

                    if(e.wasRemoved()){
                        for (Student st :
                                e.getRemoved()) {
                            log("REMOVED", st);
                            updateRemoved(st.getMark());
                        }
                    }
                }
            });

        }catch (IOException ex){

        }

        this.values = new HashMap<Double, XYChart.Data<String, Integer>>();
        this.values.put(2.0, new XYChart.Data<String, Integer>("2.0",0));
        this.values.put(2.5, new XYChart.Data<String, Integer>("2.5",0));
        this.values.put(3.0, new XYChart.Data<String, Integer>("3.0",0));
        this.values.put(3.5, new XYChart.Data<String, Integer>("3.5",0));
        this.values.put(4.0, new XYChart.Data<String, Integer>("4.0",0));
        this.values.put(4.5, new XYChart.Data<String, Integer>("4.5",0));
        this.values.put(5.0, new XYChart.Data<String, Integer>("5.0",0));

        series.setName("Mark");
        series.getData().addAll(
                this.values.get(2.0),
                this.values.get(2.5),
                this.values.get(3.0),
                this.values.get(3.5),
                this.values.get(4.0),
                this.values.get(4.5),
                this.values.get(5.0)
        );
        barChart.getData().addAll(series);
        updateChart(this.studentsList);

        //this.tabPane.getTabs().get(0).
    }

    public void log(String status, Student st){
        this.logs.add(date.format(Calendar.getInstance().getTime()) + " [" + status + "] " + st.getLastName() + "" + st.getLastName() + " "+ st.getMark() + " " + st.getAge());
    }

    public void addStudent(){
        this.studentsList.add(new Student("Piotr", "Nosek", 4.5, 21));
    }
    public void removeStudent(){
        if(!this.studentsList.isEmpty())
            this.studentsList.remove(this.studentsList.size()-1);
    }

    public void updateChart(ObservableList<Student> studentsList){
        numAxis.setUpperBound(studentsList.size()+1);
        for (Student st :
                studentsList) {
            XYChart.Data<String, Integer> col = values.get(st.getMark());
            Integer s = col.getYValue();
            col.setYValue(++s);
        }
    }

    public void updateAdded(Double mark){
        numAxis.setUpperBound(numAxis.getUpperBound()+1);
        XYChart.Data<String, Integer> col = values.get(mark);
        col.setYValue(col.getYValue()+1);
    }
    public void updateRemoved(Double mark){
        numAxis.setUpperBound(numAxis.getUpperBound()-1);
        XYChart.Data<String, Integer> col = values.get(mark);
        col.setYValue(col.getYValue()-1);
    }
}
