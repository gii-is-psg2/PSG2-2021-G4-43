/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.exceptions.DuplicatedPetNameException;
import org.springframework.samples.petclinic.service.exceptions.PetAlreadyOnAdoptionException;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration test of the Service and the Repository layer.
 * <p>
 * ClinicServiceSpringDataJpaTests subclasses benefit from the following services provided
 * by the Spring TestContext Framework:
 * </p>
 * <ul>
 * <li><strong>Spring IoC container caching</strong> which spares us unnecessary set up
 * time between test execution.</li>
 * <li><strong>Dependency Injection</strong> of test fixture instances, meaning that we
 * don't need to perform application context lookups. See the use of
 * {@link Autowired @Autowired} on the <code>{@link
 * ClinicServiceTests#clinicService clinicService}</code> instance variable, which uses
 * autowiring <em>by type</em>.
 * <li><strong>Transaction management</strong>, meaning each test method is executed in
 * its own transaction, which is automatically rolled back by default. Thus, even if tests
 * insert or otherwise change database state, there is no need for a teardown or cleanup
 * script.
 * <li>An {@link org.springframework.context.ApplicationContext ApplicationContext} is
 * also inherited and can be used for explicit bean lookup if necessary.</li>
 * </ul>
 *
 * @author Ken Krebs
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Sam Brannen
 * @author Michael Isvy
 * @author Dave Syer
 */

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class AdoptionServiceTests {        
        @Autowired
	protected AdoptionService adoptionService;
        
        @Autowired
	protected PetService petService;

	@Test
	void shouldFindAllAdoptions() {
		Collection<Adoption> adoptions = this.adoptionService.findAll();
		assertThat(adoptions.size()).isEqualTo(5);
	}	

	@Test
	void shouldFindAdoptionWithCorrectId() {
		Adoption adoption = this.adoptionService.findById(1).get();
		assertThat(adoption.getPet().getId()).isEqualTo(1);
		assertThat(adoption.getFinished()).isEqualTo(true);

	}

	@Test
	@Transactional
	public void shouldInsertAdoptionIntoDatabaseAndGenerateId() {
		int found = adoptionService.findAll().size();

		Adoption adoption = new Adoption();
		Pet pet = this.petService.findPetById(6);
		adoption.setFinished(false);
		adoption.setPet(pet);
		try {
			adoptionService.saveAdoption(adoption);
		} catch (PetAlreadyOnAdoptionException e) {
            Logger.getLogger(AdoptionServiceTests.class.getName()).log(Level.SEVERE, null, e);
		}
		assertThat(adoptionService.findAll().size()).isEqualTo(found + 1);
		// checks that id has been generated
		assertThat(adoption.getId()).isNotNull();
	}

	@Test
	@Transactional
	void shouldDeleteAdoption() {
		Adoption adoption = this.adoptionService.findById(1).get();
		Collection<Adoption> adoptions = adoptionService.findAll();
		int i = adoptions.size();
		adoptionService.delete(adoption);
		adoptions = adoptionService.findAll();
		int j = adoptions.size();
		assertThat(i-1).isEqualTo(j);
	}


	@Test
	void shouldFindAllPendientes() {
		Collection<Adoption> adoptions = this.adoptionService.findAllPendientes();
		assertThat(adoptions.size()).isEqualTo(3);
	}	
	
	@Test
	@Transactional
	public void shouldThrowExceptionInsertingAdoptionsWithTheSamePet() {
		int found = adoptionService.findAll().size();

		Adoption adoption = new Adoption();
		Pet pet = this.petService.findPetById(6);
		adoption.setFinished(false);
		adoption.setPet(pet);
		try {
			adoptionService.saveAdoption(adoption);
		} catch (PetAlreadyOnAdoptionException e) {
			// The adoption already exists!
			e.printStackTrace();
		}
		Adoption anotherAdoptionForTheSameName = new Adoption();
		anotherAdoptionForTheSameName.setFinished(false);
		anotherAdoptionForTheSameName.setPet(pet);
		Assertions.assertThrows(PetAlreadyOnAdoptionException.class, () ->{
			adoptionService.saveAdoption(adoption);
		});		
	}
}
