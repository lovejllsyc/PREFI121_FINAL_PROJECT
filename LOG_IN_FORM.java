import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class LOG_IN_FORM extends JFrame {
    private JPanel loginPanel;
    private JTextField username2;
    private JPasswordField password2;
    private JButton logInButton;
    private JButton backButton;

    public LOG_IN_FORM() {
        setTitle("Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        // Assuming loginPanel and components are already initialized via GUI designer
        add(loginPanel); // Add the panel to JFrame

        // Login button action
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = username2.getText();
                String password = new String(password2.getPassword());

                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }

                boolean found = false;
                try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data[0].equals(username) && data[1].equals(password)) {
                            found = true;
                            break;
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error reading user data!");
                    return;
                }

                if (found) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new LOAN_INFORMATION(username); // Pass username to next form
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!");
                }
            }
        });

        // Back/Register button action
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new REGISTRATION_FORM();
                dispose();
            }
        });

        setVisible(true); // Make frame visible after adding components and listeners
    }

    public static void main(String[] args) {
        new LOG_IN_FORM();
    }
}