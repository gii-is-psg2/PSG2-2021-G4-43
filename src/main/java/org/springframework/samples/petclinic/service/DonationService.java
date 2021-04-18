package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
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

	public List<Donation> findDonationsByCause(List<Cause> causes) {
		List<Donation> donations = new ArrayList<>();
		for(Cause c:causes) {
			Collection<Donation> causeDonations = donationRepository.findByCauseId(c.getId());
			donations.addAll(causeDonations);
		}
		return donations;
	}
	
	
}

