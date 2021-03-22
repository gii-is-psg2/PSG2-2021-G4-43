package org.springframework.samples.petclinic.repository;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.User;


public interface UserRepository extends  CrudRepository<User, String>{

	@Query("SELECT DISTINCT o FROM Owner o WHERE o.username LIKE :username")
	Optional<Owner> findOwner(@Param("username") String username) throws DataAccessException;
	
}
