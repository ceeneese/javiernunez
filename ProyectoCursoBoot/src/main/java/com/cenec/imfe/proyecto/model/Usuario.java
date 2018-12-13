package com.cenec.imfe.proyecto.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idUsuario", unique = true, nullable = false)	
	private Integer idUsuario;
	
	@Column(name = "idCliente", nullable = true, unique = true)	
	private int idCliente;
	
	@Column(name = "fechaAlta", nullable = false)	
	private Date fechaAlta;
	
	@Column(name = "nombre", nullable = true)	
	private String nombre;
	
	@Column(name = "apellidos", nullable = true)	
	private String apellidos;
	
	@Column(name = "direccion", nullable = true)	
	private String direccion;
	
	@Column(name = "ciudad", nullable = true)	
	private String ciudad;
	
	@Column(name = "provincia", nullable = true)	
	private String provincia;
	
	@Column(name = "tfnoFijo", nullable = true)	
	private String tfnoFijo;
	
	@Column(name = "tfnoMovil", nullable = true)	
	private String tfnoMovil;
	
	@Column(name = "correo-e", nullable = true)	
	private String correo_e;
	
	@Column(name = "usrAcceso", nullable = true, unique = true)	
	private String usrAccesoWeb;
	
	@Column(name = "pwdAcceso", nullable = true)	
	private String pwdAccesoWeb;
	
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
        name = "rel_usr_grupo", 
        joinColumns = { @JoinColumn(name = "idUsr") }, 
        inverseJoinColumns = { @JoinColumn(name = "idGrupo") }
    )
    private Set<GrupoDocumentos> grupos;
	
	/**
	 * Constructor
	 */
	public Usuario()
	{
		super();

		this.grupos = new HashSet<GrupoDocumentos>(3);
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getTfnoFijo() {
		return tfnoFijo;
	}

	public void setTfnoFijo(String tfnoFijo) {
		this.tfnoFijo = tfnoFijo;
	}

	public String getTfnoMovil() {
		return tfnoMovil;
	}

	public void setTfnoMovil(String tfnoMovil) {
		this.tfnoMovil = tfnoMovil;
	}

	public String getCorreo_e() {
		return correo_e;
	}

	public void setCorreo_e(String correo_e) {
		this.correo_e = correo_e;
	}

	public String getUsrAccesoWeb() {
		return usrAccesoWeb;
	}

	public void setUsrAccesoWeb(String usrAccesoWeb) {
		this.usrAccesoWeb = usrAccesoWeb;
	}

	public String getPwdAccesoWeb() {
		return pwdAccesoWeb;
	}

	public void setPwdAccesoWeb(String pwdAccesoWeb) {
		this.pwdAccesoWeb = pwdAccesoWeb;
	}

	public Set<GrupoDocumentos> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<GrupoDocumentos> grupos) {
		this.grupos = grupos;
	}
	
	public void addGrupo(GrupoDocumentos grupo)
	{
		this.grupos.add(grupo);
	}
	
	public void removeGrupo(GrupoDocumentos grupo)
	{
		this.grupos.remove(grupo);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		
		if (!(o instanceof Usuario))
		{
			return false;
		}
		
		Usuario other = (Usuario)o;
		
		return this.idUsuario.equals(other.idUsuario);
	}
	
	@Override
	public int hashCode()
	{
		return (idUsuario == null ? 1 : idUsuario.hashCode());
	}
}
