package com.cenec.imfe.proyecto.services;

public class ServiceException extends Exception
{
	private static final long serialVersionUID = 5087121793896682561L;

	public ServiceException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public ServiceException(String message)
	{
		super(message);
	}
}
