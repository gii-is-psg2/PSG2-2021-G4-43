package org.springframework.samples.petclinic.web;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/causes")
public class CauseController {

	@Autowired
	private CauseService causeService;
	
	@Autowired
    private DonationService donationService;
	
	@GetMapping()
	public String listCauses(ModelMap model) {
		Collection<Cause> causes = causeService.findAll();
		model.addAttribute("causes",causes);
		return "causes/causesList";
	}
	
	@GetMapping("/{id}")
	public String showOwner(@PathVariable("id") int id,ModelMap model) throws Exception {
		Optional<Cause> cause = causeService.findById(id);
		if(!cause.isPresent()) {
			throw new Exception();
		}
		model.addAttribute("cause",cause.get());
		return "causes/causeDetails";
	}

	@GetMapping("/new")
	public String initCreationCauseForm(ModelMap model) {
		Cause cause = new Cause();
		model.addAttribute("cause",cause);
		return "causes/createCauseForm";
	}

	@PostMapping("/new")
	public String processCreationCauseForm(@Valid Cause cause,BindingResult result,ModelMap model) {
		if(result.hasErrors()) {
			List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message",errores);
			return "causes/createCauseForm";
		} else {
			cause.setBudgetAchieved(0);
			causeService.saveCause(cause);
			model.addAttribute("message","La causa se ha creado con éxito.");
			return listCauses(model);
		}
	}
	
	@GetMapping("/{id}/delete")
	public String initCreationcauseForm(@PathVariable("id") int id, ModelMap model) {
		Optional<Cause> cause = causeService.findById(id);
		if(!cause.isPresent()) {
			model.addAttribute("message","La causa que intenta borrar no existe.");
			return listCauses(model);
		}
		causeService.delete(cause.get());
		model.addAttribute("message","La causa se ha borrado con éxito");
		return listCauses(model);
	}
	
	@GetMapping(value = "/{id}/donations/new")
    public String initCreationForm(@PathVariable("id") int id,ModelMap model) throws Exception {
    	Optional<Cause> causa = causeService.findById(id);
		if(!causa.isPresent()) {
			throw new Exception();
		}
		Cause cause = causa.get();
    	if (cause.isClosed()){
    		model.addAttribute("message","Ya se ha recaudado lo necesario para esta causa por lo que no son necesarias más donaciones");
    		return listCauses(model);
    	} 
        Donation donation = new Donation();
        model.addAttribute("donation", donation);
        return "/donations/createDonationForm";
    }

    @PostMapping(value = "/{id}/donations/new")
    public String processCreationForm(@PathVariable("id") int id,@Valid Donation donation, BindingResult result,ModelMap model) throws Exception {
        if (result.hasErrors()) {
            return "/donations/createDonationForm";
        }
        Optional<Cause> causa = causeService.findById(id);
        if(!causa.isPresent()) {
			throw new Exception();
		}
		Cause cause = causa.get();
        if (donation.getAmount() > (cause.getBudgetTarget()-cause.getBudgetAchieved())) {
        	model.addAttribute("message","La cantidad que desea donar es mayor que el objetivo de la donación");
    		return "/donations/createDonationForm";
        }
        cause.setBudgetAchieved(cause.getBudgetAchieved()+donation.getAmount());
        causeService.saveCause(cause);
        donation.setClient(SecurityContextHolder.getContext().getAuthentication().getName());
        donation.setCause(cause);
        donation.setDate(LocalDate.now());
        donationService.saveDonation(donation);
        return "redirect:/causes/"; 
    }
    
    @GetMapping("/{id}/donations")
	public String showDonation(@PathVariable("id") int id, ModelMap model) {
		List<Donation> donations = donationService.findDonationsByCause(id);
		model.addAttribute("donations",donations);
		return "/donations/showDonations";
	}


}
