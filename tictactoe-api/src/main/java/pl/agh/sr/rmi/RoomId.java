package pl.agh.sr.rmi;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Lukasz Raduj <raduj.lukasz@gmail.com>
 */

//TODO add lombok for equals and hashcode generation
public class RoomId implements Serializable {
    private UUID uuid;

    public RoomId() {
        this.uuid = UUID.randomUUID();
    }

    public RoomId(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoomId roomId = (RoomId) o;

        if (uuid != null ? !uuid.equals(roomId.uuid) : roomId.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
