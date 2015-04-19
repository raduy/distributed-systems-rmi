package pl.agh.sr.rmi.command;

import pl.agh.sr.rmi.TicTacToeApp;

import java.util.HashMap;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class CommandRouter {
    private final static HashMap<String, String> commands = new HashMap<String, String>();

    private TicTacToeApp app;

    static {
        commands.put(CreateNewRoomCommand.INVOCATION_PREFIX, CreateNewRoomCommand.DESCRIPTION);
    }

    public CommandRouter(TicTacToeApp app) {
        this.app = app;
    }

    public ICommand route(String cmd) {
        if (cmd.startsWith("-c")) {
            return new CreateNewRoomCommand(app);
        }

        System.out.println("No such command!");
        return new HelpCommand();
    }

    public static void printAvailableCommands() {
        for (String s : commands.keySet()) {
            System.out.printf("%s - %s", s, commands.get(s));
        }
    }
}
