package be.pxl.ja.opgave2;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PassPhraseValidator<T> extends Thread {

    private final List<T> passPhrase;

    private boolean valid;

    public PassPhraseValidator(final List<T> passPhrase) {
        this.passPhrase = passPhrase;
    }

    @Override
    public void run() {
        final Set<T> checkedValue = new HashSet<>(); // Sets are useful, they can't contain duplicates.

        boolean scanValid = true;

        for (final T value : passPhrase) {
            if (!checkedValue.add(value)) { // Was not added to the list.
                // Add returns false on a set if the same object has already been added.
                scanValid = false;  // Duplicate detected.
                break;
            }
        }

        valid = scanValid; // Setting it here since we're running this in another thread and we don't want it to show a false positive.
    }

    public List<T> getPassPhrase() {
        return passPhrase;
    }

    public boolean isValid() {
        return valid;
    }

}
