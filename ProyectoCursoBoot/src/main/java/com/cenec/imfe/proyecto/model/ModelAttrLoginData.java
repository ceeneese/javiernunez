package com.cenec.imfe.proyecto.model;

import javax.validation.constraints.NotEmpty;

/**
 * Clase utilizada en el modelo de Spring para pasar usuario y contraseña de acceso
 * @author Javier
 *
 */
public class ModelAttrLoginData
{
	@NotEmpty
	private String nombre;
	
	@NotEmpty
	private String clave;
	
	public ModelAttrLoginData()
	{
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String toString()
	{
		return nombre + "/" + clave;
	}
}
