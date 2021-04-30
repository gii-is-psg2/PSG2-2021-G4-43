package org.springframework.samples.petclinic.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=DonationController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

class DonationControllerTests {
	
	private static final int TEST_DONATION_ID = 1;

	@Autowired
	private DonationController donationController;

	@MockBean
	private DonationService donationService;

	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(value = "spring")
    @Test
	void testListDonations() throws Exception {
        mockMvc.perform(get("/donations"))
        .andExpect(status().isOk())
		.andExpect(model().attributeExists("donations"))
		.andExpect(view().name("/donations"));
    }
}