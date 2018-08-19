package be.pxl.ja.opgave1;

import be.pxl.ja.opgave1.model.Activity;
import be.pxl.ja.opgave1.model.Customer;
import be.pxl.ja.opgave1.processing.ActivityProcessor;
import be.pxl.ja.opgave1.processing.CustomerNamePrinter;
import be.pxl.ja.opgave1.repository.CustomerRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Opgave1 {

    public static void main(String[] args) {
        final CustomerRepository customerRepository = new CustomerRepository();

        System.out.println("*** Klanten uit Louisville:");

        final int customersFromLouisville = customerRepository.findAllFromLocation("Louisville").size();
        System.out.println(customersFromLouisville);

        System.out.println("*** Jarige klanten: ");

        customerRepository.findAllCustomersWithBirthday()
                .forEach(CustomerNamePrinter::printFormatted);

        System.out.println("*** 10 jongste klanten:");

        customerRepository.find10Youngest()
                .forEach(CustomerNamePrinter::printFormatted);

        final ActivityProcessor activityFileProcessor = new ActivityProcessor(customerRepository);
        List<Activity> allActivities = new ArrayList<>();

        final Path errorFile = Paths.get("resources/opgave1/log/errors.log");
        final Path opgave1Folder = Paths.get("resources/opgave1/");

        try {
            // Auto-close the stream.
            try (Stream<Path> paths = Files.list(opgave1Folder)) {
                paths.filter(Files::isRegularFile)
                        .forEach(file -> allActivities.addAll(activityFileProcessor.processActivities(file, errorFile)));
            }
        } catch (IOException e) {
            System.err.println("Cannot read the files inside ");
        }

        System.out.println("*** Top 10 klanten");

        final Set<Customer> top10Customers = customerRepository.findTop10();
        top10Customers.forEach(CustomerNamePrinter::printWithPoints);

        System.out.println("** Alle activiteiten meest actieve klant (gesorteerd op datum):");

        top10Customers.forEach(customer -> {

            CustomerNamePrinter.printWithPoints(customer);

            allActivities.stream()
                    .filter(a -> customer.getCustomerNumber().equals(a.getCustomerNumber()))
                    .sorted(Comparator.comparing(Activity::getActivityDate))
                    .forEach(a -> {
                        System.out.println(String.format(
                                "%s - %s - %d",
                                a.getActivityDate(),
                                a.getActivityType(),
                                a.getAssignedPoints()
                        ));
                    });

        });

    }

}
