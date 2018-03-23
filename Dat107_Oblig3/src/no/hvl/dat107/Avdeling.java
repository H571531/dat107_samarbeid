package no.hvl.dat107;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "", schema = "")


public class Avdeling {
	private int AvdelingId; //Bruker JPA for denne
	private String navn;
	private Ansatt AvdelingsSjef;
	
	
}
