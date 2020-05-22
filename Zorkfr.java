package zork;
 
//  zorkfr.java
//  zorkfr
//  Created by Marc on 04/03/06.
//  Copyright (c) 2006 __MyCompanyName__. All rights reserved.
//
/**
 * Classe racine du jeu "Zork".
 * <p>Cette classe constitut le point d'entree du programme,
 * elle possede la methode main qui est appelee lors du lancement du programme.</p>
 * <p>pour jouer a cejeu, cette classe cree une instance de la classe 
 * Jeu (la classe principale du jeu) et appelle la methode jouer().</p>
 */
public class Zorkfr {
    public static void main(String args[]) {
		Jeu leJeu;
		leJeu = new Jeu();
		leJeu.jouer();
    }
}
