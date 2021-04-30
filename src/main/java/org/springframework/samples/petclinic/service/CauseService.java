package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.CauseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {

	@Autowired
	private CauseRepository causeRepository;

	@Autowired
	private DonationService donationService;

	@Transactional(readOnly = true)
	public Collection<Cause> findAll() {
		return causeRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Cause> findById(int id) {
		return causeRepository.findById(id);
	}
	
	@Transactional
	public void saveCause(Cause cause) {
		causeRepository.save(cause);
	}
	
	@Transactional
	public void delete(Cause cause) {
		List<Donation> donations = donationService.findDonationsByCause(cause.getId());
		for (Donation d : donations) {
			donationService.delete(d);
		}
		causeRepository.delete(cause);
	}

}
