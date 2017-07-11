package spectralApp.view.gui.mainAppForm;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Tim on 25.06.2017.
 */

/*
Класс слушателей, который имплементирует интерфейс слушателя каретки - слушателя положения указателя
Интерфейс каретки имеет один метод, который должен быть имплементирован - изменение позиции каретки,
(он должен быть обязательно имплементирован)
(т.е. переписан  - на что также  указывает аннотация @Override)
Это слушатель применяется для курсоров в поисковом текстовой поле и текстовой области отображения
содержимого файлов

 */
public class AppSearchListener implements CaretListener {
    //указание текстового поля, за которым будет следить наш слушатель
    JTextField tf ;
    //текстовая область - канва, в которой будет происходить поиск на совпадение
    //с набором символов из поискового текстового поля
    JTextArea ta;
    //инициализация начального положения картки при запуске приложения
    static int cursor = 0;
    //инициализация количества совпадений набора символов, указаннового в поисковой строке,
    //найденного в тексте канвы
    static int counter = 0;

    //список положения коретки для каждого из найденных совпадений
    static List<Integer> positions = new ArrayList<>();
    //инициализация строки поиска, которая будят взята из поискового текстового поля
    private static String regex = "";

    //конструктор класса слушателя с помощью которого удается связать компоненты формы
    // и поля форм указанные в слушателе - провести инжекцию (injection) :)
    AppSearchListener(JTextArea canvas, JTextField searchField){
        this.tf = searchField;
        this.ta = canvas;
    }


        @Override
        public void caretUpdate(CaretEvent e) {

        List<Integer> matches = new LinkedList<>();
        //
        String regex = tf.getText();
        String text = ta.getText();

        if(!regex.equals("") && regex.length() > 0 && text.length() > 0) {
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(text);
            matches.clear();
//            while (matcher.find()) {
            cursor = 0;

            while ((cursor = text.toUpperCase().indexOf(regex.toUpperCase(), cursor))>=0){
                matches.add(cursor);
                cursor++;
            }

            counter = matches.size();
            try {
                ta.getHighlighter().removeAllHighlights();

                    if (matches.size() > 0 && matches != null) {
                        ta.setCaretPosition(matches.get(0));
                        for (int i = 0; i < matches.size(); i++) {
                            cursor = matches.get(i);
                            ta.getHighlighter().addHighlight(cursor, cursor + regex.length(), new DefaultHighlighter.DefaultHighlightPainter(Color.yellow));
                        }
                        ta.setCaretPosition(matches.get(0));
                        this.regex = regex;
                        positions.clear();
                        positions.addAll(matches);

                    }
            } catch (BadLocationException e1) {
                    e1.printStackTrace();
                }
            }else{
                ta.getHighlighter().removeAllHighlights();
            }
        }

        static List<Integer> getHighLightsPositions() {
            return positions;
        }
        static String getHighLightsRegex() {return regex;}

}
