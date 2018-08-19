package be.pxl.ja.opgave1.processing;

import be.pxl.ja.opgave1.model.Customer;

import java.time.LocalDate;
import java.util.function.Predicate;

public class CustomerBirthdayPredicate implements Predicate<Customer> {

    private final LocalDate now = LocalDate.now();

    @Override
    public boolean test(final Customer customer) {
        final LocalDate customerDateOfBirth = customer.getDateOfBirth();

        if (customerDateOfBirth == null) {
            return false;
        }

        return customerDateOfBirth.getMonthValue() == now.getMonthValue()
                && customerDateOfBirth.getDayOfMonth() == now.getDayOfMonth();
    }

}
