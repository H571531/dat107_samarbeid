package no.hvl.dat107;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Vitnemal", schema = "forelesning3")
public class Vitnemal {
	
	@Id
	private int studNr;
	
	private LocalDate Studiestart;
	private LocalDate Studieslutt;
	
	
	
	//mappedBy - objektvariabelen som kobler i den andre klassen
	//Default - FetchType.Lazy - lazy loading, laster ikke tabellen før den eksplisitt bes om??
	@OneToMany(mappedBy = "vitnemal", fetch = FetchType.EAGER)
	private List<Karakter> karakterer;
	
	public void addKarakter(Karakter karakter) {
		karakterer.add(karakter);
	}

	@Override
	public String toString() {
		return "Vitnemal [studNr=" + studNr + ", Studiestart=" + Studiestart + ", Studieslutt=" + Studieslutt
				+ ", karakterer=" + karakterer + "]";
	}
	
	

}
