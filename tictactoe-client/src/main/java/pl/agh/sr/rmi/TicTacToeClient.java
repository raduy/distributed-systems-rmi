package pl.agh.sr.rmi;

import java.rmi.Naming;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class TicTacToeClient {

    private static final String RMI_REGISTRY_ADDRESS = "rmi://127.0.0.1:1099";
    private static final String BOARD_REMOTE_OBJECT_NAME = "board";

    public static void main(String[] args) {

        try {
//            if (System.getSecurityManager() == null) {
//                System.setSecurityManager(new SecurityManager());
//            }
/*
            1. Look for remote reference (in RMI Registry)
*/
            IBoard board = (IBoard) Naming.lookup(RMI_REGISTRY_ADDRESS + "/" + BOARD_REMOTE_OBJECT_NAME);
/*
            2. Call remote method
*/
            System.out.println(board.sayHello());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
