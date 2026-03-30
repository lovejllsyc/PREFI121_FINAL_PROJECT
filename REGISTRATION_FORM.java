import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class REGISTRATION_FORM extends JFrame {
    private JPanel RegistrationPanel;
    private JTextField txtUsername;
    private JPasswordField txtPasswordField;
    private JButton registerButton;
    private JButton backButton;
    private JButton alreadyAccountButton; // New button

    public REGISTRATION_FORM() {
        setTitle("Register");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(RegistrationPanel);
        setVisible(true);

        // Back button goes to login
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LOG_IN_FORM();
                dispose();
            }
        });

        // Register button logic
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPasswordField.getPassword());

                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields!");
                    return;
                }

                boolean exists = false;
                try {
                    BufferedReader br = new BufferedReader(new FileReader("users.txt"));
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] data = line.split(",");
                        if (data[0].equals(username)) {
                            exists = true;
                            break;
                        }
                    }
                    br.close();
                } catch (IOException ex) {}

                if (exists) {
                    JOptionPane.showMessageDialog(null, "Username already exists!");
                    return;
                }

                try {
                    FileWriter fw = new FileWriter("users.txt", true);
                    fw.write(username + "," + password + "\n");
                    fw.close();
                    JOptionPane.showMessageDialog(null, "Registered Successfully!");
                    new LOG_IN_FORM();
                    dispose();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Already have an account button logic
        alreadyAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new LOG_IN_FORM(); // Open login form
                dispose();          // Close registration form
            }
        });
    }

    public static void main(String[] args) {
        new REGISTRATION_FORM();
    }
}