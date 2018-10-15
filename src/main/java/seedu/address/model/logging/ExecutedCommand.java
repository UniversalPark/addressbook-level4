package seedu.address.model.logging;

import java.util.Objects;

import seedu.address.logic.commands.AllCommandWords;

/**
 * Represents an executed command with its command word and command args.
 */
public class ExecutedCommand {
    private static final String INVALID_COMMAND_WORD = "<invalid commandWord>";

    private final String commandWord;
    private final String commandArgs;

    /**
     * Instantiates an ExecutedCommand from a complete String.
     */
    public ExecutedCommand(String commandString) {
        final String[] splitCommandString = commandString.split(" ", 2);
        if (AllCommandWords.isCommandWord(splitCommandString[0])) {
            commandWord = splitCommandString[0];
            commandArgs = splitCommandString.length >= 2 ? splitCommandString[1] : "";
        } else {
            commandWord = INVALID_COMMAND_WORD;
            commandArgs = commandString;
        }
    }

    @Override
    public String toString() {
        return commandWord + " " + commandArgs;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ExecutedCommand // instanceof handles nulls
            && Objects.equals(toString(), other.toString())); // state check
    }

}