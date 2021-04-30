package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
class DonationServiceTests {
	
	@Autowired
	protected DonationService donationService;
	
	@Autowired
	protected CauseService causeService;
	
	@Test
	void shouldFindAll() {
		Collection<Donation> donations = this.donationService.findAll();
		assertThat(donations).hasSize(1);
	}
	
	@Test
	void shouldFindByDonationId() {
		Donation donation = this.donationService.findByDonationId(1).get();
		assertThat(donation.getId()).isEqualTo(1);
	}
	
	@Test
	@Transactional
	void shouldInsertDonation() {
		int found = donationService.findAll().size();
		
		Donation donation = new Donation();
		donation.setAmount(150);
		LocalDate date = LocalDate.of(2021, 04, 20);
		donation.setDate(date);
		donation.setClient("owner1");
		Cause cause = this.causeService.findById(1).get();
		donation.setCause(cause);
		
		this.donationService.saveDonation(donation);
		assertThat(donation.getId()).isNotNull();
		assertThat(donationService.findAll()).hasSize(found + 1);
	}
	
	@Test
	void shouldFindDonationByCauseId() {
		Collection<Donation> donation = this.donationService.findDonationsByCauseId(1);
		assertThat(donation).hasSize(1);
	}
}