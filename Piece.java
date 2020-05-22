package zork;
 
import java.util.*;
/**
 *  Une piece dans un jeu d'aventure. <p>
 *  Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode
 *  texte.</p> <p>
 *  Une "Piece" represente un des lieux dans lesquels se deroule l'action du
 *  jeu. Elle est reliee a au plus quatre autres "Piece" par des sorties. Les
 *  sorties sont etiquettees "nord", "est", "sud", "ouest". Pour chaque
 *  direction, la "Piece" possede une reference sur la piece voisine ou null
 *  s'il n'y a pas de sortie dans cette direction.</p>
 *  
 * @invariant  !contient(null);
 * @invariant  description()!=null;
 * @author     Michael Kolling
 * @author     Marc Champesme (pour la traduction francaise)
 * @author     Meriem BOUDRISS 11808654 (pour l'amelioration)
 * @author     Reda LAMHATTAT  11809054 (pour l'amelioration)
 */
public class Piece extends Conteneur<ObjetZork> {
    private String description;
    private Map<Direction, Piece> sorties;
    private ArrayList<Animal> listeAnimaux;
    /**
     *constructeur avec un seul parametre
     *initialise une piece decrite par une chaine de caracteres simple
     *il fait appel au constructeur de la super classe pour initialiser 
     *la reserve, initialement la piece possede 4 sorties
     *@param description la description courte de la piece courante "salle" "cuisine"
     *@throws NullPointerException si la description est null.
     *@requires description!=null
     *@ensures description.equals(description)
     *@ensures getListAnimaux() != null;
     */
    public Piece(String description){
        super();
        if(description == null)
            throw new NullPointerException("description ne doit pas etre nulle!");
        this.description = description;
        sorties = new HashMap<Direction, Piece>(4);
        listeAnimaux = new ArrayList<Animal>();
    }
    
    /**
     * constructeur de piece qui prend en parametres une description, 
     * un tableau d'objetZork a placer dans le reserve,
     * et la taille du tableau.
     * @requires description != null;
     * @requires tab !=null;
     * @requires taille>=0;
     * @ensures description.equals(description)
     * @ensures getListAnimaux() != null; 
     * @ensures sorties !=null;
     * @ensures lesObjets != null;
     */
    public Piece(String description, ObjetZork[]tab, int taille) {
    	super();
    	this.description=description;
    	sorties = new HashMap<Direction, Piece>(4);
        listeAnimaux = new ArrayList<Animal>();
        for(int i=0; i<taille;i++) {
        	lesObjets.add(tab[i]);
        }
    }
    
    /**
     * constructeur avec deux parametres
     * intialise une piece decrite par une description courte, 
     * et la liste d'objets specifiee
     * @param description la description de la piece courante
     * @param ArrayList<ObjetZork> obj la liste des objets a ajouter dans la piece
     * @requires description != null
     * @requires obj.size()>=0
     * @ensures description.equals(description)
     * @ensures lesObjets!=null
     */
    public Piece ( String description,ArrayList<ObjetZork> obj){
        super(obj);
        this.description = description;
        sorties = new HashMap<Direction, Piece>(4);
        listeAnimaux = new ArrayList<Animal>();
    }

    /**
     * renvoie la liste des animaux presents dans cette piece
     * @requires listeAnimaux != null;
     * @return listeAnimaux la liste des animaux dans la piece
     * @pure
     */
    public ArrayList<Animal> getListAnimaux(){
        return this.listeAnimaux;
    }
    
    /**
     * afficher les noms des animaux presents dans la piece
     * @requires getListAnimaux() != null;
     * @pure
     */
    public void afficherLesAnimaux(){
        for(Animal a: this.getListAnimaux()){
            System.out.print(a.getNom()+"");
        }
        System.out.println();
    }
    
    /**
     * teste si la piece contient l'animal specifie
     * @return true si l'animal est est dans le piece, false sinon
     * @requires getListAnimaux() != null;
     * @pure
     */
    public boolean contientAnimal(Animal a){
        return this.listeAnimaux.contains(a);
    }
    
    /**
     * envoie le nombre d'animaux presents dans la piece.
     * @requires getListAnimaux()!= null;
     * @return le nombre d'animaux presents dans la piece
     * @pure
     */
    public int getNbAnimal(){
        return this.listeAnimaux.size();
    }
    
    /**
     * ajoute l'animal specifie dans la liste des animaux de la piece.
     * si l'animal est deja present, un message est affiche.
     * @param n l'animal a ajouter dans la piece
     * @requires n != null;
     * @ensures getListAnimaux() != null;
     * @ensures getNbAnimal() >= \old(getNbAnimal());
     */
    public void ajouterAnimal(Animal n){
        if(!contientAnimal(n))
            this.listeAnimaux.add(n);
            
        else{
            System.out.print(n.getNom()+" deja present dans la piece"); 
       }
    }
    
    /**
     * retire l'animal specifie de la liste des animaux de la piece.
     * si l'animal n'est pas present, un message est affiche.
     * @param n l'animal a retirer de la piece
     * @requires n != null;
     * @requires contientAnimal(n);
     * @requires getListAnimaux() != null;
     * @ensures getNbAnimal() <= \old(getNbAnimal());
     */
    public void retirerAnimal(Animal n){
        if(!contientAnimal(n))
            System.out.println(n.getNom()+" n'est pas present dans la piece");
        else{
            this.listeAnimaux.remove(n);
        }
    }
    
    /**
     *  Definie les sorties de cette piece. A chaque direction correspond ou bien
     *  une piece ou bien la valeur null signifiant qu'il n'y a pas de sortie dans
     *  cette direction.
     *
     * @param  nord   La sortie nord
     * @param  est    La sortie est
     * @param  sud    La sortie sud
     * @param  ouest  La sortie ouest
     */
    public void setSorties(Piece nord, Piece est, Piece sud, Piece ouest) {
        if (nord != null) {
            sorties.put(Direction.NORD, nord);
        }
        if (est != null) {
            sorties.put(Direction.EST, est);
        }
        if (sud != null) {
            sorties.put(Direction.SUD, sud);
        }
        if (ouest != null) {
            sorties.put(Direction.OUEST, ouest);
        }
    }
    
    /**
     *  Renvoie la description de cette piece (i.e. la description specifiee lors
     *  de la creation de cette instance).
     * @return description la description de cette piece
     * @requires description != null
     * @pure
     */
    public String descriptionCourte() {
        return description;
    }
  
    /**
     *  affiche une description de cette piece et les objets et les 
     *  animaux qu'elle contienne
     *  de la forme: <pre>
     *  Vous etes dans la cuisine qui contient les objets: .
     *  croquettes(2kgs)... et les animaux: ...</pre>
     *  Cette description utilise la chaine de caracteres
     *  renvoyee par la methode descriptionLongue dans la classe ObjetZork pour 
     *  decrire les objets de cette piece.
     *  @requires description != null;
     *  @requires getListObjets() != null;
     *  @requires getListAnimaux() != null;
     *  @pure
     */
     public void descriptionLongue() {
        System.out.println("vous etes dans "+description);
        if(this.lesObjets.size()!=0){
            System.out.println("qui contient les  objets:");
            for(ObjetZork ob : this.copy()){
                System.out.println(ob.descriptionLongue());
            }
        }
        if(this.listeAnimaux.size()!=0){
            System.out.println("\nles animaux:");
            for(Animal an: this.listeAnimaux){
                System.out.println(an.descriptionCourte());            
            }
        }
        System.out.println("*************************");
    }

    /**
     *  Renvoie une description des sorties de cette piece, de la forme: <pre>
     *  Sorties: nord(salle) ouest(bureau)</pre> 
     *  Cette description est toujours afficher au cours du jeu.
     * @requires  sorties != null;
     * @return   Une description des sorties de cette piece.
     * @ensures  \result != null
     * @pure
     */
    public String descriptionSorties() {
        String resulString = "Sorties:";
        for (Direction sortie :  sorties.keySet()) {
            resulString += " " +"-"+ sortie + "("+  sorties.get(sortie).descriptionCourte() +")";
        }
        return resulString;
    }

    /**
     * renvoie l'ensemble des sorties possibles a partir de cette piece.
     * @requires sorties != null;
     * @return l'ensemble des sorties de la piece.
     * @pure
     */
    public Set<Direction> getSorties(){
       return sorties.keySet();
    }
    
    /**
     *  Renvoie la piece atteinte lorsque l'on se deplace a partir de cette piece
     *  dans la direction specifiee. Si cette piece ne possede aucune sortie dans cette direction,
     *  renvoie null.
     *  @param  direction  La direction dans laquelle on souhaite se deplacer
     *  @requires d != null
     *  @return        Piece atteinte lorsque l'on se deplace dans la direction
     *    specifiee ou null.
     *  @pure
     */
    public Piece pieceSuivante(Direction d) {
        return sorties.get(d);
    }
}