package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class CauseServiceTests {
	
	@Autowired
	protected CauseService causeService;
	
	@Test
	void shouldFindAll() {
		Collection<Cause> causes = this.causeService.findAll();
		assertThat(causes).hasSize(6);
	}
	
	@Test
	void shouldFindCauseById() {
		Cause cause = this.causeService.findById(1).get();
		assertThat(cause.getId()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void shouldInsertCause() {
		int found = causeService.findAll().size();
		
		Cause cause = new Cause();
		cause.setName("Vacunas de prueba");
		cause.setDescription("Se necesitan vacunas para esta prueba");
		cause.setBudgetAchieved(100);
		cause.setBudgetTarget(10000);
		cause.setOng("Cruz Roja");
		
		this.causeService.saveCause(cause);
		assertThat(cause.getId()).isNotNull();
		assertThat(causeService.findAll()).hasSize(found + 1);
	}
	
	@Test
	@Transactional
	void shouldDeleteCause() {
		Cause cause = this.causeService.findById(1).get();
		Collection<Cause> causes = this.causeService.findAll();
		int i = causes.size();
		causeService.delete(cause);
		causes = causeService.findAll();
		int j = causes.size();
		assertThat(i-1).isEqualTo(j);
	}
}