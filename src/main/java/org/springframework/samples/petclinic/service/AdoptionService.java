package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionPetition;
import org.springframework.samples.petclinic.repository.AdoptionRepository;
import org.springframework.samples.petclinic.service.exceptions.PetAlreadyOnAdoptionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

	@Autowired
	private AdoptionRepository adoptionRepository;

	@Autowired
	private AdoptionPetitionService adoptionPetitionService;

	@Transactional(readOnly = true)
	public Collection<Adoption> findAll() {
		return adoptionRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Adoption> findById(int id) {
		return adoptionRepository.findById(id);
	}
	
	@Transactional
	public void saveAdoption(Adoption adoption) throws PetAlreadyOnAdoptionException {
		if(adoptionRepository.findByPetAndFinished(adoption.getPet(),false).isPresent()) {
			throw new PetAlreadyOnAdoptionException();
		}
		adoptionRepository.save(adoption);
	}
	
	@Transactional
	public void delete(Adoption adoption) {
		Collection<AdoptionPetition> petitions = adoptionPetitionService.findAllByAdoption(adoption);
		for(AdoptionPetition p : petitions) {
			adoptionPetitionService.deletePetition(p);
		}
		adoptionRepository.delete(adoption);
	}

	public Collection<Adoption> findAllPendientes() {
		return adoptionRepository.findAllByFinishedEquals(false);
	}

	public void save(Adoption adoption) {
		adoptionRepository.save(adoption);
	}

}
