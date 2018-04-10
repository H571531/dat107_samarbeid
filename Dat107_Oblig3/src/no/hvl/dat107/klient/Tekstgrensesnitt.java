package no.hvl.dat107.klient;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import no.hvl.dat107.Ansatt;
import no.hvl.dat107.AnsattEAO;
import no.hvl.dat107.Avdeling;
import no.hvl.dat107.Prosjekt;

public class Tekstgrensesnitt {
	
	private AnsattEAO ansEAO;
	private AvdelingEAO avdEAO;
	private ProsjektEAO prosjektEAO;
	private ProsjektdeltakelseEAO pdEAO;
	
	public Tekstgrensesnitt() {
		ansEAO = new AnsattEAO();
		avdEAO = new AvdelingEAO();
		prosjektEAO = new ProsjektEAO();
		pdEAO = new ProsjektdeltakelseEAO();
		
	}
	
	public void visAlleAnsatte(Scanner inn) {
		
		System.out.println("Viser alle ansatte: ");
		System.out.print(Ansatt.lagTabellOverskrift());
		
		System.out.print(lagStrek());
		List<Ansatt> ansattListe = ansEAO.hentAlleAnsatte();
		ArrayList<Integer> gyldigId = new ArrayList<Integer>();
		for(Ansatt a: ansattListe) {
			System.out.println(a);
			gyldigId.add(a.getAnsattid());
		}
		System.out.print(lagStrek());
		
		int valg = 0;
		//int antAnsatte = ansattListe.size();
		do {
			System.out.println("Skriv ansattID for å oppdatere en ansatt, eller 0 for å gå tilbake til meny");
			valg = inn.nextInt();
			inn.nextLine();
			
			
			if(valg == 0) {
				return;
			} else if(!gyldigId.contains(valg) || valg <0) {
				System.out.println("Ugyldig ansatt-id!");
			} else  {
				oppdaterAnsatt(inn, valg);
			}
			
			
		} while(valg != 0 || valg < ansattListe.size());
	}

	public void visEnAnsatt(Scanner inn) {
		System.out.println("Viser en ansatt:");
		
		
		int valg = 0;
		
		
		do {
			System.out.println("Skriv ansattID til ansatt som søkes etter, eller 0 for å avbryte: ");
			valg = inn.nextInt();
			inn.nextLine();
			if(valg == 0) {
				System.out.println("Går tilbake til meny...");
				return;
			}
			
			Ansatt ansatt = ansEAO.finnAnsattMedId(valg);
			if(ansatt == null) {
				System.out.println("Finner ikke ansatt med ansattID " + valg);
			} else {
				System.out.print(Ansatt.lagTabellOverskrift());
				System.out.print(lagStrek());
				System.out.println(ansatt.toStringMedProsjektTimer());
				System.out.print(lagStrek());
			}
			
		} while(valg != 0);
		
		
	}
	
	public void oppdaterAnsatt(Scanner inn, int ansattID) {
		
		
		Ansatt a = ansEAO.finnAnsattMedId(ansattID);
		System.out.println("Ansatt som skal oppdateres: ");
		System.out.println(a.toStringMedProsjektTimer());
		
		System.out.println("----------------------------");
		System.out.println("Angi hva som skal oppdateres:");
		System.out.println("1. Brukernavn");
		System.out.println("2. Fornavn");
		System.out.println("3. Etternavn");
		System.out.println("4. Ansettelsesdato");
		System.out.println("5. Stilling");
		System.out.println("6. Månedslønn");
		System.out.println("7. Bytt avdeling");
		System.out.println("8. Legg til prosjekt");
		System.out.println("9. Oppdater timer på prosjekt");
		System.out.println("----------------------------");
		System.out.println("0. Tilbake til hovedmeny");
		
		int valg = inn.nextInt();
		inn.nextLine();
		
		switch(valg) {
		case 1:
			System.out.println("Angi nytt brukernavn: ");
			a.setBrukernavn(inn.nextLine());
			break;
		case 2:
			System.out.println("Angi nytt fornavn: ");
			a.setFornavn(inn.nextLine());
			break;
		case 3:
			System.out.println("Angi nytt etternavn: ");
			a.setEtternavn(inn.nextLine());
			break;
		case 4:
			boolean ok = false;
			do {
				System.out.println("Skriv ansettelsesdato (YYYY-MM-DD): ");
				
				try {
					a.setAnsettelsesdato(LocalDate.parse(inn.nextLine()));
					ok = true;
				} catch(DateTimeParseException e) {
					System.out.println("Feil dato-format, Skriv YYYY-MM-DD, f. eks " + LocalDate.now());
				}
			} while(!ok);
			
			break;
		case 5:
			System.out.println("Angi ny stilling: ");
			a.setStilling(inn.nextLine());
			break;
		case 6:
			System.out.println("Angi ny månedslønn:");
			a.setMaanedsloenn(inn.nextInt());
			inn.nextInt();
			break;
		case 7:
			System.out.println("Tilgjengelige avdelinger: ");
			List<Avdeling> avdelinger = avdEAO.hentAlleAvdelinger();
			for(Avdeling avd: avdelinger) {
				System.out.println("Avdelingsid: " + avd.getAvdelingsID() + " - " + avd.getNavn());
			}
			Tekstgrensesnitt.lagStrek();
			
			Avdeling avdeling = null;
			do {
				System.out.println("Skriv avdelingsID til ny avdeling: ");
				//Query har med ORDER BY avdelingsID, så skal være sortert - kan derfor bruke ID som index
				try{
					avdeling = avdelinger.get(inn.nextInt()-1);
					inn.nextLine();
				} catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Ugyldig avdelingsID!");
				}
				
				
			} while(avdeling == null);
			
			
			
			//Fjern fra gamle avdeling
			Avdeling gammelAvd = a.getAnsattVed();
			gammelAvd.getAnsattListe().remove(a);
			avdEAO.oppdaterAvdeling(gammelAvd);
			
			//Må sette både at ansatt er ansatt ved avdeling, og legge til ansatt i avdelingens liste over ansatte
			a.setAnsattVed(avdeling);
			avdeling.leggTilAnsatt(a);
			avdEAO.oppdaterAvdeling(avdeling);
			break;
		case 8:
			System.out.println("Tilgjengelige prosjekter:");
			
			//Hent alle prosjekter, fjern prosjekter den ansatte allerede hører til
			List<Prosjekt> alleProsjekter = prosjektEAO.hentAlleProsjekt();
			for(Prosjektdeltakelse pd: a.getProsjektDeltakelser()) {
				if(alleProsjekter.contains(pd.getProsjektD_prosjekt())) {
					alleProsjekter.remove(pd.getProsjektD_prosjekt());
				}
			}
			for(Prosjekt prosjekt: alleProsjekter) {
				System.out.println(prosjekt);
				System.out.print(lagStrek());
			}
			
			System.out.println("Skriv prosjektid den ansatte skal legges til: ");
			Prosjekt nyttProsjekt = prosjektEAO.finnProsjektMedId(inn.nextInt());
			inn.nextLine();
			
			System.out.println("Skriv rolle den ansatte skal ha i prosjektet: ");
			
			Prosjektdeltakelse nyPD = new Prosjektdeltakelse(a, nyttProsjekt, inn.nextLine());
			pdEAO.leggTilPD(nyPD);
			a.getProsjektDeltakelser().add(nyPD);
			break;
			
			
			
			
			
		case 9:
			System.out.println("Prosjekter den ansatte arbeider med: ");
			System.out.println(a.prosjekterString());
			System.out.println("Skriv PD-ID som skal oppdateres: ");
			Prosjektdeltakelse pd = pdEAO.finnPDMedId(inn.nextInt());
			inn.nextLine();
			
			System.out.println("Oppdaterer: \n" + pd);
			System.out.println("Hvor mange timer har den ansatte jobbet?");
			pd.setAntallTimer(inn.nextInt());
			inn.nextLine();
			
			
			pdEAO.oppdaterPD(pd);

			//Hvis det gjøres oppdaterAnsatt(a) her forsvinner endringene for objektet - tar derfor return her
			//Måtte eventuelt ha fjernet gamle pd og lagt inn ny pd?
			System.out.println("Oppdaterte den ansatte:");
			System.out.println(ansEAO.finnAnsattMedId(a.getAnsattid()).toStringMedProsjektTimer());
			return;
			
		case 0:
			return;
		}
		
		ansEAO.oppdaterAnsatt(a);
		
		System.out.println("Oppdaterte den ansatte:");
		System.out.println(ansEAO.finnAnsattMedId(a.getAnsattid()).toStringMedProsjektTimer());
	}
	
	public void leggTilAnsatt(Scanner inn) {
		
		
		System.out.println("Legger til ny ansatt");
		System.out.println("Skriv brukernavn (4 tegn): ");
		
		String brukernavn = inn.nextLine();
		
		System.out.println("Skriv fornavn: ");
		String fornavn = inn.nextLine();
		
		System.out.println("Skriv etternavn: ");
		String etternavn = inn.nextLine();
		
		LocalDate ansDato = null;
		
		do {
			System.out.println("Skriv ansettelsesdato (YYYY-MM-DD): ");
			
			try {
				ansDato = LocalDate.parse(inn.nextLine());
			} catch(DateTimeParseException e) {
				System.out.println("Feil dato-format, Skriv YYYY-MM-DD, f. eks " + LocalDate.now());
			}
		} while(ansDato == null);
		
		System.out.println("Skriv stillingstittel: ");
		String stilling = inn.nextLine();
		
		System.out.println("Skriv månedslønn: ");
		int loenn = inn.nextInt();
		inn.nextLine();
		
		Avdeling avdeling = null;
		do {
			System.out.println("Skriv avdelingsID: ");
			avdeling = avdEAO.finnAvdelingMedId(inn.nextInt());
			inn.nextLine();
			if(avdeling == null) {
				System.out.println("Finner ikke avdeling!");
			}
		} while(avdeling == null);
		
		
		Ansatt nyAnsatt = new Ansatt(brukernavn, fornavn, etternavn, ansDato, stilling, loenn, avdeling);
		
		
		ansEAO.settInnAnsatt(nyAnsatt);
		
		//avdeling.getAnsatte().add(nyAnsatt);
		avdEAO.oppdaterAvdeling(avdeling);
		
	}
	
	public void visAlleAvdelinger(Scanner inn) {
		System.out.println("Viser alle avdelinger: ");
		List<Avdeling> avdelinger = avdEAO.hentAlleAvdelinger();
		ArrayList<Integer> gyldigeAvdelinger = new ArrayList<Integer>();
		for(Avdeling avd: avdelinger) {
			System.out.println(avd);
			System.out.print(lagStrek());
			gyldigeAvdelinger.add(avd.getAvdelingsID());
		}
		
		
		int valg = 0;
		int antAvdelinger = avdelinger.size();
		do {
			System.out.println("Skriv avdelingsID for å oppdatere en avdeling, eller 0 for å gå tilbake til meny");
			valg = inn.nextInt();
			inn.nextLine();
			
			
			if(valg == 0) {
				return;
			} else if(valg > antAvdelinger || valg <0) {
				System.out.println("Ugyldig avdelings-id!");
			} else  {
				oppdaterAvdeling(inn, valg);
			}
			
			
		} while(!gyldigeAvdelinger.contains(valg));
	}
	
	public void visEnAvdeling(Scanner inn) {
		System.out.println("Viser en avdeling");
		System.out.println("Skriv avdelingsID: ");
		int avdeling = inn.nextInt();
		inn.nextLine();
		
		System.out.println(avdEAO.finnAvdelingMedId(avdeling));
	}
	
	public void oppdaterAvdeling(Scanner inn, int avdID) {
		System.out.println("Oppdaterer avdeling");
		
		
		
		Avdeling avdeling = avdEAO.finnAvdelingMedId(avdID);
		
		
		System.out.println("Avdeling som skal oppdateres: " + avdeling);
		System.out.println("-------------------------------");
		System.out.println("Angi hva som skal oppdateres: ");
		System.out.println("1. Avdelingsnavn");
		System.out.println("2. Sjef for avdelingen");
		
		int valg = inn.nextInt();
		inn.nextLine();
		
		switch(valg) {
		case 1:
			System.out.println("Angi nytt avdelingsnavn");
			avdeling.setNavn(inn.nextLine());
			break;
		case 2:
			System.out.println("Skriv ansattID på ansatt som skal være ny sjef");
			Ansatt nySjef = ansEAO.finnAnsattMedId(inn.nextInt());
			inn.nextLine();
			
			if(nySjef.getSjefFor() != null) {
				System.out.println("Kan ikke flytte en avdelingssjef uten å først sette en ny sjef!");
				return;
			}
			
			
			//Flytt nySjef til rett avdeling hvis den er ansatt ved en annen
			if(!(nySjef.getAnsattVed().equals(avdeling))){
				Avdeling gammelAvdeling = nySjef.getAnsattVed();
				gammelAvdeling.getAnsattListe().remove(nySjef);
				avdEAO.oppdaterAvdeling(gammelAvdeling);
				nySjef.setAnsattVed(avdeling);
				avdeling.getAnsattListe().add(nySjef);
			}
				
			Ansatt gammelSjef = avdeling.getSjef();
			
			avdeling.setSjef(nySjef);
			avdEAO.oppdaterAvdeling(avdeling);
			
			//nySjef.setSjefFor(avdeling);
			nySjef.setStilling("Sjef A" + avdeling.getAvdelingsID());
			ansEAO.oppdaterAnsatt(nySjef);
			
			//Oppdater gammelSjef
			
			//gammelSjef.setSjefFor(null);
			gammelSjef.setStilling("Tidl. sjef");
			ansEAO.oppdaterAnsatt(gammelSjef);
			
			
			break;
			
		}
		//avdEAO.oppdaterAvdeling(avdeling);
		System.out.println("Avdeling etter oppdatering: ");
		System.out.println(avdeling);
		//Får ikke vist oppdatert avdeling med bare å bare printe avdeling her
		System.out.println(avdEAO.finnAvdelingMedId(avdeling.getAvdelingsID()));
	}
	
			
	
	
	public void visAlleProsjekter(Scanner inn) {
		System.out.println("Viser alle prosjekter: ");
		List<Prosjekt> prosjekter = prosjektEAO.hentAlleProsjekt();
		ArrayList<Integer> gyldigeProsjekt = new ArrayList<Integer>();
		
		System.out.print(lagStrek());
		for(Prosjekt prosjekt: prosjekter) {
			System.out.println(prosjekt);
			System.out.print(lagStrek());
			gyldigeProsjekt.add(prosjekt.getProsjektid());
		}
		
		int valg = 0;
		do {
			System.out.println("Skriv prosjektID for å oppdatere et prosjekt, eller 0 for å gå tilbake til meny");
			valg = inn.nextInt();
			inn.nextLine();
			
			
			if(valg == 0) {
				return;
			} else if(!gyldigeProsjekt.contains(valg)) {
				System.out.println("Ugyldig prosjektID!");
			} else  {
				oppdaterProsjekt(inn, valg);
			}
			
			
		} while(valg != 0);
	}
	
	public void leggTilProsjekt(Scanner inn) {
		
		System.out.println("Legger til prosjekt");
		System.out.println("Skriv prosjektnavn: ");
		String navn = inn.nextLine();
		
		System.out.println("Skriv prosjektbeskrivelse: ");
		String beskrivelse = inn.nextLine();
		
		Prosjekt prosjekt = new Prosjekt(navn, beskrivelse);
		
		prosjektEAO.settInnProsjekt(prosjekt);
		
	}
	
	public void oppdaterProsjekt(Scanner inn, int prosjektid) {
		System.out.println("Oppdaterer prosjekt");
		
		Prosjekt prosjekt = prosjektEAO.finnProsjektMedId(prosjektid);
		System.out.println("Prosjekt som skal oppdateres: ");
		System.out.println(prosjekt);
		
		int valg = 0;
		
		do{
			System.out.println("1. Oppdater prosjektnavn");
			System.out.println("2. Oppdater prosjektbeskrivelse");
			System.out.println("3. Legg til en ansatt til et prosjekt");
			System.out.println("4. Oppdater timer på prosjekt for en ansatt");
			System.out.println(lagStrek());
			System.out.println("0. Avbryt");
			
			valg = inn.nextInt();
			inn.nextLine();
			
			
			switch(valg) {
			case 1:
				System.out.println("Skriv nytt prosjeknavn: ");
				prosjekt.setProsjektnavn(inn.nextLine());
				break;
			case 2:
				System.out.println("Skriv ny prosjektbeskrivelse: ");
				prosjekt.setBeskrivelse(inn.nextLine());
				break;
			case 3:
				System.out.println("Skriv ansattid på ansatt som skal legges til: ");
				Ansatt ans = ansEAO.finnAnsattMedId(inn.nextInt());
				inn.nextLine();
				System.out.println("Skriv rolle den ansatte skal ha i prosjektet: ");
				Prosjektdeltakelse nyDeltakelse = new Prosjektdeltakelse(ans, prosjekt, inn.nextLine());
				pdEAO.leggTilPD(nyDeltakelse);
				break;
			case 4:
				System.out.println("Ansatte som arbeider på " + prosjekt.getProsjektnavn());
				System.out.println("\t" + Ansatt.lagTabellOverskrift());
				System.out.println(Tekstgrensesnitt.lagStrek());
				
				for(Prosjektdeltakelse pd: prosjekt.getAnsatte()) {
					
					System.out.println(pd.getDeltakelseid() + " \t" + pd.getProsjektD_ansatt() + " - " + pd.getAntallTimer() + " timer");
					
				}
				System.out.println(Tekstgrensesnitt.lagStrek());
				System.out.println("Skriv PDID som skal oppdateres: ");
				//Ansatt ansatt = ansEAO.finnAnsattMedId(inn.nextInt());
				Prosjektdeltakelse pdTilOppdatering = pdEAO.finnPDMedId(inn.nextInt());
				inn.nextLine();
				
				System.out.println("PD som skal oppdateres: " + pdTilOppdatering);
				
				System.out.println("Skriv hvor mange timer den ansatte har arbeidet med prosjektet: ");
				int antallTimer = inn.nextInt();
				inn.nextLine();
				
				pdTilOppdatering.setAntallTimer(antallTimer);
				pdEAO.oppdaterPD(pdTilOppdatering);
				
				System.out.println("Etter oppdatering: ");
				for(Prosjektdeltakelse pd: prosjekt.getAnsatte()) {
					
					System.out.println(pd.getProsjektD_ansatt() + " - " + pd.getAntallTimer() + " timer");
					
				}
			case 0:
				return;
				
			}
		} while(valg != 0);
		
		
		
		
		
		
		
	}
	
	public static String lagStrek() {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 150; i++) {
			sb.append("-");
		}
		sb.append("\n");
		return sb.toString();
	}
	
}
