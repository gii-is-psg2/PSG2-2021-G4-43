package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionPetition;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.PetitionState;

public interface AdoptionPetitionRepository extends Repository<AdoptionPetition, Integer>{

	Collection<AdoptionPetition> findAll() throws DataAccessException;
	
	Optional<AdoptionPetition> findById(int id) throws DataAccessException;
	
	void save(AdoptionPetition Petition) throws DataAccessException;

	Collection<AdoptionPetition> findAllByOwner(Owner owner);

	Collection<AdoptionPetition> findAllByAdoptionAndState(Adoption adoption, PetitionState state);
	
}