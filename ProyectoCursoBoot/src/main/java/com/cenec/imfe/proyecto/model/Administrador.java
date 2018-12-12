package com.cenec.imfe.proyecto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "administrador")
public class Administrador
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idAdmin", unique = true, nullable = false)	
	private Integer id;
	
	@Column(name = "usrAcceso", nullable = false, unique = true)	
	private String usr;

	@Column(name = "pwdAcceso", nullable = false, unique = false)	
	private String encodedPwd;
	
	/**
	 * Constructor
	 */
	public Administrador()
	{
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsr()
	{
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getEncodedPwd() 
	{
		return encodedPwd;
	}

	public void setEncodedPwd(String encodedPwd) {
		this.encodedPwd = encodedPwd;
	}
}
