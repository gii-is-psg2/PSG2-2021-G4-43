package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Book;

public interface BookRepository extends Repository<Book, Integer>{

	Collection<Book> findAll() throws DataAccessException;
	
	Optional<Book> findById(int id) throws DataAccessException;
	
	void save(Book book) throws DataAccessException;
	
	@Query("SELECT DISTINCT b FROM Book b WHERE b.pet.owner.user.username LIKE :username")
	Collection<Book> findAllByOwner(@Param("username") String username) throws DataAccessException;
	
	@Query("SELECT DISTINCT b FROM Book b WHERE b.room.id LIKE :id")
	Collection<Book> findAllByRoomId(@Param("id") int id) throws DataAccessException;
	
	void delete(Book book);
	
	
}
