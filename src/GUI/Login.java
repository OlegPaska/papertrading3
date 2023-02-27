package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Login.LoginHandler;

public class Login extends JPanel{
    // canvas for other GUI widgets
    JFrame frame;
    JButton login;
    JButton signup;
    JLabel usernameLabel;
    JTextField usernameTextField;
    JLabel passwordLabel;
    JTextField passwordTextField;
    JLabel balanceLabel;
    JTextField balanceTextField;
    GUIhandler guiHandler;
    JTextField warningMessage;

    LoginHandler loginHandler = new LoginHandler();


    public Login(JFrame frame, GUIhandler guiHandler) {
        this.frame = frame;
        this.guiHandler = guiHandler;

        System.out.println("GUI SEQUENCE: Login");
        this.setPreferredSize(new Dimension(350, 160));
        setLayout(null);


        usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(40,20, 80, 25);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(130,20, 165, 25);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(40,50, 80, 25);

        passwordTextField = new JTextField();
        passwordTextField.setBounds(130,50, 165, 25);

        balanceLabel = new JLabel("Balance (if signing up)");
        balanceLabel.setBounds(40,80, 80, 25);

        balanceTextField = new JTextField();
        balanceTextField.setBounds(130,80, 165, 25);

        //todo:implement
        warningMessage = new JTextField();




        login = new JButton("Log in");
        login.setBounds(212,110, 80, 25);
        login.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    loginHandler.login(usernameTextField.getText(), passwordTextField.getText());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        signup = new JButton("Sign Up?");
        signup.setBounds(120,110, 80, 25);
        signup.addMouseListener(new MouseAdapter() {
                                    public void mouseClicked(MouseEvent e) {
                                        try {
                                            if(!(usernameTextField.getText().length()>32||passwordTextField.getText().length()>32)) {
                                                loginHandler.signup(usernameTextField.getText(), passwordTextField.getText(), Double.parseDouble(balanceTextField.getText()));
                                            }else{
                                                warningMessage.setText("Invalid username or password: must be below 32 characters");
                                                warningMessage.setVisible(true);
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });

        add(usernameLabel);
        add(usernameTextField);
        add(passwordLabel);
        add(passwordTextField);
        add(login);
        add(signup);
        add(balanceLabel);
        add(balanceTextField);
    }

}
