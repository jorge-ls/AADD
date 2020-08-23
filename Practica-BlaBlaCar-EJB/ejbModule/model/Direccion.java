package model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Direccion implements Serializable {

	private static final long serialVersionUID = 1L;

	private	String	calle;
	private int cp;
	
	public	String	getCalle()	{
		return calle;
	}
	public void	setCalle(String	calle)	{
		this.calle	=	calle;
	}
	public int	getCP()	{
		return cp;
	}
	public void	setCP(int cp)	{
		this.cp	= cp;
	}
	
}
