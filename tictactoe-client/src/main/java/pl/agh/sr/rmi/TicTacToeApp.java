package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.rmi.command.CommandRouter;
import pl.agh.sr.rmi.command.ICommand;
import pl.agh.sr.rmi.player.RealPlayerImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class TicTacToeApp {

    private static final Logger log = LoggerFactory.getLogger(TicTacToeApp.class);

    private final Scanner scanner = new Scanner(System.in);
    private IBoard currentBoard;
    private CommandRouter commandRouter;
    private RmiClient rmiClient;
    private RealPlayer player;
    private IRoom currentRoom;

    public static void main(String[] args) {

        TicTacToeApp ticTacToeApp = new TicTacToeApp();
        ticTacToeApp.play();
    }

    private void play() {
        try {

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

            this.player = new RealPlayerImpl(readNickName(), this);

            CommandRouter.printAvailableCommands();
            commandMode();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public RealPlayer getPlayer() {
        return player;
    }

    public RealPlayer getRemotePlayer() {
        try {
            return (RealPlayer) UnicastRemoteObject.exportObject(getPlayer(), 0);
        } catch (RemoteException e) {
            log.error("Error", e);
        }
        return null;
    }

    private String readNickName() {
        System.out.println("What's your name dude?");
        String nickName = null;
        while(nickName == null || nickName.isEmpty()) {
            nickName = scanner.nextLine();
        }

        return nickName;
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

    public void gameMode(IRoom newRoom) {
        this.currentRoom = newRoom;
        System.out.println("gaming...");
    }

    public void readMove() {
        System.out.println("Your turn!");
        int fieldNo = scanner.nextInt();

        try {
            currentBoard.mark(fieldNo);
        } catch (RemoteException e) {
            log.error("Error", e);
        }
    }

    public void startGame() throws RemoteException {
        System.out.println("Game is starting!");
        try {
            this.currentBoard = rmiClient.loadBoard(currentRoom);

            System.out.println(currentBoard.show());
        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    private static void printInvitation() {
        System.out.println("*************************************************");
        System.out.println("* Welcome to raduy's distributed TicTacToe game *");
        System.out.println("*************************************************");
    }
}
