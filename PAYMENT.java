import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PAYMENT extends JFrame {
    private JPanel PaymentPanel;
    private JTextField loanDateField;
    private JButton CalculateButton;
    private JButton goToDashboardButton;
    private JTextArea displayInfo;
    private JTextField textField1;

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

        try (FileWriter fw = new FileWriter("loans.txt", true)) {
            fw.write(username + "," + loanAmount + "," + totalAmount + "," +
                    loanPeriod + "," + paymentMethod + "," + name + "," + dob + "," +
                    address + "," + phone + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        setupActions();
    }

    public PAYMENT(String username) {
        this.username = username;

        setTitle("Payment Form");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(PaymentPanel);
        setVisible(true);

        loadExistingLoan();

        setupActions();
    }

    private void setupActions() {
        CalculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (totalAmount == 0) {
                    JOptionPane.showMessageDialog(null, "No loan data found!");
                    return;
                }

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

    private void loadExistingLoan() {
        try (BufferedReader br = new BufferedReader(new FileReader("loans.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    loanAmount = Double.parseDouble(data[1]);
                    totalAmount = Double.parseDouble(data[2]);
                    loanPeriod = data.length > 3 ? data[3] : "12 months";
                    paymentMethod = data.length > 4 ? data[4] : "Cash";
                    name = data.length > 5 ? data[5] : "N/A";
                    dob = data.length > 6 ? data[6] : "N/A";
                    address = data.length > 7 ? data[7] : "N/A";
                    phone = data.length > 8 ? data[8] : "N/A";
                    interestRate = (loanAmount <= 50000) ? 0.10 : 0.15;
                    displayInfo.setText("Welcome back, " + username +
                            "\nLoan Amount: " + loanAmount +
                            "\nTotal Amount: " + totalAmount +
                            "\nClick CALCULATE to check balance.");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
