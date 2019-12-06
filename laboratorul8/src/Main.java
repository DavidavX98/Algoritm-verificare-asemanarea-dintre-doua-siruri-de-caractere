import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        char[] A = {'D', 'C', 'A', 'B', 'C', 'D', 'A', 'D', 'A', 'C'};
        char[] B = {'A', 'C', 'C', 'B', 'A', 'D', 'A', 'A'};
        Instant start0 = Instant.now();
        algoritmulMeu(A, B);
        Instant end0 = Instant.now();

        Instant start1 = Instant.now();
        algoritmCuMatrice(A, B);
        Instant end1 = Instant.now();

        Duration timeElapsed0 = Duration.between(start0,end0);
        Duration timeElapsed1 = Duration.between(start1,end1);

        System.out.println("\nTimp de executie pentru algoritmulMeu: "+ (float) timeElapsed0.toNanos()/1000000 + " milisecunde");
        System.out.println("Timp de executie pentru algoritmCuMatrice: "+ (float) timeElapsed1.toNanos()/1000000 + " milisecunde");

        System.out.println("Algoritmul meu a ocupat de " + (float) timeElapsed0.toNanos()/timeElapsed1.toNanos() +" ori mai mult timp decat algoritmul cu matrice.");
    }

    public static void algoritmulMeu(char[] A, char[] B) {
        ArrayList<CaracterComun> listaCaractereComune = new ArrayList<>(); //declaram o lista de obiecte in care o sa o sa adugam fiecare caracter comun si indexii sai din A si din B

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                if (A[i] == B[j]) {                                             //verificam egalitatea dintre fieacre caracter din A si fiecare caracter din B
                    listaCaractereComune.add(new CaracterComun(A[i], i, j));    // caracterele comune si indexii le adaugam neordonat in lista
                }
            }
        }

        int nrMaxCaractereCom = 0;                                            //aici vom memora numarul maxin de caractere ordonat-comune
        int nrCaractereCom = 0;                                               //aici vom memora numarul de caractere ordonat-comune pentru fiecare iteratie
        ArrayList<Character> listaCharPtNrMax = new ArrayList<>();          //declaram o lista in care vom adauga secventa FINALA de caractere comune ordonate pentru care sa gasit numarul maxim

        for (CaracterComun c : listaCaractereComune) {                      //parcurgem lista de obiecte in care avem toate caracterele neordonate comune si indexii lor
            ArrayList<Character> listaChar = new ArrayList<>();            //declaram o lista in care vom adauga secvente PROVIZORII de caractere ordonate comune pentru fiecare iteratie
            nrCaractereCom = 1;                                            //avem deja un caracter citit in FOR
            listaChar.add(c.character);                                     //adaugam acel caracter in lista provizorie
            CaracterComun ultimulCaracter = c;                              //memoram ultimul caracter comun si indexii sai intr-un obiect

            for (CaracterComun c1 : listaCaractereComune) {                                             //parcurgem din nou lista cu toate caracterele comune citind pe rand fiecare caracter
                if (c1.indexA > ultimulCaracter.indexA && c1.indexB > ultimulCaracter.indexB) {         //verificam daca indexii noului caracter sunt mai mari decat ai ultimului caracter
                    ultimulCaracter = c1;                                                               //daca sunt mai mari noul caracter va deveni ultimul caracter
                    listaChar.add(c1.character);                                                        //il adaugam in lista provizorie cu caractere comune ordonate
                    nrCaractereCom++;                                                                   //si il punem si pe el la numaratoare pt secventa curenta
                }
            }
            if (nrCaractereCom > nrMaxCaractereCom) {           //verificam daca numarul de caractere pentru secventa curenta este mai mare decat numarul maxim gasit pana acum
                nrMaxCaractereCom = nrCaractereCom;             //daca este mai mare atunci el va deveni maxim
                listaCharPtNrMax = listaChar;                   //iar lista de caractere o memoram in lista finala
            }
        }
        System.out.println("Algoritmul meu: Nr maxim de caractere comune este " + nrMaxCaractereCom + ", una dintre combinatii este:" + listaCharPtNrMax);//afisam rezulatele
    }

    public static void algoritmCuMatrice(char[] A,char[] B){
        ElementMatrice[][] matrice = new ElementMatrice[A.length+1][B.length+1];      //declaram o matrice de obiecte ElementMatrice(valoare, directie)
        int valoareMax = 0;                       //aici v-a fi memorat numarul maxim de obiecte comune ordonate/valoarea maxima din matrice
        int i_valoreMax = 0;                      //aici vor fi memorati indexii pentru valoarea maxima
        int j_valoareMax = 0;
        for(int i=0; i<A.length+1;i++){                             //vom verifica egalitatea dintre fieacre caracter din A si fiecare caracter din B
            for (int j=0; j<B.length+1;j++) {                       //vom face mai tarziu o analogie intre indexii vectorilor initiali si indexii matricei
                if(i==0 || j==0)                                    //initializam prima linie si prima coloana cu oboecte, acestea au implicit variabila vaoare=0
                    matrice[i][j] = new ElementMatrice();
                else if(A[i-1] == B[j-1]){                          //aici verificam egalitatea dintre fieacre caracter din A si fiecare caracter din B
                    matrice[i][j] = new ElementMatrice(matrice[i-1][j-1].valoare + 1,2); //in matrice memoram valoarea de pe deagonala +1(directie 2 inseamna DIAGONALA)
                }
                else if (matrice[i][j-1].valoare > matrice[i-1][j].valoare)     //cand caracterul curent din A diferit de cel din B, in matrice memoram cea mai mare valoare din stanga sau de deasupra pozitiei curente din matrice
                    matrice[i][j] = new ElementMatrice(matrice[i][j-1].valoare,1);     // directie 1 inseamna STANGA
                else
                    matrice[i][j] = new ElementMatrice(matrice[i-1][j].valoare,3);      //directie 3 inseamna DEASUPRA

                if(matrice[i][j].valoare > valoareMax){             //verificam daca valoare din pozitia curenta este mai mare decat valoarea maxima
                    valoareMax = matrice[i][j].valoare;             //dava este mai mare, atunci ea v-a deveni valoarea maxima
                    i_valoreMax = i;                                //memoram indexii pentru pozitia cu valoarea maxima
                    j_valoareMax = j;
                }
            }
        }
        ArrayList<Character> listaCaractereIdentice =new ArrayList<>();    //decalaram ao lista in care vom memora secventa de caractere comune ordonate in sens invers
        int i = i_valoreMax;                                                //plecam direct de la pozitia in care am gasit valoare maxima
        int j = j_valoareMax;
        while (i>0 && j>0){                                                  //verificam directia pentru pozitia curenta din matrice
            if(matrice[i][j].directie == 2){                               //daca directia este 2 atunci mergem pe diagonala decrementand ambii indexi
                listaCaractereIdentice.add(A[i-1]);                     //ne intereseaza pozitiile care au directia diagonama asa ca memoram caracterele corespunzatoare in lista
                i--;
                j--;
            }
            else if (matrice[i][j].directie == 1)                       //daca directia este 1 mergem in stanga decrementand indexul coloanei
                j--;
            else                                       //daca directia nu este 1 sau 2 inseamna ca este 3,de aceea mergem in sus decrementand indexul liniei
                i--;
        }
        Collections.reverse(listaCaractereIdentice);            //inversam ordinea caracterelor din lista gasita pentru a fi in ordinea crescatoare
        System.out.println("Algoritm cu matrice: Nr maxim de caractere comune este " + valoareMax + ", una dintre combinatii este:" + listaCaractereIdentice); //afisam rezultatele
    }
}
