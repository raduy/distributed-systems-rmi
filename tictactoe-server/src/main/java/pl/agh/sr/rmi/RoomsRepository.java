package pl.agh.sr.rmi;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */
public class RoomsRepository {
    private final Map<RoomId, Room> rooms = new ConcurrentHashMap<RoomId, Room>();


    public void addRoom(Room newRoom) {
        if (rooms.containsKey(newRoom.getId())) {
            return;
        }

        rooms.put(newRoom.getId(), newRoom);
    }

    public Set<Room> immutableView() {
        return new HashSet<Room>(rooms.values());
//        return ImmutableSet.copyOf(rooms.values());
    }
}
