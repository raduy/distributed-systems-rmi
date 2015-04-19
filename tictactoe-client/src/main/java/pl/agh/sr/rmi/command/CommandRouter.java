package pl.agh.sr.rmi.command;

import pl.agh.sr.rmi.RmiClient;
import pl.agh.sr.rmi.TicTacToeApp;

import java.util.HashMap;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class CommandRouter {
    private final static HashMap<String, String> commands = new HashMap<String, String>();

    private TicTacToeApp app;
    private RmiClient rmiClient;

    static {
        commands.put(CreateNewRoomCommand.INVOCATION_PREFIX, CreateNewRoomCommand.DESCRIPTION);
        commands.put(PlayWithBotCommand.INVOCATION_PREFIX, PlayWithBotCommand.DESCRIPTION);
    }

    public CommandRouter(TicTacToeApp app, RmiClient rmiClient) {
        this.app = app;
        this.rmiClient = rmiClient;
    }

    public ICommand route(String cmd) {
        if (cmd.startsWith(CreateNewRoomCommand.INVOCATION_PREFIX)) {
            return new CreateNewRoomCommand(app, rmiClient);
        } else if (cmd.startsWith(PlayWithBotCommand.INVOCATION_PREFIX)) {
            return new PlayWithBotCommand(app, rmiClient);
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
