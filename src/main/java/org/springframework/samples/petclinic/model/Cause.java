package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "causes")
public class Cause extends BaseEntity {

	@NotEmpty(message= "Debe indicarse un nombre para la causa")
	private String name;
	
	@NotEmpty(message= "Debe indicarse una descripción para la causa")
	private String description;
	
	@Column(name="budget_achieved")
	private Integer budgetAchieved;
	
	@NotNull(message= "Debe indicarse un presupuesto objetivo para la causa")
	@Column(name="budget_target")
	private Integer budgetTarget;
	
	@NotEmpty(message= "Debe indicarse una ONG para la causa")
	private String ong;

}
