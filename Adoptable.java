package zork;
 
/**
 * Animaux pouvrant etre adoptes.
 * <p>
 * Un animal adoptable, peut faire l'objet de propositions pour ses
 * deplacements, lorsqu'il est adopte (et donc non libre) les choix effectues
 * par l'animal adopte pour ses deplacements sont conformes aux propositions qui
 * lui sont faites.
 * </p>
 * 
 * @invariant !estLibre() ==> (\forall Piece p; p != null; choisirPiece(p) == getSortieProposee(p));
 * 
 * @author Marc Champesme
 * @since 15/11/2019
 * @version 29/11/2019
 *
 */
public interface Adoptable extends Animal {

	/**
	 * Renvoie true si cet animal est libre.
	 * 
	 * @return true si cet animal est libre, false sinon.
	 * 
	 * @pure
	 */
	public boolean estLibre();

	/**
	 * Fait de cet animal un animal adopte.
	 * 
	 * @return true si cet animal etait libre, false sinon.
	 * 
	 * @ensures !estLibre();
	 * @ensures \result <==> (estLibre() != \old(estLibre()));
	 */
	public boolean adoption();

	/**
	 * Libere cet animal.
	 * 
	 * @return true si cet animal etait precedemment adopte, false sinon.
	 * 
	 * @ensures estLibre();
	 * @ensures \result <==> (estLibre() != \old(estLibre()));
	 */
 	public boolean liberation();

	/**
	 * Propose ou impose la sortie specifiee pour la piece specifiee. Lorsque cet
	 * animal est adopte cette sortie proposee sera la sortie choisie (par la
	 * methode choisirSortie) pour le prochain deplacement de cet animal, si ce
	 * deplacement se fait e partir de cette piece.
	 * 
	 * @param p la piece pour laquelle une sortie est proposee
	 * @param d sortie proposee pour la piece specifiee
	 * 
	 * @throws NullPointerException si l'un des arguments specifies est null
	 * @throws IllegalArgumentException si la piece specifiee ne possede pas de 
	 * 			sortie dans la direction specifiee
	 * 
	 * @requires p != null;
	 * @requires d != null;
	 * @requires p.pieceSuivante(d) != null;
	 * @ensures getSortieProposee(p) == d;
	 * 
	 */
	public void proposerSortie(Piece p, Direction d);

	/**
	 * Renvoie une sortie proposee pour la piece specifiee. La sortie renvoyee est
	 * celle proposee par le dernier appel a proposerSortie pour la piece specifiee,
	 * ou null si cet animal s'est deplace (appel a la methode deplacerDepuis)
	 * depuis le dernier appel a proposerSortie.
	 * 
	 * @param p piece dont on veut connaitre la sortie proposee.
	 * @return sortie proposee pour la piece specifiee ou null si aucune sortie n'a
	 *         ete proposee depuis le dernier deplacement.
	 * 
	 * @throws NullPointerException si l'un des arguments specifies est null 
	 * 
	 * @requires p != null;
	 * @ensures (\result != null) ==> (p.pieceSuivante(\result) != null);
	 * @ensures !estLibre() ==> (\result == choisirSortie(p));
	 * 
	 * @pure
	 */
	public Direction getSortieProposee(Piece p);

	/**
	 * {@inheritDoc}
	 * <p>
	 * Lorsque cet animal est adopte, la sortie choisie doit imperativement etre
	 * celle renvoyee par la methode getSortieProposee, l'entitee ayant adopte cet
	 * animal doit donc imposer le choix en appelant la methode proposerSortie avant
	 * d'effectuer un deplacement a l'aide de la methode deplacerDepuis. Si cet
	 * animal est libre, il n'y a aucune obligation a ce que le choix effectue
	 * prenne en compte la proposition de sortie renvoyee par la methode
	 * getSortieProposee.
	 * </p>
	 * 
	 * @requires p != null;
	 * @ensures !estLibre() ==> (\result == getSortieProposee(p));
	 * 
	 * @pure
	 */
	@Override
	public Direction choisirSortie(Piece p);

	/**
	 * {@inheritDoc}
	 * @param p la piece a partir de laquelle a lieu le deplacement
	 * @return la nouvelle piece dans laquelle se trouve cet animal ou null si le
	 *         deplacement n'a pas eu lieu.
	 * 
	 * @throws NullPointerException     si l'argument specifie est null
	 * @throws IllegalStateException    si cet animal est mort
	 * @throws IllegalArgumentException si cet animal n'est pas present dans la
	 *                                  piece specifiee avant appel de cette methode
	 * 
	 * @requires p != null;
	 * @requires estVivant();
	 * @requires p.contientAnimal(this);
	 * @ensures getSortieProposee(p) == null;
	 */
	@Override
	public Piece deplacerDepuis(Piece p);

}