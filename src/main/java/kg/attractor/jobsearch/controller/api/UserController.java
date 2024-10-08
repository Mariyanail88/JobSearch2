package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;

import java.io.IOException;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserDto user) {
        user.setAccountType("applicant");
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<Void> uploadAvatar(@RequestParam("userId") Integer userId, @RequestParam("avatar") MultipartFile avatar) throws UserNotFoundException, IOException {
        userService.saveAvatar(userId, avatar);
        return ResponseEntity.ok().build();
    }
}