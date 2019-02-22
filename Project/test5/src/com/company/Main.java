package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.Vector;

public class Main extends JFrame {
    private Connection DBConnect;
    private Statement lines;
    private ResultSet setOutput;
    private JTable DBTable;
    private JComboBox inputfromCombo;
    private TextField inputfromTextField;
    private JButton addSalariedEmployee;
    private JButton addCommissionEmployee;
    private JButton addBasePlusCommissionEmployee;
    private JButton addHourlyEmployee;

    public Main(){
        super("Employee Database");
        String url = "jdbc:sqlite:C:\\Users\\Ming\\Desktop\\test5\\employee.db";
        try{
            DBConnect = DriverManager.getConnection(url);
        } catch(SQLException e){
            System.err.println("Connection Error");
            e.printStackTrace();
            System.exit(1);
        }
        //24.4
        addSalariedEmployee = new JButton("Add Salaried Employee");
        addCommissionEmployee = new JButton("Add Commission Employee");
        addBasePlusCommissionEmployee = new JButton("Add Base Plus Commission Employee");
        addHourlyEmployee = new JButton("Add Hourly Employee");
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        centerPanel.add(addSalariedEmployee);
        addSalariedEmployee.addActionListener(new ButtonHandler());
        centerPanel.add(addCommissionEmployee);
        addCommissionEmployee.addActionListener(new ButtonHandler());
        centerPanel.add(addBasePlusCommissionEmployee);
        addBasePlusCommissionEmployee.addActionListener(new ButtonHandler());
        centerPanel.add(addHourlyEmployee);
        addHourlyEmployee.addActionListener(new ButtonHandler());
        //24.5
        String selectQuery[] = {"Select all employees",
                "Select all employees working in Department SALES",
                "Select hourly employees working over 30 hours.",
                "Select all commission employees in descending order of the commission rate.",
                "Select all base plus  commission employees",
                "Select all commission employees",
                "Select all hourly employees",
                "Select all salaried employees",
        };
        inputfromCombo = new JComboBox(selectQuery);
        JButton inputfromButton = new JButton("Submit query");
        inputfromButton.addActionListener(e -> showDBTable());
        JPanel panel = new JPanel();
        inputfromTextField = new TextField(100);
        inputfromTextField.addActionListener(e -> {
                    try{
                        String newQuery = inputfromTextField.getText();
                        lines = DBConnect.createStatement();
                        if(newQuery.substring(0,6).equals("SELECT")){
                            setOutput = lines.executeQuery(newQuery);
                            displaySet(setOutput);
                        }
                        else if(newQuery.substring(0,6).equals("INSERT")){
                            setOutput = lines.executeQuery(newQuery);
                        }
                        else lines.executeUpdate(newQuery);
                    }
                    catch(SQLException ex){
                        System.err.println("Error");
                        ex.printStackTrace();
                    }
                }
        );
        panel.setLayout(new BorderLayout());
        panel.add(inputfromTextField, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(inputfromCombo, BorderLayout.SOUTH);
        panel.add(inputfromButton, BorderLayout.EAST);
        DBTable = new JTable(10, 10);
        Container getCont = getContentPane();
        getCont.setLayout(new BorderLayout());
        getCont.add(panel, BorderLayout.NORTH);
        getCont.add(DBTable, BorderLayout.SOUTH);
        showDBTable();
        setSize (1000, 500);
        setVisible(true);
    }

    private void showDBTable(){
        try{
            String queryStatement = null;
            int comboQuery = inputfromCombo.getSelectedIndex();
            switch(comboQuery){
                case 0: queryStatement = "SELECT * FROM employees"; break;
                case 1: queryStatement = "SELECT * FROM employees WHERE " + "departmentName = 'SALES'"; break;
                case 2: queryStatement = "SELECT * FROM hourlyEmployees WHERE hours >= 30"; break;
                case 3: queryStatement = "SELECT * FROM commissionEmployees ORDER BY " + "commissionRate DESC"; break;
                case 4: queryStatement = "SELECT * FROM basePlusCommissionEmployees"; break;
                case 5: queryStatement = "SELECT * FROM commissionEmployees"; break;
                case 6: queryStatement = "SELECT * FROM hourlyEmployees"; break;
                case 7: queryStatement = "SELECT * FROM salariedEmployees"; break;
            }
            lines = DBConnect.createStatement();
            if(queryStatement.substring(0,6).equals("SELECT")){
                setOutput = lines.executeQuery(queryStatement);
                displaySet(setOutput);
            }
            else if(queryStatement.substring(0,6).equals("INSERT")){
                setOutput = lines.executeQuery(queryStatement);
            }
            else lines.executeUpdate(queryStatement);
        }
        catch(SQLException ex){
            System.err.println("Error");
            ex.printStackTrace();
        }
    }
    private void displaySet(ResultSet r)throws SQLException {
        boolean numRecords = r.next();
        if (!numRecords) {
            JOptionPane.showMessageDialog(this, "ResultSet contained no records");
            setTitle("No records to display");
            return;
        }
        Vector numColumns = new Vector();
        Vector numRows = new Vector();
        try {
            int i;
            ResultSetMetaData rsmd = r.getMetaData();
            for (i = 1; i <= rsmd.getColumnCount(); i++) numColumns.addElement(rsmd.getColumnName(i));
            do {
                numRows.addElement(nextRows(r, rsmd));
            }
            while (r.next());
            DBTable = new JTable(numRows, numColumns);
            JScrollPane js = new JScrollPane(DBTable);
            Container cont = getContentPane();
            cont.remove(1);
            cont.add(js, BorderLayout.CENTER);
            cont.validate();
        } catch (SQLException ex) {
            System.err.println("Error");
            ex.printStackTrace();
        }
    }
    private Vector nextRows(ResultSet r, ResultSetMetaData rsmd) throws SQLException {
        int i;
        Vector currentRow = new Vector();
        for (i = 1; i <= rsmd.getColumnCount(); ++i)
            switch (rsmd.getColumnType(i)) {
                case Types.VARCHAR:
                case Types.LONGVARCHAR:
                    currentRow.addElement(r.getString(i)); break;
                case Types.INTEGER:
                    currentRow.addElement(r.getLong(i)); break;
                case Types.REAL:
                    currentRow.addElement((float) r.getDouble(i)); break;
                default:
                    currentRow.addElement(r.getString(i)); break;
            }
        return currentRow;
    }
    // inner class ButtonHandler handle button event
    private class ButtonHandler implements ActionListener {
        public void actionPerformed( ActionEvent event ) {
            String socialSecurityNumber = JOptionPane.showInputDialog("Employee Social Security Number");
            String insertQuery = "";
            if (event.getSource() == addSalariedEmployee) {
                double weeklySalary = Double.parseDouble(JOptionPane.showInputDialog("Weekly Salary:"));
                insertQuery = "INSERT INTO salariedEmployees VALUES ( '" + socialSecurityNumber + "', '" + weeklySalary + "', '0' )";
            } else if (event.getSource() == addHourlyEmployee) {
                int hours = Integer.parseInt(JOptionPane.showInputDialog("Hours:"));
                double wage = Double.parseDouble(JOptionPane.showInputDialog("Wage:"));
                insertQuery = "INSERT INTO hourlyEmployees VALUES ( '" + socialSecurityNumber + "', '" + hours + "', '" + wage + "', '0' )";
            } else if (event.getSource() == addCommissionEmployee) {
                int grossSales = Integer.parseInt(JOptionPane.showInputDialog("Gross Sales:"));
                double commissionRate = Double.parseDouble(JOptionPane.showInputDialog("Commission Rate:"));
                insertQuery = "INSERT INTO commissionEmployees VALUES ( '" + socialSecurityNumber + "', '" + grossSales + "', '" + commissionRate + "', '0' )";
            } else if (event.getSource() == addBasePlusCommissionEmployee) {
                int grossSales = Integer.parseInt(JOptionPane.showInputDialog("Gross Sales:"));
                double commissionRate = Double.parseDouble(JOptionPane.showInputDialog("Commission Rate:"));
                double baseSalary = Double.parseDouble(JOptionPane.showInputDialog("Base Salary:"));
                insertQuery = "INSERT INTO basePlusCommissionEmployees " + "VALUES ( '" + socialSecurityNumber + "','"
                        + grossSales + " ', '" + commissionRate + "', '" + baseSalary + "', '0')";
            } else {
                System.out.println("Failed to add");
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

    }
    public void disconnection(){
        try{
            DBConnect.close();
        }
        catch (SQLException ex){
            System.err.println("Disconnection Error");
            ex.printStackTrace();
        }
    }
    public static void main( String[] args) {
        final Main application = new Main();
        application.addWindowListener( new WindowAdapter() {
            public void windowClosing( WindowEvent e) {
                application.disconnection();
                System.exit( 0 );
            }
        }
        );
    }
}