package be.pxl.ja.opgave1.processing;

import be.pxl.ja.opgave1.model.Customer;

import java.util.function.Predicate;

public class CustomerLocationPredicate implements Predicate<Customer> {

    private final String location;

    public CustomerLocationPredicate(final String location) {
        this.location = location;
    }

    @Override
    public boolean test(final Customer customer) {
        return customer.getCity().equalsIgnoreCase(location);
    }

}
