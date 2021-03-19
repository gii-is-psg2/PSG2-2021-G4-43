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
	
	public Boolean habitacionLibre(LocalDate arrival, LocalDate departure){
		for(Room r : findAll()) {
			for(Book b : r.getBooks()) {
				if(arrival.isAfter(b.getArrival_date()) && arrival.isBefore(b.getDeparture_date()) 
						|| departure.isAfter(b.getArrival_date()) && departure.isBefore(b.getDeparture_date())) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void updateHabitacion(Room room,Pet pet) {
		room.setPet(pet);
		save(room);
	}
	
	public void updateReservasHabitacion(Room room,Book book) {
		room.getBooks().add(book);
		save(room);
	}
	
	public Room getHabitacionLibre(LocalDate arrival, LocalDate departure,Pet pet){
		for(Room r : findAll()) {
			Boolean aux = true;
			for(Book b : r.getBooks()) {
				if(arrival.isAfter(b.getArrival_date()) && arrival.isBefore(b.getDeparture_date()) 
						|| departure.isAfter(b.getArrival_date()) && departure.isBefore(b.getDeparture_date())) {
					aux = false;
					break;
				}
			}
			if(aux==true) {
				updateHabitacion(r,pet);
				return r;
			}
		}
		return null;
	}
	

	@Transactional
	public void delete(int id) {
		roomRepository.delete(id);
	}

}
