package org.springframework.samples.petclinic.repository;


import java.util.Collection;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Donation;

public interface DonationRepository extends Repository<Donation, Integer> {
	
	void save(Donation donation);
	
	Collection<Donation> findAll() throws DataAccessException;
	
	Optional<Donation> findById(int id) throws DataAccessException;
	
	@Query("SELECT d FROM Donation d where d.cause.id=:causeId")
	Collection<Donation> findByCauseId(@Param(value = "causeId") int causeId);
	
	@Query("SELECT d FROM Donation d where d.cause.id=:causeId")
	Collection<Donation> findDonationByCauseId(@Param(value = "causeId") int causeId);
	
	void delete(Donation donation);
}