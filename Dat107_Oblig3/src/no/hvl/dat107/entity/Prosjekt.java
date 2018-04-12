package no.hvl.dat107.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import no.hvl.dat107.klient.Tekstgrensesnitt;

@Entity
@Table(name = "prosjekt", schema = "oblig3")
public class Prosjekt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prosjektId; // Lages av JPA

	private String prosjektNavn;
	private String beskrivelse;

	@OneToMany(mappedBy = "prosjekt")
	private List<Prosjektdeltakelse> deltakelser;
	
	public Prosjekt() {
		
	}
	
	public Prosjekt(String navn, String beskrivelse) {
		this.prosjektNavn = navn;
		this.beskrivelse = beskrivelse;
		
		
	}
	
	@Override
	public String toString() {
		int totaleTimer = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("Prosjekt " + prosjektId + " - " + prosjektNavn + ": " + beskrivelse + "\nAnsatte: | ");
		if(deltakelser.size()>0) {
			for(Prosjektdeltakelse pd: deltakelser) {
				
				sb.append(pd.getAnsatt().getAnsattid() + " (" + pd.getRolle() + "): " + pd.getTimer() + " timer | ");
				totaleTimer += pd.getTimer();
			}
			
		} else {
			sb.append("INGEN");
		}
		sb.append("\nTotalt antall timer:" + totaleTimer);
		sb.append("\n" + Tekstgrensesnitt.lagStrek() + "\n");
		return sb.toString();
	}
	
	public void visAnsatte() {
		System.out.println(Ansatt.lagTabellOverskrift());
		System.out.println(Tekstgrensesnitt.lagStrek());
		
		for(Prosjektdeltakelse pd: deltakelser) {
			
			System.out.println(pd.getAnsatt() + " - " + pd.getTimer() + " timer");
			
		}
		System.out.println(Tekstgrensesnitt.lagStrek());
	}

	public void leggTilProsjektdeltakelse(Prosjektdeltakelse prosjektdeltakelse) {
		deltakelser.add(prosjektdeltakelse);
	}

	public void fjernProsjektdeltakelse(Prosjektdeltakelse prosjektdeltakelse) {
		deltakelser.remove(prosjektdeltakelse);
	}

	public int getProsjektId() {
		return prosjektId;
	}

	public void setProsjektId(int prosjektID) {
		this.prosjektId = prosjektID;
	}

	public String getProsjektNavn() {
		return prosjektNavn;
	}

	public void setProsjektNavn(String prosjektNavn) {
		this.prosjektNavn = prosjektNavn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}

	public List<Prosjektdeltakelse> getDeltakelser() {
		return deltakelser;
	}

	public void setDeltagelser(List<Prosjektdeltakelse> deltakelser) {
		this.deltakelser = deltakelser;
	}

}
