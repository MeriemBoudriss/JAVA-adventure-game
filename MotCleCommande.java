package zork;
 
 
/**
 *  <p> Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte.</p> <p>
 *
 *  Classe repertoriant l'ensemble des mots-cle utilisables comme commande dans
 *  le jeu. Elle est utilisée pour vérifier la validite des commandes de
 *  l'utilisateur.
 *
 * @author     Michael Kolling
 * @author     Marc Champesme (pour la traduction francaise)
 * @version    1.0
 * @since      July 1999
 */

public class MotCleCommande {
	/**
	 *  Un tableau constant contenant tous les mots-clé valides comme commandes.
	 */
	private final static String commandesValides[] = {"aller","nbObjets","adopter","prendreObParCh","deposerObParCh" ,"afficher","info","liberer", "quitter", "aide", "prendre","deposer"};


	/**
 	 *  Initialise la liste des mots-cle utilisables comme commande.
	 */
	public MotCleCommande() {}
 

	/**
	 *  Teste si la chaine de caracteres specifiee est un mot-clé de commande
	 *  valide. Check whether a given String is a valid command word. Return true
	 *  if it is, false if it isn't.
	 *
	 * @param  aString  Chaine de caractères a tester
	 * @return          true si le paramètre est une commande valide, false sinon
	 */
	public boolean estCommande(String aString) {
		for (int i = 0; i < commandesValides.length; i++) {
			if (commandesValides[i].equals(aString)) {
				return true;
			}
		}
		return false;
	}


	/**
	 *  Affiche toutes les commandes (i.e. les mots-clé) valides.
	 */
	public void afficherToutesLesCommandes() {
		for (int i = 0; i < commandesValides.length; i++) {
			System.out.print(commandesValides[i] + "  ");
		}
		System.out.println();
	}
}

