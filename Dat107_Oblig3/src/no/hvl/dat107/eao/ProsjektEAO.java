package no.hvl.dat107.eao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import no.hvl.dat107.entity.Prosjekt;

public class ProsjektEAO {
    private EntityManagerFactory emf;

    public ProsjektEAO() {
        emf = Persistence.createEntityManagerFactory("AnsattProsjektPU");
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
}
