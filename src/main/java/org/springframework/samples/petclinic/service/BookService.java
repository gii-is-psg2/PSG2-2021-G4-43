package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.BookRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private UserService userService;


	@Transactional(readOnly = true)
	public Collection<Book> findAll(){
		return bookRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Book> findById(int id){
		return bookRepository.findById(id);
	}
	
	@Transactional
	public void save(Book book) {
		Optional<User> user = userService.findUser(SecurityContextHolder.getContext().getAuthentication().getName());
		book.setUser(user.get());
		book.setRoom(roomService.getHabitacionLibre(book.getArrival_date(),book.getDeparture_date(),book.getPet()));
		roomService.updateReservasHabitacion(book.getRoom(), book);
		bookRepository.save(book);
	}
	
	@Transactional(readOnly = true)
	public Collection<Book> findAllByAlumno(String username){
		return bookRepository.findAllByUserId(username);
	}
	
	@Transactional
	public void delete(int id) {
		Optional<Book> book = findById(id);
		roomService.updateHabitacion(book.get().getRoom(),book.get().getPet());
		bookRepository.delete(id);
	}

}
