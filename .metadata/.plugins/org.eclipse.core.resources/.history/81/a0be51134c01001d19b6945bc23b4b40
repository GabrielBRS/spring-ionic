package com.gabrielsousa.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class ItemRequestPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="request_id")
	private Request request;

	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	public ItemRequestPK() {
		
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(product, request);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemRequestPK other = (ItemRequestPK) obj;
		return Objects.equals(product, other.product) && Objects.equals(request, other.request);
	}
}
