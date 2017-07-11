package spectralApp.view.gui.authorization;


import spectralApp.view.gui.mainAppForm.MainAppForm;

import javax.swing.*;
import java.awt.*;
import java.time.format.TextStyle;

/**
 * Created by Tim on 29.06.2017.
 */
public class AuthorizationForm extends JFrame {

    AuthorizationForm(String name){
        super(name);

        JPanel generalPan = new JPanel();

        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Clear");

        JLabel nameLabel = new JLabel("Username");
        JLabel passwordLabel = new JLabel("Password");

        JTextField usernameFiled = new JTextField(15);
        JPasswordField passwordFiled = new JPasswordField(15);

        generalPan.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confirmButton);
        confirmButton.addActionListener(new AuthorizListener(usernameFiled, passwordFiled, this));
        buttonPanel.add(cancelButton);
        cancelButton.addActionListener(new AuthorizListener(usernameFiled, passwordFiled, this));


        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.6;
        c.ipadx = 10;
        c.ipady = 5;
        generalPan.add(nameLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.weightx = 0.6;
        generalPan.add(usernameFiled, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.6;
        c.ipadx = 10;
        c.ipady = 5;
        generalPan.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.weightx = 0.6;
        generalPan.add(passwordFiled, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.weightx = 0.5;
        generalPan.add(buttonPanel, c);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(100, 100);

        this.add(generalPan);
        JLabel text = new JLabel("Enter registration data");
        text.setFont(new Font("Times", Font.ITALIC, 16));
        this.add(text, BorderLayout.NORTH);


        pack();


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AuthorizationForm("Spectrum APP");
            }

        });

    }


}
