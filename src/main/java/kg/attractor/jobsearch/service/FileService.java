package kg.attractor.jobsearch.service;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;

public interface FileService {
    ResponseEntity<?> getOutputFile(String filename, String subDir, MediaType mediaType);
}