package org.springframework.samples.petclinic.web;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cause;
import org.springframework.samples.petclinic.model.Donation;
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
@RequestMapping("/donations")
public class DonationController {
	
	@Autowired
    private DonationService donationService;

	@GetMapping()
	public String listDonations(ModelMap model) {
		Collection<Donation> donations = donationService.findAll();
		model.addAttribute("donations",donations);
		return "/donations";
	}
		
}
