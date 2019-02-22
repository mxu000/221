//Mingzhi Xu
package com.company;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.sql.*;
import java.util.Optional;

public class Main extends Application {
    private Connection DBConnect;
    String url = "jdbc:sqlite:C:\\Users\\Ming\\Desktop\\test6\\employee.db";
    private Stage stage;
    private Statement lines;
    private Button addEmployee, addSalariedEmployee, addCommissionEmployee, addBasePlusCommissionEmployee, addHourlyEmployee, Submit;
    private ComboBox comboBox;
    private ResultSet setOutput;
    private ObservableList<ObservableList> data = FXCollections.observableArrayList();
    private TableView tableview;
    private VBox vbox;
    private TextField input;

    @Override
    public void start(Stage primaryStage) {
        //connect to database
        try{
            DBConnect = DriverManager.getConnection(url);
            System.out.println("Connect Success");
        } catch(SQLException e){
            System.err.println("Connection Error");
            e.printStackTrace();
            System.exit(1);
        }
        //24.4 Add employee and employee infos
        addEmployee = new Button("Add New Employee");
        addSalariedEmployee = new Button("Add Salaried Employee");
        addCommissionEmployee = new Button("Add Commission Employee");
        addBasePlusCommissionEmployee = new Button("Add Base Plus Commission Employee");
        addHourlyEmployee = new Button("Add Hourly Employee");
        addEmployee.setOnAction(e -> actionPerformed(addEmployee));
        addSalariedEmployee.setOnAction(e -> actionPerformed(addSalariedEmployee));
        addCommissionEmployee.setOnAction(e -> actionPerformed(addCommissionEmployee));
        addBasePlusCommissionEmployee.setOnAction(e -> actionPerformed(addBasePlusCommissionEmployee));
        addHourlyEmployee.setOnAction(e -> actionPerformed(addHourlyEmployee));
        HBox addButtons = new HBox(8);
        addButtons.getChildren().addAll(addEmployee, addSalariedEmployee, addCommissionEmployee,
                addBasePlusCommissionEmployee, addHourlyEmployee);
        //24.5 Show Table and Query
        input = new TextField();
        input.setPrefWidth(750);
        input.setOnAction(e -> {
                    try{
                        String newQuery = input.getText();
                        lines = DBConnect.createStatement();
                        exeQ(newQuery);
                    }
                    catch(SQLException ex){
                        System.err.println("Error");
                        ex.printStackTrace();
                    }
                }
        );
        Submit = new Button("Submit Query");
        Submit.setOnAction(event -> {
            tableview.getItems().clear();
            tableview.getColumns().clear();
            showDBTable();
            tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        });
        ObservableList<String> options = FXCollections.observableArrayList(
                "Select all employees",
                "Select all base plus commission employees",
                "Select all commission employees",
                "Select all hourly employees",
                "Select all salaried employees",
                "Select all employees working in Department SALES",
                "Select hourly employees working over 30 hours",
                "Select all commission employees in descending order of the commission rate",
                "Increase base salary by 10% for all base plus commission employees",
                "If the employee's birthday is in the current month, add a $100 bonus",
                "For all commission employees with gross sales over $10000, add a $100 bonus"
                );
        comboBox = new ComboBox(options);
        HBox inQ = new HBox(20);
        inQ.getChildren().addAll(input);
        HBox comSub = new HBox(20);
        comSub.getChildren().addAll(comboBox, Submit);
        BorderPane pane = new BorderPane();
        pane.setTop(inQ);
        pane.setCenter(addButtons);
        pane.setBottom(comSub);
        tableview = new TableView();
        vbox = new VBox();
        vbox.getChildren().add(tableview);
        BorderPane pane1 = new BorderPane();
        pane1.setTop(pane);
        pane1.setCenter(vbox);
        Scene scene = new Scene(pane1, 1200, 600);
        stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    //function for addbuttons
    public void actionPerformed( Button event) {
        String socialSecurityNumber = showTextInput("Employee Social Security Number", "Enter Empployee's SSN", "xxx-xx-xxxx");
        String insertQuery = "";
        if (event == addEmployee) {
            String fName = showTextInput("Add Employee Info","Enter First Name", "First");
            String lName = showTextInput("Add Employee Info","Enter Last Name", "Last");
            String day = showTextInput("Add Employee Info","Enter Employee Birthday", "xxxx-xx-xx");
            String eType = showTextInput("Add Employee Info","Enter Employee Type", "salariedEmployee");
            String dName = showTextInput("Add Employee Info","Enter Department Name", "SALES");
            insertQuery = "INSERT INTO employees VALUES ( '" + socialSecurityNumber + "', '" + fName + "', '" + lName
                    + "', '" + day + "', '" + eType + "', '" + dName + "')";
        }
        else if (event == addSalariedEmployee) {
            double weeklySalary = Double.parseDouble(showTextInput("Add Salaried Employee Info","Enter Weekly Salary", "0.0"));
            insertQuery = "INSERT INTO salariedEmployees VALUES ( '" + socialSecurityNumber + "', '" + weeklySalary + "', '0' )";
        } else if (event == addHourlyEmployee) {
            int hours = Integer.parseInt(showTextInput("Add Hourly Employee Info","Enter Hours", "0"));
            double wage = Double.parseDouble(showTextInput("Add Hourly Employee Info","Enter Wage", "0.0"));
            insertQuery = "INSERT INTO hourlyEmployees VALUES ( '" + socialSecurityNumber + "', '" + hours + "', '" + wage + "', '0' )";
        } else if (event == addCommissionEmployee) {
            int grossSales = Integer.parseInt(showTextInput("Add Commission Employee Info","Enter Gross Sales", "0"));
            double commissionRate = Double.parseDouble(showTextInput("Add Commission Employee Info","Enter Commission Rate", "0.00"));
            insertQuery = "INSERT INTO commissionEmployees VALUES ( '" + socialSecurityNumber + "', '" + grossSales + "', '" + commissionRate + "', '0' )";
        } else if (event == addBasePlusCommissionEmployee) {
            int grossSales = Integer.parseInt(showTextInput("Add Base Plus Commission Employee Info","Enter Gross Sales", "0"));
            double commissionRate = Double.parseDouble(showTextInput("Add Base Plus Commission Employee Info","Enter Commission Rate", "0.00"));
            double baseSalary = Double.parseDouble(showTextInput("Add Base Plus Commission Employee Info","Enter Base Salary", "0.00"));
            insertQuery = "INSERT INTO basePlusCommissionEmployees " + "VALUES ( '" + socialSecurityNumber + "','"
                    + grossSales + " ', '" + commissionRate + "', '" + baseSalary + "', '0')";
        } else {
            System.out.println("Fail to Insert");
        }
        // execute insert query
        try
        {
            lines = DBConnect.createStatement();
            lines.executeUpdate(insertQuery);
        }
        catch ( SQLException exception)
        {
            System.err.println("Error");
            exception.printStackTrace();
        }
    }
    //User Input Box
    public static String showTextInput(String title, String message, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Input");
        dialog.setHeaderText(title);
        dialog.setContentText(message);
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }
    //Combo Box Statement
    private void showDBTable(){
        try{
            String queryStatement = null;
            String s = comboBox.getSelectionModel().getSelectedItem().toString();
            switch(s){
                case "Select all employees":
                    queryStatement = "SELECT * FROM employees"; break;
                case "Select all base plus commission employees":
                    queryStatement = "SELECT * FROM basePlusCommissionEmployees"; break;
                case "Select all commission employees":
                    queryStatement = "SELECT * FROM commissionEmployees"; break;
                case "Select all hourly employees":
                    queryStatement = "SELECT * FROM hourlyEmployees"; break;
                case "Select all salaried employees":
                    queryStatement = "SELECT * FROM salariedEmployees"; break;
                case "Select all employees working in Department SALES":
                    queryStatement = "SELECT * FROM employees WHERE " + "departmentName = 'SALES'"; break;
                case "Select hourly employees working over 30 hours":
                    queryStatement = "SELECT * FROM hourlyEmployees WHERE hours >= 30"; break;
                case "Select all commission employees in descending order of the commission rate":
                    queryStatement = "SELECT * FROM commissionEmployees ORDER BY " + "commissionRate DESC"; break;
                case "Increase base salary by 10% for all base plus commission employees":
                    queryStatement = "UPDATE basePlusCommissionEmployees SET " + "baseSalary = baseSalary * 1.1";
                    lines = DBConnect.createStatement();
                    exeQ(queryStatement);
                    queryStatement = "SELECT * FROM basePlusCommissionEmployees"; break;
                case "For all commission employees with gross sales over $10000, add a $100 bonus":
                    queryStatement = "UPDATE commissionEmployees SET " + "bonus = bonus + 100.00 WHERE grossSales >= 10000";
                    lines = DBConnect.createStatement();
                    exeQ(queryStatement);
                    queryStatement = "SELECT * FROM commissionEmployees"; break;
                case "If the employee's birthday is in the current month, add a $100 bonus":
                    bdBonus();
                    queryStatement = "SELECT * FROM employees"; break;
            }
            lines = DBConnect.createStatement();
            exeQ(queryStatement);
        }
        catch(SQLException ex){
            System.err.println("Error");
            ex.printStackTrace();
        }
    }
    //Birthday Bonus
    private void bdBonus() throws SQLException {
        String day, ebdMonth;
        String bdMonth = showTextInput("Birthday Bonus to Employees","Enter Current Month", "xx");
        try{
            lines = DBConnect.createStatement();
            setOutput = lines.executeQuery("SELECT * FROM employees");
            while(setOutput.next()){
                day = setOutput.getString(4);
                ebdMonth = day.substring(5, 7);
                checkbd(ebdMonth, bdMonth);
            }
        }
        catch (SQLException e){ e.printStackTrace();}
    }

    private void checkbd(String ebdMonth, String bdMonth) throws SQLException {
        String ssn;
        String employType;
        if(ebdMonth.equals(bdMonth)){
            ssn = setOutput.getString(1);
            employType = setOutput.getString(5);
            String q = "UPDATE " + employType + "s SET " + "bonus = bonus + 100.00 WHERE socialSecurityNumber = "
                    + "'" + ssn + "'";
            exeQ(q);
        }
    }

    //Execute Query Statement
    private void exeQ(String queryStatement) throws SQLException {
        lines = DBConnect.createStatement();
        if(queryStatement.substring(0,6).equals("SELECT")){
            setOutput = lines.executeQuery(queryStatement);
            displaySet(setOutput);
        }
        else lines.executeUpdate(queryStatement);
    }
    //Display Table
    private void displaySet(ResultSet r)throws SQLException {
        tableview.getItems().clear();
        tableview.getColumns().clear();
        boolean numRecords = r.next();
        if (!numRecords) {
            System.out.println("No records to display");
            return;
        }
        try {
            int i;
            for (i = 0; i < r.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(r.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>)
                        param -> new SimpleStringProperty(param.getValue().get(j).toString()));
                tableview.getColumns().addAll(col);
            }
            ObservableList<String> frow = FXCollections.observableArrayList();
            for (i = 0; i < r.getMetaData().getColumnCount(); i++) {
                frow.add(r.getString(i + 1));
            }
            data.add(frow);
            while (r.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (i = 0; i < r.getMetaData().getColumnCount(); i++) {
                    row.add(r.getString(i + 1));
                    }
                    data.add(row);
            }
            tableview.setItems(data);
        }catch(SQLException ex){
            System.err.println("Error");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}