import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class VIEW_RECORDS extends JFrame {
    private JPanel dashboard;          // your main panel from drag-and-drop
    private JButton viewLoansButton;   // button from form
    private JButton logOutButton;      // button from form
    private JTextArea loanshistory;    // text area from form
    private String username;

    public VIEW_RECORDS(String username) {
        this.username = username;

        setContentPane(dashboard); // must be first
        setTitle("Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        viewLoansButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loanshistory.setText(""); // clear previous content
                boolean hasLoans = false;

                try (BufferedReader br = new BufferedReader(new FileReader("loans.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data[0].equals(username)) {
                            loanshistory.append("Loan: " + data[1] + "  Total: " + data[2] + "\n");
                            hasLoans = true;
                        }
                    }
                } catch (IOException ex) {
                    loanshistory.append("Error reading loans data.\n");
                }

                if (!hasLoans) {
                    loanshistory.append("No loans yet.\n");
                }
            }
        });

        logOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LOG_IN_FORM();
                dispose();
            }
        });
    }
}