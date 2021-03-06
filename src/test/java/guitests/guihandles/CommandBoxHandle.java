package guitests.guihandles;

import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

/**
 * A handle to the {@code CommandBox} in the GUI.
 */
public class CommandBoxHandle extends NodeHandle<TextField> {

    public static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(TextField commandBoxNode) {
        super(commandBoxNode);
    }

    /**
     * Returns the text in the command box.
     */
    public String getInput() {
        return getRootNode().getText();
    }

    /**
     * Returns the focus state of the command box.
     */
    public boolean isFocused() {
        return getRootNode().isFocused();
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     */
    public void run(String command) {
        click();
        setText(command);
        guiRobot.pauseForHuman();

        guiRobot.type(KeyCode.ENTER);
    }

    /**
     * Enters the given text in the Command Box.
     */
    public void setText(String text) {
        click();
        guiRobot.interact(() -> getRootNode().setText(text));
    }

    /**
     * Enters the given text in the Command Box.
     */
    public void pressTab() {
        click();
        guiRobot.push(KeyCode.TAB);
    }

    /**
     * Returns the list of style classes present in the command box.
     */
    public ObservableList<String> getStyleClass() {
        return getRootNode().getStyleClass();
    }
}
