package be.pxl.ja.opgave1.processing;

import be.pxl.ja.opgave1.model.Customer;

public class CustomerNamePrinter {

    public static void printFormatted(final Customer customer) {
        final String customerInfo = String.format(
                "%s %s %s",
                customer.getFirstName(),
                customer.getName(),
                customer.getDateOfBirth()
        );

        System.out.println(customerInfo);
    }

    public static void printWithPoints(final Customer customer) {
        final String customerInfo = String.format(
                "%s %s %d",
                customer.getFirstName(),
                customer.getName(),
                customer.getPoints()
        );

        System.out.println(customerInfo);
    }

}
