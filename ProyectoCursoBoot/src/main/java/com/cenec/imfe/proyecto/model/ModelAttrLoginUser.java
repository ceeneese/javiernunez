package com.cenec.imfe.proyecto.model;

/**
 * Clase utilizada en el modelo de Spring para pasar usuario y contraseña de acceso
 * @author Javier
 *
 */
public class ModelAttrLoginUser
{
	private String usr;
	private String pwd;
	
	public ModelAttrLoginUser()
	{
		super();
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String toString()
	{
		return usr + "/" + pwd;
	}

}
