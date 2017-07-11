package spectralApp.controller;

import javax.swing.*;
import java.util.List;

/**
 * Created by Tim on 19.06.2017.
 */
public interface WorkWithFiles {

     int createFile(String str);
     int loadFile(String str); //from basic dir
     int loadFile(String filename, String dir); //from custom dir
     int saveFile(String filename, JTextArea ta);
     int deleteFile(String str);
     int unloadFile(String str);
}
