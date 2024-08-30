package kg.attractor.jobsearch.service;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public interface FileService {
    ResponseEntity<?> getOutputFile(String filename, String subDir, MediaType mediaType);
}