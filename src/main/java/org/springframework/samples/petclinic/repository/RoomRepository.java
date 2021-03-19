package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Room;

public interface RoomRepository extends Repository<Room, Integer>{

	Collection<Room> findAll() throws DataAccessException;
	
	Optional<Room> findById(int id) throws DataAccessException;
	
	void save(Room room) throws DataAccessException;
	
	void delete(int id);
	
	
}
