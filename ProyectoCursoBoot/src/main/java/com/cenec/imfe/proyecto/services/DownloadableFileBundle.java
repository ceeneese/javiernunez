package com.cenec.imfe.proyecto.services;

import org.springframework.core.io.InputStreamResource;

public class DownloadableFileBundle
{
	private String fileName;
	
	private long fileLength;
	
	private InputStreamResource stream;
	
	public DownloadableFileBundle(String fileName, long fileLength, InputStreamResource stream)
	{
		super();
		
		this.fileName = fileName;
		this.fileLength = fileLength;
		this.stream = stream;
	}

	public String getFileName() {
		return fileName;
	}

	public long getFileLength() {
		return fileLength;
	}

	public InputStreamResource getStream() {
		return stream;
	}
}
