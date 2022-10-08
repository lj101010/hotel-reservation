package api;


import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

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

    public IRoom getARoom(String roomNumber){

        return reservationService.getARoom(roomNumber);

    }

    public Reservation bookARoom( Customer customer, IRoom room,
                                  Date checkinInDate, Date checkOutDate) {

        return reservationService.reserveARoom( customer, room, checkinInDate,checkOutDate);

    }

    public Collection<Reservation> getCustomersReservation(String customerEmail){

        return reservationService.getCustomersReservation(getCustomer(customerEmail));
    }

    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate){

        return reservationService.findRooms(checkInDate, checkOutDate);

    }

    public Date searchNewDate(Date date){
        return reservationService.searchNewDate(date);
    }


}
