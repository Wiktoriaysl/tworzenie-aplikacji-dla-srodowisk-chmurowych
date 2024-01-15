package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.persistence.UserEntity;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService)
    {
        this.userService = userService;
    }
    @CrossOrigin()
    @GetMapping("/user")
    public UserEntity getUserById(@RequestParam Integer id){
        return userService.getUserById(id);
    }

    @CrossOrigin()
    @GetMapping("/users")
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @CrossOrigin()
    @PostMapping("create-user")
    public ResponseEntity<Integer> addUser(HttpEntity<String> httpEntity){
        Optional<UserEntity> insertionSuccess = userService.insertNewUser(httpEntity);
        Integer userId = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if(insertionSuccess.isPresent()){
            userId = insertionSuccess.get().getId();
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(userId, status);
    }

    @CrossOrigin()
    @PutMapping("update-user")
    public ResponseEntity<Integer> updateUser(@RequestParam Integer id, HttpEntity<String> httpEntity){
        Optional<UserEntity> insertionSuccess = userService.updateUser(id, httpEntity);
        Integer userId = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if(insertionSuccess.isPresent()){
            userId = insertionSuccess.get().getId();
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(userId, status);
    }

    @CrossOrigin()
    @DeleteMapping("delete-user")
    public ResponseEntity<Integer> deleteUser(@RequestParam Integer id){
        Optional<UserEntity> deletionSuccess = userService.deleteUser(id);
        Integer userId = null;
        HttpStatus status = HttpStatus.CONFLICT;
        if(deletionSuccess.isPresent()){
            userId = deletionSuccess.get().getId();
            status = HttpStatus.OK;
        }

        return new ResponseEntity<>(userId, status);
    }

    @CrossOrigin()
    @GetMapping("/users/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
    response.setContentType("text/csv");
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    String currentDateTime = dateFormatter.format(new Date());

    String headerKey = "Content-Disposition";
    String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
    response.setHeader(headerKey, headerValue);

    List <UserEntity> listUsers = userService.getAllUsers();

    ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
    String[] csvHeader = {"Name", "E-mail", "Status"};
    String[] nameMapping = {"name", "email", "status"};

    csvWriter.writeHeader(csvHeader);

    for (UserEntity user : listUsers) {
        csvWriter.write(user, nameMapping);
    }

    csvWriter.close();
    }

    @GetMapping("/HealthCheck")
    public void healthCheck(HttpServletResponse response) {
        try {
            userService.getAllUsers();
            response.setStatus(200);
        }
        catch (Exception exception) {
            response.setStatus(500);
        }
    }
}
