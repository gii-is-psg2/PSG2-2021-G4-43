package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Room;
import org.springframework.samples.petclinic.service.BookService;
import org.springframework.samples.petclinic.service.OwnerService;
import org.springframework.samples.petclinic.service.RoomService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private OwnerService ownerService;
	
	@GetMapping()
	public String listBooks(ModelMap model) {
		Optional<Owner> owner = userService.findOwner(SecurityContextHolder.getContext().getAuthentication().getName());
		Collection<Book> books = bookService.findAllByOwner(owner.get().getUser().getUsername());
		model.addAttribute("book",books);
		return "books/listBooks";
	}

	@GetMapping("/new")
	public String initCreationBookForm(ModelMap model) {
		Book book = new Book();
		Optional<Owner> owner = userService.findOwner(SecurityContextHolder.getContext().getAuthentication().getName());
		Collection<Pet> pets = ownerService.findPetsByOwner(owner.get().getUser().getUsername());
		model.addAttribute("book",book);
		model.addAttribute("pets",pets);
		return "books/CreateBookForm";
	}

	@PostMapping("/new")
	public String processCreationBookForm(@Valid Book book,BindingResult result,ModelMap model) {
		if(result.hasErrors()) {
			List<String> errores = result.getAllErrors().stream().map(x->x.getDefaultMessage()).collect(Collectors.toList());
			model.addAttribute("message",errores);
			return "books/CreateBookForm";
		} else {
			Optional<Room> room = roomService.getHabitacionLibre(book.getArrival_date(),book.getDeparture_date(),book.getPet());
			if(!room.isPresent()) {
				model.addAttribute("message","Lo sentimos, no tenemos una habitación libre en la fecha indicada.");
				return "books/CreateBookForm";
			}
			bookService.save(book);
			model.addAttribute("message","La reserva se ha realizado con exito");
			return listBooks(model);
		}
	}
	
	@PostMapping("/{id}/delete")
	public String initCreationBookForm(@PathVariable("id") int id, ModelMap model) {
		Optional<Book> book = bookService.findById(id);
		if(!book.isPresent()) {
			model.addAttribute("message","La reserva que intenta borrar no existe.");
			return listBooks(model);
		}
		bookService.delete(book.get().getId());
		model.addAttribute("message","La reserva se ha cancelado con exito");
		return listBooks(model);
	}

}
