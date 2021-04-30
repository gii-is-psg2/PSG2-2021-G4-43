package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private BookService bookService;


	@Transactional(readOnly = true)
	public Collection<Room> findAll(){
		return roomRepository.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Room> findById(int id){
		return roomRepository.findById(id);
	}
	
	@Transactional
	public void save(Room room) {
		roomRepository.save(room);
	}
	
	@Transactional
	public Optional<Room> getHabitacionLibre(LocalDate arrival, LocalDate departure,Pet pet){
		for(Room r : findAll()) {
			Boolean libre = true;
			for(Book b : bookService.findAllByRoomId(r.getId())) {
				if( (arrival.isAfter(b.getArrivalDate()) && arrival.isBefore(b.getDepartureDate()))
					|| (departure.isAfter(b.getArrivalDate()) && departure.isBefore(b.getDepartureDate()))
					|| (arrival.isBefore(b.getArrivalDate()) && departure.isAfter(b.getDepartureDate()))
					|| arrival.isEqual(b.getArrivalDate()) || arrival.isEqual(b.getDepartureDate())
					|| departure.isEqual(b.getArrivalDate()) || departure.isEqual(b.getDepartureDate())) {
					libre = false;
					break;
				}
			}
			if(libre) {
				return Optional.of(r);
			}
		}
		return Optional.empty();
	}
	

	@Transactional
	public void delete(Room room) {
		roomRepository.delete(room);
	}

}
