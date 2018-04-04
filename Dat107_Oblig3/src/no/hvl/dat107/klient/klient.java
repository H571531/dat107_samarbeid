package no.hvl.dat107.klient;

import no.hvl.dat107.*;

public class klient {

	public static void main(String[] args) {
		AnsattEAO service=new AnsattEAO();
		Ansatt Adrian=service.finnAnsattMedPK(1);

		System.out.println(Adrian);
		
		Adrian.setStilling("Teknikker");
		service.updateAnsatt(Adrian);
		System.out.println(Adrian);
		
	}

}
