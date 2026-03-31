import javax.swing.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;

public class LOAN_CALCULATION extends JFrame {
    private JPanel LoanCalculationPanel;
    private JTextField loanAmmount;
    private JComboBox<String> loanPeriod;
    private JComboBox<String> paymentMethod;
    private JButton proceedButton;

    private String username, name, dob, address, phone;
    private double interestRate, totalAmount;

    public LOAN_CALCULATION(String username, String name, String dob, String address, String phone) {
        this.username = username;
        this.name = name;
        this.dob = dob;
        this.address = address;
        this.phone = phone;

        setTitle("Loan Calculation");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setContentPane(LoanCalculationPanel);

        proceedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double amount;
                try {
                    amount = Double.parseDouble(loanAmmount.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid amount!");
                    return;
                }

                if (amount <= 50000) interestRate = 0.10;
                else interestRate = 0.15;
                totalAmount = amount + (amount * interestRate);

                new PAYMENT(username, name, dob, address, phone, amount, interestRate, totalAmount,
                        loanPeriod.getSelectedItem().toString(), paymentMethod.getSelectedItem().toString());
                dispose();
            }
        });
    }
}
