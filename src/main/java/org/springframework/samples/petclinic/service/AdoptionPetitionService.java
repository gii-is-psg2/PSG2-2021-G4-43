package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionPetition;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.PetitionState;
import org.springframework.samples.petclinic.repository.AdoptionPetitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionPetitionService {

	@Autowired
	private AdoptionPetitionRepository petitionRepository;

	@Transactional(readOnly = true)
	public Collection<AdoptionPetition> findAll() {
		return petitionRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<AdoptionPetition> findById(int id) {
		return petitionRepository.findById(id);
	}
	
	@Transactional
	public void savePetition(AdoptionPetition petition) {
		petitionRepository.save(petition);
	}

	public Collection<AdoptionPetition> findAllByOwner(Owner owner) {
		return petitionRepository.findAllByOwner(owner);
	}

	public Collection<AdoptionPetition> findAllByAdoptionAndState(Adoption adoption, PetitionState state) {
		return petitionRepository.findAllByAdoptionAndState(adoption,state);
	}

}
