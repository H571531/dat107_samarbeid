package no.hvl.dat107.eao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.Prosjektdeltakelse;

public class ProsjektdeltakelseEAO {
	private EntityManagerFactory emf;
	
	
	public void fjernAnsattFraDeltagelse(Ansatt a, Prosjektdeltakelse p) {
		a.fjernProsjektdeltakelse(p);
		p.getProsjekt().fjernProsjektdeltakelse(p);
	}
	
	public Prosjektdeltakelse finnProsjektdeltagelse(int prosjektdeltagelseID) {

		EntityManager em = emf.createEntityManager();

		Prosjektdeltakelse prosjektdeltagelsen = null;
		try {
			prosjektdeltagelsen = em.find(Prosjektdeltakelse.class, prosjektdeltagelseID);
		} finally {
			em.close();
		}
		return prosjektdeltagelsen;
	}
	
	public void ForTimer(Prosjektdeltakelse p, int timer) {
		p.setTimer(p.getTimer()+timer);
		updateProsjektDeltakelse(p);
	}
	
	public void updateProsjektDeltakelse(Prosjektdeltakelse p) {

		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			Prosjektdeltakelse q = em.merge(p);

			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	
}
