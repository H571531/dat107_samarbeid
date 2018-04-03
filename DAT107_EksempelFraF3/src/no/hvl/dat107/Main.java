package no.hvl.dat107;

public class Main {

    public static void main(String[] args) {
        
        //Opprette hjelpeobjekt for databasetilgang
    	VitnemalEAO eao = new VitnemalEAO();
        
        //Hent ut og skriv ut ???(en-siden) med PK = ???
    	Vitnemal v1 = eao.finnVitnemalPaaPK(123456);
    	System.out.println(v1);
        
        //Hent ut og skriv ut ???(mange-siden) med unik egenskap (som ikke er PK) = ???

        //Registrer eller oppdater en entitet p� mange-siden
        
        //For � sjekke at registrering er gjort:
        //Hent ut og skriv ut ???(en-siden) med PK = ???
    	
    }

}
