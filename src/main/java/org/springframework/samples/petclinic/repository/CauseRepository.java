package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Cause;

public interface CauseRepository extends Repository<Cause, Integer>{

	Collection<Cause> findAll() throws DataAccessException;
	
	Optional<Cause> findById(int id) throws DataAccessException;
	
	void save(Cause Cause) throws DataAccessException;
	
	void delete(Cause Cause);
	
	
}
