package spectralApp.view.gui.authorization;

import spectralApp.controller.WorkWithDB;
import spectralApp.view.gui.mainAppForm.MainAppForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Tim on 29.06.2017.
 */
public class AuthorizListener implements ActionListener {
    JTextField name;
    JTextField password;
    JFrame frame;
    WorkWithDB withDB = WorkWithDB.getInstance();
    static int state = 0;

    public AuthorizListener(JTextField name, JPasswordField password, JFrame frame){
        this.name = name;
        this.password = password;
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Confirm")){
            String name = this.name.getText();
            String password = this.password.getText();
            if(withDB.checkUserName(name, password) == 1){
                state = 1;
            }else{
                state = 0;
            }

            if(AuthorizListener.state == 1){
                frame.setVisible(false);
                new MainAppForm("Spectral APP");
            }

        }

        if(e.getActionCommand().equals("Clear")){
                password.setText("");
                name.setText("");
        }

    }
}
