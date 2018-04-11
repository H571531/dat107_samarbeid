package no.hvl.dat107;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "prosjekt", schema = "oblig3")
public class Prosjekt {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int prosjektID; // Lages av JPA

	private String ProsjektNavn;
	private String Beskrivelse;

	@OneToMany(mappedBy = "prosjekt")
	private List<prosjektdeltagelse> deltagelser;

	public void leggTilProsjektdeltagelse(prosjektdeltagelse prosjektdeltagelse) {
		deltagelser.add(prosjektdeltagelse);
	}

	public void fjernProsjektdeltagelse(prosjektdeltagelse prosjektdeltagelse) {
		deltagelser.remove(prosjektdeltagelse);
	}

	public int getProsjektID() {
		return prosjektID;
	}

	public void setProsjektID(int prosjektID) {
		this.prosjektID = prosjektID;
	}

	public String getProsjektNavn() {
		return ProsjektNavn;
	}

	public void setProsjektNavn(String prosjektNavn) {
		ProsjektNavn = prosjektNavn;
	}

	public String getBeskrivelse() {
		return Beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		Beskrivelse = beskrivelse;
	}

	public List<prosjektdeltagelse> getDeltagelser() {
		return deltagelser;
	}

	public void setDeltagelser(List<prosjektdeltagelse> deltagelser) {
		this.deltagelser = deltagelser;
	}

}
