package model;

public enum RoomType {
    SINGLE,
    DOUBLE;

    public static RoomType convertToEnum(int number) {
        //Just give them single by default
        RoomType roomType = RoomType.DOUBLE;

        switch (number){
            case 1:
                roomType = RoomType.SINGLE;
                break;
            case 2:
                roomType = RoomType.DOUBLE;
                break;
            default:
                System.out.println(" Not a valid room type, default to double");
                break;
        }

        return roomType;
    }
}

