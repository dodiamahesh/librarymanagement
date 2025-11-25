package com.dodia.librarymanagement.user;

import com.dodia.librarymanagement.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    //get student list
    //get Librarian list
    //update user
    //delete user
    //insert user
    //add address in saparat table

    @RequestMapping(value = "/alluser", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<User> getAllUser(Model model) {
        List<User> listOfUser = (List<User>) userRepository.findAll();
        model.addAttribute("user", new User());
        model.addAttribute("listOfUsers", listOfUser);
        return listOfUser;
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<User> getAllUser(@RequestParam  String type,Model model) {
        List<User> listOfUser = (List<User>) userRepository.findByType(type);
        model.addAttribute("user", new User());
        model.addAttribute("listOfUsers", listOfUser);
        return listOfUser;
    }
    @RequestMapping(value = "/user/{uid}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<User> getUserById(@PathVariable(value = "uid") Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, headers = "Accept=application/json")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<User> updateEmployee(@RequestBody User userDetail) throws ResourceNotFoundException {
        long userId = userDetail.getUid();
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        user.setEmailId(userDetail.getEmailId());
        user.setLastName(userDetail.getLastName());
        user.setFirstName(userDetail.getFirstName());
        user.setMobile(userDetail.getMobile());
        user.setType(userDetail.getType());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{sid}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "sid") Long employeeId)throws ResourceNotFoundException {
        User user = userRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + employeeId));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
