package no.hvl.dat107.entity;

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
	private int Prosjektid; // Lages av JPA

	private String prosjektNavn;
	private String beskrivelse;

	@OneToMany(mappedBy = "prosjekt")
	private List<Prosjektdeltakelse> deltagelser;

	public void leggTilProsjektdeltakelse(Prosjektdeltakelse prosjektdeltagelse) {
		deltagelser.add(prosjektdeltagelse);
	}

	public void fjernProsjektdeltakelse(Prosjektdeltakelse prosjektdeltagelse) {
		deltagelser.remove(prosjektdeltagelse);
	}

	public int getProsjektID() {
		return Prosjektid;
	}

	public void setProsjektID(int prosjektID) {
		this.Prosjektid = prosjektID;
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

	public List<Prosjektdeltakelse> getDeltagelser() {
		return deltagelser;
	}

	public void setDeltagelser(List<Prosjektdeltakelse> deltagelser) {
		this.deltagelser = deltagelser;
	}

}
