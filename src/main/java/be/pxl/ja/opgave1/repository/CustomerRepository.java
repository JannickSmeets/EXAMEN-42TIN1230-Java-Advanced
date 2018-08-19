package be.pxl.ja.opgave1.repository;

import be.pxl.ja.opgave1.model.Customer;
import be.pxl.ja.opgave1.processing.CustomerBirthdayPredicate;
import be.pxl.ja.opgave1.processing.CustomerLocationPredicate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerRepository {

    private final Map<String, Customer> customers = new HashMap<>();

    public CustomerRepository() {
        for (final Customer customer : Customers.CUSTOMERS) {
            customers.put(customer.getCustomerNumber(), customer);
        }
    }

    public Customer getByCustomerNumber(final String customerNumber) {
        //  Indien er geen klant bestaat met het gegeven klantnummer geef je null als resultaat.
        //  De tweede methode voorziet een lijst met alle klanten.
        //  NOTE -->  collection.get will return NULL if the key does not exist in the collection.

        // No need to query the database is the customer number isn't properly set.
        if (customerNumber == null || customerNumber.isEmpty()) {
            return null;
        }

        return customers.get(customerNumber);
    }

    public Set<Customer> findAll() {
        return new HashSet<>(customers.values());
    }

    public Set<Customer> findAllFromLocation(final String location) {
        return findAll()
                .stream()
                .filter(new CustomerLocationPredicate(location))
                .collect(Collectors.toSet());
    }

    public Set<Customer> findAllCustomersWithBirthday() {
        return findAll()
                .stream()
                .filter(new CustomerBirthdayPredicate())
                .collect(Collectors.toSet());
    }

    public Set<Customer> find10Youngest() {
        return findAll()
                .stream()
                .sorted((c1, c2) -> c2.getDateOfBirth().compareTo(c1.getDateOfBirth()))
                .limit(10)
                .collect(Collectors.toSet());
    }

    public Set<Customer> findTop10() {
        return findAll()
                .stream()
                .sorted((c1, c2) -> c2.getPoints() - c1.getPoints())
                .limit(10)
                .collect(Collectors.toSet());
    }

}
