package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

	@Autowired
	private AdoptionRepository adoptionRepository;

	@Transactional(readOnly = true)
	public Collection<Adoption> findAll() {
		return adoptionRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Adoption> findById(int id) {
		return adoptionRepository.findById(id);
	}
	
	@Transactional
	public void saveAdoption(Adoption adoption) {
		adoptionRepository.save(adoption);
	}
	
	@Transactional
	public void delete(Adoption adoption) {
		adoptionRepository.delete(adoption);
	}

}
