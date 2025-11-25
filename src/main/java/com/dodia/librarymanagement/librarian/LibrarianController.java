package com.dodia.librarymanagement.Librarian;

import com.dodia.librarymanagement.book.Book;
import com.dodia.librarymanagement.book.BookRepository;
import com.dodia.librarymanagement.exception.ResourceNotFoundException;
import com.dodia.librarymanagement.librarian.Librarian;
import com.dodia.librarymanagement.librarian.LibrarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class LibrarianController {

    @Autowired
    private LibrarianRepository librarianRepository;

    @RequestMapping(value = "/librarian", method = RequestMethod.GET, headers = "Accept=application/json")
    public List<Librarian> getAllStudent(Model model) {
        List<Librarian> listOfStudent = (List<Librarian>) librarianRepository.findAll();
        model.addAttribute("librarian", new Librarian());
        model.addAttribute("listOfLibrarians", listOfStudent);
        return listOfStudent;
    }

    @RequestMapping(value = "/librarian/{bid}", method = RequestMethod.GET, headers = "Accept=application/json")
    public ResponseEntity<Librarian> getStudentById(@PathVariable(value = "bid") Long studentId)throws ResourceNotFoundException {
        Librarian librarian = librarianRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Librarian not found for this id :: " + studentId));
        return ResponseEntity.ok().body(librarian);
    }

    @RequestMapping(value = "/librarian", method = RequestMethod.POST, headers = "Accept=application/json")
    public Librarian createStudent(@RequestBody Librarian librarian) {
        return librarianRepository.save(librarian);
    }
//
//    @RequestMapping(value = "/librarian", method = RequestMethod.PUT, headers = "Accept=application/json")
//    public ResponseEntity<Librarian> updateEmployee(@RequestBody Librarian librarianDetail) throws ResourceNotFoundException {
//        long librarianId = librarianDetail.getBid();
//        Librarian librarian = librarianRepository.findById(librarianId).orElseThrow(() -> new ResourceNotFoundException("Librarian not found for this id :: " + librarianId));
//
//        librarian.setLibrarianName(librarianDetail.getLibrarianName());
//        librarian.setAuthor(librarianDetail.getAuthor());
//        librarian.setTotalCopy(librarianDetail.getTotalCopy());
//        librarian.setAvailableCopy(librarianDetail.getAvailableCopy());
//        final Librarian updatedStudent = librarianRepository.save(librarian);
//        return ResponseEntity.ok(updatedStudent);
//    }

    @DeleteMapping("/librarian/{bid}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "bid") Long librarianId)throws ResourceNotFoundException {
        Librarian librarian = librarianRepository.findById(librarianId).orElseThrow(() -> new ResourceNotFoundException("Librarian not found for this id :: " + librarianId));
        librarianRepository.delete(librarian);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
