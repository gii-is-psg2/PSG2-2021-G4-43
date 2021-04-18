package org.springframework.samples.petclinic.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.repository.DonationRepository;
import org.springframework.stereotype.Service;

@Service
public class DonationService {
	
	private DonationRepository donationRepository;
	
	@Transactional
	public Collection<Donation> findAll() {
		return donationRepository.findAll();
	}
	
	public Donation findByDonationId(int donationId)  {
		return donationRepository.findByDonationId(donationId);
	}	
	
	public void saveDonation(Donation donation)  {
		donationRepository.save(donation);
	}

	public List<Double> findDonationsByCauses(List<Cause> causes) {
		List<Double> res=new ArrayList<>();
		for(Cause c:causes) {
			Double res1=0.;
				for(Donation d:this.findDonationsByCauseId(c.getId())) {
					res1+=d.getAmount();
			
					}
			res.add(res1);
		}
		return res;
	}
	
	private Collection<Donation> findDonationsByCauseId(Integer id) {
		return donationRepository.findByCauseId(id);
	}
	
	
}

