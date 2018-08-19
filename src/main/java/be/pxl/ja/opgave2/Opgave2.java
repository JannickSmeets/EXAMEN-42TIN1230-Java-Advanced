package be.pxl.ja.opgave2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Opgave2 {

    private static final String PASSPHRASES_FILE_LOCATION = "resources/opgave2/passphrases.txt";

    public static void main(String[] args) {
        int validPassphrases = 0;
        final List<String> lines = readAllPassphrases();

        for (final String line : lines) {
            final List<String> values = Arrays.asList(line.split("\\s+"));

            // Run the validator let's go.
            PassPhraseValidator<String> validator = new PassPhraseValidator<>(values);

            // Bypass the thread since it's not required.
            // --> Beter uw oef schrijven if you wanted this.
            validator.run();

            if (validator.isValid()) {
                validPassphrases++;
            }

        }

        System.out.println(String.format("Aantal geldige wachtwoordzinnen %d", validPassphrases));
    }

    private static List<String> readAllPassphrases() {
        final Path passphrasesFile = Paths.get(PASSPHRASES_FILE_LOCATION);

        try {
            return Files.readAllLines(passphrasesFile);
        } catch (IOException e) {
            System.err.println("Cannot read file: " + passphrasesFile);
        }

        return Collections.emptyList();
    }
}
