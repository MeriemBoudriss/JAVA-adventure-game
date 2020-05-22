package zork;
 
import java.util.Set;
import java.lang.*;
/**
 * Les animaux dans le jeu Zork.
 * <p>
 * Il est fortement recommande que chaque espece animale (Chien, Loup, Chevre)
 * donne lieu a la definition d'une classe implementant cette interface ainsi
 * que, le cas echeant, une des interfaces Adoptable ou Chasseur.
 * </p>
 * <p>
 * Dans le jeu Zork, tous les animaux ont les caracteristiques et comportements
 * suivant:
 * <ul>
 * <li>tous les animaux disposent au depart d'un certain nombre de points de
 * vie. Ils perdent des points a chaque action realisee et en accumulent a
 * chaque fois qu'ils mangent (le nombre de point de vie depend de la valeur
 * nutritive de ce qui est consomme). Lorsque le nombre de point de vie d'un
 * animal atteint une valeur negative ou nulle, l'animal meurt, depose les
 * aliments qu'il transporte, reste dans la piece ou il est mort et ne fait plus
 * aucune action.</li>
 * <li>les animaux se deplacent en respectant les memes contraintes que les
 * joueurs: le deplacement d'une piece a une autre n'est possible que s'il
 * existe un passage entre les deux pieces.</li>
 * <li>les animaux peuvent manger et les pieces peuvent contenir plusieurs types
 * d'aliment (ces types sont ceux enumeres par le type enum TypeAliment): chaque
 * espece animale possede son propre regime alimentaires correspondant a un ou
 * plusieurs types d'aliment.</li>
 * <li>A l'exception de manger, chaque action de l'animal entraine une
 * diminution de ses points de vie, ce cout en nombre de points de vie est
 * specifique a chaque action et a chaque espece. Les differentes actions
 * possibles pour tous les animaux sont:
 * <ul>
 * <li>ramasser un aliment: l'animal choisit un aliment dans la piece, l'ajoute
 * a sa reserve, le retire de la piece et diminue son capital de point de vie de
 * la valeur du cout de cette action</li>
 * <li>manger un aliment: l'animal choisit un aliment compatible avec son regime
 * alimentaire parmi ceux qui sont dans sa reserve, le supprime de sa reserve et
 * augmente son capital vie de la valeur nutritive de cet aliment</li>
 * <li>se deplacer: l'animal (ou son maitre si l'animal est adoptable et adopte)
 * choisit une des sorties de la piece, il est retire de cette piece, est ajoute
 * a la piece choisie, puis diminue son capital de point de vie de la valeur du
 * cout de cette action</li>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * <p>
 * Chaque classe implementant cette interface doit definir au moins les deux
 * constructeurs suivant:
 * <ul>
 * <li>un constructeur ayant pour unique parametre le nom de l'animal</li>
 * <li>un constructeur a deux arguments: le nom de l'animal et une Collection
 * d'aliments a placer dans sa reserve</li>
 * </ul>
 * </p>
 * 
 * @invariant getNom() != null;
 * @invariant descriptionCourte() != null;
 * @invariant descriptionLongue() != null;
 * @invariant getMaxCapitalVie() > 0;
 * @invariant getCapitalVie() <= getMaxCapitalVie();
 * @invariant getRegimeAlimentaire() != null;
 * @invariant !getRegimeAlimentaire().isEmpty();
 * @invariant getValeurNutritive() > 0;
 * @invariant !contient(null);
 * @invariant estVivant() <==> (getCapitalVie() > 0);
 * @invariant !estVivant() ==> reserveEstVide();
 * @invariant getCoutRamasser() > 0;
 * @invariant getCoutDeplacement() > getCoutRamasser();
 * 
 * @author Marc Champesme
 * @since 15/11/2019
 * @version 25/11/2019
 *
 */
public interface Animal extends Iterable<Aliment> {

	/**
	 * Renvoie le nom de cet animal.
	 * 
	 * @return le nom de cet animal
	 * 
	 * @pure
	 */
	public String getNom();

	
	
	/**
	 * Renvoie une description courte de cet animal incluant son nom.
	 * 
	 * @return une description courte de cet animal
	 * 
	 * @ensures \result.contains(getNom());
	 * 
	 * @pure
	 */
	public String descriptionCourte();

	/**
	 * Renvoie une description longue de cet animal incluant son nom et les aliments
	 * contenus dans sa reserve.
	 * 
	 * @return une description longue de cet animal
	 * 
	 * @ensures \result.contains(getNom());
	 * 
	 * @pure
	 */
	public String descriptionLongue();

	/**
	 * Renvoie le nombre de points de vie de cet animal.
	 * 
	 * @return le capital vie de cet animal
	 * 
	 * @pure
	 */
	public int getCapitalVie();

	/**
	 * Renvoie le nombre de points de vie maximal de cet animal.
	 * 
	 * @return le capital vie maximal de cet animal
	 * 
	 * @pure
	 */
	public int getMaxCapitalVie();

	/**
	 * Renvoie true si cet animal est vivant.
	 * 
	 * @return true si cet animal est vivant, false sinon
	 * 
	 * @pure
	 */
	public boolean estVivant();

	/**
	 * Fait mourir cet animal et depose tous les aliments de sa 
	 * reserve dans cette piece.
	 * @param p la piece dans laquelle cet animal meurt
	 * 
	 * @requires p != null;
	 * @ensures !estVivant();
	 * @ensures reserveEstVide();
	 * @ensures p.getNbObjets() == \old(p.getNbObjets() + getNbAliments());
	 * @ensures (\forall Aliment al; \old(contient(al)); p.contientObjet(al));
	 * 
	 * @throws NullPointerException si la piece specifiee est null
	 */
	public void mourirDans(Piece p);

	/**
	 * Renvoie la valeur nutritive de cet animal. Cette valeur est prise en compte
	 * dans le cas ou cet animal serait mange.
	 * 
	 * @return la valeur nutritive de cet animal
	 * 
	 * @pure
	 */
	public int getValeurNutritive();

	/**
	 * Renvoie true si cet animal peut manger l'aliment specifie.
	 * 
	 * @param al aliment dont on souhaite savoir s'il peut etre mange par cet animal
	 * @return true si cet animal peut manger l'aliment specifie, false sinon.
	 * 
	 * @ensures (al == null) ==> !\result;
	 * @ensures (al != null) ==> (\result <==> getRegimeAlimentaire().contains(al.getType()));
	 * 
	 * @pure
	 */
	public boolean peutManger(Aliment al);

	/**
	 * Renvoie le nombre d'aliments presents dans la reserve de cet animal.
	 * 
	 * @return le nombre d'aliments presents dans la reserve de cet animal
	 * 
	 * @ensures \result >= 0;
	 * 
	 * @pure
	 */
	public int getNbAliments();

	/**
	 * Renvoie le nombre d'exemplaires de l'objet specifie presents dans la reserve
	 * de cet animal.
	 * 
	 * @param obj l'objet dont on souhaite connaitre le nombre d'exemplaires dans la
	 *            reserve de cet animal
	 * @return le nombre d'exemplaires de l'objet specifie
	 * 
	 * @ensures !(obj instanceof Aliment) ==> (\result == 0);
	 * @ensures \result >= 0;
	 * @ensures \result <= getNbAliemnts();
	 * @ensures (\result > 0) ==> contient(obj);
	 * 
	 * @pure
	 */
	public int getNbOccurences(Object obj);

	/**
	 * Renvoie true si l'objet specifie est present dans la reserve de cet animal.
	 * 
	 * @param obj l'objet dont on souhaite savoir s'il est present dans la reserve
	 *            de cet animal
	 * @return true si la reserve de cet animal contient l'objet specifie, false
	 *         sinon
	 * 
	 * @ensures !(obj instanceof Aliment) ==> !\result;
	 * @ensures \result ==> (getNbOccurences() > 0);
	 * 
	 * @pure
	 */
	public boolean contient(Object obj);

	/**
	 * Renvoie true si la reserve de cet animal est vide.
	 * 
	 * @return true si la reserve de cet animal est vide
	 * 
	 * @ensures \result <==> (getNbAliments() == 0);
	 * 
	 * @pure
	 */
	public boolean reserveEstVide();

	/**
	 * Choisit dans la piece specifiee un aliment a ramasser. Les classes
	 * implementant cette interface sont entierement libres des criteres de choix de
	 * l'aliment, il n'est pas impose qu'un aliment soit choisi.
	 * 
	 * 
	 * @param p la piece dans laquelle l'aliment doit etre choisi
	 * @return l'aliment choisi ou null si aucun aliment n'a ete choisi.
	 * 
	 * @throws NullPointerException si l'argument specifie est null
	 * 
	 * @requires p != null;
	 * @ensures (\result != null) ==> p.contientObjet(\result);
	 * 
	 * @pure
	 */
	public Aliment choisirAliment(Piece p);

	/**
	 * Renvoie le cout d'une action "ramasser" de cet animal. Lors de chaque
	 * ramassage d'aliment effectue par cet animal, son capital vie sera diminue de
	 * ce cout.
	 * 
	 * @return le cout d'une action "ramasser" pour cet animal
	 * 
	 * @pure
	 */
	public int getCoutRamasser();

	/**
	 * Ramasse dans la piece specifie l'aliment renvoye par la methode
	 * choisirAliment. Si cette action a pour effet de tuer cet animal le contenu de
	 * sa reserve est transfere dans la piece specifie.
	 * 
	 * @param p la piece dans laquelle un aliment doit etre ramasse.
	 * @return l'aliment ramasse ou null si aucun aliment n'a ete ramasse
	 * 
	 * @throws NullPointerException  si l'argument specifie est null
	 * @throws IllegalStateException si cet animal est mort
	 * 
	 * @requires p != null;
	 * @requires estVivant();
	 * @ensures (\result == null) ==> (getNbAliments() == \old(getNbAliments()));
	 * @ensures (\result == null) ==> (getCapitalVie() == \old(getCapitalVie()));
	 * @ensures ((\result != null) && estVivant()) ==> contient(\result);
	 * @ensures ((\result != null) && estVivant()) ==> (getNbAliments() == \old(getNbAliments() + 1));
	 * @ensures ((\result != null) && estVivant()) ==> (p.getNbObjets() == \old(p.getNbObjets() - 1));
	 * @ensures ((\result != null) && estVivant()) ==> (getCapitalVie() == \old(getCapitalVie() - getCoutRamasser()));
	 * @ensures ((\result != null) && !estVivant()) ==> reserveEstVide();
	 * @ensures ((\result != null) && !estVivant()) ==> (p.getNbObjets() == \old(p.getNbObjets() + getNbAliments()));
	 */
	public Aliment ramasserDans(Piece p);

	// Gestion des deplacements

	/**
	 * Renvoie le cout d'un deplacement de cet animal. Lors de chaque deplacement de
	 * cet animal, son capital vie sera diminue de ce cout.
	 * 
	 * @return le cout d'un deplacement de cet animal
	 * 
	 * @pure
	 */
	public int getCoutDeplacement();

	/**
	 * Deplace cet animal vers une piece voisine de la piece specifiee. Le
	 * deplacement a lieu dans la direction renvoyee par la methode choisirPiece, si
	 * choisirPiece renvoie null aucun deplacement n'a lieu. Si le deplacement a
	 * lieu, le capital vie de cet animal est diminue de la valeur renvoyee par la
	 * methode getCoutDeplacement. Seul un animal vivant peut se deplacer. Si ce
	 * deplacement a pour effet de tuer cet animal, l'animal est deplace puis le
	 * contenu de sa reserve est transfere dans la piece de destination renvoye.
	 * 
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
	 * @ensures (\result == null) <==> \old(choisirSortie(p) == null);
	 * @ensures (\result == null) ==> p.contientAnimal(this);
	 * @ensures (\result == null) ==> (getCapitalVie() == \old(getCapitalVie()));
	 * @ensures (\result != null) ==> (\result == p.pieceSuivante(\old(choisirSortie(p)));
	 * @ensures (\result != null) ==> \result.contientAnimal(this);
	 * @ensures (\result != null) ==> !p.contientAnimal(this);
	 * @ensures ((\result != null) && estVivant()) ==> (getCapitalVie() == (\old(getCapitalVie()) - getCoutDeplacement()));
	 * @ensures ((\result != null) && !estVivant()) ==> reserveEstVide();
	 * @ensures ((\result != null) && !estVivant()) ==> (\result.getNbObjets() == \old(p.pieceSuivante(choisirSortie(p)).getNbObjets() + getNbAliments()));
	 */
	public Piece deplacerDepuis(Piece p);

	/**
	 * Choisit une des sorties de la piece specifiee. Les classes implementant cette
	 * interface sont entierement libres des criteres de choix de la piece, il n'est
	 * pas impose qu'une sortie soit choisie.
	 * 
	 * 
	 * @param p la piece dont une sortie doit etre choisie
	 * @return une sortie de la piece specifiee ou null
	 * 
	 * @throws NullPointerException si l'argument specifie est null
	 * 
	 * @requires p != null;
	 * @ensures (\result != null) ==> (p.pieceSuivante(\result) != null);
	 * 
	 * @pure
	 */
	public Direction choisirSortie(Piece p);

	/**
	 * Renvoie l'ensemble des types d'aliment que cet animal peut manger.
	 * 
	 * @return le regime alimentaire de cet animal
	 * 
	 * @ensures \result != null;
	 * @ensures !\result.isEmpty();
	 * @ensures (\forall Aliment al; al != null && \result.contains(al.getType()); peutManger(al));
	 * 
	 * @pure
	 */
	public Set<TypeAliment> getRegimeAlimentaire();

	/**
	 * Renvoie un aliment present dans la reserve de cet animal et correspondant au
	 * regime alimentaire de cet animal (cf. getRegimeAlimentaire). L'aliment
	 * renvoye sera consomme lors de la prochaine action "manger" sous reserve
	 * qu'aucune modification n'ait eu lieu concernant le contenu de la reserve de
	 * cet animal. Il n'est pas impose que cette methode renvoie un aliment, y
	 * compris si un element consommable est present dans la reserve, les classes
	 * implementant cette interface sont entierement libre dans la definition des
	 * criteres de choix de l'aliment.
	 * 
	 * 
	 * @return un aliment consommable par cet animal ou null
	 * 
	 * @ensures (\result != null) ==> peutManger(\result);
	 * @ensures (\result != null) ==> contient(\result);
	 * 
	 * @pure
	 */
	public Aliment getAliment();

	/**
	 * Mange l'aliment renvoye par getAliment. Si getAliment renvoie une valeur non
	 * null, supprime l'aliment renvoye de la reserve de cet animal et ajoute sa
	 * valeur nutritive au capital vie de cet animal.
	 * 
	 * @return l'aliment qui a ete mange ou null sinon.
	 * 
	 * @throws IllegalStateException si cet animal est mort
	 * 
	 * @requires estVivant();
	 * @ensures estVivant();
	 * @ensures (\result != null) ==> \result.equals(\old(getAliment()));
	 * @ensures (\result != null) ==> (getNbOccurences(\result) == \old(getNbOccurences(getAliment()) - 1));
	 * @ensures (\result != null) ==> (getCapitalVie() == \old(getCapitalVie() + getAliment().getValeur()));
	 * @ensures (\result == null) ==> (\old(getAliment()) == null);
	 * @ensures (\result == null) ==> (getCapitalVie() == \old(getCapitalVie()));
	 * @ensures (\result == null) ==> (getNbAliments() == \old(getNbAliments()));
	 * 
	 */
	public Aliment manger();

}