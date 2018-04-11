package no.hvl.dat107.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.hvl.dat107.entity.Avdeling;

public class AvdelingEAO {
	private EntityManagerFactory emf;
	
	public AvdelingEAO() {
		emf = Persistence.createEntityManagerFactory("avdelingPersistenceUnit");
	}
	
	public Avdeling finnAvdelingMedId(int AvdelingId) {
		EntityManager em = emf.createEntityManager();
		
		Avdeling avdeling = null;
		try {
			avdeling = em.find(Avdeling.class, AvdelingId);
		} finally {
			em.close();
		}
		return avdeling;
	}
	
	public void lagAvdeling(Avdeling p) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.persist(p);
			tx.commit();
		} catch(Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}
	
	public List<Avdeling> hentAvdelinger() {
		
		EntityManager em = emf.createEntityManager();
		
		List<Avdeling> Avdelinger = null;
		try {
			TypedQuery<Avdeling> query = em.createQuery("SELECT p FROM oblig3.avdeling p", Avdeling.class);
			Avdelinger = query.getResultList();
		} finally {
			em.close();
		}
		return Avdelinger;
	}
	
	public void oppdaterAvdeling(Avdeling a) {
		
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			Avdeling b = em.merge(a);
			
			em.getTransaction().commit();
		} catch(Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
}
