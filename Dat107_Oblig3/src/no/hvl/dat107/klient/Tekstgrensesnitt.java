package no.hvl.dat107.klient;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import no.hvl.dat107.eao.AnsattEAO;
import no.hvl.dat107.eao.AvdelingEAO;
import no.hvl.dat107.eao.ProsjektEAO;
import no.hvl.dat107.eao.ProsjektdeltakelseEAO;
import no.hvl.dat107.entity.Ansatt;
import no.hvl.dat107.entity.Avdeling;
import no.hvl.dat107.entity.Prosjekt;
import no.hvl.dat107.entity.Prosjektdeltakelse;

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

	/**
	 * Viser liste over alle ansatte i en tabell
	 * 
	 * @param inn
	 *            Tastatur
	 */
	public void visAlleAnsatte(Scanner inn) {

		System.out.println("Viser alle ansatte: ");
		System.out.print(Ansatt.lagTabellOverskrift());

		System.out.print(lagStrek());
		List<Ansatt> ansattListe = ansEAO.hentAlleAnsatte();

		// Hold oversikt over gyldige ansattID, for å kunne kontrollere valg av ansatt
		// senere
		ArrayList<Integer> gyldigId = new ArrayList<Integer>();

		for (Ansatt a : ansattListe) {
			System.out.println(a);
			gyldigId.add(a.getAnsattid());
		}
		System.out.print(lagStrek());

		int valg = 0;
		do {
			System.out.println("Skriv ansattID for å oppdatere en ansatt, eller 0 for å gå tilbake til meny");
			valg = inn.nextInt();
			inn.nextLine();

			if (valg == 0) {
				return;
			} else if (!gyldigId.contains(valg) || valg < 0) {
				System.out.println("Ugyldig ansatt-id!");
			} else {
				oppdaterAnsatt(inn, valg);
			}

		} while (valg != 0 || valg < ansattListe.size());
	}

	/**
	 * Søker etter en ansatt, enten med ansattID eller med brukernavn
	 * 
	 * @param inn
	 *            Tastatur
	 */
	public void visEnAnsatt(Scanner inn) {
		System.out.println("\n\nSøker etter ansatt:");

		int valg = 0;
		Ansatt ansatt = null;
		String input = null;

		do {
			System.out.println();
			System.out.println(lagStrek());

			System.out.println("1. Søk med ansatt-ID");
			System.out.println("2. Søk med brukernavn");
			System.out.println("----------------------");
			System.out.println("0. Tilbake til meny");
			System.out.println("\n");

			valg = inn.nextInt();
			inn.nextLine();

			switch (valg) {
			case 1:
				System.out.println("Skriv ansattID som søkes etter: ");
				ansatt = ansEAO.finnAnsattMedId(inn.nextInt());
				break;
			case 2:
				System.out.println("Skriv brukernavn som søkes etter (4 tegn): ");
				ansatt = ansEAO.finnAnsattMedBrukernavn(inn.nextLine());
				break;

			case 0:
				System.out.println("Går tilbake til meny...");
				return;
			default:
				System.out.println("Ugyldig valg!\n\n");
			}

			if (ansatt == null) {
				System.out.println("Finner ikke ansatt!");
			} else {
				System.out.println("Fant ansatt!");
				System.out.print(lagStrek());
				System.out.println(ansatt.toStringMedProsjektTimer());
				System.out.print(lagStrek());
				System.out.println("Oppdatere ansatt? (j/n)");
				input = inn.next();
				inn.nextLine();
				if (input.equalsIgnoreCase("j")) {
					oppdaterAnsatt(inn, ansatt.getAnsattid());
				} else {
					System.out.println("Går tilbake til meny...\n");
				}
			}

		} while (ansatt == null);

	}

	/**
	 * Finner en ansatt med gitt ansattID, tilbyr valg av hvilken attributt som skal
	 * oppdateres, og oppdaterer både objekt og entitet.
	 * 
	 * @param inn
	 *            Tastatur
	 * @param ansattID
	 *            ansattID til ansatt som skal oppdateres
	 */
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
		System.out.println("10. Fjern ansatt fra prosjekt");
		System.out.println("11. Fjern ansatt");
		System.out.println("----------------------------");
		System.out.println("0. Tilbake til hovedmeny");

		int valg = inn.nextInt();
		inn.nextLine();

		switch (valg) {
		case 1:
			System.out.println("Angi nytt brukernavn: ");
			a.setBrukernavn(inn.nextLine());
			ansEAO.oppdaterAnsatt(a);
			break;
		case 2:
			System.out.println("Angi nytt fornavn: ");
			a.setFornavn(inn.nextLine());
			ansEAO.oppdaterAnsatt(a);
			break;
		case 3:
			System.out.println("Angi nytt etternavn: ");
			a.setEtternavn(inn.nextLine());
			ansEAO.oppdaterAnsatt(a);
			break;
		case 4:
			boolean ok = false;
			do {
				System.out.println("Skriv ansettelsesdato (YYYY-MM-DD): ");

				try {
					a.setAnsettelsesdato(LocalDate.parse(inn.nextLine()));
					ok = true;
				} catch (DateTimeParseException e) {
					System.out.println("Feil dato-format, Skriv YYYY-MM-DD, f. eks " + LocalDate.now());
				}
			} while (!ok);
			ansEAO.oppdaterAnsatt(a);
			break;
		case 5:
			System.out.println("Angi ny stilling: ");
			a.setStilling(inn.nextLine());
			ansEAO.oppdaterAnsatt(a);
			break;
		case 6:
			System.out.println("Angi ny månedslønn:");
			a.setMaanedsloenn(inn.nextInt());
			inn.nextLine();
			ansEAO.oppdaterAnsatt(a);
			break;
		case 7:
			System.out.println("Tilgjengelige avdelinger: ");
			List<Avdeling> avdelinger = avdEAO.hentAlleAvdelinger();
			for (Avdeling avd : avdelinger) {
				System.out.println("Avdelingsid: " + avd.getAvdelingsID() + " - " + avd.getNavn());
			}
			Tekstgrensesnitt.lagStrek();

			Avdeling avdeling = null;
			do {
				System.out.println("Skriv avdelingsID til ny avdeling: ");
				// Query har med ORDER BY avdelingsID, så skal være sortert - kan derfor bruke
				// ID som index
				try {
					avdeling = avdelinger.get(inn.nextInt() - 1);
					inn.nextLine();
				} catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Ugyldig avdelingsID!");
				}

			} while (avdeling == null);

			// Fjern fra gamle avdeling
			Avdeling gammelAvd = a.getAnsattVed();
			gammelAvd.getAnsattListe().remove(a);
			avdEAO.oppdaterAvdeling(gammelAvd);

			// Må sette både at ansatt er ansatt ved avdeling, og legge til ansatt i
			// avdelingens liste over ansatte
			a.setAnsattVed(avdeling);
			avdeling.leggTilAnsatt(a);
			avdEAO.oppdaterAvdeling(avdeling);
			ansEAO.oppdaterAnsatt(a);
			break;
		case 8:
			System.out.println("Tilgjengelige prosjekter:");

			// Hent alle prosjekter

			List<Prosjekt> alleProsjekter = prosjektEAO.hentAlleProsjekt();
			// (fjern prosjekter den ansatte allerede hører til - FUNGERER IKKE)
			/*
			 * for(Prosjektdeltakelse pd: a.getProsjektDeltakelser()) {
			 * if(alleProsjekter.contains(pd.getProsjekt())) {
			 * alleProsjekter.remove(pd.getProsjekt()); } }
			 */
			for (Prosjekt prosjekt : alleProsjekter) {
				System.out.println(prosjekt);
				System.out.print(lagStrek());
			}

			System.out.println("Skriv prosjektid den ansatte skal legges til: ");
			Prosjekt nyttProsjekt = prosjektEAO.finnProsjektMedId(inn.nextInt());
			inn.nextLine();

			System.out.println("Skriv rolle den ansatte skal ha i prosjektet: ");

			// Prosjektdeltakelse nyPD = new Prosjektdeltakelse(a, nyttProsjekt,
			// inn.nextLine(), 0);
			pdEAO.leggTilPD(nyttProsjekt, a, inn.nextLine());
			break;

		case 9:
			System.out.println("Prosjekter den ansatte arbeider med: ");
			System.out.println(a.prosjekterString());
			System.out.println("Skriv prosjektID til prosjektdeltakelse som skal oppdateres: ");
			Prosjekt prosjekt = prosjektEAO.finnProsjektMedId(inn.nextInt());
			Prosjektdeltakelse pd = pdEAO.finnPDMedAnsattOgProsjekt(a, prosjekt);
			inn.nextLine();

			System.out.println("Oppdaterer: \n" + pd);
			System.out.println("Hvor mange timer har den ansatte jobbet?");
			pd.setAntallTimer(inn.nextInt());
			inn.nextLine();

			pdEAO.oppdaterPD(pd);

			break;

		case 10:
			fjernProsjektdeltakelse(inn, a, null);
			break;
		case 11:
			fjernAnsatt(inn, a);
			return;

		case 0:
			return;
		default:
			System.out.println("Ugyldig valg! ");
			break;
		}

		// ansEAO.oppdaterAnsatt(a);

		System.out.println("Oppdaterte den ansatte:");
		System.out.println(ansEAO.finnAnsattMedId(a.getAnsattid()).toStringMedProsjektTimer());
	}

	/**
	 * Legger til en ansatt, tar inn opplysninger gjennom tastaturet
	 * 
	 * @param inn
	 *            Tastatur
	 */
	public void leggTilAnsatt(Scanner inn) {

		System.out.println("Legger til ny ansatt");

		String brukernavn = null;
		System.out.println("Skriv brukernavn (4 tegn): ");
		do {
			brukernavn = inn.nextLine();
			if (brukernavn.length() != 4) {
				System.out.println("Brukernavn må ha 4 tegn!");
			}
		} while (brukernavn.length() != 4);

		System.out.println("Skriv fornavn: ");
		String fornavn = inn.nextLine();

		System.out.println("Skriv etternavn: ");
		String etternavn = inn.nextLine();

		LocalDate ansDato = null;

		do {
			System.out.println("Skriv ansettelsesdato (YYYY-MM-DD): ");

			try {
				ansDato = LocalDate.parse(inn.nextLine());
			} catch (DateTimeParseException e) {
				System.out.println("Feil dato-format, Skriv YYYY-MM-DD, f. eks " + LocalDate.now());
			}
		} while (ansDato == null);

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
			if (avdeling == null) {
				System.out.println("Finner ikke avdeling!");
			}
		} while (avdeling == null);

		Ansatt nyAnsatt = new Ansatt(brukernavn, fornavn, etternavn, ansDato, stilling, loenn, avdeling);

		int nyId = ansEAO.settInnAnsatt(nyAnsatt);

		// avdeling.getAnsatte().add(nyAnsatt);
		// avdEAO.oppdaterAvdeling(avdeling);

		System.out.println(ansEAO.finnAnsattMedId(nyId).toStringMedProsjektTimer());

	}

	
	public void fjernAnsatt(Scanner inn, Ansatt ans) {
		//Ikke lov å slette ansatt hvis ansatt er sjef, eller hvis ansatt har registrert timer i et prosjekt
		if(ans.getSjefFor() != null) {
			System.out.println("Kan ikke slette ansatt som er sjef!");
		} else if(ans.totaleTimer() > 0) {
			System.out.println("Kan ikke slette ansatt som har registrerte timer i et prosjekt!");
		} else {
			System.out.println("Sletter ansatt...");
			ansEAO.fjernAnsatt(ans);
		}
	}
	

	/**
	 * Henter alle avdelinger i databasen og viser disse, inkludert ansatte, i
	 * tabeller
	 * 
	 * @param inn
	 *            Tastatur
	 */
	public void visAlleAvdelinger(Scanner inn) {
		System.out.println("Viser alle avdelinger: ");
		List<Avdeling> avdelinger = avdEAO.hentAlleAvdelinger();
		ArrayList<Integer> gyldigeAvdelinger = new ArrayList<Integer>();
		for (Avdeling avd : avdelinger) {
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

			if (valg == 0) {
				return;
			} else if (valg > antAvdelinger || valg < 0) {
				System.out.println("Ugyldig avdelings-id!");
			} else {
				oppdaterAvdeling(inn, valg);
			}

		} while (!gyldigeAvdelinger.contains(valg));
	}

	/**
	 * Søker etter en avdeling med avdelingsID, og viser denne i konsoll
	 * 
	 * @param inn
	 *            Tastatur
	 */
	public void visEnAvdeling(Scanner inn) {

		int avdelingsID = -1;
		String input = null;

		Avdeling avdeling = null;
		System.out.println("Viser en avdeling");
		do {
			System.out.println("Skriv avdelingsID, eller 0 for å avbryte søk: ");

			avdelingsID = inn.nextInt();
			inn.nextLine();
			if (avdelingsID == 0) {
				return;
			}

			avdeling = avdEAO.finnAvdelingMedId(avdelingsID);

			if (avdeling == null) {
				System.out.println("Finner ikke avdeling!");
			} else {
				System.out.println("Fant avdeling!");
				System.out.print(lagStrek());
				System.out.println(avdeling);
				System.out.print(lagStrek());
				System.out.println("Oppdatere avdeling? (j/n)");
				input = inn.next();
				inn.nextLine();
				if (input.equalsIgnoreCase("j")) {
					oppdaterAnsatt(inn, avdeling.getAvdelingsID());
				} else {
					System.out.println("Går tilbake til meny...\n");
					avdelingsID = 0;
				}
			}
		} while (avdelingsID != 0);

	}

	/**
	 * Finner en avdeling med gitt avdelingsID, og oppdaterer enten avdelingens
	 * navn, eller avdelingens sjef, gjennom tastaturet.
	 * 
	 * @param inn
	 *            Tastatur
	 * @param avdID
	 *            AvdelingsID til avdeling som skal oppdateres
	 */
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

		switch (valg) {
		case 1:
			System.out.println("Angi nytt avdelingsnavn");
			avdeling.setNavn(inn.nextLine());
			avdEAO.oppdaterAvdeling(avdeling);
			break;
		case 2:
			System.out.println("Skriv ansattID på ansatt som skal være ny sjef");
			Ansatt nySjef = ansEAO.finnAnsattMedId(inn.nextInt());
			inn.nextLine();

			if (nySjef.getSjefFor() != null) {
				System.out.println("Kan ikke flytte en avdelingssjef uten å først sette en ny sjef!");
				return;
			}

			// Flytt nySjef til rett avdeling hvis den er ansatt ved en annen
			if (!(nySjef.getAnsattVed().equals(avdeling))) {
				Avdeling gammelAvdeling = nySjef.getAnsattVed();
				gammelAvdeling.getAnsattListe().remove(nySjef);
				avdEAO.oppdaterAvdeling(gammelAvdeling);
				nySjef.setAnsattVed(avdeling);
				avdeling.getAnsattListe().add(nySjef);
			}

			avdEAO.oppdaterNySjef(avdeling, nySjef);

			break;

		}
		//
		System.out.println("Avdeling etter oppdatering: ");
		System.out.println(avdeling);
		// Får ikke vist oppdatert avdeling med bare å bare printe avdeling her
		System.out.println(avdEAO.finnAvdelingMedId(avdeling.getAvdelingsID()));

	}

	/**
	 * Viser alle prosjekter, inkludert ansatte og timetall
	 * 
	 * @param inn
	 *            Tastatur
	 */
	public void visAlleProsjekter(Scanner inn) {
		System.out.println("Viser alle prosjekter: ");
		List<Prosjekt> prosjekter = prosjektEAO.hentAlleProsjekt();
		ArrayList<Integer> gyldigeProsjekt = new ArrayList<Integer>();

		System.out.print(lagStrek());
		for (Prosjekt prosjekt : prosjekter) {
			System.out.println(prosjekt);
			System.out.print(lagStrek());
			gyldigeProsjekt.add(prosjekt.getProsjektid());
		}

		int valg = 0;
		do {
			System.out.println("Skriv prosjektID for å oppdatere et prosjekt, eller 0 for å gå tilbake til meny");
			valg = inn.nextInt();
			inn.nextLine();

			if (valg == 0) {
				return;
			} else if (!gyldigeProsjekt.contains(valg)) {
				System.out.println("Ugyldig prosjektID!");
			} else {
				oppdaterProsjekt(inn, valg);
			}

		} while (valg != 0);
	}

	/**
	 * Legger til nytt prosjekt i databasen. Tar inn opplysninger gjennom tastatur
	 * 
	 * @param inn
	 *            Tastatur
	 */
	public void leggTilProsjekt(Scanner inn) {

		System.out.println("Legger til prosjekt");
		System.out.println("Skriv prosjektnavn: ");
		String navn = inn.nextLine();

		System.out.println("Skriv prosjektbeskrivelse: ");
		String beskrivelse = inn.nextLine();

		Prosjekt prosjekt = new Prosjekt(navn, beskrivelse);

		prosjektEAO.settInnProsjekt(prosjekt);

	}

	/**
	 * Oppdaterer prosjekt-attributt, som bestemmes gjennom tastatur
	 * 
	 * @param inn
	 *            Tastatur
	 * @param prosjektid
	 *            ProsjektID til prosjekt som skal oppdateres
	 */
	public void oppdaterProsjekt(Scanner inn, int prosjektid) {
		System.out.println("Oppdaterer prosjekt");

		Prosjekt prosjekt = prosjektEAO.finnProsjektMedId(prosjektid);
		System.out.println("Prosjekt som skal oppdateres: \n");
		System.out.println(prosjekt + "\n");

		int valg = 0;

		do {
			System.out.println("1. Oppdater prosjektnavn");
			System.out.println("2. Oppdater prosjektbeskrivelse");
			System.out.println("3. Legg til en ansatt til et prosjekt");
			System.out.println("4. Oppdater timer på prosjekt for en ansatt");
			System.out.println("5. Fjern en ansatt");
			System.out.println(lagStrek());
			System.out.println("0. Avbryt");

			valg = inn.nextInt();
			inn.nextLine();

			switch (valg) {
			case 1:
				// Oppdatere navn
				System.out.println("Skriv nytt prosjektnavn: ");
				prosjekt.setProsjektnavn(inn.nextLine());
				prosjektEAO.oppdaterProsjekt(prosjekt);
				break;
			case 2:
				// Oppdatere beskrivelse
				System.out.println("Skriv ny prosjektbeskrivelse: ");
				prosjekt.setBeskrivelse(inn.nextLine());
				prosjektEAO.oppdaterProsjekt(prosjekt);
				break;
			case 3:
				// Legg til ansatt
				System.out.println("Skriv ansattid på ansatt som skal legges til: ");
				Ansatt ans = ansEAO.finnAnsattMedId(inn.nextInt());
				inn.nextLine();
				System.out.println("Skriv rolle den ansatte skal ha i prosjektet: ");

				pdEAO.leggTilPD(prosjekt, ans, inn.nextLine());
				break;
			case 4:
				// Oppdatere timetall
				System.out.println("Ansatte som arbeider på " + prosjekt.getProsjektnavn());

				System.out.println(Ansatt.lagTabellOverskrift());
				System.out.println(Tekstgrensesnitt.lagStrek());

				for (Prosjektdeltakelse pd : prosjekt.getAnsatte()) {

					System.out.println(pd.getAnsatt() + " - " + pd.getAntallTimer() + " timer");

				}
				System.out.println(Tekstgrensesnitt.lagStrek());

				prosjekt.visAnsatte();

				System.out.println("Skriv ansattID til ansatt som skal oppdateres: ");
				Ansatt ansatt = ansEAO.finnAnsattMedId(inn.nextInt());
				Prosjektdeltakelse pdTilOppdatering = pdEAO.finnPDMedAnsattOgProsjekt(ansatt, prosjekt);
				inn.nextLine();

				System.out.println("Prosjekt som skal oppdateres: " + pdTilOppdatering);

				System.out.println("Skriv hvor mange timer den ansatte har arbeidet med prosjektet: ");
				int antallTimer = inn.nextInt();
				inn.nextLine();

				pdTilOppdatering.setAntallTimer(antallTimer);
				pdEAO.oppdaterPD(pdTilOppdatering);

				break;
			case 5:
				fjernProsjektdeltakelse(inn, null, prosjekt);
				break;
			case 0:
				return;

			}
			System.out.println("Prosjekt etter oppdatering:");
			System.out.println(lagStrek());
			System.out.println(prosjektEAO.finnProsjektMedId(prosjekt.getProsjektid()));

		} while (valg != 0);


		} while(valg != 0);
	}
	
	public void fjernProsjektdeltakelse(Scanner inn, Ansatt ans, Prosjekt prosjekt) {
		if(prosjekt == null) {
			System.out.println("Prosjekter den ansatte er registrert ved: ");
			System.out.println(ans.prosjekterString());
			System.out.println("\nAngi prosjekt den ansatte skal fjernes fra:");
			prosjekt = prosjektEAO.finnProsjektMedId(inn.nextInt());
			inn.nextLine();
		} else if(ans == null) {
			System.out.println("Ansatte som arbeider på prosjektet:");
			prosjekt.visAnsatte();
			System.out.println("\nAngi ansatt som skal fjernes fra prosjektet:");
			ans = ansEAO.finnAnsattMedId(inn.nextInt());
			inn.nextLine();
		}
		
		System.out.println("Fjerner prosjektdeltakelse...\n");
		pdEAO.fjernProsjektdeltakelse(ans, prosjekt);
		
		//Viser oppdatert prosjekt/ansatt i kallende metode, ikke nødvendig å vise her
		//System.out.println("Prosjekt etter fjerning:\n" + prosjektEAO.finnProsjektMedId(prosjekt.getProsjektid()));
		//System.out.println("\nDen ansatte etter fjerning:\n" + ansEAO.finnAnsattMedId(ans.getAnsattid()));
	
		

	}

	/**
	 * Gir en String med 150 "-"
	 * 
	 * @return Skillestrek
	 */
	public static String lagStrek() {
		// Kunne vært public void som printet en strek - men bruker i opprettelse og
		// formatering av tabeller,
		// derfor nyttig med String
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 150; i++) {
			sb.append("-");
		}
		sb.append("\n");
		return sb.toString();
	}

}
