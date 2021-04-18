package org.springframework.samples.petclinic.web;


import java.time.LocalDate;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/donations")
public class DonationController {
	
	@Autowired
    private DonationService donationService;
	@Autowired
	private CauseService causeService;

	@GetMapping()
	public String listDonations(ModelMap model) {
		Collection<Donation> donations = donationService.findAll();
		model.addAttribute("donations",donations);
		return "/donations";
	}
    

    @GetMapping(value = "/new")
    public String initCreationForm(@ModelAttribute Cause cause,ModelMap model) {
    	Cause causa = causeService.findById(cause.getId()).get();
    	if (causa.isClosed()){
            return "redirect:/causes";
    	} 
        Donation donation = new Donation();
        donation.setCause(causa);
    	donation.setDate(LocalDate.now());
        model.put("donation", donation);
        return "/donations/createOrUpdateDonationForm";
    }

    @PostMapping(value = "/new")
    public String processCreationForm(@Valid Donation donation, ModelMap model, BindingResult result) {
        if (result.hasErrors()) {
        	model.put("donation", donation);
            return "/donations/createOrUpdateDonationForm";
        } else {
            this.donationService.saveDonation(donation);
           
        return "redirect:/causes";
    }  
}

    }
