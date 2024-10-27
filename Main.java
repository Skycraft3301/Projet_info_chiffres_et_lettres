// Projet informatique
// des chiffres et des lettres

import java.util.Objects;

public class Main {
public static void main (String[] args) {

int scoreJoueurA = 0, scoreJoueurB = 0 ;

String nomJoueurA, nomJoueurB ;


String joueurVoyelles ;


System.out.println("Joueur A, donnez votre nom :") ;
nomJoueurA = Lire.S() ;

System.out.println("Joueur B, donnez votre nom :") ;
nomJoueurB = Lire.S() ;

joueurVoyelles = nomJoueurA;

for (int i=1 ; i<=10 ; i++) {
    ModeLettres.modeLettres(joueurVoyelles, nomJoueurA, nomJoueurB, scoreJoueurA, scoreJoueurB) ;
    ModeChiffres.modeChiffres() ;

    // Pour changer à chaque tour le joueur qui choisit le nombre de voyelles
    if (Objects.equals(joueurVoyelles, nomJoueurA)){
        joueurVoyelles = nomJoueurB;
    }else {
        joueurVoyelles = nomJoueurA;
    }
}



}
}
