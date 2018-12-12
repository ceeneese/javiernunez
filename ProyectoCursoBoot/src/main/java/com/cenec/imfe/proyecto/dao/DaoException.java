package com.cenec.imfe.proyecto.dao;

public class DaoException extends Exception
{
	private static final long serialVersionUID = 8420214974054633530L;

	public DaoException(String message)
	{
		super(message);
	}

	public DaoException(String message, Throwable prevException)
	{
		super(message, prevException);
	}
}
