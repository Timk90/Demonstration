package spectralApp.view.gui.mainAppForm;

import javafx.stage.FileChooser;
import spectralApp.controller.WorkWithTxtFiles;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by Tim on 20.06.2017.
 */

/*
Этот класс слушателей отвечает за обработку событий, связанных с нажатием кнопок и элементов меню навигационной панели
 главным образом тут сосредоточены действия, связанные с сохранением, нажатием, удалением файлов и т.д.
 */

public class AppButtonListeners implements ActionListener{
    //в список попадает контент файла - т.е. текст
    List<String> content = new ArrayList<>();
    //имя загружаемого файла
    String filename = "";
    // получение сущности (синглтона - единственного экземпляра класса) класса работы с файлами
    //обеспечивающего реализацию работы с текстовыми файлами
    WorkWithTxtFiles wwf = WorkWithTxtFiles.getInstance();
    //объявление переменной текстовой области - сюда будет передаваться ссылка на канву
    JTextArea component;
    //а сюда ссылка на главное окно (фрейм) приложения
    JFrame frame;
    Path path;
    //флажок - показывает сохранен ли фйл или нет
    static boolean saved = true;

    //несколько конструктоов класса, в зависимости от передаваемых (инжектирруемых) объектов фрейма
    AppButtonListeners(){}
    AppButtonListeners(JTextArea component){ this.component = component;}
    AppButtonListeners(JTextArea component, JFrame frame){
        this.component = component;
        this.frame = frame;
    }
//        AppButtonListeners(JTextArea component, Path path){
//        this.path = path;
//        this.component = component;}

    //главный метод слушателя кнопок, в котором и реализуется вся логика обработки события
    @Override
        public void actionPerformed(ActionEvent e) {

        //экшн команд предоставляет ссылку на указанное пользователем имя компонетна в главной форме
        //котором произошло событие - нажатие на кнопку
        //если оно совпадает со строкой, указанной в методе equals , то выполняется тело условного оператора иф
            if (e.getActionCommand().equals("New") || e.getActionCommand().equals("New file")) {
                //проверка был ли текущий  файл сохранен перед созданием нового файла
                if(saved) {
                    //если сохранен , то создается новый дефолтный текстовый файл
                    createNewTxtFile();
                }else{
                    //если файл не был сохранен , то появляется диалоговое окно
                    //с уточнением необходимости сохранения файла перед созданием нового
                    int answer = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to save current file?",
                            "Save or not...",
                            JOptionPane.YES_NO_OPTION );
                    //если выбор пользователя сохранить файл, то вызывается функция отвечающая за появление
                    //диалога сохранения файла
                    if(answer == 0){
                        fileSavingMode();
                        component.setText(null);
                        saved = true;
                    //в противно случае текстовая панель просто очищается
                    }else if(answer == 1){
                        component.setText(null);
                        saved = true;
                    }
                    //после сохранения текущего файла (или отказа от сохранения) создается новый файл
                    createNewTxtFile();
                }
            }

            //выход из приложения при нажатии кнопки "exit"
            if (e.getActionCommand().equals("Exit")) {
                    //вызов диалого подтверждения намерения выйти из приложения
                    int answer = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to close the application",
                            "Exiting...",
                            JOptionPane.YES_NO_OPTION);

                    if (answer == 0) {
                        exit(1);
                    }
            }

            //загрузка файла из выбранного пользователем места в проводнике
            if (e.getActionCommand().equals("Load")) {
                //проверка - был ли файл сохранен и если не был, то вызов диалога с
                //предложением сохранить его в указанное место
                if (!saved) {
                    int answer = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to save current file?",
                            "Save or not...",
                            JOptionPane.YES_NO_OPTION);
                    //если был выбран ответ "да"
                    if (answer == 0) {
                        //то сохранение файла и очистка текстовой панели
                        fileSavingMode();
                        component.setText(null);
                        saved = true;
                    //если был выбран ответ "нет"
                    } else if (answer == 1) {
                        //просто очистка панели
                        component.setText(null);
                        saved = true;
                    }
                }
                    //объявление файлЧузера
                    JFileChooser fc = new JFileChooser();
                    //открытие диалога проводника для выбора файла (пути к нему)
                    Integer value = fc.showOpenDialog(null);
                    if (value == JFileChooser.APPROVE_OPTION) {
                        //присвоение чузеру ссылки на выбранный файл
                        File choosenFile = fc.getSelectedFile();
                        //получить абсолютный путь к выбранному файлу
                        String filePath = choosenFile.getAbsolutePath();
                        //отделение директории в которой находится файл от всего пути
                        String fileDir = filePath.substring(0, filePath.lastIndexOf("\\") + 1);
                        //отделение имени файла с расширением от всего пути
                        String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
                        //загрузка файла и размещение содержимого в текстовой панели
                        wwf.loadFile(fileName, fileDir);
                        for (String line : wwf.getContent()) {
                            component.append(line + "\n");
                        }
                        component.setEnabled(true);
                    }

            }

            //если была нажата кнопка сохранения файла
            if(e.getActionCommand().equals("Save")){
                //часто повторяющийся код решил вынести в отдельную функицию.
//                JFileChooser fc = new JFileChooser();
//                Integer value = fc.showSaveDialog(null);
//                if(value == JFileChooser.APPROVE_OPTION) {
//                    File chosenFile = fc.getSelectedFile();
//                    String filePath = chosenFile.getAbsolutePath();
//                    wwf.saveFile(filePath, component);
//
//                }
                fileSavingMode();
            }

            //удаление открытого (текущего) файла
            if(e.getActionCommand().equals("Delete")){
                int answer = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to delete current file?",
                        "Deleting current file",
                        JOptionPane.YES_NO_OPTION );
                if(answer == 0) {
                    wwf.deleteCurrentFile();
                    component.setText(null);
                    component.setEnabled(false);
                    saved = true;
                }
            }

            //очистка текстовой панели от всего текста при нажатии кнопки "clear"
            if(e.getActionCommand().equals("Clear")){
                int answer = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure you want to clear all the text?",
                        "Clear option",
                        JOptionPane.YES_NO_OPTION );
                if(answer == 0) {
                    component.setText(null);
                }
            }
        }

        //вынесенный отдельно код сохранения текущего файла
        void fileSavingMode(){
            JFileChooser fc = new JFileChooser();
            Integer value = fc.showSaveDialog(null);
            if(value == JFileChooser.APPROVE_OPTION) {
                File chosenFile = fc.getSelectedFile();
                String filePath = chosenFile.getAbsolutePath();
                //имя файла выбранного в файлЧузере передается в функцию для сохранения текста из компонента
                // т.е. из текстовой панели
                wwf.saveFile(filePath, component);
            }
        }


        private void createNewTxtFile(){
                //создание пустого текстового файла с именем по умолчанию
                String name = wwf.createDefaultNewFile();
                //установка флажка сохранения файла на значение "не сохранен"
                saved = false;
                //очистка тестовой панели от содержания предыдущего файла
                component.setText(null);
                //функция загружает созданный дефолтный файл в очищенную текстовую панель
                wwf.loadFile(name, "");
                //дает возможность редактирования содержимого текстовой панели
                component.setEnabled(true);
        }

}
