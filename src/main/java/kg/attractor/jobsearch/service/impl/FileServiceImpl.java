package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public ResponseEntity<?> getOutputFile(String filename, String subDir, MediaType mediaType) {
        try {
            byte[] image = Files.readAllBytes(Paths.get(UPLOAD_DIR + subDir + "/" + filename));
            Resource resource = new ByteArrayResource(image);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentLength(resource.contentLength())
                    .contentType(mediaType)
                    .body(resource);
        } catch (NoSuchFileException e) {
            log.error("No file found:", e);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image not found");
        } catch (IOException e) {
            log.error("Error reading file:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file");
        }
    }
}