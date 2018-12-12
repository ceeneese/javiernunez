package com.cenec.imfe.proyecto.dao;

public abstract class AccessBy
{
	public enum AccessType
	{
		ID_USUARIO,
		ID_CLIENTE,
		USR_WEB;
	}

	private AccessType type;
	
	protected AccessBy(AccessType type)
	{
		super();
		
		this.type = type;
	}
	
	public AccessType getType()
	{
		return this.type;
	}
	
	/**
	 * Método para que cada una de las subclases retorne su valor de búsqueda en modo String
	 */
	protected abstract String valueStr();
	
	public String toString()
	{
		return new String(type.toString() + " = " + valueStr());
	}

	public static class AccessByUsr extends AccessBy
	{
		private Integer usrId;
		
		public AccessByUsr(Integer usrId)
		{
			super(AccessType.ID_USUARIO);
			
			this.usrId = usrId;
		}
		
		public Integer getUsrId()
		{
			return this.usrId;
		}
		
		@Override
		protected String valueStr()
		{
			return Integer.toString(this.usrId);
		}
	}

	public static class AccessByClient extends AccessBy
	{
		private Integer clientId;
		
		public AccessByClient(Integer clientId)
		{
			super(AccessType.ID_CLIENTE);
			
			this.clientId = clientId;
		}

		public Integer getClientId()
		{
			return this.clientId;
		}

		@Override
		protected String valueStr()
		{
			return Integer.toString(this.clientId);
		}
	}

	public static class AccessByWebUsr extends AccessBy
	{
		private String webUsr;
		
		public AccessByWebUsr(String webUsr)
		{
			super(AccessType.USR_WEB);
			
			this.webUsr = webUsr;
		}

		public String getWebUser()
		{
			return this.webUsr;
		}

		@Override
		protected String valueStr()
		{
			return this.webUsr;
		}
	}
}
