package listeners;

import main.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class UndoMenuListener implements MenuListener {
    private View view;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;

    public UndoMenuListener(View view, JMenuItem undoMenuItem, JMenuItem redoMenuItem) {
        this.view = view;
        this.undoMenuItem = undoMenuItem;
        this.redoMenuItem = redoMenuItem;
    }

    @Override
    public void menuSelected(MenuEvent e) {
            if(view.canUndo()){
                undoMenuItem.setEnabled(true);

            }
            if(!view.canUndo()){
                undoMenuItem.setEnabled(false);
            }
            if(view.canRedo()){
                redoMenuItem.setEnabled(true);
            }
            if(!view.canRedo()){
                redoMenuItem.setEnabled(false);
            }
    }



    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }

    public boolean canUndo(){
        return false;
    }

    public boolean canRedo(){
        return false;
    }
}
