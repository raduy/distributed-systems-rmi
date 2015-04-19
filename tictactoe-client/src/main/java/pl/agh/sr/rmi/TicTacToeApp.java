package pl.agh.sr.rmi;

import pl.agh.sr.rmi.command.CommandRouter;
import pl.agh.sr.rmi.command.ICommand;

import java.util.Scanner;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class TicTacToeApp {

    private final Scanner scanner = new Scanner(System.in);
    private IBoard currentBoard;
    private CommandRouter commandRouter;
    private RmiClient rmiClient;

    public static void main(String[] args) {

        TicTacToeApp ticTacToeApp = new TicTacToeApp();
        ticTacToeApp.play();
    }

    private void play() {
        try {
//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager(new SecurityManager());
//            }
/*
            1. Look for remote reference (in RMI Registry)
*/
//            IBoard board = (IBoard) Naming.lookup(RMI_REGISTRY_ADDRESS + "/" + BOARD_REMOTE_OBJECT_NAME);
/*
            2. Call remote method
*/
//            System.out.println(board.sayHello());

//            ------------------------------

            printInvitation();

            this.rmiClient = new RmiClient(this);
            this.commandRouter = new CommandRouter(this, rmiClient);

            CommandRouter.printAvailableCommands();
            commandMode();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    public void updateCurrentBoard(IBoard newBoard) {
        this.currentBoard = newBoard;
    }

    public void commandMode() {
        while (!Thread.interrupted()) {
            String cmd = scanner.nextLine();
            if (cmd.isEmpty()) {
                continue;
            }
            ICommand command = commandRouter.route(cmd);
            command.execute();
        }
    }

    public void gameMode() {
        System.out.println("gaming...");
    }

    private static void printInvitation() {
        System.out.println("*************************************************");
        System.out.println("* Welcome to raduy's distributed TicTacToe game *");
        System.out.println("*************************************************");
    }
}
