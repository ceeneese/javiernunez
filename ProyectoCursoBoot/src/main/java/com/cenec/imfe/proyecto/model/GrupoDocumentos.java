package com.cenec.imfe.proyecto.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "grupodocumentos")
public class GrupoDocumentos
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idGrupo", unique = true, nullable = false)	
	private Integer id;
	
	@NotEmpty
	@Column(name = "nombre", nullable = false)	
	private String nombre;
	
    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
        name = "rel_grupo_doc", 
        joinColumns = { @JoinColumn(name = "idGrupo") }, 
        inverseJoinColumns = { @JoinColumn(name = "idDocumento") }
    )
    private Set<DocumentInfo> documentos;
    
	// Por ahora la relación ManytoMany entre grupos y usuarios va a ser unidireccional,
	// es decir, un grupo no va a saber qué usuarios le referencian
	// @ManyToMany(cascade=CascadeType.ALL, mappedBy="grupos")  
	// private Set<Usuario>usuarios;

    /**
     * Constructor
     */
    public GrupoDocumentos()
    {
    	super();
    	
    	documentos = new HashSet<DocumentInfo>();
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<DocumentInfo> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(Set<DocumentInfo> documentos) {
		this.documentos = documentos;
	}
	
	public void addDocumento(DocumentInfo doc)
	{
		this.documentos.add(doc);
	}
	
	public void removeDocumento(DocumentInfo doc)
	{
		this.documentos.remove(doc);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		
		if (!(o instanceof GrupoDocumentos))
		{
			return false;
		}
		
		GrupoDocumentos other = (GrupoDocumentos)o;
		
		return this.id.equals(other.id);
	}
	
	@Override
	public int hashCode()
	{
		return (id == null ? 1 : id.hashCode());
	}
	
	@Override
	public String toString()
	{
		return nombre + "-" + documentos.toString();
	}
}
