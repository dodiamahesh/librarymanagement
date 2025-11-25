package com.dodia.librarymanagement.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RequestBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dodia.librarymanagement.exception.*;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
//@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

//    @GetMapping("/student")
    @RequestMapping(value = "/student", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Student> getAllStudent(Model model) {
        List<Student> listOfStudent = (List<Student>) studentRepository.findAll();
        model.addAttribute("customer", new Student());
        model.addAttribute("listOfCustomers", listOfStudent);
        return listOfStudent;
    }

//    @GetMapping("/student/{sid}")
    @RequestMapping(value = "/student/{sid}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "sid") Long studentId)
            throws ResourceNotFoundException {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId));
        return ResponseEntity.ok().body(student);
    }

//    @PostMapping("/student)
@RequestMapping(value = "/student", method = RequestMethod.POST, headers = "Accept=application/json")
public Student createStudent(@RequestBody Student student) {

        return studentRepository.save(student);
    }

//    @PutMapping("/student")
    @RequestMapping(value = "/student", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<Student> updateEmployee(@RequestBody Student studentDetail) throws ResourceNotFoundException {
        long studentId = studentDetail.getSid();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + studentId));

        student.setEmailId(studentDetail.getEmailId());
        student.setLastName(studentDetail.getLastName());
        student.setFirstName(studentDetail.getFirstName());
        student.setMobile(studentDetail.getMobile());
        final Student updatedStudent = studentRepository.save(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/student/{sid}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "sid") Long employeeId)
            throws ResourceNotFoundException {
        Student student = studentRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found for this id :: " + employeeId));

        studentRepository.delete(student);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
