package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "petitions")
public class AdoptionPetition extends BaseEntity {
	
	@ManyToOne
	@NotNull
	private Adoption adoption;
	
	@ManyToOne
	@NotNull
	private Owner owner;
	
	@NotNull
	private PetitionState state;
	
	@NotEmpty
	private String description;
	
}
