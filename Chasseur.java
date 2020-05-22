package zork;
 

import java.util.Set;

/**
 * Un animal chasseur a la possibilite de choisir une proie presente dans une
 * piece, puis de la tuer et la manger.
 * <p>
 * Comme pour les autres actions definies par l'interface Animal, l'animal doit
 * etre vivant pour chasser et son capital vie sera diminue du cout de la
 * chasse, puis augmente de la valeur nutritive de la proie.
 * </p>
 * <p>
 * Si la piece dans laquelle l'animal chasse contient un animal mort dont il est
 * predateur, il a la possibilite de choisir cet animal comme proie et dans ce
 * cas il ne subit aucune perte de points de vie du fait de la chasse et se
 * contente d'augmenter son capital vie de la valeur nutritive de la proie.
 * </p>
 * <p>
 * Si le cout de la chasse est superieur a son capital vie anterieur a la chasse
 * le chasseur et la proie sont tues, mais la proie n'est pas mangee et reste
 * morte dans la piece ou elle etait.
 * </p>
 * <p>
 * Lorsqu'une proie est mangee elle disparait du jeu, apres avoir laisse les
 * aliments qu'elle transportait dans la piece oe elle est morte
 * </p>
 * 
 * @invariant getCoutChasse() > (2 * getCoutDeplacement());
 * @invariant getRegimeAlimentaireChasse() != null;
 * @invariant !getRegimeAlimentaireChasse().isEmpty();
 * 
 * @author Marc Champesme
 * @since 15/11/2019
 * @version 30/11/2019
 */
public interface Chasseur extends Animal {

	/**
	 * Choisit une proie dans la piece specifiee. 
	 * Le choix se fait parmi les animaux
	 * dont cet animal chasseur est predateur.
	 * 
	 * @param p piece dans laquelle la proie doit etre choisie
	 * @return la proie choisie ou null.
	 * 
	 * @throws NullPointerException si la piece specifiee est null
	 * 
	 * @requires p != null;
	 * @ensures (\result != null) ==> this.peutChasser(\result);
	 * @ensures (\result != null) ==> p.contientAnimal(\result);
	 * 
	 * @pure
	 */
	public Animal choisirProie(Piece p);

	/**
	 * Renvoie l'ensemble des especes animales que cet animal peut chasser.
	 * 
	 * @return l'ensemble des classes d'animaux dont cet animal peut chasser les
	 *         instances
	 * 
	 * @pure
	 */
	public Set<Class<? extends Animal>> getRegimeAlimentaireChasse();

	/**
	 * Renvoie true si cet animal est predateur de l'animal specifie.
	 * 
	 * @param ani l'animal dont on souhaite savoir si ce chasseur est predateur
	 * @return true si cet animal est predateur de l'animal specifie, false sinon
	 * 
	 * @ensures (ani == null) ==> !\result;
	 * @ensures (ani != null) ==> (\result <==> getRegimeAlimentaireChasse().contains(ani.getClass()));
	 * 
	 * @pure
	 */
	public boolean peutChasser(Animal ani);

	/**
	 * Choisit une proie dans la piece specifiee, la tue et la mange. La proie
	 * choisie est l'animal renvoye par choisirProie. Dans le cas ou la methode
	 * choisirProie renvoie une valeur non null, une fois la proie tuee, le capital
	 * vie de cet animal est diminue du cout de la chasse (cf. getCoutChasse()), il
	 * est alors possible que cet animal meurt et dans ce cas la proie reste morte
	 * dans la piece et pourra etre manger plus tard par un autre predateur. Dans le
	 * cas ou la proie choisie etait deja morte, le captial vie de cet animal n'est
	 * pas diminue du cout de la chasse.
	 * 
	 * @param p la piece dans laquelle cet animal chasse
	 * @return la proie qui a ete mangee ou null si aucune proie n'a pu etre choisie
	 *         dans la piece specifiee
	 * 
	 * @throws NullPointerException  si la piece specifiee est null
	 * @throws IllegalStateException si cet animal est mort
	 * 
	 * @requires this.estVivant();
	 * @requires p != null;
	 * @ensures \result == \old(choisirProie(p));
	 * @ensures (\result != null) ==> !\result.estVivant();
	 * @ensures (\result != null) ==> peutChasser(\result);
	 * @ensures ((\result != null) && !estVivant()) ==> p.contientAnimal(\result);
	 * @ensures (\result != null) && (estVivant() && \old(choisirProie(p).estVivant()) 
	 * 		==> (getCapitalVie() 
	 * 			== Math.min(\old(getCapitalVie() + choisirProie(p).getValeurNutritive() - getCoutChasse()),
	 * 						getMaxCapitalVie()));
	 * @ensures (\result != null) && (estVivant() && !\old(choisirProie(p).estVivant()) 
	 * 		==> (getCapitalVie() 
	 * 			== Math.min(\old(getCapitalVie() + choisirProie(p).getValeurNutritive()), 
	 * 						getMaxCapitalVie()));
	 * @ensures (this.estVivant()) ==> !p.contientAnimal(\result);
	 * @ensures ((\result != null) && this.estVivant()) 
	 * 		==> (p.getNbObjets() == \old(p.getNbObjets() + choisirProie(p).getNbAliments()));
	 * @ensures ((\result != null) && !this.estVivant()) 
	 * 		==> (p.getNbObjets() == \old(p.getNbObjets() + getNbAliments() + choisirProie(p).getNbAliments()));
	 */
	public Animal chasser(Piece p);

	/**
	 * Renvoie le cout de la chasse pour cet animal. La valeur renvoyee est le
	 * nombre de points de vie dont le capital vie de cet animal sera diminue a chaque
	 * action de chasse au cours de laquelle il tue un animal.
	 * 
	 * @return le cout de la chasse pour cet animal
	 * 
	 * @pure
	 */
	public int getCoutChasse();

}