package com.cenec.imfe.proyecto.services;

/**
 * Clase utilizada para devolver el resultado de un intento de operación en el sistema.
 * 
 * Si el resultado es erróneo, el valor de 'result' es 'false' y se retorna un mensaje de error. Si
 * el resultado es exitoso, el valor de 'result' es 'true' y se puede retornar un objeto que es el
 * resultado de la operación solicitada
 * 
 * @author Javier
 */
public class OperationResult
{
	private boolean result;
	private String error;
	private Object access;
	
	/**
	 * Constructor
	 * 
	 * @param result
	 * @param error
	 * @param access
	 */
	public OperationResult(boolean result, String error, Object access)
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

	public Object getResultObject() {
		return access;
	}
}
