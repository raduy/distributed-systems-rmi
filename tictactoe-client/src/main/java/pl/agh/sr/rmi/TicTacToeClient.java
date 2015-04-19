package pl.agh.sr.rmi;

import java.rmi.Naming;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class TicTacToeClient {

    private static final String RMI_REGISTRY_ADDRESS = "rmi://127.0.0.1:1099";
    private static final String BOARD_REMOTE_OBJECT_NAME = "board";
    private static final String ROOM_REMOTE_OBJECT_NAME = "room";

    public static void main(String[] args) {

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

            App app = (App) Naming.lookup(RMI_REGISTRY_ADDRESS + "/app");
            Room newRoom = app.createNewRoom();

            String roomRMIAddress = RMI_REGISTRY_ADDRESS + "/" + ROOM_REMOTE_OBJECT_NAME + "/" + newRoom.getId().toString() + "/board";
            IBoard newBoard = (IBoard) Naming.lookup(roomRMIAddress);

            System.out.println("New one turn");

            System.out.println(newBoard.show());
            System.out.println(app.listRooms());

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
