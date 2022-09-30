package model;

public class FreeRoom extends Room{

    public FreeRoom ( String _roomNumber, Double _roomPrice, RoomType _roomType){
        super(_roomNumber,0.0,_roomType);

    }

    @Override
    public String toString() {
        return super.toString();
    }
}
