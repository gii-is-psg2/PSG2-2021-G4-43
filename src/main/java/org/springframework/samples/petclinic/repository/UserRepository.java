package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.User;


public interface UserRepository extends  CrudRepository<User, String>{

	@Query("SELECT DISTINCT p FROM Pet p WHERE p.user_id LIKE :username")
	Collection<Pet> findPetsByUserId(@Param("username") String username) throws DataAccessException;
	
}
