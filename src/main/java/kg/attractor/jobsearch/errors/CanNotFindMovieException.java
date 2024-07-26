package kg.attractor.jobsearch.errors;

public class CanNotFindMovieException extends RuntimeException {
    public CanNotFindMovieException() {
    }

    public CanNotFindMovieException(String message) {
        super(message);
    }
}
