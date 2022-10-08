package api;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();

    private final Scanner scanner = new Scanner(System.in);
    private final AdminMenu adminMenu = new AdminMenu();

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
                if(!reservations.isEmpty()) {
                    for (Reservation reservation : reservations) {
                        System.out.println(reservation);
                    }
                }else{
                    System.out.println("You have no reservations at this hotel. ");
                    System.out.println("Choose Option One to make a reservation. ");
                }
            }catch (Exception e){
                System.out.println(" This email did not return an existing reservation.");
                System.out.println(" Make sure you have created an account. ");
            }
        }
   }

    //Ensure date entered is correct format
    private Date validateDate( ){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        boolean validDate = false;

        while (!validDate){
            try {
                String dateEnteredIn = scanner.next();
                date = dateFormat.parse(dateEnteredIn);
                validDate = true;
            } catch (ParseException e) {
                System.out.println(" You need to enter date in MM/dd/yyyy format");
            }
        }
        return date;
    }

    private void findAndReserveRoom() {
        String email = null;
        Customer customer = null;
        Collection<IRoom> rooms = new HashSet<>();
        Date checkinDate = new Date();
        Date checkoutDate = new Date();

        System.out.println("Enter your check in date in format of MM/dd/yyyy");
        checkinDate = validateDate();

        System.out.println("Enter your check out date in format MM/dd/yyyy");
        checkoutDate = validateDate();

        //Grab today's date to use as boundary for date check
        Calendar calendar = Calendar.getInstance();
        Date date = new java.util.Date();
        Date todayDate = null;
        calendar.setTime(date);
        todayDate = calendar.getTime();

        if (!checkinDate.before(todayDate) && !checkoutDate.before(checkinDate)) {

            try {
                rooms = hotelResource.findARoom(checkinDate, checkoutDate);
                //no rooms returned for initial date request so search +7
                while(rooms.isEmpty()){
                System.out.println(" NO rooms were found for your initial dates ");
                Date newDateIn = new Date();
                Date newDateOut = new Date();

                newDateIn = hotelResource.searchNewDate(checkinDate);

                newDateOut = hotelResource.searchNewDate(checkoutDate);

                Collection<IRoom> roomOptions = hotelResource.findARoom(newDateIn, newDateOut);
                System.out.println(" These are the rooms available for new dates :" + roomOptions);
                checkinDate = newDateIn;
                checkoutDate = newDateOut;
                rooms.addAll(roomOptions);
            }

                System.out.println("Would you like to book a room? y/n");
                while (scanner.hasNextInt()) {
                    System.out.println("Error: enter y or n ");
                    scanner.nextLine();
                }

                String bookARoom = scanner.next();

                if (bookARoom.matches("y")) {
                    System.out.println(" Enter email to search for your account");
                    email = scanner.next();

                    if (validEmail(email)) {

                            customer = hotelResource.getCustomer(email);

                        if( customer != null) {
                            System.out.println("These are the rooms to choose from : " + rooms);
                            System.out.println(" Enter room number request  ");

                            String roomNum = scanner.next();
                            System.out.println(" Customer request room " + roomNum);

                            IRoom aRoom = hotelResource.getARoom(roomNum);
                            if (null != aRoom) {
                                Reservation reservation = hotelResource.bookARoom(customer, aRoom, checkinDate, checkoutDate);
                                System.out.println(" RESERVATION : " + reservation);
                            } else {
                                System.out.println(" Room selected does not exist");
                            }
                        }else{
                            System.out.println(" Email did not return a customer account. ");
                            System.out.println(" Please use Option 3 to create an account with this email. ");
                        }
                    }else{
                        System.out.println(" Invalid email");
                    }
                }

            }catch( Exception e){
                System.out.println(" Something is terribly wrong at this hotel, no rooms, contact ADMIN" + e ) ;
            }
        } else{
            System.out.println(" Invalid dates entered returning to main menu ");
        }
    }
}





