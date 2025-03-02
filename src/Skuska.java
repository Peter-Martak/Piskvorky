import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Skuska {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String hrac1 = "Hrac-X";
        String hrac2 = "Hrac-O";
        int[] miestoVHre = new int[2];
        boolean vitaz = false;
        char hraciZnak = ' ';

        int hrac1PocetTahov = 0;
        int hrac2PocetTahov = 0;

        char[][] hraciaPlocha = new char[3][3];


        System.out.println("Hra začína");
        naplnAVypisPole(hraciaPlocha);

        while (!vitaz) {
            // určím ktorý hráč je na ťahu
            String hracKtoryJeNaTahu = hracNaTahu(hrac1, hrac2, hrac1PocetTahov, hrac2PocetTahov);
            int zadaneCislo;
            try {
                if (hracKtoryJeNaTahu.equals(hrac1)) {
                    System.out.println("Na ťahu je 1.hráč " + hracKtoryJeNaTahu +
                            " prosím zadaj číslo od 1 - 9");

                    // zadavam číslo na najdenie miesta pre znak na hracom poli
                    zadaneCislo = scanner.nextInt();
                    hraciZnak = 'x';
                    while (zadaneCislo < 1 || zadaneCislo > 9) {
                        System.out.println("musíš zadať číslo od 1 - 9");
                        zadaneCislo = scanner.nextInt();
                    }

                    //vlozCisloDoPola(hraciaPlocha, zadaneCislo, hraciZnak, scanner);

                    miestoVHre = vyberMiestoVPoli(zadaneCislo);
                    if (hraciaPlocha[miestoVHre[0]][miestoVHre[1]] == 'x'
                            || hraciaPlocha[miestoVHre[0]][miestoVHre[1]] == 'o'){
                        System.out.println("toto miesto na hracej ploche je obsadené");
                        System.out.println("zadaj iné číslo od 1-9");
                        continue;
                    }else{
                        hraciaPlocha[miestoVHre[0]][miestoVHre[1]] = 'x';
                        System.out.println("Poceť ťahov 1.hráča: " + ++hrac1PocetTahov);
                        vykresliPolePoVlozeniZnaku(hraciaPlocha);
                    }

                } else {
                    System.out.println("Na ťahu je 2.hráč " + hracKtoryJeNaTahu +
                            " prosím zadaj číslo od 1 - 9");

                    // zadavam číslo na najdenie miesta pre znak na hracom poli
                    zadaneCislo = scanner.nextInt();
                    hraciZnak = 'O';
                    while (zadaneCislo < 1 || zadaneCislo > 9) {
                        System.out.println("musíš zadať číslo od 1 - 9");
                        zadaneCislo = scanner.nextInt();
                    }

                    //vlozCisloDoPola(hraciaPlocha, zadaneCislo, hraciZnak, scanner);
                    miestoVHre = vyberMiestoVPoli(zadaneCislo);
                    if (hraciaPlocha[miestoVHre[0]][miestoVHre[1]] == 'x'
                            || hraciaPlocha[miestoVHre[0]][miestoVHre[1]] == 'o'){
                        System.out.println("toto miesto na hracej ploche je obsadené");
                        System.out.println("zadaj iné číslo od 1-9");
                        continue;
                    }else {
                        hraciaPlocha[miestoVHre[0]][miestoVHre[1]] = '0';
                    }
                    System.out.println("Poceť ťahov 1.hráča: " + ++hrac2PocetTahov);
                    vykresliPolePoVlozeniZnaku(hraciaPlocha);
                }

                //overenie vitaza v riadku
                for (char[] znak : hraciaPlocha) {
                    if (znak[0] == hraciZnak && znak[1] == hraciZnak && znak[2] == hraciZnak) {
                        System.out.println("Vyhral hráč: " + hracKtoryJeNaTahu);
                        vitaz = true;
                    }
                }
                //overenie vitaza v stlpci
                for (int i = 0; i < hraciaPlocha.length; i++) {
                    if (hraciaPlocha[0][i] == hraciZnak && hraciaPlocha[1][i] == hraciZnak && hraciaPlocha[2][i] == hraciZnak) {
                        System.out.println("Vyhral hráč: " + hracKtoryJeNaTahu);
                        vitaz = true;
                    }
                }

                //overenie vitaza diagonalne
                if (hraciaPlocha[0][0] == hraciZnak && hraciaPlocha[1][1] == hraciZnak && hraciaPlocha[2][2] == hraciZnak
                        || hraciaPlocha[2][0] == hraciZnak && hraciaPlocha[1][1] == hraciZnak && hraciaPlocha[0][2] == hraciZnak) {
                    System.out.println("Vyhral hráč: " + hracKtoryJeNaTahu);
                    vitaz = true;
                }
                //overenie remizy
                if (overRemizu(hraciaPlocha)) {
                    System.out.println("Hra skončíla remízou");
                    break;
                }


            } catch (InputMismatchException e) {
                System.out.println("Musíš zadať číslo od 1-9");
                scanner.nextLine();
            }
        }
    }

    private static boolean overRemizu(char[][] pole) {
        boolean remiza = false;
        for (int i = 0; i < pole.length; i++) {
            for (int j = 0; j < pole.length; j++) {
                if (pole[i][j] == ' ') {
                    remiza = false;
                    return remiza;
                }else {
                    remiza = true;
                }
            }
        }
        return remiza;
    }

    private static void vykresliPolePoVlozeniZnaku(char[][] hraciaPlocha) {
        for (int i = 0; i < hraciaPlocha.length; i++){
            for (int j = 0; j < hraciaPlocha.length; j++) {
                if (j == 2) {
                    System.out.print(hraciaPlocha[i][j]);
                } else {
                    System.out.print(hraciaPlocha[i][j] + "|");
                }
            }
            System.out.println();
            System.out.println("------");
        }
    }

    private static int[] vyberMiestoVPoli(int cislo){
        int[] miestoVPoli = new int[2];
        switch (cislo){
            case 1 -> miestoVPoli =new int[]{0,0};
            case 2 -> miestoVPoli= new int[]{0,1};
            case 3 -> miestoVPoli= new int[]{0,2};
            case 4 -> miestoVPoli= new int[]{1,0};
            case 5 -> miestoVPoli= new int[]{1,1};
            case 6 -> miestoVPoli= new int[]{1,2};
            case 7 -> miestoVPoli= new int[]{2,0};
            case 8 -> miestoVPoli= new int[]{2,1};
            case 9 -> miestoVPoli= new int[]{2,2};

        }
        return miestoVPoli;
    }

    private static void vlozCisloDoPola(char[][] pole, int cislo,char znak, Scanner scanner) {

        switch (cislo){
            case 1 -> {
                if(pole[0][0] == 'x' || pole[0][0] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[0][0] = znak;
                }
            }
            case 2 -> {
                if(pole[0][1] == 'x' || pole[0][1] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[0][1] = znak;
                }
            }
            case 3 -> {
                if(pole[0][2] == 'x' || pole[0][2] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[0][2] = znak;
                }
            }
            case 4 -> {
                if(pole[1][0] == 'x' || pole[1][0] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[1][0] = znak;
                }
            }
            case 5 -> {
                if(pole[1][1] == 'x' || pole[1][1] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[1][1] = znak;
                }
            }
            case 6 -> {
                if(pole[1][2] == 'x' || pole[1][2] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[1][2] = znak;
                }
            }
            case 7 -> {
                if(pole[2][0] == 'x' || pole[2][0] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[2][0] = znak;
                }
            }
            case 8 -> {
                if(pole[2][1] == 'x' || pole[2][1] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[2][1] = znak;
                }
            }
            case 9 -> {
                if(pole[2][2] == 'x' || pole[2][2] == 'O'){
                    System.out.println("toto miesto na hracej ploche je obsadené");
                    System.out.println("zadaj iné číslo od 1-9");
                    cislo = scanner.nextInt();
                    vlozCisloDoPola(pole,cislo,znak,scanner);
                }else{
                    pole[2][2] = znak;
                }
            }
        }
    }
    private static String hracNaTahu(String hrac1, String hrac2, int pocetTahovHrac1, int pocetTahovHrac2) {
        if(pocetTahovHrac2 < pocetTahovHrac1){
            return hrac2;
        }else {
            return hrac1;
        }
    }

    private static void naplnAVypisPole(char[][] pole) {
        for (int i = 0; i < pole.length; i++){
            for (int j = 0; j < pole.length; j++) {
                if (j == 2) {
                    pole[i][j] = ' ';
                    System.out.print(pole[i][j]);
                } else {
                    pole[i][j] = ' ';
                    System.out.print(pole[i][j] + "|");
                }
            }
            System.out.println();
            System.out.println("------");
        }
    }
}

