package org.springframework.samples.petclinic.web;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.samples.petclinic.service.CauseService;
import org.springframework.samples.petclinic.service.DonationService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers=CauseController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)

public class CauseControllerTests {
	
	private static final int TEST_CAUSE_ID = 1;

	@Autowired
	private CauseController causeController;

	@MockBean
	private CauseService causeService;
	
	@MockBean
    private DonationService donationService;
        
	@Autowired
	private MockMvc mockMvc;
	
	
	@WithMockUser(value = "spring")
    @Test
	void testListCauses() throws Exception {
        mockMvc.perform(get("/causes"))
        .andExpect(status().isOk())
		.andExpect(model().attributeExists("causes"))
		.andExpect(view().name("causes/causesList"));
    }
	
	@WithMockUser(value = "spring")
    @Test
	void testInitCreationCauseForm() throws Exception {
		mockMvc.perform(get("/causes/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("cause")) 
		.andExpect(view().name("causes/createCauseForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationCauseFormSuccess() throws Exception {
		mockMvc.perform(post("/causes/new")
				.with(csrf())
				.param("name", "Vacunas en Zimbawe")
				.param("description", "Se necesita dinero para administrar vacunas en Zimbawe")
				.param("budgetAchieved", "0")
				.param("budgetTarget", "100000")
				.param("ong","Cruz Roja"))
				.andExpect(view().name("causes/causesList"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessCreationCauseFormHasErrors() throws Exception {
	mockMvc.perform(post("/causes/new")
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("cause"))
				.andExpect(view().name("causes/createCauseForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessDeleteCause() throws Exception {
		mockMvc.perform(get("/causes/{id}/delete", TEST_CAUSE_ID)
				.with(csrf()))                              
		.andExpect(status().isOk())
		.andExpect(view().name("causes/causesList"));
	}
	
	
	@WithMockUser(value = "spring")
    @Test
	void testInitCreationDonationForm() throws Exception {
		mockMvc.perform(get("/causes/{id}/donations/new",TEST_CAUSE_ID))
		.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessDonationFormSuccess() throws Exception {
		mockMvc.perform(post("/causes/{id}/donations/new",TEST_CAUSE_ID)
				.with(csrf())
				.param("amount", "100"))
				.andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testProcessDonationFormHasErrors() throws Exception {
	mockMvc.perform(post("/causes/{id}/donations/new",TEST_CAUSE_ID)
				.with(csrf()))
				.andExpect(status().isOk())
				.andExpect(model().attributeHasErrors("donation"))
				.andExpect(view().name("/donations/createDonationForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testShowDonations() throws Exception {
		mockMvc.perform(get("/causes/{id}/donations", TEST_CAUSE_ID))                              
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("donations"))
		.andExpect(view().name("/donations/showDonations"));
	}
}