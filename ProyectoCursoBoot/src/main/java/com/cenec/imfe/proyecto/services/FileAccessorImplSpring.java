package com.cenec.imfe.proyecto.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Implementación de FileAccessor para servicios basados en Spring
 * 
 * @author Javier
 */
public class FileAccessorImplSpring implements FileAccessor
{
	private MultipartFile mpFile;
	
	private String path;

	/**
	 * Constructor
	 *
	 * @param mpFile
	 * @param path
	 */
	public FileAccessorImplSpring(MultipartFile mpFile, String path)
	{
		super();
		
		this.mpFile = mpFile;
		this.path = path;
	}
	
	public MultipartFile getMpFile()
	{
		return mpFile;
	}
	
	public String getPath()
	{
		return path;
	}
}
