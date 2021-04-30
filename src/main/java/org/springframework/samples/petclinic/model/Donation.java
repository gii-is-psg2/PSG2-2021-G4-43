package org.springframework.samples.petclinic.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name = "donations")
public class Donation extends BaseEntity{
	
	@NotNull
	@Min(0)
	private Integer amount;
	
    @Column(name = "date_of_donation")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;
	
    private String client;
    
    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cause_id")
    private Cause cause;
    
	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}
    
    public Cause getCause() {
        return cause;
    }

    public void setCause(Cause cause) {
        this.cause = cause;
    }

}

