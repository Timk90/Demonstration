package spectralApp.view.gui.mainAppForm;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 19.06.2017.
 */

/*
главная форма приложения, представляющая собой пользовательский графический интерфейс (GUI)
обеспечивает отображение всех основных компонентов приложения таких как: меню, кнопки, панели навигации,
текстовые панели поиска и отображения, а также редактирования данных. Весь функционал вынесн в класс слушателей
для того, чтобы избежать нагромождения кода и, тем самым, обеспечить удобство его поддержки - повысить читаемость
постарался реализовать в виде MVC паттерна, однако слушатели просто добавлены в новый класс, поскольку они не являются
непосредственной реализацией функционала, а скорее промежуточное звено, которое обеспечивает вызов нужного функционала
при действии над компонентой интерфейса - нажатием кнопки, например
 */
public class MainAppForm extends JFrame {

    //имя приложения отображаемое в основной форме
    final static String  APP_NAME = "My Spectral Application";

    //дефолтный конструктор приложения, вызываемый из другово фрейма - фрейма авторизации пользователя
    public MainAppForm(String name){
        //обращение к дефолтному конструктору родительского класса для задания имени фрейма
        super(name);
        //по умолчанию форма будет закрываться при нажатии на крестик
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //при вызове конструктора форма будет видна для пользователей
        setVisible(true);
        //устанеовка дефолтных предпочтительных размеров основной формы
        setPreferredSize(new Dimension(700, 900));
        //установка компоновки формы, имеющей пять областей (северная, южная, западная, восточная и центральная)
        setLayout(new BorderLayout());
        //инициализация (создание) центральной панели формы
        JPanel central = new JPanel();
        //инициализация (создание) верхней панели формы
        JPanel top = new JPanel();
        //установка для верхней панели табличного способа компановки компонентов
        top.setLayout(new GridLayout(0, 1));
        //инициализация (создание) нижней панели формы
        JPanel bottom = new JPanel();
        //установка для нижней панели поточного способа компоновки
        //т.е. все компоненты будут располагаться друг за другом
        bottom.setLayout(new FlowLayout());
        //добавление верхней панели на форму
        add(top, BorderLayout.NORTH);
        //добавление центральной панели на форму
        add(central, BorderLayout.CENTER);
        //добавление нижней панели на форму
        add(bottom, BorderLayout.SOUTH);
        //просто смешная строка с указанием владельца приложения - т.е. меня любимого
        JLabel bottomLabel = new JLabel("All the rights is owned by Tim.. :P");
        //добавление смешной строки на нижню панель
        bottom.add(bottomLabel);
        //установка способа компановки компанентов для центральной панели
        central.setLayout(new BorderLayout());
        //инициализация текстового поля в котором будет отображаться содержимое файлов
        JTextArea canvas = new JTextArea();
        //настрока строчного врапера (обертывалищака) - жуть какая на русском языке
        //строка необходимая для переноса строки при достижении границы канвы - текстовой области
        canvas.setLineWrap(true);
        //при инициализации формы изменения в канве недопустимы, потому что файл еще не открыт
        canvas.setEnabled(false);
        //инициялизация скроллера для канвы, необходима при большом количестве загружаемых в поле строк
        JScrollPane canvasScroller = new JScrollPane(canvas);
        //размещение скроллера на центральной панели
        central.add(canvasScroller, BorderLayout.CENTER);
        //инициализация нижней панелт формы
        JPanel buttomPanel = new JPanel();
        //внутренний класс кнопок , наследующий от базового кноочного класса
        //решение создавать собственные кнопки связано с желанием указать их размер по умолчанию
        class MyButton extends JButton{
            //конструктор внутреннего кнопочного класса
            MyButton(String name){
                //обращение в конструктор родителя, чтобы указать надпись на кнопке
                super(name);
                //указание предпочтительного размера кноки по умолчанию
                setPreferredSize(new Dimension(90, 10));
            }
        }
        //контейнер для хранения списка кнопок боковой (левой) панели
        List<JButton> buttons = new ArrayList<JButton>();
        //далее следует перечень создаваесых кнопок для левой панели и их размещение в последней
        //также осуществляется добавление слушателя, но логика вынесена в отдельный класс
        //это связано с желанием отделить интерфейс от функционала той или иной кнопки
        //некое подобие паттерна проектирования - MVC (model, view, controller)
        JButton createFile = new MyButton("New");
        createFile.addActionListener(new AppButtonListeners(canvas));
        buttons.add(createFile);
        JButton loadFile = new MyButton("Load");
        loadFile.addActionListener(new AppButtonListeners(canvas));
        buttons.add(loadFile);
        JButton saveFile = new MyButton("Save");
        saveFile.addActionListener(new AppButtonListeners(canvas));
        buttons.add(saveFile);
        JButton deleteFile = new MyButton("Delete");
        deleteFile.addActionListener(new AppButtonListeners(canvas));
        buttons.add(deleteFile);
        JButton clearFile = new MyButton("Clear");
        clearFile.addActionListener(new AppButtonListeners(canvas, this));
        buttons.add(clearFile);

        //кнопки размещаются в кнопочной панеле, для которой задается табличный тип компановки
        //компонентов
        buttomPanel.setLayout(new GridLayout(buttons.size()+25,0));
        //цикл for each для списка кнопков - используется для добавления кнопок на панель
        for(JButton button: buttons)
        buttomPanel.add(button);
        //добавляется сама панель
        central.add(buttomPanel, BorderLayout.WEST);

        //инициализация панели навигации , которая располагается в верхней части формы
        //она реализована как контейнер для меню
        JMenuBar navigation = new JMenuBar();
        //далее следует инициализация компонентов панели навигации - менюшки
        JMenu fileMenu = new JMenu("File");
        //и указание их наполнения отдельными строками подменю
        JMenuItem newFileItem = new JMenuItem("New file");
        newFileItem.addActionListener(new AppButtonListeners(canvas));
        fileMenu.add(newFileItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new AppButtonListeners());
        fileMenu.add(exitItem);

        // возникла идея, чтобы поиск по тесту фала всегда был доступен
//        JMenu searchMenu = new JMenu("Search");
//        searchMenu.add(new JMenuItem("Search"));

        JMenu settingsMenu = new JMenu("Settings");
        settingsMenu.add(new JMenuItem("Main settings"));
        settingsMenu.add(new JMenuItem("View settings"));

        JMenu aboutMenu = new JMenu("About");
        aboutMenu.add(new JMenuItem("About app"));
        aboutMenu.add(new JMenuItem("Donate"));

        JMenu graphMenu = new JMenu("Graph");
        graphMenu.add(new JMenuItem("Draw plot"));
        graphMenu.add(new JMenuItem("clear plot"));

        JMenu mathMenu = new JMenu("Math");
        mathMenu.add(new JMenuItem("Integrate"));
        mathMenu.add(new JMenuItem("Derivative"));

        //добавление проининициализированных компонентов на панель навигации
        navigation.add(fileMenu);
//        navigation.add(searchMenu);
        navigation.add(settingsMenu);
        navigation.add(aboutMenu);
        navigation.add(graphMenu);
        navigation.add(mathMenu);

        //добавление пустого места после панели навигации, просто для красоты дизайна
        navigation.add(new Label("                                 "));
        //надпись перед поисковой строкой, следующей за панелью навигации
        navigation.add(new Label("Search..."));
        //добавление строки поиска и ее инициализация, а также указание дефолтного размера в конструкторе
        JTextField searchField = new JTextField(16);
        navigation.add(searchField);
        searchField.addCaretListener(new AppSearchListener(canvas, searchField));

        //добавлением стрелок необходимых для перемещения по совпадениям
        // с указанным набором символов
        //в поисковой строке
        BasicArrowButton previous = new BasicArrowButton(BasicArrowButton.WEST);
        previous.addActionListener(new AppHighLightListener(canvas,"previous"));
        BasicArrowButton next = new BasicArrowButton(BasicArrowButton.EAST);
        next.addActionListener(new AppHighLightListener(canvas, "next"));
        navigation.add(previous);
        navigation.add(next);

        //добавление в топовую панель панели навигации
        top.add(navigation);
        //базовый способ комплектования фрейма
        pack();
    }

    //главный метод, служащий точкой входа в приложение
    //в данном случае служил исключительно для проверки отображения элементов на главном фрейме
    public static void main(String[] args) {

        //запускается с помощью утилиты стринг как новая нить(поток)
        //таким образом обеспечивается дополнительная безопасность исполнения кода
        //но я плохо представляю себе детали - с чем именно это связано
        //возможно при одновременном обращении к приложению (то есть одновременном запуске)
        // нескольких клиентов не возникает проблемы разделения ресурсов между ними т.к. они синхронизируются
        //еализовано в виде анонимного внутреннего метода
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainAppForm(APP_NAME);
            }
        });
    }

}
