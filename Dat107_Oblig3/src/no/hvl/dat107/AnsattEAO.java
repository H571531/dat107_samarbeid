package no.hvl.dat107;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AnsattEAO {
	private EntityManagerFactory emf;

	public AnsattEAO() {
		emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit");
	}

	public Ansatt finnAnsattMedPK(int AnsattId) {

		EntityManager em = emf.createEntityManager();

		Ansatt ansatt = null;
		try {
			ansatt = em.find(Ansatt.class, AnsattId);
		} finally {
			em.close();
		}
		return ansatt;
	}

	public void lagAnsatt(Ansatt p) {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(p);
			tx.commit();

		} catch (Throwable e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public List<Ansatt> finnAlleAnsatte() {

		EntityManager em = emf.createEntityManager();

		List<Ansatt> Ansatte = null;
		try {
			TypedQuery<Ansatt> query = em.createQuery("SELECT p FROM oblig3.ansatt p", Ansatt.class);
			Ansatte = query.getResultList();
		} finally {
			em.close();
		}
		return Ansatte;
	}

	public void updateAnsatt(Ansatt p) {

		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			Ansatt q = em.merge(p);

			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

	public void deleteAnsatt(Ansatt p) {

		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			em.remove(em.merge(p));
			em.getTransaction().commit();

		} catch (Throwable e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		} finally {
			em.close();
		}
	}

}
