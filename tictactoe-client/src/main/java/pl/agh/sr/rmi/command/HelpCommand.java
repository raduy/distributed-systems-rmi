package pl.agh.sr.rmi.command;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class HelpCommand implements ICommand {

    @Override
    public void execute() {
        CommandRouter.printAvailableCommands();
    }
}
