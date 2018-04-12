package no.hvl.dat107.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;

public class AvdelingEAO {
	private EntityManagerFactory emf;
	
	public AvdelingEAO() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit");
	}
	
	public Avdeling finnAvdelingMedId(int avdelingId) {
		EntityManager em = emf.createEntityManager();
		
		Avdeling avdeling = null;
		try {
			avdeling = em.find(Avdeling.class, avdelingId);
		} finally {
			em.close();
		}
		return avdeling;
	}
	
	public int lagAvdeling(String navn, Ansatt nySjef) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Avdeling nyAvd = null;
		
		try {
			tx.begin();
			
			nySjef = em.merge(nySjef);
			Avdeling gammelAvd = em.merge(nySjef.getAnsattVed());
			nyAvd = new Avdeling(navn, nySjef);
			
			
			em.persist(nyAvd);
			
			
			tx.commit();
		} catch(Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return nyAvd.getAvdelingsID();
	}
	
	public List<Avdeling> hentAlleAvdelinger() {
		
		EntityManager em = emf.createEntityManager();
		
		List<Avdeling> Avdelinger = null;
		try {
			TypedQuery<Avdeling> query = em.createQuery("SELECT a FROM Avdeling a ORDER BY a.avdelingsID", Avdeling.class);
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
			em.merge(a);
			
			em.getTransaction().commit();
		} catch(Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}
	
	public void oppdaterNySjef(Avdeling avdeling, Ansatt ansatt) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			ansatt = em.merge(ansatt);
			avdeling = em.merge(avdeling);
			
			avdeling.setSjef(ansatt);
			
			tx.commit();
		} finally {
			em.close();
		}
	}
	
	public void fjernAvdeling(Avdeling avdeling) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			System.out.print("Sletter avdeling...");
			tx.begin();
			
			avdeling = em.merge(avdeling);
			em.remove(avdeling);
			
			tx.commit();
			System.out.println("OK!");
		} finally {
			em.close();
		}
	}
}
