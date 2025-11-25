package com.dodia.librarymanagement.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookRepository extends JpaRepository<Book,Long> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Book  SET availableCopy = :availableCopy WHERE bid = :bookId")
    int updateBookCount(@Param("bookId") int bookId, @Param("availableCopy") int availableCopy);
}
