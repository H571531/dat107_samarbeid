package no.hvl.dat107.exception;

import org.eclipse.persistence.exceptions.ExceptionHandler;

public class AlleredeAnsattException  extends RuntimeException implements ExceptionHandler {
	
	public AlleredeAnsattException() {
		
	}

	@Override
	public Object handleException(RuntimeException arg0) {
		System.out.println("\n\nDen ansatte jobber allerede på prosjektet!\nAvbryter...");
		return null;
	}
	

}
