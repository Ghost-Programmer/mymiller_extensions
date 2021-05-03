package name.mymiller.javafx.display.editor.html;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import name.mymiller.javafx.display.DisplayScreen;

/**
 * HTML Editor Display
 *
 * @author jmiller
 */
public class HTMLEditorDisplay extends DisplayScreen {

    /**
     * HTML Editor for this display.
     */
    private HTMLEditor htmlEditor;

    /**
     * Default Constructor
     */
    public HTMLEditorDisplay() {
        super("HTML Editor");
    }

    /**
     * @return String containing the HTML Text from the editor
     * @see javafx.scene.web.HTMLEditor#getHtmlText()
     */
    public String getHtmlText() {
        return this.htmlEditor.getHtmlText();
    }

    /**
     * Sets the contents of the HTML Editor
     *
     * @param htmlText String containing the HTML Text to send.
     * @see javafx.scene.web.HTMLEditor#setHtmlText(java.lang.String)
     */
    public void setHtmlText(String htmlText) {
        this.htmlEditor.setHtmlText(htmlText);
    }

    @Override
    protected void startDisplay(Stage stage, double height, double width) {
        stage.setTitle("HTML EDITOR");

        this.htmlEditor = new HTMLEditor();
        final VBox root = new VBox();
        VBox.setVgrow(this.htmlEditor, Priority.ALWAYS);
        root.setPadding(new Insets(20, 10, 10, 10));
        root.getChildren().addAll(this.htmlEditor);
        final Scene scene = new Scene(root, 250, 200);
        stage.setScene(scene);
        stage.show();

        final WebView webview = (WebView) this.htmlEditor.lookup("WebView");
        GridPane.setHgrow(webview, Priority.ALWAYS);
        GridPane.setVgrow(webview, Priority.ALWAYS);
    }

}
