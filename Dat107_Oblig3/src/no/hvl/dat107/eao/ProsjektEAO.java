package no.hvl.dat107.eao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import no.hvl.dat107.entity.Prosjekt;

public class ProsjektEAO {
    private EntityManagerFactory emf;

    public ProsjektEAO() {
        emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit");
    }

    public Prosjekt finnProsjektMedId(int id) {

        EntityManager em = emf.createEntityManager();

        Prosjekt prosjekt = null;
        try {
            prosjekt = em.find(Prosjekt.class, id);
        } finally {
            em.close();
        }
        return prosjekt;
    }
    
    public List<Prosjekt> hentAlleProsjekt(){
		EntityManager em = emf.createEntityManager();
		
		List<Prosjekt> prosjekter= null;
		try {
			TypedQuery<Prosjekt> query = em.createQuery("SELECT p FROM Prosjekt p ORDER BY p.prosjektId", Prosjekt.class);
			prosjekter = query.getResultList();
		} finally {
			em.close();
		}
		
		return prosjekter;
	}
	
	public void oppdaterProsjekt(Prosjekt prosjekt) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			em.merge(prosjekt);
			tx.commit();
		} finally {
			em.close();
		}
	}
	
	public int settInnProsjekt(Prosjekt prosjekt) {
		System.out.print("Setter inn prosjekt...");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			em.persist(prosjekt);
			
			tx.commit();
			
			System.out.println("OK!");
		} finally {
			em.close();
		}
		
		return prosjekt.getProsjektId();
	}
	
	public void fjernProsjekt(Prosjekt prosjekt) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			System.out.println("Fjerner prosjekt...");
			tx.begin();
			
			prosjekt = em.merge(prosjekt);
			em.remove(prosjekt);
			
			tx.commit();
			System.out.println("OK!\n");
		} finally {
			em.close();
		}
	}
}
