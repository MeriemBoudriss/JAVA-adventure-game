package zork;
  
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 *  
 *
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte. <p>
 *
 *  Cet analyseur syntaxique lit les entrees (au terminal) utilisateur et tente
 *  de les interpreter comme des commande du jeu "Zork". Chaque appel a la
 *  methode getCommande() lit une ligne au terminal et tente de l'interpréter
 *  comme constituant une commande composee de deux mots. La commande est alors
 *  renvoyee sous forme d'une instance de la classe Commande. </p> <p>
 *
 *  Cet analyseur contient un repertoire de toutes les commandes reconnues par
 *  le jeu. Il compare les entrees de l'utilisateur au commandes repertoriees et
 *  si la commande utilisateur n'est pas reconnue il renvoie un objet Commande
 *  marque comme etant une commande inconnue.</p>
 *
 * @author     Michael Kolling
 * @author     Marc Champesme (pour la traduction francaise)
 * @version    1.0
 * @since      July 1999
 */

public class AnalyseurSyntaxique {
	// repertorie toutes les commandes reconnues
	private MotCleCommande commandes;

	/**
	 *  Initialise un nouvel analyseur syntaxique
	 */
	public AnalyseurSyntaxique() {
		commandes = new MotCleCommande();
	}


	/**
	 *  Lit une ligne au terminal et tente de l'interpreter comme constituant une
	 *  commande composee de deux mots. La commande est alors renvoyee sous forme
	 *  d'une instance de la classe Commande.
	 *
	 * @return    La commande utilisateur sous la forme d'un objet Commande
	 */
	public Commande getCommande() {
		// pour memoriser la ligne entree par l'utilisateur
		String ligneEntree = "";
		String mot1;
		String mot2;

		// affiche l'invite de commande
		System.out.print("> ");

		BufferedReader reader = new BufferedReader(new InputStreamReader(
			System.in));
		try {
			ligneEntree = reader.readLine();
		} catch (java.io.IOException exc) {
			System.out.println("Une erreur est survenue pendant la lecture de votre commande: "
				 + exc.getMessage());
		}

		StringTokenizer tokenizer = new StringTokenizer(ligneEntree);

		if (tokenizer.hasMoreTokens()) {
			// recuperation du permier mot (le mot commande)
			mot1 = tokenizer.nextToken();
		} else {
			mot1 = null;
		}
		if (tokenizer.hasMoreTokens()) {
			// recuperation du second mot
			mot2 = tokenizer.nextToken();
		} else {
			mot2 = null;
		}

		// note: le reste de la ligne est ignore.

		// Teste si le permier mot est une commande valide, si ce n'est pas
		// le cas l'objet renvoyé l'indique
		if (commandes.estCommande(mot1)) {
			return new Commande(mot1, mot2);
		} else {
			return new Commande(null, mot2);
		}
	}


	/**
	 *  Affiche la liste de toutes les commandes reconnues pour le jeu.
	 */
	public void afficherToutesLesCommandes() {
		commandes.afficherToutesLesCommandes();
	}
}