package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.repository.BookRepository;
import org.springframework.samples.petclinic.service.exceptions.NoRoomAvailableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private RoomService roomService;


	@Transactional(readOnly = true)
	public Collection<Book> findAll(){
		return bookRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Book> findById(int id){
		return bookRepository.findById(id);
	}
	
	@Transactional
	public void save(Book book) throws NoRoomAvailableException {
		Optional<Room> room = roomService.getHabitacionLibre(book.getArrivalDate(),book.getDepartureDate(),book.getPet());
		if(!room.isPresent()) {
			throw new NoRoomAvailableException();
		}
		else {
			book.setRoom(room.get());
			bookRepository.save(book);
		}
	}
	
	@Transactional(readOnly = true)
	public Collection<Book> findAllByOwner(String username){
		return bookRepository.findAllByOwner(username);
	}
	
	@Transactional(readOnly = true)
	public Collection<Book> findAllByRoomId(int id){
		return bookRepository.findAllByRoomId(id);
	}
	
	@Transactional(readOnly = true)
	public Boolean findSameBooks(LocalDate arrivalDate, LocalDate departureDate, Integer petId) throws DataAccessException {
		return bookRepository.findSameBooks(arrivalDate, departureDate, petId).size()>0;
	}
	
	@Transactional
	public void delete(Book book) {
		bookRepository.delete(book);
	}

}
