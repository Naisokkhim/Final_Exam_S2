import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        SwingUtilities.invokeLater(CustomerInfo::new);

    }
}