package tana_edp_perume.example.tana_edp_perfume.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tana_edp_perume.example.tana_edp_perfume.Commons.ResourceNotFoundException;
import tana_edp_perume.example.tana_edp_perfume.Domain.Entities.UserSection.User;
import tana_edp_perume.example.tana_edp_perfume.Repositories.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserRepository _userRepository;
    @GetMapping("/users")
    public List<User> getAllEmployees() {
        return _userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity< User > getEmployeeById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = _userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/user")
    public User createUser(@Valid @RequestBody User user) {

        return _userRepository.save(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity < User > updateUser(@PathVariable(value = "id") Long userId,
                                                      @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = _userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));


        final User updatedUser = _userRepository.save(userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public Map < String, Boolean > deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User employee = _userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));

        _userRepository.delete(employee);
        Map< String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
