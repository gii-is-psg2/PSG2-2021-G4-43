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
import org.springframework.samples.petclinic.service.AdoptionPetitionService;
import org.springframework.samples.petclinic.service.AdoptionService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers=AdoptionController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class AdoptionControllerTests {

	private static final int TEST_ADOPTION_ID = 3;

	private static final int TEST_PETITION_ID = 3;

	@MockBean
	private AdoptionService adoptionService;

	@MockBean
	private AdoptionPetitionService adoptionPetitionService;
        
    @MockBean
    private OwnerService ownerService; 
    
	@MockBean
	private PetService petService;

	@Autowired
	private MockMvc mockMvc;

	
//	@WithMockUser(value = "spring")
//    @Test
//	void testListAdoptions() throws Exception {
//        mockMvc.perform(get("/adoptions"))
//        .andExpect(status().isOk())
//		.andExpect(model().attributeExists("adoptions"))
//		.andExpect(view().name("adoptions/adoptionsList"));
//    }

	@WithMockUser(value = "spring")
    @Test
	void testInitCreationAdoptionForm() throws Exception {
		mockMvc.perform(get("/adoptions/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("adoption"))
		.andExpect(view().name("adoptions/createAdoptionForm"));
	}
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/adoptions/new")
				.with(csrf())
				.param("pet", "Leo"))
				.andExpect(status().isOk());
	}

//	@WithMockUser(value = "spring")
//    @Test
//	void testListRequests() throws Exception {
//		mockMvc.perform(get("/adoptions/{adoptionId}", TEST_ADOPTION_ID))
//		.andExpect(status().isOk())
//		.andExpect(model().attributeExists("petitions"))
//		.andExpect(view().name("adoptions/receivedPetitionsList"));
//	}

//	@WithMockUser(value = "spring")
//    @Test
//	void testAcceptPetition() throws Exception {
//		mockMvc.perform(get("/adoptions/{adoptionId}/accept/{petitionId}", TEST_ADOPTION_ID,
//				TEST_PETITION_ID))
//		.andExpect(status().isOk())
//		.andExpect(view().name("adoptions/adoptionsList"));
//	}

//	@WithMockUser(value = "spring")
//    @Test
//	void testDenyPetition() throws Exception {
//		mockMvc.perform(get("/adoptions/{adoptionId}/deny/{petitionId}", TEST_ADOPTION_ID,
//				TEST_PETITION_ID))
//		.andExpect(status().isOk())
//		.andExpect(view().name("adoptions/adoptionsList"));
//	}

//	@WithMockUser(value = "spring")
//    @Test
//	void testRequestAdoptionInitForm() throws Exception {
//		mockMvc.perform(get("/adoptions/petitions/request/{adoptionId}",TEST_ADOPTION_ID))
//		.andExpect(status().isOk())
//		.andExpect(model().attributeExists("petition"))
//		.andExpect(view().name("adoptions/petitionForm"));
//	}
	
//	@WithMockUser(value = "spring")
//    @Test
//	void testRequestAdoptionInitFormSuccess() throws Exception {
//		mockMvc.perform(post("/adoptions/petitions/request/{adoptionId}",TEST_ADOPTION_ID)
//				.with(csrf())
//				.param("description", "Description")
//				.param("owner.id", "1")
//				.param("adoption.id", String.valueOf(TEST_ADOPTION_ID))
//				.param("state", "PENDIENTE"))
//				.andExpect(status().isOk());
//	}

}
