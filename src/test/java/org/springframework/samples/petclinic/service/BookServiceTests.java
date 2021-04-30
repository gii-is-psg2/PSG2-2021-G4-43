package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.model.Authorities;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.NoRoomAvailableException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class BookServiceTests {                
    
	@Autowired
	protected BookService bookService;

	
	@Test
	void shouldFindAllByOwner() {
		Collection<Book> books = this.bookService.findAllByOwner("owner1");
		assertThat(books).hasSize(1);

		books = this.bookService.findAllByOwner("owner2");
		assertThat(books).isEmpty();
	}
	
	@Test
	void shouldFindAllByRoomId() {
		Collection<Book> books = this.bookService.findAllByRoomId(1);
		assertThat(books).hasSize(1);

		books = this.bookService.findAllByRoomId(5);
		assertThat(books).isEmpty();
	}

	@Test
	@Transactional
	void shouldInsertBook() throws NoRoomAvailableException{
		Collection<Book> books = this.bookService.findAll();
		int found = books.size();

		Book book = new Book();
		LocalDate dateArrival = LocalDate.of(2021, 03, 15);
		LocalDate dateDeparture = LocalDate.of(2021, 03, 21);
		book.setDepartureDate(dateDeparture);
		book.setArrivalDate(dateArrival);
		Pet pet = new Pet();
		pet.setBirthDate(dateArrival);
		pet.setId(10);
		pet.setName("Prueba");
		//pet.setType(PetType.);
		book.setPet(pet);
		Room room = new Room();
		room.setId(1);
		book.setRoom(room);
		
		this.bookService.save(book);
        assertThat(book.getId().longValue()).isNotZero();
        assertThat(bookService.findAll()).hasSize(found + 1);
	}
	
	
	@Test
	@Transactional
	void shouldDeleteBook() {
		Collection<Book> books = this.bookService.findAll();
		int booksbefore = books.size();
		
		Book book = this.bookService.findById(1).get();
		this.bookService.delete(book);
		
		books = this.bookService.findAll();
		assertThat(books).hasSize(booksbefore - 1);
	}
}
