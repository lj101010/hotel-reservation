package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.*;

public final class AdminResource {

    private static CustomerService customerService = CustomerService.getInstance();
    private static ReservationService reservationService = ReservationService.getInstance();

    private static AdminResource adminResource;

    private AdminResource(){}
    public static AdminResource getInstance() {
        if (adminResource== null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    public Customer getCustomer(String email){

       return customerService.getCustomer(email);

    }

    public void addRoom(List<IRoom> rooms) {

        for( IRoom i: rooms)
            reservationService.addRoom(i);

    }

    public Collection <IRoom> getAllRooms(){

        return reservationService.getAllRooms();
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayAllReservations(){
        reservationService.printAllReservations();
    }

    public boolean doesRoomExist( String roomNumber){

        IRoom room = reservationService.getARoom( roomNumber);

        if (null != room) {
            return true;
        }else {
            return false;
        }
    }
}
