package com.dodia.librarymanagement.assignment;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AssignmentRepository extends CrudRepository<Assignment, Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Assignment  SET active = :active,submitBy = :submitBy WHERE bid = :bookId and sid = :studentId and active = :isActive")
    int submitBook(@Param("active") int active,@Param("submitBy") int submitBy, @Param("bookId") int bookId,@Param("studentId") int studentId,@Param("isActive") int isActive);
}
