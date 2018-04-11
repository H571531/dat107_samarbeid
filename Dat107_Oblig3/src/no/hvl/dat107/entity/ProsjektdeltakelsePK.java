package no.hvl.dat107.entity;

@SuppressWarnings("unused")
public class ProsjektdeltakelsePK {
    
    private int ansatt;
    private int prosjekt;
    
    public ProsjektdeltakelsePK() {}
    
    public ProsjektdeltakelsePK(int ansattId, int prosjektId) {
        this.ansatt = ansattId;
        this.prosjekt = prosjektId;
    }
}
