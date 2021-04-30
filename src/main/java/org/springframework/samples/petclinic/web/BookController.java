package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.service.BookService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.PetService;
import org.springframework.samples.petclinic.service.exceptions.NoRoomAvailableException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private PetService petService;
	
	@Autowired
	private OwnerService ownerService;
	
	@InitBinder("book")
	public void initBookBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(new BookValidator());
	}
	
	@GetMapping()
	public String listBooks(ModelMap model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Collection<Book> books = bookService.findAllByOwner(username);
		model.addAttribute("books",books);
		return "books/booksList";
	}

	@GetMapping("/new")
	public String initCreationBookForm(ModelMap model) throws Exception {
		Book book = new Book();
		Optional<Owner> owner = ownerService.findOwner(SecurityContextHolder.getContext().getAuthentication().getName());
		if(!owner.isPresent()) {
			throw new Exception();
		}
		Collection<Pet> pets = petService.findPetsByOwner(owner.get().getUser().getUsername());
		Map<Integer,String> petsId = pets.stream().collect(Collectors.toMap(x->x.getId(), y->y.getName()));
		model.addAttribute("book",book);
		model.addAttribute("pets",petsId);
		return "books/createBookForm";
	}

	@PostMapping("/new")
	public String processCreationBookForm(@Valid Book book,BindingResult result,ModelMap model) throws Exception {
		Optional<Owner> owner = ownerService.findOwner(SecurityContextHolder.getContext().getAuthentication().getName());
		if(!owner.isPresent()) {
			throw new Exception();
		}
		Collection<Pet> pets = petService.findPetsByOwner(owner.get().getUser().getUsername());
		Map<Integer,String> petsId = pets.stream().collect(Collectors.toMap(x->x.getId(), y->y.getName()));
		model.addAttribute("pets",petsId);
		
		if(result.hasErrors()) {
			List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message",errores);
			return "books/createBookForm";
		} else {
			try {
				if(!this.bookService.findSameBooks(book.getArrivalDate(), book.getDepartureDate(), book.getPet().getId())) {
					bookService.save(book);
					model.addAttribute("message","La reserva se ha realizado con éxito.");
					return listBooks(model);
				}else {
					model.addAttribute("message","Ya existe una reserva para esta mascota en la fecha indicada");
					return "books/createBookForm";
				}
			} catch (NoRoomAvailableException e) {
				model.addAttribute("message","Lo sentimos, no tenemos una habitación libre en la fecha indicada.");
				return "books/createBookForm";
			}
		}
	}
	
	@PostMapping("/{id}/delete")
	public String initCreationBookForm(@PathVariable("id") int id, ModelMap model) {
		Optional<Book> book = bookService.findById(id);
		if(!book.isPresent()) {
			model.addAttribute("message","La reserva que intenta borrar no existe.");
			return listBooks(model);
		}
		bookService.delete(book.get());
		model.addAttribute("message","La reserva se ha cancelado con exito");
		return listBooks(model);
	}

}
