package no.hvl.dat107.klient;

import java.util.Scanner;

public class Meny {
	
	
	//private static AnsattEAO ansEAO; 
	//private static AvdelingEAO avdEAO; 
	private static Tekstgrensesnitt grensesnitt;
	
	public static void startMeny() {
		
		//ansEAO = new AnsattEAO();
		//avdEAO = new AvdelingEAO();
		grensesnitt = new Tekstgrensesnitt();
		Scanner inn = new Scanner(System.in);
		int valg = 0;
		
		do {
			
		
			System.out.println("//////////////MENY//////////////");
			System.out.println("1. Vis/Oppdater ansatte");
			System.out.println("2. Vis en bestemt ansatt");
			
			System.out.println("3. Legg inn ny ansatt");
			System.out.println("-------------------------------");
			System.out.println("4. Vis/Oppdater avdelinger");
			System.out.println("5. Vis en bestemt avdeling");
			
			System.out.println("-------------------------------");
			System.out.println("6. Vis/Oppdater prosjekter");
			System.out.println("0. Avslutt");
			
			
			
			valg = inn.nextInt();
			inn.nextLine();
			
			switch(valg) {
			case 1:
				grensesnitt.visAlleAnsatte(inn);
				break;
			case 2: 
				grensesnitt.visEnAnsatt(inn);
				break;
			case 3:
				grensesnitt.leggTilAnsatt(inn);
				break;
			case 4:
				grensesnitt.visAlleAvdelinger(inn);
				break;
			case 5:
				grensesnitt.visEnAvdeling(inn);
				break;
			case 6:
				grensesnitt.visAlleProsjekter(inn);
				break;
			
			}
		} while(valg != 0);
		
		inn.close();
	}

}
