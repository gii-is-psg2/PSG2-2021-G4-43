package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.AdoptionPetition;
import org.springframework.samples.petclinic.model.PetitionState;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionPetitionService;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.PetAlreadyOnAdoptionException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adoptions")
public class AdoptionController {

	@Autowired
	private AdoptionService adoptionService;
	
	@Autowired
	private AdoptionPetitionService petitionService;

	@Autowired
	private OwnerService ownerService;
	
	@Autowired
	private PetService petService;
	
	@ModelAttribute("owner")
	public Owner getOwner() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Owner owner = ownerService.findOwner(username).get();
		return owner;
	}
	
	@ModelAttribute("pets")
	public Map<Integer, String> getPets() {
		Owner owner = getOwner();
		Collection<Pet> pets = owner.getPets();
		Map<Integer,String> petsId = pets.stream().collect(Collectors.toMap(x->x.getId(), y->y.getName()));
		return petsId;
	}
	
	@GetMapping()
	public String listAdoptions(ModelMap model) {
		Collection<Adoption> adoptions = adoptionService.findAllPendientes();
		model.addAttribute("adoptions",adoptions);
		return "adoptions/adoptionsList";
	}
	
	/* De momento no es necesario
	@GetMapping("/{id}")
	public String showOwner(@PathVariable("id") int id,ModelMap model) {
		Optional<Adoption> adoption = adoptionService.findById(id);
		model.addAttribute("adoption",adoption.get());
		return "adoptions/adoptionDetails";
	}*/

	@GetMapping("/new")
	public String initCreationAdoptionForm(ModelMap model) {
		Adoption adoption = new Adoption();
		model.addAttribute("adoption",adoption);
		return "adoptions/createAdoptionForm";
	}

	@PostMapping("/new")
	public String processCreationAdoptionForm(@Valid Adoption adoption,BindingResult result,ModelMap model) {
		if(result.hasErrors()) {
			List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message",errores);
			return "adoptions/createAdoptionForm";
		} else {
			adoption.setFinished(false);
			try {
			adoptionService.saveAdoption(adoption);
			model.addAttribute("message","La adopcion se ha creado con éxito.");
			return listAdoptions(model);
			}
			catch(PetAlreadyOnAdoptionException e) {
				model.addAttribute("message","La mascota ya está en adopción.");
				return "adoptions/createAdoptionForm";
			}
		}
	}
	
	@GetMapping("/{id}")
	public String listRequests(@PathVariable("id") int id,ModelMap model) {
		Optional<Adoption> adoption = adoptionService.findById(id);
		if(!adoption.isPresent() || adoption.get().getFinished()) {
			model.addAttribute("message","Solicitud de adopción no existente o ya cerrada.");
			return listAdoptions(model);
		}
		Collection<AdoptionPetition> petitions = petitionService.findAllByAdoptionAndState(adoption.get(), "PENDIENTE");
		model.addAttribute("petitions",petitions);
		model.addAttribute("adoption",adoption.get());
		return "adoptions/receivedPetitionsList";
	}
	
	@GetMapping("/{adoptionId}/accept/{petitionId}")
	public String acceptPetition(@PathVariable("adoptionId") int adoptionId,@PathVariable("petitionId") int petitionId,ModelMap model) {
		Optional<Adoption> adoption = adoptionService.findById(adoptionId);
		Optional<AdoptionPetition> petition = petitionService.findById(petitionId);
		if(!petition.isPresent() || petition.get().getState()!="PENDIENTE" || !adoption.isPresent() || adoption.get().getFinished()) {
			model.addAttribute("message","Petición no existente o ya cerrada.");
			return listRequests(adoptionId,model);
		}
		
		//Marca como aceptada la peticion seleccionada, rechaza todas las demás pendientes
		Collection<AdoptionPetition> petitions = petitionService.findAllByAdoptionAndState(adoption.get(), "PENDIENTE");
		for(AdoptionPetition adoptionPetition: petitions) {
			if(adoptionPetition.equals(petition.get())) adoptionPetition.setState("ACEPTADO");
			else adoptionPetition.setState("RECHAZADO");
			petitionService.savePetition(adoptionPetition);
		}
		
		//Marca la adopción como finalizada
		Adoption finishedAdoption = adoption.get();
		finishedAdoption.setFinished(true);
		adoptionService.save(finishedAdoption);
		
		//Cambia de dueño la mascota
		Pet pet = finishedAdoption.getPet();
		pet.setOwner(petition.get().getOwner());
		petService.save(pet);
		
		model.addAttribute("message","Petición aceptada.");
		return listAdoptions(model);
	}
	
	@GetMapping("/{adoptionId}/deny/{petitionId}")
	public String denyPetition(@PathVariable("adoptionId") int adoptionId,@PathVariable("petitionId") int petitionId,ModelMap model) {
		Optional<Adoption> adoption = adoptionService.findById(adoptionId);
		Optional<AdoptionPetition> petition = petitionService.findById(petitionId);
		if(!petition.isPresent() || petition.get().getState()!="PENDIENTE" || !adoption.isPresent() || adoption.get().getFinished()) {
			model.addAttribute("message","Petición no existente o ya cerrada.");
			return listRequests(adoptionId,model);
		}
		AdoptionPetition adoptionPetition = petition.get();
		adoptionPetition.setState("RECHAZADO");
		petitionService.savePetition(adoptionPetition);
		model.addAttribute("message","Petición rechazada.");
		return listRequests(adoptionId,model);
	}
	
	@GetMapping("/petitions")
	public String listPetitions(ModelMap model) {
		Owner owner = getOwner();
		Collection<AdoptionPetition> petitions = petitionService.findAllByOwner(owner);
		model.addAttribute("petitions",petitions);
		return "adoptions/petitionsList";
	}
	
	@GetMapping("/petitions/request/{id}")
	public String requestAdoptionInitForm(@PathVariable("id") int id,ModelMap model) {
		Optional<Adoption> adoption = adoptionService.findById(id);
		if(!adoption.isPresent() || adoption.get().getFinished()) {
			model.addAttribute("message","Solicitud de adopción no existente o ya cerrada.");
			return listAdoptions(model);
		}
		AdoptionPetition petition = new AdoptionPetition();
		petition.setOwner(getOwner());
		petition.setState("PENDIENTE");
		petition.setAdoption(adoption.get());
		model.addAttribute("petition",petition);
		return "adoptions/petitionForm";
	}
	
	@PostMapping("/petitions/request/{id}")
	public String requestAdoption(@PathVariable("id") int id,@Valid AdoptionPetition petition,BindingResult result,ModelMap model) {
		if(result.hasErrors()) {
			List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message",errores);
			return requestAdoptionInitForm(id,model);
		} else {
			petitionService.savePetition(petition);
			model.addAttribute("message","La petición se ha creado con éxito.");
			return listAdoptions(model);
		}
	}
	
	/* De momento, no es necesario
	@GetMapping("/{id}/delete")
	public String initCreationadoptionForm(@PathVariable("id") int id, ModelMap model) {
		Optional<Adoption> adoption = adoptionService.findById(id);
		if(!adoption.isPresent()) {
			model.addAttribute("message","La adopcion que intenta borrar no existe.");
			return listAdoptions(model);
		}
		adoptionService.delete(adoption.get());
		model.addAttribute("message","La adopción se ha borrado con éxito");
		return listAdoptions(model);
	}
	*/

}

