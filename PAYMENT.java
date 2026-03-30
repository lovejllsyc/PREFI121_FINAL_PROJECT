import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PAYMENT extends JFrame {
    private JPanel PaymentPanel;
    private JTextField loanDateField;
    private JButton CalculateButton;
    private JButton goToDashboardButton;
    private JTextArea displayInfo;
    private JTextPane pleaseEnterTheDateTextPane;

    private String username, name, dob, address, phone, loanPeriod, paymentMethod;
    private double loanAmount, interestRate, totalAmount;

    public PAYMENT(String username, String name, String dob, String address, String phone,
                   double loanAmount, double interestRate, double totalAmount,
                   String loanPeriod, String paymentMethod) {

        this.username = username;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.totalAmount = totalAmount;
        this.loanPeriod = loanPeriod;
        this.paymentMethod = paymentMethod;

        setTitle("Payment Form");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(PaymentPanel);
        setVisible(true);

        try {
            FileWriter fw = new FileWriter("loans.txt", true);
            fw.write(username + "," + loanAmount + "," + totalAmount + "\n");
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        CalculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String startDateStr = loanDateField.getText().trim();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate startDate;

                try {
                    startDate = LocalDate.parse(startDateStr, formatter);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Enter date in yyyy-MM-dd format!");
                    return;
                }

                int termMonths;
                switch (loanPeriod) {
                    case "6 months" -> termMonths = 6;
                    case "12 months" -> termMonths = 12;
                    case "24 months" -> termMonths = 24;
                    default -> termMonths = 12;
                }

                double monthlyPayment = totalAmount / termMonths;

                String input = JOptionPane.showInputDialog("How many payments have you completed? (numbers only)");
                int paymentsMade = 0;
                try {
                    paymentsMade = Integer.parseInt(input);
                    if (paymentsMade < 0 || paymentsMade >= termMonths) paymentsMade = 0;
                } catch (Exception ex) {
                    paymentsMade = 0;
                }

                LocalDate nextDueDate = startDate.plusMonths(paymentsMade + 1);
                double remainingBalance = totalAmount - (monthlyPayment * paymentsMade);

                displayInfo.setText(
                        "Name: " + name +
                                "\nDate of Birth: " + dob +
                                "\nAddress: " + address +
                                "\nPhone: " + phone +
                                "\nLoan Amount: " + loanAmount +
                                "\nInterest Rate: " + (interestRate * 100) + "%" +
                                "\nTotal Amount: " + totalAmount +
                                "\nLoan Period: " + loanPeriod +
                                "\nPayment Method: " + paymentMethod +
                                "\nNext Due Date: " + nextDueDate +
                                "\nRemaining Balance: " + String.format("%.2f", remainingBalance)
                );
            }
        });

        goToDashboardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new VIEW_RECORDS(username);
                dispose();
            }
        });
    }
}
