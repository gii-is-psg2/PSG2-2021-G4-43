package org.springframework.samples.petclinic.web;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.VetService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.service.AuthoritiesService;
import org.springframework.samples.petclinic.service.BookService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;


@WebMvcTest(controllers=BookController.class,
		excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
		excludeAutoConfiguration= SecurityConfiguration.class)
class BookControllerTests {

	private static final int TEST_BOOK_ID = 1;

	@Autowired
	private BookController bookController;

	@MockBean
	private BookService bookService;
        
    @MockBean
	private PetService petService;
        
    @MockBean
    private OwnerService ownerService; 

	@Autowired
	private MockMvc mockMvc;

	
	@WithMockUser(value = "spring")
    @Test
	void testListBooks() throws Exception {
        mockMvc.perform(get("/books"))
        .andExpect(status().isOk())
		.andExpect(model().attributeExists("books"))
		.andExpect(view().name("books/booksList"));
    }
	
	@WithMockUser(value = "spring")
    @Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/books/new")
				.with(csrf())
				.param("arrivalDate", "2021-01-01")
				.param("departureDate", "2021-01-07")
				.param("pet", "Leo"))
				.andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
    @Test
    void testProcessDeleteBook() throws Exception {
		mockMvc.perform(post("/books/{bookId}/delete", TEST_BOOK_ID)
				.with(csrf()))                              
		.andExpect(status().isOk())
		.andExpect(view().name("books/booksList"));
}

}
