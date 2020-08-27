/*******************************************************************************
 * Copyright 2018 MyMiller Consulting LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package name.mymiller.javafx.display.editor.code;

import javafx.scene.layout.StackPane;
import javafx.scene.web.WebView;

/**
 * A syntax highlighting code editor for JavaFX created by wrapping a CodeMirror
 * code editor in a WebView.
 * <p>
 * See http://codemirror.net for more information on using the codemirror
 * editor.
 */
public class CodeEditor extends StackPane {
    /**
     * a webview used to encapsulate the CodeMirror JavaScript.
     */
    final WebView webview = new WebView();
    /**
     * a template for editing code - this can be changed to any template derived
     * from the supported modes at http://codemirror.net to allow syntax highlighted
     * editing of a wide variety of languages.
     */
    private final String editingTemplate = "<!doctype html>" + "<html>" + "<head>"
            + "  <link rel=\"stylesheet\" href=\"http://codemirror.net/lib/codemirror.css\">"
            + "  <script src=\"http://codemirror.net/lib/codemirror.js\"></script>"
            + "  <script src=\"http://codemirror.net/mode/clike/clike.js\"></script>" + "</head>" + "<body>"
            + "<form><textarea id=\"code\" name=\"code\">\n" + "${code}" + "</textarea></form>" + "<script>"
            + "  var editor = CodeMirror.fromTextArea(document.getElementById(\"code\"), {" + "    lineNumbers: true,"
            + "    matchBrackets: true," + "    mode: \"text/x-java\"" + "  });" + "</script>" + "</body>" + "</html>";
    /**
     * a snapshot of the code to be edited kept for easy initialization and reversion
     * of editable code.
     */
    private String editingCode;

    /**
     * Create a new code editor.
     *
     * @param editingCode the initial code to be edited in the code editor.
     */
    public CodeEditor(String editingCode) {
        this.editingCode = editingCode;

        this.webview.setMaxHeight(Double.MAX_VALUE);
        this.webview.setMaxWidth(Double.MAX_VALUE);
        this.webview.setPrefSize(650, 325);
        this.webview.setMinSize(650, 325);
        this.webview.getEngine().loadContent(this.applyEditingTemplate());

        this.getChildren().add(this.webview);
    }

    /**
     * @return applies the editing template to the editing code to create the
     * html+javascript source for a code editor.
     */
    private String applyEditingTemplate() {
        return this.editingTemplate.replace("${code}", this.editingCode);
    }

    /**
     * @return returns the current code in the editor and updates an editing
     * snapshot of the code which can be reverted to.
     */
    public String getCodeAndSnapshot() {
        this.editingCode = (String) this.webview.getEngine().executeScript("editor.getValue();");
        return this.editingCode;
    }

    /**
     * revert edits of the code to the last edit snapshot taken.
     */
    public void revertEdits() {
        this.setCode(this.editingCode);
    }

    /**
     * sets the current code in the editor and creates an editing snapshot of
     *
     * @param newCode the code which can be reverted to.
     */
    public void setCode(String newCode) {
        this.editingCode = newCode;
        this.webview.getEngine().loadContent(this.applyEditingTemplate());
    }
}
