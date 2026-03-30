import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class VIEW_RECORDS extends JFrame {
    private JPanel dashboard;
    private JButton viewLoansButton;
    private JButton logOutButton;
    private JButton viewPenaltiesButton;
    private JTextArea loanshistory;
    private String username;

    public VIEW_RECORDS(String username) {
        this.username = username;

        setTitle("Dashboard");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(dashboard);
        setVisible(true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();

        viewLoansButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loanshistory.setText("");
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

        viewPenaltiesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loanshistor.setText("");
                boolean hasLoans = false;

                try (BufferedReader br = new BufferedReader(new FileReader("loans.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data[0].equals(username)) {
                            hasLoans = true;

                            double totalAmount = Double.parseDouble(data[2]);

                            int loanPeriod = (data.length >= 5) ? Integer.parseInt(data[4]) : 12;

                            LocalDate startDate = (data.length >= 4) ? LocalDate.parse(data[3], formatter)
                                    : today.minusMonths(loanPeriod);

                            double monthlyPayment = totalAmount / loanPeriod;
                            long monthsPassed = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), today.withDayOfMonth(1));
                            long paymentsMade = Math.min(monthsPassed, loanPeriod);

                            long missedPayments = monthsPassed - paymentsMade;
                            double penalty = 0;
                            if (missedPayments > 0) {
                                penalty = monthlyPayment * 0.02 * missedPayments;
                            }

                            if (penalty > 0) {
                                loanshistory.append(
                                        "Loan Total: " + totalAmount +
                                                " | Missed Payments: " + missedPayments +
                                                " | Penalty: " + String.format("%.2f", penalty) + "\n"
                                );
                            } else {
                                loanshistory.append("No penalties have been made.\n");
                            }
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
