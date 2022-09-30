package api;

import model.Customer;
import model.IRoomInterface;
import model.Room;
import model.RoomType;

import java.util.*;

public class AdminMenu {
    private final Scanner adminScanner = new Scanner(System.in);
    private int adminSelection;
    private final AdminResource adminResource = AdminResource.getInstance();
    public void launchAdminMenu() {

        try {
            System.out.println("ADMIN MENU");
            System.out.println("=======================================");
            System.out.println("1.) See All Customers");
            System.out.println("2.) See ALL Rooms");
            System.out.println("3.) See ALL Reservations");
            System.out.println("4.) Add a Room");
            System.out.println("5.) Back To Main Menu");
            System.out.println("=======================================\n");
            System.out.print("Please Select a Number for Menu Option: ");

            while (!adminScanner.hasNextInt()){
                System.out.println("Error: Use a numeric from 1-5");
                adminScanner.nextLine();
            }
            adminSelection = adminScanner.nextInt();

            do {
                switch (adminSelection) {
                    case 1:
                        seeAllCustomers();
                        adminSelection=5;
                        break;
                    case 2:
                        seeAllRooms();
                        adminSelection=5;
                        break;
                    case 3:
                        seeAllReservations();
                        adminSelection=5;
                        break;
                    case 4://add a room
                       addRoom();
                       adminSelection = 5;
                        break;
                    case 5:
                        System.out.println(" Returning to Main Menu");
                        break;
                    default:
                        System.out.println(" Invalid Response\n");
                        break;

                }
            } while (adminSelection != 5);
        }catch (Exception e){
            System.out.println(" Input Error, Please ensure your input is of requested format");
        }

    }

    private void seeAllReservations() {
        adminResource.displayAllReservations();
    }

    private void seeAllCustomers(){

        Collection<Customer> customers = adminResource.getAllCustomers();
        if(customers.isEmpty()){
            System.out.println(" There are no customers currently registered at this hotel ");
        }else {
            System.out.println(adminResource.getAllCustomers());
        }

    }

    private void seeAllRooms(){
        Collection<IRoomInterface> allRooms = adminResource.getAllRooms();
        if(allRooms.isEmpty()){
            System.out.println(" This is a Roomless hotel: Admin must add rooms");
        }
        else {
            System.out.println(allRooms.toString());
        }
    }
    private void addRoom() {
        List<IRoomInterface> rooms = new ArrayList<>();

        int inputtedRoomNum = 0;
        String roomNum = new String();
        double roomPrice = 0.0;
        RoomType roomType = RoomType.SINGLE;
        String addAnotherRoom = new String();

        boolean validRoomNum = false;

        ///////////////////////////////////////////////////////////////////
        System.out.println("Enter Room Number");
            try {
                adminScanner.nextLine();
                while (!adminScanner.hasNextInt()) {
                    System.out.println("Error: Use a numeric for room number");
                    adminScanner.nextLine();
                }

                inputtedRoomNum = adminScanner.nextInt();
                roomNum = String.valueOf(inputtedRoomNum);

                boolean exists = adminResource.doesRoomExist(roomNum);

                if (exists == true) {
                    System.out.println("Room number: " + roomNum + " has already been created");


                    while (adminResource.doesRoomExist(roomNum)) {
                        System.out.println(" Please enter another room number ");
                        adminScanner.nextLine();

                        while (!adminScanner.hasNextInt()){
                            System.out.println("Error: Use a numeric for room number");
                            adminScanner.nextLine();
                        }
                        inputtedRoomNum = adminScanner.nextInt();
                        roomNum = String.valueOf(inputtedRoomNum);
                    }
                }

            } catch (Exception e) {
                adminScanner.next();
            }

        ///////////////////////////////////////////////////////////
        System.out.println("Enter Room Price");
        try {
            adminScanner.nextLine();
            while(!adminScanner.hasNextDouble())
            {
                System.out.println(" Error: Use a number for price");
                adminScanner.nextLine();
            }
            roomPrice = adminScanner.nextDouble();
            System.out.println(" This is the room price entered :" + roomPrice);
        } catch (Exception e) {
            System.out.println(" Error: invalid room price entered");
        }
        //////////////////////////////////////////////////////////
        System.out.println("Enter 1 for Single, 2 for Double");
        int inputtedType;
        try {
            adminScanner.nextLine();
            while(!adminScanner.hasNextInt()){
                System.out.println(" Error only 1 or 2 can be entered for room type");
                adminScanner.nextLine();
            }
            inputtedType = adminScanner.nextInt();
            roomType = RoomType.convertToEnum(inputtedType);
        } catch (Exception e) {
            System.out.println(" Type of room entered is invalid");
        }
        // add room
        IRoomInterface newRoom = new Room(roomNum, roomPrice, roomType);
        rooms.add(newRoom); // add to local array
        adminResource.addRoom(rooms); //return the array of added rooms

        //////////////////////////////////////////////////////////////////
        System.out.println(" Would you like to add another room? (y/n)");

        try {
            adminScanner.nextLine();
            addAnotherRoom = adminScanner.next();

            if ( addAnotherRoom.matches("y") || addAnotherRoom.matches("Y")) {
                System.out.println("Adding another room " );
                addRoom();

            } else
            {
                System.out.println(" Return to main menu");
            }
           // adminResource.addRoom(rooms); //return the array of added rooms
        }catch( Exception e){
            System.out.println(" this exception in add more room " + e);
        }
    }
}




