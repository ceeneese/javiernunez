package com.cenec.imfe.proyecto.utils;

/**
 * Clase de utilidad para encriptar claves antes de ser guardadas en la base de datos del sistema
 * 
 * @author usuario
 *
 */
public class PasswordEncoder
{
	private static PasswordEncoder instance;
	
	private PasswordEncoder()
	{
		super();
	}
	
	/**
	 * Obtiene la instancia única de la clase de utilidad
	 * 
	 * @return
	 */
	public static synchronized PasswordEncoder getInstance()
	{
		if (instance == null)
		{
			instance = new PasswordEncoder();
		}
		
		return instance;
	}

	/**
	 * Codifica una contraseña
	 * 
	 * @param plainPassword
	 * @return
	 */
	public String encode(String plainPassword)
	{
		// Método chorra de encriptación
		char[] result = new char[plainPassword.length()];
		
		for (int idx = 0 ; idx < plainPassword.length() ; idx++)
		{
			result[idx] = (char)(plainPassword.charAt(idx) + 1);
		}
		
		return new String(result);
	}

	/**
	 * Comprueba que una clave es correcta en comparación a su modo encriptado
	 * 
	 * @param plainPassword
	 * @param encoded
	 * @return 'true' si la clave se corresponde con el cifrado esperado; 'false' en otro caso
	 */
	public boolean checkPassword(String plainPassword, String encoded)
	{
		String newEncoded = encode(plainPassword);
		
		boolean result = newEncoded.length() == encoded.length();
		
		int idx = 0;
		while (result && idx < newEncoded.length())
		{
			if (newEncoded.charAt(idx) != encoded.charAt(idx))
			{
				result = false;
			}
			else
			{
				idx++;
			}
		}
		
		return result;
	}
}
