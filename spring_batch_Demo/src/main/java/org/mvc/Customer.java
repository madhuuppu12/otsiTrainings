package org.mvc;

import java.io.Serializable;

public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String address;
	private String name;
	private String rest_id;
	private String status;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}
	
	public Customer() {
		super();
	}

	public Customer(String id, String address, String name, String rest_id, String status) {
		super();
		this.id = id;
		this.address = address;
		this.name = name;
		this.rest_id = rest_id;
		this.status = status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRest_id() {
		return rest_id;
	}

	public void setRest_id(String rest_id) {
		this.rest_id = rest_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
