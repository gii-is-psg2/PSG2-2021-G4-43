package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public Boolean habitacionLibre(){
		for(Room r : findAll()) {
			if(r.getPet()==null) {
				return true;
			}
		}
		return false;
	}
	
	public void updateHabitacion(Room room,Pet pet) {
		room.setPet(pet);
		save(room);
	}
	
	public Room getHabitacionLibre(Pet pet){
		for(Room r : findAll()) {
			if(r.getPet()==null) {
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
