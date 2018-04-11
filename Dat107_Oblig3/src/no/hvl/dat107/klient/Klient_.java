package no.hvl.dat107.klient;

import no.hvl.dat107.eao.AnsattEAO;
import no.hvl.dat107.entity.*;

public class Klient_ {

	public static void main(String[] args) {
		AnsattEAO AnsattService=new AnsattEAO();
		Ansatt Adrian=AnsattService.finnAnsattMedPK(1);

		Adrian.skrivUt();
		
		Adrian.setStilling("Teknikker");
		AnsattService.updateAnsatt(Adrian);
		Adrian.skrivUt();
		
	}

}
