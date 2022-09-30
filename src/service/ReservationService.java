package service;

import model.*;


import java.util.*;

public final class ReservationService {

    private static ReservationService reservationService;

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }


    public Set<Reservation> reservationList = new HashSet<>();

    public Map<String, IRoomInterface> mapOfRooms = new HashMap<>();
    // mapCustomerReservation is map of reservations based on customer email
    public Map<String, Reservation> mapCustomerReservation = new HashMap<>();

    // Create a room and add it to the map containing all rooms
    public void addRoom(IRoomInterface room) {
        //add room to map of room with room number as key
        mapOfRooms.put(room.getRoomNumber(), room);
    }

    public Collection<IRoomInterface> getAllRooms() {
        return mapOfRooms.values();
    }

    // Pass in the room number and get corresponding room
    public IRoomInterface getARoom(String roomNumber) {

        for (String aRoomNum : mapOfRooms.keySet()) {
            if (aRoomNum.equals(roomNumber)) {
                return mapOfRooms.get(roomNumber);
            }
        }
        //no room found
        return null;


    }


    //Reserve a room using customer email as key value for map
    public Reservation reserveARoom(Customer customer, IRoomInterface room, Date checkin, Date checkout) {

        Reservation newReservation = new Reservation(customer, room, checkin, checkout);
        reservationList.add(newReservation);
        mapCustomerReservation.put(customer.getEmail(), newReservation);

        return newReservation;
    }

    public Collection<IRoomInterface> findRooms(Date checkIn, Date checkOut) {
        if (reservationList.isEmpty()) {
            if (!mapOfRooms.isEmpty()) {
                return mapOfRooms.values();
            } else {
                return null;
            }
        } else {
            return findAvailableRooms(checkIn, checkOut);
        }
    }


    //3rd
    // Look for a room that is available on requested date
   private Collection<IRoomInterface> findAvailableRooms(Date checkin, Date checkout) {
        Collection<IRoomInterface> availableRooms = new HashSet<>();

        for (Reservation reservation : reservationList) {
            System.out.println(" RESERVATION  : " + reservation);
            for (IRoomInterface room : mapOfRooms.values()) {
                System.out.println("ROOM : " + room);

                if ((room.getRoomNumber().equals(reservation.getRoom().getRoomNumber()))
                        && ((checkin.before(reservation.getCheckinInDate()) && checkout.before(reservation.getCheckinInDate()))
                        || (checkin.after(reservation.getCheckoutDate()) && checkout.after(reservation.getCheckoutDate())))
                        || (!reservation.getRoom().getRoomNumber().contains(room.getRoomNumber()))) {

                    System.out.println(" IN IF CHECK");
                    System.out.println(" this is room.getRoomNumber() " +room.getRoomNumber() );
                    System.out.println(" this is reservation.getRoom().getRoomNumber() " + reservation.getRoom().getRoomNumber());

                    System.out.println(" reservation.getCheckinInDate(): " + reservation.getCheckinInDate());
                    System.out.println(" checkin date " + checkin);
                    System.out.println(" =============================");
                    System.out.println(" reservation.getCheckoutDate() " + reservation.getCheckoutDate());
                    System.out.println(" checkout date " + checkout);
                    System.out.println(" ADDING ROOM " + room);
                    availableRooms.add(room);

                } else if (room.getRoomNumber().equals(reservation.getRoom().getRoomNumber())) {
                    availableRooms.remove(room);
                }
            }
        }
        return availableRooms;
    }




     //original
    /* private Collection<IRoomInterface> findAvailableRooms(Date checkinDate, Date checkoutDate) {
         Collection<IRoomInterface> availableRooms = new HashSet<>();
         // availableRooms = mapOfRooms.values();

         // go  through all existing reservations for date conflicts
         for (Reservation r : reservationList) {
             System.out.println(" RESERVATION  : " + r);
             for (IRoomInterface room : mapOfRooms.values()) {
                 System.out.println("ROOM : " + room);
                 if ((r.getRoom().equals(room))) {
                     if ((checkinDate.before(r.getCheckinInDate()) && checkoutDate.before(r.getCheckoutDate())) ||
                             (checkinDate.after(r.getCheckoutDate()) && checkoutDate.after(r.getCheckoutDate()))) {
                         System.out.println(" adding room  " + room);
                         availableRooms.add(room);


                     } else {
                         System.out.println(" In else now removing this room : " + room);
                         availableRooms.remove(room);
                       //y  searchNewDate(checkinDate, checkoutDate);

                     }
                 } else {
                     System.out.println(" Room does not match the reservation " + r.getRoom() + " room " + room);
                     availableRooms.add(room);

                 }
             }
         }
         System.out.println(" THIS IS availableRooms returned " + availableRooms);
         return availableRooms;
     }*/


     void searchNewDate( Date inDate, Date outDate){

        //Supply optional dates
        Calendar cal = Calendar.getInstance();
        cal.setTime(inDate);
        cal.add(Calendar.DATE, 7);
        Date newcheckinDate = cal.getTime();


        cal.setTime(outDate);
        cal.add(Calendar.DATE, 7);
        Date newcheckoutDate = cal.getTime();
        System.out.println(" Search for new check in date : ");
        System.out.println(" Try checking in date " + newcheckinDate);
        System.out.println(" Try checking out date" + newcheckoutDate);
    }

    public Collection<Reservation> getCustomersReservation(Customer customer){

       Set <Reservation> customerReservations = new HashSet<>();

       for( int i = 0; i < reservationList.size(); i++){
            for (Reservation r : reservationList) {
                if (r.getCustomer().equals(customer)) {
                    customerReservations.add(r);
                }
            }
        }
       return customerReservations;
    }


    public void printAllReservations(){

        if(!reservationList.isEmpty()) {
            System.out.println("Reservation: " + reservationList.toString());
        }else{
            System.out.println(" There are no reservations at this hotel ");
        }

    }
}
