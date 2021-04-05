package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book extends BaseEntity {

	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name="arrival_date")
	private LocalDate arrivalDate; //Fecha con hora?
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name="departure_date")
	private LocalDate departureDate;
	
	//@JoinColumn(name = "owner_id")
	//private Owner owner;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;
	
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;

}
