package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.SupportContact;
import org.springframework.samples.petclinic.service.SupportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/support")
public class SupportController {

	@Autowired
	private SupportService supportService;
	

	@GetMapping()
	public String listSupportTeamInfo(ModelMap model) {
		List<SupportContact> contacts = supportService.getSupportContacts();
		model.addAttribute("contacts",contacts);
		return "support/contactsList";
	}

}
