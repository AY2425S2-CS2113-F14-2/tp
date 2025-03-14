package commands;


public abstract class Command {
    private String commandName;
    private String description;
    public Command(String commandName) {
        this.commandName = commandName;
    }
    public String getCommandName() {
        return commandName;
    }
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Abstract method to execute the command
     * @param args
     * @return
     */
    public abstract String execute(String[] args);

    /**
     * Throws CEGStudyBuddyException of the message
     * @param message
     * @throws CEGStudyBuddyException
     */
    private void throwException (String message) throws CEGStudyBuddyException {
        throw new CEGStudyBuddyException(message);
    }
}
