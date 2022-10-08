package service;

import model.Customer;
import model.Reservation;

import java.util.*;

public final class CustomerService {

    private static CustomerService customerService;

    private CustomerService(){}
    public static CustomerService  getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public static Set<Customer> customerList = new HashSet<>();
    public static Map< String, Customer > mapOfCustomers = new HashMap<>();

    public void addCustomer( String email, String firstName, String lastName){

        //add new customer to array
        Customer newCustomer = new Customer( firstName, lastName, email);
        customerList.add(newCustomer);

        System.out.println(" Created account : "+ newCustomer);
        //add to map with email as key
        mapOfCustomers.put(newCustomer.getEmail() , newCustomer);

    }

    public Customer getCustomer( String customerEmail) {

        if(!mapOfCustomers.containsKey(customerEmail)){
            System.out.println(" This email does not correlate with a customer ");
            return null;

        }else {
            return mapOfCustomers.get(customerEmail);
        }

    }

    public Collection<Customer> getAllCustomers(){
       return mapOfCustomers.values();
    }
}
