package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.agh.sr.rmi.command.CommandRouter;
import pl.agh.sr.rmi.command.ICommand;
import pl.agh.sr.rmi.player.RealPlayer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import static pl.agh.sr.rmi.Config.*;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class TicTacToeApp {
    private static final Logger log = LoggerFactory.getLogger(TicTacToeApp.class);

    private final String rmiIp;
    private final int rmiPort;
    private final Scanner scanner = new Scanner(System.in);
    private IBoard currentBoard;
    private CommandRouter commandRouter;
    private RmiClient rmiClient;
    private IPlayer player;
    private IRoom currentRoom;
    private Sign sign;

    public TicTacToeApp(String rmiIp, int rmiPort) {
        this.rmiIp = rmiIp;
        this.rmiPort = rmiPort;
    }

    public static void main(String[] args) {

        String rmiIp = DEFAULT_RMI_REGISTRY_IP;
        int rmiPort = DEFAULT_RMI_REGISTRY_PORT;

        try {
            if (args.length < 2) {
                System.out.println("Usage: java TicTacToeApp <ip> <port>");
                System.out.println("Loading default ip and port...");
            } else {
                rmiIp = args[0];
                rmiPort = Integer.parseInt(args[1]);
            }
        } catch (Exception e) {
            System.out.println("Wrongs args given!");
            System.out.println("Loading default ip and port");
            rmiIp = DEFAULT_RMI_REGISTRY_IP;
            rmiPort = DEFAULT_RMI_REGISTRY_PORT;
        }

        TicTacToeApp ticTacToeApp = new TicTacToeApp(rmiIp, rmiPort);
        ticTacToeApp.play();
    }

    private void play() {
        try {
            printInvitation();

            this.rmiClient = new RmiClient(rmiIp, rmiPort);
            this.commandRouter = new CommandRouter(this, rmiClient);

            RealPlayer realPlayer = new RealPlayer(readNickName(), this);
            this.player = (IPlayer) UnicastRemoteObject.exportObject(realPlayer, 0);

            CommandRouter.printAvailableCommands();
            commandMode();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public IPlayer getPlayer() {
        return player;
    }

    private String readNickName() {
        System.out.println("What's your name dude?");
        String nickName = null;
        while (nickName == null || nickName.isEmpty()) {
            nickName = scanner.nextLine();
        }

        return nickName;
    }

    public void commandMode() {
        String cmd = scanner.nextLine();
        if (cmd.isEmpty()) {
            commandMode(); //todo fix
        }
        ICommand command = commandRouter.route(cmd);
        command.execute();
    }

    public void gameMode(IRoom newRoom) {
        this.currentRoom = newRoom;
        System.out.println("Waiting for second player...");
    }

    public void readMove() {
        System.out.printf("Your turn! You have: %s\n", sign.name());

        while (!Thread.interrupted()) {
            try {
                int fieldNo = Integer.parseInt(scanner.nextLine());
                System.out.printf("You have chosen %d\n", fieldNo);
                currentBoard.mark(fieldNo, player);
            } catch (RemoteException e) {
                System.out.println("Hey you, this field is already marked! Pick sth else");
            } catch (Exception e) {
                //retry
            }
        }
    }

    public void startGame(Sign sign) throws RemoteException {
        this.sign = sign;

        System.out.println("Game is starting!");
        try {
            this.currentBoard = rmiClient.loadBoard(currentRoom);

            printBoard();
        } catch (Exception e) {
            log.error("Error", e);
        }
    }

    public void printBoard() throws RemoteException {
        System.out.println(currentBoard.show());
    }

    private static void printInvitation() {
        System.out.println("*************************************************");
        System.out.println("* Welcome to raduy's distributed TicTacToe game *");
        System.out.println("*************************************************");
    }
}
