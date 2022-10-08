package model;

import java.util.Objects;

public class Room  implements IRoom {

    private final String roomNumber;
    private final Double roomPrice;
    private final RoomType roomType;

    public Room (String _roomNumber, Double _roomPrice, RoomType _roomType) {
        super();
        roomNumber = _roomNumber;
        roomPrice = _roomPrice;
        roomType = _roomType;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return roomPrice;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", roomPrice=" + roomPrice +
                ", roomType=" + roomType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(getRoomNumber(), room.getRoomNumber()) && Objects.equals(getRoomPrice(), room.getRoomPrice()) && getRoomType() == room.getRoomType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoomNumber(), getRoomPrice(), getRoomType());
    }
}
