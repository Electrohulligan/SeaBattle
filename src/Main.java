import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("My Layout");
        jFrame.setSize(600, 600);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new GridBagLayout());
        jFrame.setVisible(true);

        JLabel loginLabel = new JLabel("Login: ");
        JLabel passwordLabel = new JLabel("Password: ");

        JButton loginButton = new JButton("Login in");
        JButton registrationButton = new JButton("Regstration");

        JTextField loginTextField = new JTextField(15);
        JPasswordField passwordTextField = new JPasswordField(15);

    }
}
