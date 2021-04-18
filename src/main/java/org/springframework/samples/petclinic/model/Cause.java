package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;


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

	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cause")
	private Set<Donation> donations;
	
	protected Set<Donation> getDonationsInternal() {
	    if (this.donations == null) {
	       this.donations = new HashSet<>();
	    }
	    return this.donations;
	}
	
	public List<Donation> getDonations() {
	    List<Donation> sortedDonations = new ArrayList<>(getDonationsInternal());
	    PropertyComparator.sort(sortedDonations, new MutableSortDefinition("date", false, false));
	    return Collections.unmodifiableList(sortedDonations);
	}

	public void addDonation(Donation donation) {
	    getDonationsInternal().add(donation);
	    donation.setCause(this);
	}
	
	

}