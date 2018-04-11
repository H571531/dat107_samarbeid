package no.hvl.dat107.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;

public class AnsattEAO {
	private EntityManagerFactory emf;

	public AnsattEAO() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit");
	}

	public Ansatt finnAnsattMedId(int AnsattId) {

		EntityManager em = emf.createEntityManager();

		Ansatt ansatt = null;
		try {
			ansatt = em.find(Ansatt.class, AnsattId);
		} finally {
			em.close();
		}
		return ansatt;
	}

	public int settInnAnsatt(Ansatt a) {
		

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			Avdeling avd = em.merge(a.getAnsattVed());
	           
            em.persist(a);
            
            avd.getAnsattListe().add(a);

		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		return a.getAnsattid();
	}

	public List<Ansatt> hentAlleAnsatte() {

		EntityManager em = emf.createEntityManager();

		List<Ansatt> ansatte = null;
		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT a FROM Ansatt a", Ansatt.class);
			ansatte = query.getResultList();
		} finally {
			em.close();
		}
		return ansatte;
	}

	
	public Ansatt finnAnsattMedBrukernavn(String brukernavn) {
		EntityManager em = emf.createEntityManager();
		
		Ansatt ut = null;
		String queryString = "SELECT a FROM Ansatt a WHERE a.brukernavn = :brukernavn";
		
		try {
			TypedQuery<Ansatt> query = em.createQuery(queryString, Ansatt.class);
			query.setParameter("brukernavn", brukernavn);
			ut = query.getSingleResult();
		} catch(NoResultException e) {
			System.out.println("Finner ikke ansatt!");
		} finally {
			em.close();
		}
		return ut;
	}

	

	public void oppdaterAnsatt(Ansatt p) {

		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(p);

			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}


	public void fjernAnsatt(Ansatt a) {


		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			em.remove(a);
			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

}
