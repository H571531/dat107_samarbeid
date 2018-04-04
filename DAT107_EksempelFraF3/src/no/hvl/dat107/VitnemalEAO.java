package no.hvl.dat107;

import java.time.LocalDate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class VitnemalEAO {

    private EntityManagerFactory emf;

    public VitnemalEAO() {
        emf = Persistence.createEntityManagerFactory("VitnemalPersistenceUnit");
    }

    public Vitnemal finnVitnemalPaaPK(int studNr) {

        EntityManager em = emf.createEntityManager();

        Vitnemal vitnemal = null;
        try {
            vitnemal = em.find(Vitnemal.class, studNr);
        } finally {
            em.close();
        }
        return vitnemal;
    }

    public Karakter finnEnPaaMangesidenMedMatchPaaParametre() {

        String queryString = "";

        EntityManager em = emf.createEntityManager();

        Karakter mange = null;
        try {
            TypedQuery<Karakter> query = em.createQuery(queryString, Karakter.class);
            query.setParameter("???", null);
            query.setParameter("???", null);
            mange = query.getSingleResult();
        } finally {
            em.close();
        }
        return mange;
    }

    public void registrerKarakterForStudent(String kurskode, LocalDate eksamensDato, char kar, int studnr) {
    	

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            
            Vitnemal vitnemal = em.find(Vitnemal.class, studnr);
            Karakter karakter = new Karakter(kurskode, eksamensDato, kar, vitnemal);
            
            em.persist(karakter);
            //Vitnemålet-objektet vi har hentet fra db er ikke oppdatert -> gjør manuelt
            vitnemal.addKarakter(karakter);
            

            tx.commit();

        } catch (Throwable e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }

}
