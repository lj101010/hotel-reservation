package model;

import java.util.Date;
import java.util.Objects;

public class Reservation {

    private  Customer customer;

    private  IRoomInterface room;

    private Date checkinInDate;

    private  Date checkoutDate;

    public Reservation( Customer _customer, IRoomInterface _room, Date _checkinDate, Date _checkoutDate){
        customer = _customer;
        room = _room;
        checkinInDate = _checkinDate;
        checkoutDate = _checkoutDate;
    }

    public IRoomInterface getRoom() {
        return room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Date getCheckinInDate() {
        return checkinInDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

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
