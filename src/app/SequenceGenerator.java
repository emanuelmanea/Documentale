package app;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import model.Documentin;
import model.Documentout;
import model.Ente;

import org.apache.log4j.Logger;

public class SequenceGenerator {
	
	static Logger log4j = Logger.getLogger("documentale.sequenceGenerator");
	
	public int documentInNoProtocollo(Documentin doc, Ente ent , EntityManager em){
		int res = 0;
		try {
			TypedQuery<Integer> query = em.createQuery("select d.noprotocollo from Documentin d where d.ente=:e and FUNC('YEAR',d.dataprotocollo) = :year order by d.noprotocollo desc",Integer.class);
			query.setParameter("e", ent);
			
			// to test
			Calendar cal = Calendar.getInstance();  
			cal.setTime(doc.getDataprotocollo());
			query.setParameter("year", cal.get(Calendar.YEAR));
			
			// debug
			//System.out.println("documentInNoProtocollo: " + cal.getTime());
			log4j.debug("documentInNoProtocollo: " + cal.getTime());
			
			query.setMaxResults(1);
			
			//debug
			//System.out.println("seqGen result : " + query.getResultList());
			//System.out.println("seqGen year : " + cal.get(Calendar.YEAR));
			log4j.debug("seqGen result : " + query.getResultList());
			log4j.debug("seqGen year : " + cal.get(Calendar.YEAR));
			
			if(query.getResultList().size() == 0)
				return 1;
			else
				res = query.getResultList().get(0);
		}
		// no row in DB
		catch(NoResultException ex){
			ex.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println("SequenceProtocolloIn: " + (res+1));
		log4j.debug("SequenceProtocolloIn: " + (res+1));
		return res+1;
	}
	
	public int documentInID(Documentin doc, Ente ent , EntityManager em){
		int res = 0;
		try {
			TypedQuery<Integer> query = em.createQuery("select d.iddocumentin from Documentin d order by d.iddocumentin desc",Integer.class);
			query.setMaxResults(1);
			if(query.getResultList().size() == 0)
				return 1;
			else
				res = query.getResultList().get(0);
		}
		catch(NoResultException ex){
			//ex.printStackTrace();
			return 1;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println("SequenceIDIn: " + (res+1));
		log4j.debug("SequenceIDIn: " + (res+1));
		return res+1;
	}
	
	public int documentOutNoProtocollo(Documentout doc, Ente ent , EntityManager em){
		int res = 0;
		try {
			TypedQuery<Integer> query = em.createQuery("select d.noprotocollo from Documentout d where d.ente=:e and FUNC('YEAR',d.dataprotocollo) = :year order by d.noprotocollo desc",Integer.class);
			query.setParameter("e", ent);
			
			// to test
			Calendar cal = Calendar.getInstance();  
			cal.setTime(doc.getDataprotocollo());
			query.setParameter("year", cal.get(Calendar.YEAR));

			// debug
			//System.out.println("documentInNoProtocollo: " + cal.getTime());
			log4j.debug("documentInNoProtocollo: " + cal.getTime());
			
			query.setMaxResults(1);
			
			//debug
			//System.out.println("seqGen result : " + query.getResultList());
			//System.out.println("seqGen year : " + cal.get(Calendar.YEAR));
			log4j.debug("seqGen result : " + query.getResultList());
			log4j.debug("seqGen year : " + cal.get(Calendar.YEAR));
			
			if(query.getResultList().size() == 0 )
				return 1;
			else
				res = query.getResultList().get(0);
		}
		// no row in DB
		catch(NoResultException ex){
			ex.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println("SequenceProtocolloOut: " + (res+1));
		log4j.debug("SequenceProtocolloOut: " + (res+1));
		return res+1;
	}
	
	public int documentOutID(Documentout doc, Ente ent , EntityManager em){
		int res = 0;
		try {
			TypedQuery<Integer> query = em.createQuery("select d.iddocumentout from Documentout d order by d.iddocumentout desc",Integer.class);
			query.setMaxResults(1);
			if(query.getResultList().size() == 0)
				return 1;
			else
				res = query.getResultList().get(0);
		}
		catch(NoResultException ex){
			//ex.printStackTrace();
			return 1;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		//System.out.println("SequenceIDIn: " + (res+1));
		log4j.debug("SequenceIDIn: " + (res+1));
		return res+1;
	}
	
	public int enteID(EntityManager em){
		Object queryResult = new Object() ;
		int res = 0;
		try {
			Query query = em.createQuery("select e.idente from Ente e order by e.idente desc");
			query.setMaxResults(1);
			queryResult = query.getSingleResult();
			res = (Integer) queryResult;
		}
		//no row in DB
		catch(NoResultException ex){
			//ex.printStackTrace();
			return 1;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//System.out.println("SequenceIDEnte: " + (res+1));
		log4j.debug("SequenceIDEnte: " + (res+1));
		return res+1;
	}
}
