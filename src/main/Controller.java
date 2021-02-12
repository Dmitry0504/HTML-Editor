package main;

import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public void init(){

    }

    //записывает переданный текст с html тегами в документ document
    public void setPlainText(String text){
        resetDocument();
        StringReader reader = new StringReader(text);
        HTMLEditorKit editorKit = new HTMLEditorKit();
        try{
            editorKit.read(reader, document, 0);
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
    }

    //получает текст из документа со всеми html тегами
    public String getPlainText(){
        StringWriter writer = new StringWriter();
        HTMLEditorKit editorKit = new HTMLEditorKit();
        try{
            editorKit.write(writer, document, 0, document.getLength());
        }catch (Exception e){
            ExceptionHandler.log(e);
        }
        return writer.toString();
    }

    //сбрасывает текущий документ
    public void resetDocument(){
        if(document != null) document.removeUndoableEditListener(view.getUndoListener());

        HTMLEditorKit editorKit = new HTMLEditorKit();
        document = (HTMLDocument) editorKit.createDefaultDocument();
        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }

    public void exit(){
        System.exit(0);
    }

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }
}
