package org.springframework.samples.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "adoptions")
public class Adoption extends BaseEntity {

	@NotNull
	@ManyToOne
	private Pet pet;
	
	private Boolean finished;
}