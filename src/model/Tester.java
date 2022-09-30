package model;

import java.util.concurrent.Callable;

public class Tester {

    public static void main( String [] args ){
        Customer customer = new Customer(" first", "second", "j@domain.com");
        System.out.println(customer);

        Customer customer2 = new Customer(" first", "second", "email.com");
        System.out.println(customer2);
    }
}
