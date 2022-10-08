package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {

    private  final Customer customer;

    private final IRoom room;

    private final Date checkinInDate;

    private  final Date checkoutDate;

    public Reservation(Customer _customer, IRoom _room, Date _checkinDate, Date _checkoutDate){
        customer = _customer;
        room = _room;
        checkinInDate = _checkinDate;
        checkoutDate = _checkoutDate;
    }

    public IRoom getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getCheckinInDate() {
        return checkinInDate;
    }

    public  Date getCheckoutDate() {
        return checkoutDate;
    }

    String getCustomerLastName() { return customer.getLastName();}

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer +
                ", room=" + room +
                ", checkinInDate=" + checkinInDate +
                ", checkoutDate=" + checkoutDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(customer, that.customer) && Objects.equals(getRoom(), that.getRoom()) && Objects.equals(getCheckinInDate(), that.getCheckinInDate()) && Objects.equals(getCheckoutDate(), that.getCheckoutDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, getRoom(), getCheckinInDate(), getCheckoutDate());
    }
}
