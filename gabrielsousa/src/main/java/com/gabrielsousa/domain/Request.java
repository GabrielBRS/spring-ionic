package com.gabrielsousa.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Request implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instant;
	
	@OneToOne(cascade=CascadeType.ALL, mappedBy ="request")
	private Payment payment;
	
	@ManyToOne
	@JoinColumn(name="client_id")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "adress_delivery_id")
	private Adress adressDelivery;
	
	@OneToMany(mappedBy = "id.request")
	private Set<ItemRequest> itens = new HashSet<>();
	
	public Request() {
		
	}

	public Request(Integer id, Date instant, Client client, Adress adressDelivery) {
		super();
		this.id = id;
		this.instant = instant;
		this.client = client;
		this.adressDelivery = adressDelivery;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstant() {
		return instant;
	}

	public void setInstant(Date instant) {
		this.instant = instant;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Adress getAdressDelivery() {
		return adressDelivery;
	}

	public void setAdressDelivery(Adress adressDelivery) {
		this.adressDelivery = adressDelivery;
	}

	public Set<ItemRequest> getItens() {
		return itens;
	}

	public void setItens(Set<ItemRequest> itens) {
		this.itens = itens;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", instant=" + instant + ", payment=" + payment + ", client=" + client
				+ ", adressDelivery=" + adressDelivery + ", itens=" + itens + "]";
	}
	
}
