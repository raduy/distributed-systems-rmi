package pl.agh.sr.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class TicTacToeServer {

    private static final String RMI_REGISTRY_ADDRESS = "rmi://127.0.0.1:1099";
    private static final String BOARD_REMOTE_OBJECT_NAME = "board";

    public static void main(String[] args) {

        try {
//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager(new SecurityManager());
//            }

/*
            1. Create remote object
*/
            IBoard board = new BoardImpl();
            AppImpl app = new AppImpl();

/*
            2. Export remote object, so that:
            - JVM opens socket (arg 0 means, that port will be chosen automatically by JVM)
            - after this step everybody who has reference can call methods on this object
*/
            IBoard stub = (IBoard) UnicastRemoteObject.exportObject(board, 0);
            App appStub = (App) UnicastRemoteObject.exportObject(app, 0);

/*
            3. Now we have a reference to object, and we want to publish it so that clients could access it

            There are several ways to publish reference:
            - read from serialized object,
            - get it from argument call,
            - register reference in public RMI Registry (the most popular way)

            3a. Create RMI Registry in server's process. Consequences:
            - RMI Register has the same classpath as server (it sees the same classes)
*/
            // instead of running: rmiregistry 1099 in shell
            LocateRegistry.createRegistry(1099);
/*
            3b. Register reference in registry
            "rmi://127.0.0.1:1099/board"
*/
            Naming.rebind(RMI_REGISTRY_ADDRESS + "/" + BOARD_REMOTE_OBJECT_NAME, stub);
            Naming.rebind(RMI_REGISTRY_ADDRESS + "/app", appStub);

/*
            5. Now clients can access our remote object in RMI Registry and start using it
*/
            System.out.println("Registry initialized");
            System.out.println("Server up...");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
