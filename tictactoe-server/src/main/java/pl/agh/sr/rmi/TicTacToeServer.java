package pl.agh.sr.rmi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import static pl.agh.sr.rmi.Config.RMI_ADDRESS_FORMAT;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class TicTacToeServer {
    private final static Logger log = LoggerFactory.getLogger(TicTacToeServer.class);

    private final String rmiRegistryAddress;
    private final int registryPort;

    public TicTacToeServer(String registryIp, int registryPort) {
        this.registryPort = registryPort;
        this.rmiRegistryAddress = buildRegistryAddress(registryIp, registryPort);
    }

    public static void main(String[] args) {
        String registryIp;
        int registryPort;

        try {
            if (args.length < 2) {
                System.out.println("Too little args given!");
                System.out.println("Usage: java TicTacToeServer <ip> <port>");
                System.out.println("Loading default ip and port");

                registryIp = Config.DEFAULT_RMI_REGISTRY_IP;
                registryPort = Config.DEFAULT_RMI_REGISTRY_PORT;
            } else {
                registryIp = args[0];
                registryPort = Integer.parseInt(args[1]);
            }
        } catch (Exception e) {
            System.out.println("Wrongs args given!");
            System.out.println("Loading default ip and port");
            registryIp = Config.DEFAULT_RMI_REGISTRY_IP;
            registryPort = Config.DEFAULT_RMI_REGISTRY_PORT;
        }

        TicTacToeServer server = new TicTacToeServer(registryIp, registryPort);
        server.play();
    }

    private String buildRegistryAddress(String registryIp, int registryPort) {
        return String.format(RMI_ADDRESS_FORMAT, registryIp, registryPort);
    }

    private void play() {
        try {
            setupSecurityManager();
/*
            1. Create remote object
*/
            AppImpl app = new AppImpl();
/*
            2. Export remote object, so that:
            - JVM opens socket (arg 0 means, that port will be chosen automatically by JVM)
            - after this step everybody who has reference can call methods on this object
*/
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
            // instead of running: rmiregistry <port> in shell
            LocateRegistry.createRegistry(registryPort);
/*
            3b. Register reference in registry
*/
            Naming.rebind(rmiRegistryAddress + "/app", appStub);
/*
            5. Now clients can access our remote object in RMI Registry and start using it
*/
            log.debug("Registry initialized");
            log.debug("Server up...");
        } catch (Exception e) {
            log.error("Error starting server", e);
            System.exit(-1);
        }
    }

    private static void setupSecurityManager() {
        if (System.getProperty("java.security.policy") == null) {
            System.out.println("!!! Please set location of security policy file! Do it with: -Djava.security.policy=path/to/server/policy/file/server.policy");

            System.out.println("Loading very poor default...");
            String poorDefault = "/home/raduy/Dropbox/Development/IdeaProjects/distributed-systems-rmi/tictactoe-server/src/main/resources/server.policy";
            System.setProperty("java.security.policy", poorDefault);
        }

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
    }
}
