package api;


import model.Customer;
import model.IRoomInterface;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.Queue;
import java.util.Set;

public final class HotelResource {

    private static HotelResource hotelResource;

    private static CustomerService customerService = CustomerService.getInstance();
    private static ReservationService reservationService = ReservationService.getInstance();
    private HotelResource(){}
    public static HotelResource getInstance() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    public Customer getCustomer(String email) {
        Customer customer = customerService.getCustomer(email);

        return customer;
    }

    public void createACustomer(String email, String firstName, String lastName){

        customerService.addCustomer(email,firstName,lastName);

    }

    public IRoomInterface getARoom( String roomNumber){

        return reservationService.getARoom(roomNumber);

    }

    public Reservation bookARoom( String customerEmail, IRoomInterface room,
                                  Date checkinInDate, Date checkOutDate) {

        return reservationService.reserveARoom(getCustomer(customerEmail),
                room, checkinInDate,checkOutDate);

    }

    public Collection<Reservation> getCustomersReservation(String customerEmail){

        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public Collection<IRoomInterface> findARoom(Date checkInDate, Date checkOutDate){

        return reservationService.findRooms(checkInDate, checkOutDate);

    }


}
