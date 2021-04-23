package org.springframework.samples.petclinic.web;

import java.time.LocalDate;

import org.springframework.samples.petclinic.model.Book;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookValidator implements Validator {

	private static final String REQUIRED = "required";

	@Override
	public void validate(Object obj, Errors errors) {
		Book book = (Book) obj;
		
		LocalDate departure = book.getDepartureDate();
		LocalDate arrival = book.getArrivalDate();
		Pet pet = book.getPet();
		
		// arrival date not null validation
		if(arrival == null) {
			errors.rejectValue("arrivalDate", REQUIRED, REQUIRED);
		}

		// departure date not null validation
		if (departure==null) {
			errors.rejectValue("departureDate", REQUIRED, REQUIRED);
		}

		// pet not null validation
		if (pet == null) {
			errors.rejectValue("pet", REQUIRED, REQUIRED);
		}
		
		//arrival before departure validation
		if(arrival!=null && departure!=null && !arrival.isBefore(departure)) {
			errors.rejectValue("arrivalDate", "Horario inválido", "Horario inválido (la fecha de llegada tiene que ser anterior a la de salida)");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);
	}

}