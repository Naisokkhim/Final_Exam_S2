import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CustomerInfo extends JFrame implements ActionListener {
    private JTextField idField, lastNameField, firstNameField, phoneField;
    private JButton prevButton, nextButton;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public CustomerInfo() {
        setTitle("Customer Info");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        JLabel idLabel = new JLabel("Customer ID:");
        JLabel lastNameLabel = new JLabel("Last Name:");
        JLabel firstNameLabel = new JLabel("First Name:");
        JLabel phoneLabel = new JLabel("Phone:");

        idField = new JTextField();
        lastNameField = new JTextField();
        firstNameField = new JTextField();
        phoneField = new JTextField();

        idField.setEditable(false);
        lastNameField.setEditable(false);
        firstNameField.setEditable(false);
        phoneField.setEditable(false);

        prevButton = new JButton("Previous");
        nextButton = new JButton("Next");

        prevButton.addActionListener(this);
        nextButton.addActionListener(this);

        add(idLabel);
        add(idField);
        add(lastNameLabel);
        add(lastNameField);
        add(firstNameLabel);
        add(firstNameField);
        add(phoneLabel);
        add(phoneField);
        add(prevButton);
        add(nextButton);

        connectToDatabase();
        fetchFirstRecord();

        setVisible(true);
    }

    private void connectToDatabase() {
        try {

            String url = "jdbc:mysql://localhost:2003/Final_Exam";
            String user = "root";
            String password = "root";

            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery("SELECT * FROM CustomerInfo");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fetchFirstRecord() {
        try {
            if (resultSet.first()) {
                displayRecord();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayRecord() {
        try {
            idField.setText(resultSet.getString("customer_id"));
            lastNameField.setText(resultSet.getString("customer_last_name"));
            firstNameField.setText(resultSet.getString("customer_first_name"));
            phoneField.setText(resultSet.getString("customer_phone"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == prevButton) {
                if (resultSet.previous()) {
                    displayRecord();
                } else {
                    resultSet.first();
                    displayRecord();
                }
            } else if (e.getSource() == nextButton) {
                if (resultSet.next()) {
                    displayRecord();
                } else {
                    resultSet.last();
                    displayRecord();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
