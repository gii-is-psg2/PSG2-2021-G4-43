package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

	@NotNull(message="El campo Fecha de inicio no puede estar vacío")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate arrival_date;
	
	@NotNull(message="El campo Fecha de fin no puede estar vacío")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private LocalDate departure_date;
	
	@JoinColumn(name = "user_id")
	private User user;

	@NotNull(message="Debe seleccionar una mascota")
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

}
