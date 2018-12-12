package com.cenec.imfe.proyecto.services;

/**
 * Clase utilizada para devolver el resultado de un intento de operación en el sistema.
 * 
 * Si el resultado es erróneo, el valor de 'result' es 'false' y se retorna un mensaje de error. Si
 * el resultado es exitoso, el valor de 'result' es 'true' y se puede retornar un identificador de tipo
 * Integer (identificador de usuario o de documento, por ejemplo)
 * 
 * @author usuario
 *
 */
public class OperationResult
{
	private boolean result;
	private String error;
	private Integer access;
	
	/**
	 * Constructor
	 * 
	 * @param result
	 * @param error
	 * @param access
	 */
	public OperationResult(boolean result, String error, Integer access)
	{
		super();
		
		this.result = result;
		this.error = error;
		this.access = access;
	}

	public boolean getOperationResult() {
		return result;
	}

	public String getError() {
		return error;
	}

	public Integer getAccess() {
		return access;
	}
}
