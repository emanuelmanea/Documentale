package app;

import gui.Documentale;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Documentin;
import model.Documentout;

import org.apache.log4j.Logger;

public class VerificaDataProtocollo {
	
	EntityManager em;
	static Logger log4j = Logger.getLogger("documentale.verificaDataProtocollo");
	
	public boolean isAdequateIn(Documentin docIn){
		em = Documentale.getEm();
		boolean result = false;
		try{
			TypedQuery<Date> query = em.createQuery("select d.dataprotocollo from Documentin d where d.ente=:ente and FUNC('YEAR',d.dataprotocollo)=:year order by d.noprotocollo desc",Date.class);
			query.setParameter("ente", docIn.getEnte());
						
			// to test
			Calendar cal = Calendar.getInstance();  
			cal.setTime(docIn.getDataprotocollo());
			query.setParameter("year", cal.get(Calendar.YEAR));
			query.setMaxResults(1);
			cal = null;
			
			if(query.getResultList().size() != 0){
				Date oldDate = query.getResultList().get(0);
				Date newDate = docIn.getDataprotocollo();
								
				// zero out HH:mm:ss
				cal = Calendar.getInstance();  
				cal.setTime(newDate);
				cal.set(Calendar.HOUR_OF_DAY, 0);  
				cal.set(Calendar.MINUTE, 0);  
				cal.set(Calendar.SECOND, 0);  
				cal.set(Calendar.MILLISECOND, 0);  
				newDate = cal.getTime();
				
				//debug
				//System.out.println("isAdequateIn oldDate: " + oldDate);
				//System.out.println("isAdequateIn newDate: " + newDate);
				//System.out.println("isAdequateIn newDate.before(data)"+newDate.before(oldDate));
				log4j.debug("isAdequateIn oldDate: " + oldDate);
				log4j.debug("isAdequateIn newDate: " + newDate);
				log4j.debug("isAdequateIn newDate.before(data)"+newDate.before(oldDate));
				
				if(!newDate.before(oldDate))
					result = true;
			}
			else
				// no records
				result = true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println("isAdequateIn() return value = " + result);
		log4j.debug("isAdequateIn() return value = " + result);
		return result;
	}
	
	public boolean isAdequateOut(Documentout docOut){
		em = Documentale.getEm();
		boolean result = false;
		try{
			TypedQuery<Date> query = em.createQuery("select d.dataprotocollo from Documentout d where d.ente=:ente and FUNC('YEAR',d.dataprotocollo)=:year order by d.noprotocollo desc",Date.class);
			query.setParameter("ente", docOut.getEnte());
			// to test
			Calendar cal = Calendar.getInstance();  
			cal.setTime(docOut.getDataprotocollo());
			query.setParameter("year", cal.get(Calendar.YEAR));
			query.setMaxResults(1);
			cal = null;
			
			if(query.getResultList().size() != 0){
				Date oldDate = query.getResultList().get(0);
				Date newDate = docOut.getDataprotocollo();
								
				// zero out HH:mm:ss
				cal = Calendar.getInstance();  
				cal.setTime(newDate);
				cal.set(Calendar.HOUR_OF_DAY, 0);  
				cal.set(Calendar.MINUTE, 0);  
				cal.set(Calendar.SECOND, 0);  
				cal.set(Calendar.MILLISECOND, 0);  
				newDate = cal.getTime();
				
				//debug
				//System.out.println("isAdequateOut oldDate: " + oldDate);
				//System.out.println("isAdequateOut newDate: " + newDate);
				//System.out.println("isAdequateOut newDate.before(data)"+newDate.before(oldDate));
				log4j.debug("isAdequateOut oldDate: " + oldDate);
				log4j.debug("isAdequateOut newDate: " + newDate);
				log4j.debug("isAdequateOut newDate.before(data)"+newDate.before(oldDate));
				
				if(!newDate.before(oldDate))
					result = true;
			}
			else
				// no records
				result = true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println("isAdequateOut() return value = " + result);
		log4j.debug("isAdequateOut() return value = " + result);
		return result;
	}
}