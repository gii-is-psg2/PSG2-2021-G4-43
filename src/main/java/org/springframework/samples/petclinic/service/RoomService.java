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
	
	public Optional<Room> getHabitacionLibre(LocalDate arrival, LocalDate departure,Pet pet){
		for(Room r : findAll()) {
			Boolean libre = false;
			for(Book b : bookService.findAllByRoomId(r.getId())) {
				if(!(arrival.isAfter(b.getArrival_date()) && arrival.isBefore(b.getDeparture_date()) 
						|| departure.isAfter(b.getArrival_date()) && departure.isBefore(b.getDeparture_date())
						|| arrival.isBefore(b.getArrival_date()) && departure.isAfter(b.getDeparture_date()))) {
					libre = true;
					break;
				}
			}
			if(libre==true) {
				return Optional.of(r);
			}
		}
		return null;
	}
	

	@Transactional
	public void delete(int id) {
		roomRepository.delete(id);
	}

}
