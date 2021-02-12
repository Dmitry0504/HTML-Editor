package listeners;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

/**
 Этот класс будет следить за правками, которые можно отменить или вернуть.
 */

public class UndoListener implements UndoableEditListener {
    private UndoManager undoManager;

    public UndoListener(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    /**
     из переданного события получает правку и добавляет ее в undoManager
     */
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        undoManager.addEdit(e.getEdit());
    }
}
