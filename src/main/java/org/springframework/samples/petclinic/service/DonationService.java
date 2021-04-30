package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
	
	@Autowired
	private DonationRepository donationRepository;
	
	@Transactional
	public Collection<Donation> findAll() {
		return donationRepository.findAll();
	}
	
	public Optional<Donation> findByDonationId(int donationId)  {
		return donationRepository.findById(donationId);
	}	
	
	public void saveDonation(@Valid Donation donation)  {
		donationRepository.save(donation);
	}

	public List<Donation> findDonationsByCause(int causeId) {
		List<Donation> donations = new ArrayList<>();
			Collection<Donation> causeDonations = donationRepository.findByCauseId(causeId);
			donations.addAll(causeDonations);
		return donations;
	}
	
	public Collection<Donation> findDonationsByCauseId(Integer id) {
		return donationRepository.findByCauseId(id);
	}
	
	public void delete(Donation donation) {
		donationRepository.delete(donation);
	}
	
}

