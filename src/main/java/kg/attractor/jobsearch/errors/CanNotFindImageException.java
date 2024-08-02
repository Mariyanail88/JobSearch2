package kg.attractor.jobsearch.errors;

import java.util.NoSuchElementException;

public class CanNotFindImageException extends NoSuchElementException {
    public CanNotFindImageException(String msg) {
        super(msg);
    }
}
