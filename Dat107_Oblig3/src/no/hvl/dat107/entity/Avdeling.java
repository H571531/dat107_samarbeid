package no.hvl.dat107.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import no.hvl.dat107.klient.Tekstgrensesnitt;

@Entity
@Table(name = "avdeling", schema = "oblig3")
public class Avdeling {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int avdelingsID;
	
	private String navn;
	
	@OneToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name="sjef", nullable=false)
	private Ansatt sjef;
	
	@OneToMany(mappedBy="ansattVed", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
	private List<Ansatt> ansattListe;
	
	
	public Avdeling() {
		
	}
	
	public Avdeling(String navn, Ansatt sjef) {
		this.navn = navn;
		this.sjef = sjef;
		
		ansattListe = new ArrayList<Ansatt>();
		Avdeling gammelAvdeling = sjef.getAnsattVed();
		gammelAvdeling.getAnsattListe().remove(sjef);
		sjef.setAnsattVed(this);
		ansattListe.add(sjef);
		this.sjef = sjef;
		sjef.setStilling("Avdelingssjef");

		
	}
	
	/**
	 * Setter ny sjef for avdelingen, setter den nye sjefens sjef-attributt til denne avdelingen, og setter den gamle sjefens sjef-attributt til null, samt oppdaterer stiling for begge to.
	 * @param sjef
	 */
	public void setSjef(Ansatt sjef) {
		
		
		
		Ansatt gammelSjef = this.sjef;
		
		this.sjef = sjef;
		
		sjef.setStilling("Avdelingssjef");
		this.sjef = sjef;
		sjef.setSjefFor(this);
		
		//Sjekk om gammel sjef, bruker samme metode for ny avdeling som ikke har sjef
		if(gammelSjef != null) {
			gammelSjef.setSjefFor(null);
			
			//gammelSjef.setStilling("Tidl. sjef");
			gammelSjef.setStilling("Tidl. sjef");
		}
		
		
	}

	public List<Ansatt> getAnsattListe() {
		return ansattListe;
	}

	public void setAnsattListe(List<Ansatt> ansattListe) {
		this.ansattListe = ansattListe;
	}

	@Override
	public String toString() {
		
		
		StringBuilder sb = new StringBuilder();
		sb.append( "AVDELING: " + avdelingsID + " - " + navn + " - SJEF: " + sjef.getAnsattid() + " - " + sjef.getFornavn() + " " + sjef.getEtternavn());
		if(ansattListe.size() > 0) {
			sb.append("\nANSATTE\n" + Tekstgrensesnitt.lagStrek() + Ansatt.lagTabellOverskrift() + Tekstgrensesnitt.lagStrek());
			for(Ansatt ans: ansattListe) {
				sb.append(ans + "\n");
			}
		}
		return sb.toString();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Avdeling other = (Avdeling) obj;
		if (avdelingsID != other.avdelingsID)
			return false;
		if (navn == null) {
			if (other.navn != null)
				return false;
		} else if (!navn.equals(other.navn))
			return false;
		return true;
	}
	
	public void leggTilAnsatt(Ansatt ans) {
		ansattListe.add(ans);
	}
	
	public int getAvdelingsID() {
		return avdelingsID;
	}

	public void setAvdelingsID(int avdelingsID) {
		this.avdelingsID = avdelingsID;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public Ansatt getSjef() {
		return sjef;
	}
	
	

}
