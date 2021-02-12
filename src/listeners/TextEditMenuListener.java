package listeners;

import main.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public class TextEditMenuListener implements MenuListener {
    private View view;

    public TextEditMenuListener(View view) {
        this.view = view;
    }

    /**
     Из переданного параметра получать объект, над которым было совершено действие.
     В нашем случае это будет объект с типом JMenu.
     У полученного меню получать список компонентов (пунктов меню).
     Для каждого пункта меню вызывать метод setEnabled, передав в качестве
     параметра результат вызова метода isHtmlTabSelected() из представления.
     */
    @Override
    public void menuSelected(MenuEvent e) {
        JMenu jMenu = (JMenu) e.getSource();
        for(Component c: jMenu.getMenuComponents()){
            c.setEnabled(view.isHtmlTabSelected());
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
