package no.hvl.dat107.eao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.prosjektdeltagelse;

public class ProsjektdeltakelseEAO {
	private EntityManagerFactory emf;
	
	
	public void fjernAnsattFraDeltagelse(Ansatt a, prosjektdeltagelse p) {
		a.fjernProsjektdeltagelse(p);
		p.getProsjekt().fjernProsjektdeltagelse(p);
	}
	
	public prosjektdeltagelse finnProsjektdeltagelse(int prosjektdeltagelseID) {

		EntityManager em = emf.createEntityManager();

		prosjektdeltagelse prosjektdeltagelsen = null;
		try {
			prosjektdeltagelsen = em.find(prosjektdeltagelse.class, prosjektdeltagelseID);
		} finally {
			em.close();
		}
		return prosjektdeltagelsen;
	}
	
	public void ForTimer(prosjektdeltagelse p, int timer) {
		p.setTimer(p.getTimer()+timer);
		updateProsjektDeltakelse(p);
	}
	
	public void updateProsjektDeltakelse(prosjektdeltagelse p) {

		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			prosjektdeltagelse q = em.merge(p);

			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	
}
