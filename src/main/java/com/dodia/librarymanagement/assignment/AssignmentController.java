package com.dodia.librarymanagement.assignment;

import com.dodia.librarymanagement.book.BookRepository;
import com.dodia.librarymanagement.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AssignmentController {

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private BookRepository bookRepository;

    //get list of book which total assigne by librarian
    //get list of book based on student id
    //save- Issue books to student -decrise count in book table's avaulable column
    //update -return book by student  -increase count in book table's available column


    @RequestMapping(value = "/issueBook", method = RequestMethod.POST, headers = "Accept=application/json")
    public Assignment createUser(@RequestBody Assignment assignment) {
        bookRepository.updateBookCount(assignment.getBid(),assignment.getAvailableCopy());
        return assignmentRepository.save(assignment);
    }

    @RequestMapping(value = "/submitBook", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<Integer> updateEmployee(@RequestBody Assignment assignment) throws ResourceNotFoundException {
        bookRepository.updateBookCount(assignment.getBid(),assignment.getAvailableCopy());
        final int updatedStudent =  assignmentRepository.submitBook(assignment.getActive(),assignment.getSubmitBy(),assignment.getBid(),assignment.getSid(),0);
        return ResponseEntity.ok(updatedStudent);
//        long bookId = assignment.getBid();
//        Book book = assignmentRepository.findByBidAndSid(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found for this id :: " + bookId));
//
//        book.setBookName(bookDetail.getBookName());
//        book.setAuthor(bookDetail.getAuthor());
//        book.setTotalCopy(bookDetail.getTotalCopy());
//        book.setAvailableCopy(bookDetail.getAvailableCopy());
//        final Book updatedStudent = bookRepository.save(book);
//        return ResponseEntity.ok(updatedStudent);
    }
}
