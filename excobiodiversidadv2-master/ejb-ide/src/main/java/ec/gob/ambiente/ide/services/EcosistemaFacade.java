package ec.gob.ambiente.ide.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ec.gob.ambiente.ide.facade.AbstractFacade;
import ec.gob.ambiente.ide.model.Ecosistema;




/**
 * Servicios para administracion de ecosistemas de la bd ide
 * @author EXCO
 *
 */
@Stateless
public class EcosistemaFacade extends AbstractFacade<Ecosistema, Integer> implements Serializable{
	
	private static final long serialVersionUID = 1164242243166182670L;

	public EcosistemaFacade() {
		super(Ecosistema.class, Integer.class);		
	}
	
	/**
	 * Buscar ecosistemas
	 * @return
	 */
	public List<String> findEcosystems()
	{
		try
		{
			Query query = super.getEntityManager().createQuery("SELECT DISTINCT(o.ceco)  FROM Ecosistema o ");
			@SuppressWarnings("unchecked")
			List<String> result=(List<String>)query.getResultList();
			if(result.size()>0)
				return result;
			
		}catch(NoResultException e)
		{
			return new ArrayList<String>();
		}
		return new ArrayList<String>();
	}
	
	/**
	 * Buscar ecosistema por codigo
	 * @param code
	 * @return
	 */
	public Ecosistema findEcosystemByCode(String code)
	{
		try
		{
			Query query = super.getEntityManager().createQuery("SELECT o FROM Ecosistema o where ceco= :elCodigo ");
			query.setParameter("elCodigo", code);
			Ecosistema result=(Ecosistema)(query.getResultList().get(0));
			
				return result;
			
		}catch(NoResultException e)
		{
			return new Ecosistema();
		}
	
	}
	
	
}
