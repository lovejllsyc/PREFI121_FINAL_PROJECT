import javax.swing.*;
import java.awt.event.*;

public class LOAN_INFORMATION extends JFrame {
    private JPanel LoanInformation;
    private JTextField fullname, birth, add, cnum;
    private JButton proceedButton;
    private JButton alreadyHaveALoanButton;
    private String username;

    public LOAN_INFORMATION(String username) {
        this.username = username;

        setTitle("Loan Information");
        setContentPane(LoanInformation);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Already Have Loan Button
        alreadyHaveALoanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PAYMENT(username);
                dispose();
            }
        });

        // Proceed Button
        proceedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = fullname.getText().trim();
                String dob = birth.getText().trim();
                String address = add.getText().trim();
                String phone = cnum.getText().trim();

                if (name.isEmpty() || dob.isEmpty() || address.isEmpty() || phone.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }

                new LOAN_CALCULATION(username, name, dob, address, phone);
                dispose();
            }
        });
    }
}