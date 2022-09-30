package api;

import model.Customer;
import model.IRoomInterface;
import model.Reservation;
import service.CustomerService;

import javax.lang.model.type.NullType;
import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    private static HotelResource hotelResource = HotelResource.getInstance();
    private static AdminResource adminResource = AdminResource.getInstance();
    private Scanner scanner = new Scanner(System.in);
    private AdminMenu adminMenu = new AdminMenu();

    public void launchMainMenu() {


        int selection;
        try {
            do {

                System.out.println("Welcome Hotel Reservation Application");
                System.out.println("=======================================");
                System.out.println("1.) Find and Reserve a Room ");
                System.out.println("2.) See My Reservations");
                System.out.println("3.) Create An Account");
                System.out.println("4.) Admin ");
                System.out.println("5.) EXIT");
                System.out.println("=======================================\n");
                System.out.print("Please Select a Number for Menu Option: ");

                while (!scanner.hasNextInt()){
                    System.out.println("Error: Use a numeric from 1-5");
                    scanner.nextLine();
                }

                selection = scanner.nextInt();
                System.out.println(" You selected: " + selection);

                switch (selection) {
                    case 1:
                        findAndReserveRoom();
                        break;
                    case 2:
                        seeMyReservations();
                        break;
                    case 3:
                        createAccount();
                        break;
                    case 4:
                        adminMenu.launchAdminMenu();
                        break;
                    case 5:
                        System.out.println("Exiting Program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println(" Invalid Input, Please make selection 1 - 5");
                        break;
                }
            } while (selection != 5);

        } catch (Exception e) {
            System.out.println(" Invalid input, please review format of choice");
            System.exit(0);

        }


    }

    private void createAccount() {
        String email;
        String firstName;
        String lastName;
        System.out.println("Enter your email Format=name@host.com");
        email = scanner.next();

        if (!validEmail(email)) {
            System.out.println(" Not a valid email, please enter valid email");
            createAccount();
        }else {

            System.out.println("Type in your first name ");
            firstName = scanner.next();

            System.out.println("Type in your last name");
            lastName = scanner.next();
            hotelResource.createACustomer(email, firstName, lastName);
        }
    }

    private boolean validEmail(String email) {
        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void seeMyReservations() {
        String email;
        System.out.println("Enter your email:");
        email = scanner.next();

        if (!validEmail(email)) {
            System.out.println(" Not a valid email format, please try again");
            seeMyReservations();
        }else {

            try {
                Collection<Reservation> reservations = hotelResource.getCustomersReservation(email);
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }
            }catch (Exception e){
                System.out.println(" This email did not return an existing reservation");
                System.out.println(" If you want to reserve a room, select option 1");
            }
        }
    }

    private void findAndReserveRoom() {
        Collection<IRoomInterface> availableRooms = new ArrayList<>();

        Date checkinDate = new Date();
        Date checkoutDate = new Date();
        DateFormat dateInFormat = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("Enter your check in date in format of MM/dd/yyyy");
        boolean validcheckinDate = false;

        while (!validcheckinDate){
            try {

                String dateEnteredIn = scanner.next();
                checkinDate = dateInFormat.parse(dateEnteredIn);
                validcheckinDate = true;
            } catch (ParseException e) {
                System.out.println(" You need to enter date in MM/dd/yyyy format");
            }
        }

        SimpleDateFormat dateOut = new SimpleDateFormat("MM/dd/yyyy");
        System.out.println("Enter your check out date in format MM/dd/yyyy");
        boolean validcheckoutDate = false;

        while(!validcheckoutDate) {
            try {
                String dateEnteredOut = scanner.next();
                checkoutDate = dateOut.parse(dateEnteredOut);
                validcheckoutDate = true;
            } catch (ParseException e) {
                System.out.println(" You need to enter date in MM/dd/yyy format");

            }
        }


        Calendar calendar = Calendar.getInstance();
        Date date = new java.util.Date();

        Date todaysDate = null;
        calendar.setTime(date);
        todaysDate = calendar.getTime();

        //cannot check in before today
        if (!checkinDate.before(todaysDate) && !checkoutDate.before(checkinDate)) {

            Collection<IRoomInterface> rooms = hotelResource.findARoom(checkinDate, checkoutDate);

            try{
                if(!rooms.isEmpty()) {
                    System.out.println("Room Available  for time range " + rooms.toString());

                    System.out.println("Would you like to book a room? y/n");
                    while (scanner.hasNextInt()){
                        System.out.println("Error: enter y or n ");
                        scanner.nextLine();
                    }

                    String bookARoom = scanner.next();

                    if(bookARoom.matches("y")){
                        System.out.println(" Enter email to search for your account");
                        String email = scanner.next();

                    if (!validEmail(email)) {
                        System.out.println(" Not a valid email, returning to main menu ");

                    }else{
                        try{
                            Customer customer = hotelResource.getCustomer(email);
                            System.out.println("These are the rooms to choose from : "+ rooms);
                            System.out.println(" Enter room number request  ");

                            String roomNum = scanner.next();
                            System.out.println(" Customer request room " + roomNum);

                            IRoomInterface aRoom = hotelResource.getARoom(roomNum);
                            if(null != aRoom) {
                                hotelResource.bookARoom(customer.getEmail(), aRoom, checkinDate, checkoutDate);
                                seeMyReservations();
                            }else{
                                System.out.println(" Room selected does not exist");
                            }

                       } catch ( RuntimeException string){
                       System.out.println(" Your email did not return a valid account");
                       System.out.println(" Return to main menu , Select option 3 Create Account.");
                       }
                }
                    }else{
                        System.out.println(" You have selected not to book a room. Back to main menu");
                    }
                } else{
                    System.out.println(" There are no rooms available for your initial dates");
                }
            }catch (NullPointerException ex){
            System.out.println(" There are no rooms in this hotel. Contact admin");
            }
        }
        else{
            System.out.println(" Requested dates are in the past or invalid");
            System.out.println(" Please choose option 1 with reservable date");
        }
    }
}





