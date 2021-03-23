package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
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
	@Column(name="arrival_date")
	private LocalDate arrivalDate; //Fecha con hora?
	
	@NotNull(message="El campo Fecha de fin no puede estar vacío")
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name="departure_date")
	private LocalDate departureDate;
	
	//@JoinColumn(name = "owner_id")
	//private Owner owner;

	@NotNull(message="Debe seleccionar una mascota")
	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

}
