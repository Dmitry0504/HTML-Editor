package main;

import listeners.FrameListener;
import listeners.TabbedPaneChangeListener;
import listeners.UndoListener;
import main.Controller;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 Графический интерфейс будет представлять собой окно, в котором будет меню и панель с двумя вкладками.
 На первой вкладке будет располагаться текстовая панель, которая будет отрисовывать html страницу.
 На ней можно будет форматировать и редактировать текст страницы.
 На второй вкладке будет редактор, который будет отображать код html страницы, в нем будут видны
 все используемые html теги. В нем также можно будет менять текст страницы, добавлять и удалять различные теги.
 */

public class View extends JFrame implements ActionListener {
    private Controller controller;
    //панель с двумя вкладками
    private JTabbedPane tabbedPane = new JTabbedPane();
    //компонент для визуального редактирования html
    private JTextPane htmlTextPane = new JTextPane();
    //компонент для редактирования html в виде текста, он будет отображать
    // код html (теги и их содержимое)
    private JEditorPane plainTextPane = new JEditorPane();

    private UndoManager undoManager = new UndoManager();

    private UndoListener undoListener = new UndoListener(undoManager);

    public View() {
        try {
            //устанавливаем оформление как в системе
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init(){
        initGui();
        FrameListener frameListener = new FrameListener(this);
        this.addWindowListener(frameListener);
        setVisible(true);
    }

    //инициализация панели Файл, Редактировать и т.д....
    public void initMenuBar(){
        JMenuBar jMenuBar = new JMenuBar();
        MenuHelper.initFileMenu(this, jMenuBar);
        MenuHelper.initEditMenu(this, jMenuBar);
        MenuHelper.initStyleMenu(this, jMenuBar);
        MenuHelper.initAlignMenu(this, jMenuBar);
        MenuHelper.initColorMenu(this, jMenuBar);
        MenuHelper.initFontMenu(this, jMenuBar);
        MenuHelper.initHelpMenu(this, jMenuBar);
        this.getContentPane().add(jMenuBar, BorderLayout.NORTH);
    }

    public void initEditor(){
        //вкладка для html
        htmlTextPane.setContentType("text/html");
        //делаем её прокручиваемой
        JScrollPane jScrollPane = new JScrollPane(htmlTextPane);
        //добавляем её в панель
        tabbedPane.addTab("HTML", jScrollPane);
        JScrollPane pane = new JScrollPane(plainTextPane);
        tabbedPane.addTab("Текст", pane);
        //устанавливаем предпочтительный размер окна
        tabbedPane.setPreferredSize(new Dimension(200, 300));
        TabbedPaneChangeListener tabbedPaneChangeListener = new TabbedPaneChangeListener(this);
        tabbedPane.addChangeListener(tabbedPaneChangeListener);
        //вкладываем в View получившуюся панель и центрируем
        this.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    public void initGui(){
        initMenuBar();
        initEditor();
        pack();
    }

    public void exit(){
        controller.exit();
    }

    //будет вызваться при выборе пунктов меню,
    // у которых наше представление указано в виде слушателя событий
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Новый":
                controller.createNewDocument();
                break;
            case "Открыть":
                controller.openDocument();
                break;
            case "Сохранить":
                controller.saveDocument();
                break;
            case "Сохранить как...":
                controller.saveDocumentAs();
                break;
            case "Выход":
                exit();
                break;
            case "О программе":
                showAbout();
                break;
        }
    }

    //Этот метод будет вызываться, когда произошла смена выбранной вкладки
    public void selectedTabChanged() {
        if(tabbedPane.getSelectedIndex() == 0){
            controller.setPlainText(plainTextPane.getText());
        }
        else if(tabbedPane.getSelectedIndex() == 1) {
            plainTextPane.setText(controller.getPlainText());
        }
        resetUndo();
    }

    public void undo(){
        try{
            undoManager.undo();
        }catch (Exception e){
            ExceptionHandler.log(e);
        }

    }

    public void redo(){
        try{
            undoManager.redo();
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

    public void resetUndo(){
        undoManager.discardAllEdits();
    }
    public boolean canUndo(){
        return undoManager.canUndo();
    }

    public boolean canRedo(){
        return undoManager.canRedo();
    }

    //возвращат true, если выбрана вкладка, отображающая html в панели вкладок
    public boolean isHtmlTabSelected(){
        //return tabbedPane.getComponentAt(0).isShowing();
        return tabbedPane.getSelectedIndex() == 0;
    }

    //выбирает вкладку HTML и сбрасывает все изменения
    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    //получает документ у контроллера и устанавливает его в панель редактирования htmlTextPane
    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }

    //показывает диалоговое окно с информацией о программе
    public void showAbout(){
        String message = "HTML редактор с графическим интерфейсом.\n" +
                "В качестве библиотеки для создания графического интерфейса используется Swing.\n" +
                "А в качестве архитектурного каркаса приложения использована MVC модель.";
        JOptionPane.showMessageDialog(
                this, message, "О программе", JOptionPane.INFORMATION_MESSAGE);
    }
}
