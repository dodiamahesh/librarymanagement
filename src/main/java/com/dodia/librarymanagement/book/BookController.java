package com.dodia.librarymanagement.book;

import com.dodia.librarymanagement.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class BookController {

        @Autowired
        private BookRepository bookRepository;

        @RequestMapping(value = "/book", method = RequestMethod.GET, headers = "Accept=application/json")
        public List<Book> getAllStudent(Model model) {
            List<Book> listOfStudent = (List<Book>) bookRepository.findAll();
            model.addAttribute("book", new Book());
            model.addAttribute("listOfBooks", listOfStudent);
            return listOfStudent;
        }

        @RequestMapping(value = "/book/{bid}", method = RequestMethod.GET, headers = "Accept=application/json")
        public ResponseEntity<Book> getStudentById(@PathVariable(value = "bid") Long studentId) throws Throwable {
            Book book = bookRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + studentId));
            return ResponseEntity.ok().body(book);
        }

        @RequestMapping(value = "/book", method = RequestMethod.POST, headers = "Accept=application/json")
        public Book createStudent(@RequestBody Book book) {
            return (Book) bookRepository.save(book);
        }

        @RequestMapping(value = "/book", method = RequestMethod.PUT, headers = "Accept=application/json")
        public ResponseEntity<Book> updateEmployee(@RequestBody Book bookDetail) throws Throwable {
            long bookId = bookDetail.getBid();
            Book book = (Book) bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));

            book.setBookName(bookDetail.getBookName());
            book.setAuthor(bookDetail.getAuthor());
            book.setTotalCopy(bookDetail.getTotalCopy());
            book.setAvailableCopy(bookDetail.getAvailableCopy());
            final Book updatedStudent = (Book) bookRepository.save(book);
            return ResponseEntity.ok(updatedStudent);
        }

        @DeleteMapping("/book/{bid}")
        public Map<String, Boolean> deleteStudent(@PathVariable(value = "bid") Long bookId) throws Throwable {
            Book book = (Book) bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
            bookRepository.delete(book);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            return response;
        }
}
