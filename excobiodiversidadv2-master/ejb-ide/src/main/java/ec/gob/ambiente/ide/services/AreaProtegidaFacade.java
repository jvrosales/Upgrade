package ec.gob.ambiente.ide.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.ide.facade.AbstractFacade;
import ec.gob.ambiente.ide.model.AreaProtegida;




/**
 * Servicios para administracion de areas protegidas de la bd ide
 * @author EXCO
 *
 */
@Stateless
public class AreaProtegidaFacade extends AbstractFacade<AreaProtegida, Integer> implements Serializable{
	
	private static final long serialVersionUID = 1164242243166182670L;

	public AreaProtegidaFacade() {
		super(AreaProtegida.class, Integer.class);		
	}
	
	/**
	 * Buscar areas protegidas
	 * @return
	 */
	public List<AreaProtegida> findAreas()
	{
		try
		{
			Query query = super.getEntityManager().createQuery("SELECT o FROM AreaProtegida o ");
			@SuppressWarnings("unchecked")
			List<AreaProtegida> result=(List<AreaProtegida>)query.getResultList();
			if(result.size()>0)
				return result;
			
		}catch(NoResultException e)
		{
			return new ArrayList<AreaProtegida>();
		}
		return new ArrayList<AreaProtegida>();
	}
	
	/**
	 * Buscar area protegida por codigo snap
	 * @param code
	 * @return
	 */
	public AreaProtegida findAreaProtegidaPorCodigo(String code)
	{
		try
		{
			Query query = super.getEntityManager().createQuery("SELECT o FROM AreaProtegida o where csnap= :elCodigo ");
			query.setParameter("elCodigo", code);
			AreaProtegida result=(AreaProtegida)query.getSingleResult();
			
				return result;
			
		}catch(NoResultException e)
		{
			return new AreaProtegida();
		}
	
	}
	
	
}
