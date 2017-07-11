package spectralApp.view.gui.mainAppForm;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by Tim on 27.06.2017.
 */
public class AppHighLightListener implements ActionListener {

    JTextArea ta;

    static List<Integer> positions = AppSearchListener.getHighLightsPositions();
    static String regex;
    static int numberOfCurrentPos = 0;
    String source;
    static String tmpSource = "";

    AppHighLightListener(JTextArea ta, String source){
        this.ta = ta;
        this.source = source;

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ta.requestFocus();

        if(positions.size() >0) {
            regex = AppSearchListener.getHighLightsRegex();
            if(!tmpSource.equals(regex) || regex.length() == 0) {
                numberOfCurrentPos = 0 ;
                ta.setCaretPosition(positions.get(numberOfCurrentPos));
                tmpSource = regex;
            }


            if (source.equals("previous")) {
                if (numberOfCurrentPos > 0) {
                    numberOfCurrentPos--;
                    ta.setCaretPosition(positions.get(numberOfCurrentPos));
                }
            }

            if (source.equals("next")) {
                if (numberOfCurrentPos < positions.size()-1) {
                    numberOfCurrentPos++;
                    ta.setCaretPosition(positions.get(numberOfCurrentPos));
                }
            }
        }else{
            numberOfCurrentPos = 0;
        }
    }
}
