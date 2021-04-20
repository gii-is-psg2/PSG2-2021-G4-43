package org.springframework.samples.petclinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


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
	
	public Boolean isClosed() {
		if (this.getBudgetAchieved() >= this.getBudgetTarget()) {
			return true;
		}
	return false;
				
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBudgetAchieved() {
		return budgetAchieved;
	}

	public void setBudgetAchieved(Integer budgetAchieved) {
		this.budgetAchieved = budgetAchieved;
	}

	public Integer getBudgetTarget() {
		return budgetTarget;
	}

	public void setBudgetTarget(Integer budgetTarget) {
		this.budgetTarget = budgetTarget;
	}

	public String getOng() {
		return ong;
	}

	public void setOng(String ong) {
		this.ong = ong;
	}
	

}