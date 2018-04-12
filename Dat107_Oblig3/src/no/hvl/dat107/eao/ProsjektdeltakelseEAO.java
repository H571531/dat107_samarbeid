package no.hvl.dat107.eao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.Prosjektdeltakelse;
import no.hvl.dat107.entity.ProsjektdeltakelsePK;
import no.hvl.dat107.exception.AlleredeAnsattException;

public class ProsjektdeltakelseEAO {
	private EntityManagerFactory emf;
	
	public ProsjektdeltakelseEAO() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit");
	}
	
	public void leggTilPD(Prosjekt prosjekt, Ansatt ansatt, String rolle) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			prosjekt = em.merge(prosjekt);
			ansatt = em.merge(ansatt);
			
			Prosjektdeltakelse pd = new Prosjektdeltakelse(ansatt, prosjekt, rolle, 0);
			em.persist(pd);
			//Dårlig håndtering av Exception hvis pd allerede finnes! Vil bli lagt til som objekt som vises i menyene selv om rollback skjer
			//Heller ha oppdatering av ansatt og prosjekt etter persist()?
			
			tx.commit();
		} catch(AlleredeAnsattException e) {
			System.out.println("Ansatt jobber allerede på prosjektet!\n");
			
		}
		finally {
			em.close();
		}
		
	}
	
	public void fjernProsjektdeltakelse(Ansatt ans, Prosjekt prosjekt) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			ProsjektdeltakelsePK pk = new ProsjektdeltakelsePK(ans.getAnsattid(), prosjekt.getProsjektId());
			Prosjektdeltakelse pd = em.find(Prosjektdeltakelse.class, pk);
			
			ans = em.merge(ans);
			prosjekt = em.merge(prosjekt);
			
			ans.getProsjektDeltakelser().remove(pd);
			prosjekt.getDeltakelser().remove(pd);
			
			em.remove(pd);
			
			tx.commit();
			
		} finally {
			em.close();
		}
	}
	//
	public Prosjektdeltakelse finnPDMedAnsattOgProsjekt(Ansatt ansatt, Prosjekt prosjekt) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		
		Prosjektdeltakelse pd = null;
		
		try {
			tx.begin();
			
			ProsjektdeltakelsePK pk = new ProsjektdeltakelsePK(ansatt.getAnsattid(), prosjekt.getProsjektId());
			pd = em.find(Prosjektdeltakelse.class, pk);
		} finally {
			em.close();
		}
		
		return pd;
	}
	
	public void ForTimer(Prosjektdeltakelse p, int timer) {
		p.setTimer(p.getTimer()+timer);
		oppdaterPD(p);
	}
	
	public void oppdaterPD(Prosjektdeltakelse pd) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			
			pd.setAnsatt(em.merge(pd.getAnsatt()));
			pd.setProsjekt(em.merge(pd.getProsjekt()));
			pd = em.merge(pd);
			
			tx.commit();
			
		}finally {
			em.close();
		}
	}
	
	public Prosjektdeltakelse oppdaterPD2(Prosjektdeltakelse pd) {
		EntityManager em = emf.createEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			
			pd.setAnsatt(em.merge(pd.getAnsatt()));
			pd.setProsjekt(em.merge(pd.getProsjekt()));
			pd = em.merge(pd);
			
			tx.commit();
			
		}finally {
			em.close();
		}
		return pd;
	}
	
	
	
}
