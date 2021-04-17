package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Adoption;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
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
	private OwnerService ownerService;

	@ModelAttribute("pets")
	public Map<Integer, String> getPets() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Owner owner = ownerService.findOwner(username).get();
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
	
	@GetMapping("/{id}")
	public String showOwner(@PathVariable("id") int id,ModelMap model) {
		Optional<Adoption> adoption = adoptionService.findById(id);
		model.addAttribute("adoption",adoption.get());
		return "adoptions/adoptionDetails";
	}

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

