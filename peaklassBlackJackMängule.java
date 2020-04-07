import java.util.Scanner;

// Seda peaklassi ma koodiplokiti detailselt ei kommenteeri, sest kasutajale antavad teated ja valikud on ise piisavalt seotud koodiploki sisu avavad.
// Kommentaarid paaris detailsemas mängu loogikat avavas kohas.
public class peaklassBlackJackMängule {

    // Mängu käivitav peameetod
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Mängu seaded - pakkide arv, panus ja pakkide kokkutõstmine ning segamine
        System.out.print("Tere! Mitme kaardipakiga Sa tahad BlackJacki mängida?");
        Integer kaardipakke = Integer.parseInt(scanner.next());
        System.out.print("Tõstan kaardipakid kokku ja segan neid");

        laualOlevadKaardid mängusOlevadkaardid = new laualOlevadKaardid(kaardipakke);
        mängusOlevadkaardid.segaLaualOlevadKaardid();

        System.out.print("Hakkame mängima! ");
        boolean tahanJätkata = true;

        // Kuni pakis on veel võtmata kaarte enam kui 10 ja kuni peale iga kätt mängija indikeerib, et ta tahab uut kätt mängida
        // jätkatakse diiler-tavamängija paaris mängu. Arvutikasutaja kehastab diileri vastu mängivat tavamängijat.
        while(tahanJätkata) {
            if(mängusOlevadkaardid.kaarteJärg() < 10) {
                System.out.print("Laual on kaardid otsas. Mäng läbi! ");
                tahanJätkata = false;
                continue;
            }

            System.out.print("Tee panus! ");
            double panus = Double.parseDouble(scanner.next());

            diileriKäsi diileriKaardidKäes = new diileriKäsi(mängusOlevadkaardid, panus);
            System.out.print(diileriKaardidKäes.näitaAlgulKätt());

            System.out.print("Nii ja nüüd Sinu kaardid ... ");

            tavalineKäsi sinuKäsi = new tavalineKäsi(mängusOlevadkaardid, panus);
            System.out.println(sinuKäsi.näitaKätt());

            if(sinuKäsi.getblackJack()) {
                System.out.print("Kas Sa tahad jätkata! Kirjuta arv \"1\" kui tahad? ");
                Integer jätkamine = Integer.parseInt(scanner.next());
                if(jätkamine != 1){
                    tahanJätkata = false;
                }
                continue;
            }

            while(sinuKäsi.iskäsiOnAktiivne()) {
                System.out.print("Tahad sa kaarti juurde ..... Kirjuta arv \"1\" kui soovid.");
                Integer küsibKaarti2 = Integer.parseInt(scanner.next());

                if(küsibKaarti2 == 1) {
                    sinuKäsi.küsiÜksKaartJuurde(mängusOlevadkaardid);
                    System.out.println(sinuKäsi.näitaKätt());
                }

                else {
                    sinuKäsi.pööraKaardidMaha();
                    System.out.println("Pöörasin kaardid maha. Vaatame nüüd diileri kätt.");
                    System.out.println(diileriKaardidKäes.näitaKätt());
                    // Diiler peab võtma kaarte peale oma eisalgse 2 kaardise käe avamise kuni nende väärtus on enam kuni 16
                    // Kui kahe esimese kaardi väärtus on enam kui 16, siis diiler kaarte juurde ei võta.
                    if(diileriKaardidKäes.arvutaKäeVäärtus() > 16) {
                        diileriKaardidKäes.setKäsiOnAktiivne(false);
                        System.out.println("Diileri käe väärtus on enam kui 16 ehk " + diileriKaardidKäes.arvutaKäeVäärtus());
                        System.out.println("Diiler ei pea seegi kaarte juurde küsima. Vaatame kuidas mäng lõppes.");
                        if(sinuKäsi.compareTo(diileriKaardidKäes) == 1) {
                            System.out.println("Diiler võitis. Sa kaotasid " + sinuKäsi.getPanus());
                        }
                        else if(sinuKäsi.compareTo(diileriKaardidKäes) == -1) {
                            System.out.println("Diiler kaotas. Sa võitsid " + sinuKäsi.getPanus());
                        }
                        else if (sinuKäsi.compareTo(diileriKaardidKäes) == 0) {
                            System.out.println("Jäite viiki! Saad oma raha tagasi.");
                        }
                    }
                    else if (diileriKaardidKäes.arvutaKäeVäärtus() <= 16) {
                        while(diileriKaardidKäes.arvutaKäeVäärtus() <= 16) {
                            System.out.println("Diileri käe väärtus on vähem kui 16 ehk " + diileriKaardidKäes.arvutaKäeVäärtus());
                            System.out.println("Diiler  peab seegi kaarte juurde küsima.");
                            diileriKaardidKäes.küsiÜksKaartJuurde(mängusOlevadkaardid);
                            System.out.println("Diileri käsi peale kaardi juurde küsimist : ");
                            System.out.println(diileriKaardidKäes.näitaKätt());
                            if(diileriKaardidKäes.arvutaKäeVäärtus() > 16) {
                                diileriKaardidKäes.setKäsiOnAktiivne(false);
                                System.out.println("Diileri käe väärtus on nüüd üle 16 ja ta ei pea kaarte enam juurde võtma. Vaatame, mis väärtuse diilerikaardid kokku annavad ");
                                System.out.println("Diileri käe väärtus on : " + diileriKaardidKäes.arvutaKäeVäärtus());
                                if(sinuKäsi.compareTo(diileriKaardidKäes) == 1) {
                                    System.out.println("Diiler võitis. Sa kaotasid " + sinuKäsi.getPanus());
                                }
                                else if(sinuKäsi.compareTo(diileriKaardidKäes) == -1) {
                                    System.out.println("Diiler kaotas. Sa võitsid " + sinuKäsi.getPanus());
                                }
                                else if (sinuKäsi.compareTo(diileriKaardidKäes) == 0) {
                                    System.out.println("Jäite viiki! Saad oma raha tagasi.");
                                }
                            }
                        }

                    }
                }
            }

            System.out.print("Kas Sa tahad jätkata! Kirjuta arv \"1\" kui tahad?");
            Integer jätkamine = Integer.parseInt(scanner.next());
            if(jätkamine != 1){
                tahanJätkata = false;
            }
        }

    }
}


