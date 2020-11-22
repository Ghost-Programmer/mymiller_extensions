/******************************************************************************
 Copyright 2018 MyMiller Consulting LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not
 use this file except in compliance with the License.  You may obtain a copy
 of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 License for the specific language governing permissions and limitations under
 the License.
 */
package name.mymiller.javafx.display.editor.code;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import name.mymiller.javafx.display.DisplayScreen;

import java.util.logging.Logger;

/**
 * @author jmiller Java Code Editor Display for creating Java Code files.
 */
public class JavaCodeEditorDisplay extends DisplayScreen {
    /**
     * Sample code TODO replace with templates.
     */
    static final private String editingCode = "import javafx.application.Application;\n"
            + "import javafx.scene.Scene;\n" + "import javafx.scene.web.WebView;\n" + "import javafx.stage.Stage;\n"
            + "\n" + "/** Sample code editing application wrapping an editor in a WebView. */\n"
            + "public class CodeEditorExample extends Application {\n"
            + "  public static void main(String[] args) { launch(args); }\n"
            + "  @Override public void start(Stage stage) throws Exception {\n"
            + "    WebView webView = new WebView();\n"
            + "    webView.getEngine().load(\"http://codemirror.net/mode/groovy/index.html\");\n"
            + "    final Scene scene = new Scene(webView);\n"
            + "    webView.prefWidthProperty().bind(scene.widthProperty());\n"
            + "    webView.prefHeightProperty().bind(scene.heightProperty());\n" + "    stage.setScene(scene);\n"
            + "    stage.show();\n" + "  }\n" + "}";

    /**
     * Default Constructor
     */
    public JavaCodeEditorDisplay() {
        super("Java Code Editor");
    }

    @Override
    protected void startDisplay(Stage stage, double height, double width) throws Exception {
        Logger.getLogger(JavaCodeEditorDisplay.class.getName()).info("Starting Display");
        // create the editing controls.
        final Label title = new Label("Editing: CodeEditor.java");
        title.setStyle("-fx-font-size: 20;");
        final Label labeledCode = new Label(JavaCodeEditorDisplay.editingCode);
        final CodeEditor editor = new CodeEditor(JavaCodeEditorDisplay.editingCode);
        final Button revertEdits = new Button("Revert edits");
        revertEdits.setOnAction(actionEvent -> editor.revertEdits());
        final Button copyCode = new Button("Take a snapshot from the editor and set a revert point");
        copyCode.setOnAction(actionEvent -> {
            labeledCode.setText(editor.getCodeAndSnapshot());
            System.out.println(editor.getCodeAndSnapshot());
        });

        // layout the scene.

        final HBox hLayout = new HBox();
        hLayout.setSpacing(10);
        hLayout.getChildren().addAll(copyCode, revertEdits);

        final VBox layout = new VBox();
        layout.setSpacing(10);
        layout.getChildren().addAll(title, editor, hLayout, labeledCode);
        layout.setStyle("-fx-padding: 10;");

        // display the scene.
        final Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();

        final WebView webview = (WebView) editor.lookup("WebView");
        GridPane.setHgrow(webview, Priority.ALWAYS);
        GridPane.setVgrow(webview, Priority.ALWAYS);
    }

}
