package com.cenec.imfe.proyecto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "documento")
public class DocumentInfo
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDocumento", unique = true, nullable = false)	
	private Integer idDoc;
	
	@Column(name = "nombre", unique = true, nullable = false)
	private String name;
	
	@Column(name = "ubicacion", unique = true, nullable = false)
	private String location;
	
	// Por ahora la relación ManytoMany entre documentos y grupos va a ser unidireccional,
	// es decir, un documento no va a saber a qué grupos pertenece
	// @ManyToMany(cascade=CascadeType.ALL, mappedBy="documentos")  
	// private Set<GrupoDocumentos>grupos;
	
	/**
	 * Constructor
	 */
	public DocumentInfo()
	{
		super();
	}

	public Integer getIdDoc()
	{
		return idDoc;
	}

	public void setIdDoc(Integer idDoc)
	{
		this.idDoc = idDoc;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		
		if (!(o instanceof DocumentInfo))
		{
			return false;
		}
		
		DocumentInfo other = (DocumentInfo)o;
		
		return this.idDoc.equals(other.idDoc);
	}
	
	@Override
	public int hashCode()
	{
		return (idDoc == null ? 1 : idDoc.hashCode());
	}
}
