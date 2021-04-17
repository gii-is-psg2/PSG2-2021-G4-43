package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Pet;

public interface AdoptionRepository extends Repository<Adoption, Integer>{

	Collection<Adoption> findAll() throws DataAccessException;
	
	Optional<Adoption> findById(int id) throws DataAccessException;
	
	void save(Adoption Adoption) throws DataAccessException;
	
	void delete(Adoption Adoption);

	Collection<Adoption> findAllByFinishedEquals(boolean b);

	Optional<Adoption> findByPetAndFinished(Pet pet, boolean b);
	
	
}