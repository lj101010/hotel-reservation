package service;

import model.*;


import java.util.*;

public final class ReservationService implements IReservationService{

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
    public static Set<IRoom> allRooms = new HashSet<>();


    public void addRoom(IRoom room) {

        allRooms.add(room);
    }

    public Collection<IRoom> getAllRooms() {
        Collection<IRoom> rooms = new HashSet<>();

        if(!allRooms.isEmpty())
        {
            for( IRoom i : allRooms){
                rooms.add(i);
            }

        }
        else {
            System.out.println(" THERE ARE NO ROOMS");
        }
        return rooms;
    }

    // Pass in the room number and get corresponding room
    public IRoom getARoom(String roomNumber) {


        if (!allRooms.isEmpty()) {
            for (IRoom i : allRooms) {
                if (i.getRoomNumber().equals(roomNumber)) {
                    return i;
                }
            }
        }

        return null;
    }


    //Reserve a room using customer email as key value for map
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkin, Date checkout) {

        Reservation newReservation = new Reservation(customer, room, checkin, checkout);
        reservationList.add(newReservation);
        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkIn, Date checkOut) {
        if (reservationList.isEmpty()) {
            if(!allRooms.isEmpty()) {
                return allRooms;
            } else {
                return null;
            }
        } else {
            return findAvailableRooms(checkIn, checkOut);
        }
    }


    private boolean validateDates( Reservation existingR, Date requestIn, Date requestOut){

         if ( (requestIn.before( existingR.getCheckinInDate() ) && requestOut.before(existingR.getCheckinInDate() ) )
                || (requestIn.after(existingR.getCheckoutDate()) && requestOut.after(existingR.getCheckoutDate())) ){
             return true;
         }else{
             return false;
         }

    }

    // Look for a room that is available on requested date
   private Collection<IRoom> findAvailableRooms(Date checkin, Date checkout) {

       Collection<IRoom> availableRooms = new HashSet<>();

        //First add rooms that are not reserved
       Collection<IRoom> totalRooms = getAllRooms();
       Collection<IRoom> reservedRooms = new HashSet<>();

       //check for rooms that are reserved
       for (Reservation r : reservationList) {
           reservedRooms.add(r.getRoom());
       }

       if(reservedRooms.size() < totalRooms.size())
       {
           for(IRoom rm: totalRooms) {
               for (IRoom rmRes : reservedRooms) {
                   if (!rm.equals(rmRes)) {
                       System.out.println(" Adding unreserved rooms as an option " + rm);
                       availableRooms.add(rm);
                   }
               }
           }
       }



       // Search all reservations to see if date conflicts
       for (Reservation reservation : reservationList) {
           for (IRoom room : allRooms) {

               if ((room.getRoomNumber().equals(reservation.getRoom().getRoomNumber()))) {
                   if (validateDates(reservation, checkin, checkout)) {

                       availableRooms.add(room);

                   }else {
                       System.out.println(" Room " + room + " is reserved for your selected time ");
                       availableRooms.remove(room);
                   }
               }
           }
       }

       return availableRooms;
       }


       public Date searchNewDate (Date date){
           Calendar cal = Calendar.getInstance();
           cal.setTime(date);
           cal.add(Calendar.DATE, 7);
           Date newDate = cal.getTime();
           System.out.println(" Try this date : " + newDate);
           return newDate;
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
            System.out.println("Reservation: " + reservationList);
        }else{
            System.out.println(" There are no reservations at this hotel ");
        }

    }
}
