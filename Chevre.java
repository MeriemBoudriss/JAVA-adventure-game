package zork;
 
import java.util.*;
/**
 * Une chevre dans un jeu d'aventure Zork.
 * Cette classe fait patie du logiciel Zork, 
 * un jeu d'aventure simple en mode texte.
 * Une chevre represente un animal qui effectue des actions dans le jeu.
 * Une chevre peut ramasser des aliments correspondants 
 * a son regime alimentaire et les manger, elle peut se deplacer dans 
 * les pieces du jeu, elle peut aussi etre chassee par un loup.
 * Une chevre a les memes caracteristiques que les autres animaux
 * etqui sont decrites dans la classe AbstractAnimal.
 * de plus la chevre a un attribut supplementaire mangerPar qui precise 
 * si la chevre a ete manger ou non.
 * 
 *  {@inheritDoc}
 *  
 * @author Meriem BOUDRISS 11808654
 * @version 15/12/2019
 */
public class Chevre extends AbstractAnimal {
    private boolean mangerPar;
    /**
     * premier constructeur de la classe.
     * initialise une chevre avec le nom passe en argument.
     * il fait appel au constructeur de la super classe qui prend un argument
     * @param nom le nom de la chevre
     * @requires nom!=null
     * @ensures getNon().equals(nom);
     * @ensures getMangerPar()==false;
     * @ensures getgetListAliments()!=null;
     */
    public Chevre(String nom){
       super(nom); 
       this.mangerPar=false;
    }
    
    /**
     * deuxieme constructeur de la classe.
     * initialise unr chevre avecle nom et une collection
     * d'aliments a placer dans sa reserve.
     * @param nom le nom de la chevre;
     * @param alime la collection d'aliments
     * @requires nom != null;
     * @requires alime != null;
     * @ensures getNon().equals(nom);
     * @ensures getMangerPar()==false;
     * @ensures getgetListAliments()!=null;
     */
    public Chevre(String nom, Collection<? extends Aliment>alime){
    	super(nom,alime);
    	this.mangerPar=false;
    }
    
    /**
     * troisieme constructeur de la classe.
     * inintialise une chevre avec le nom, les points de vie 
     * la valeur nutritive et la piece actuelle.
     * fait appel au constructeur de la super classe AbstractAnimal
     * qui prend 4 arguments
     * @param nom le nom de la chevre
     * @param points les points de vie de la chevre au depart du jeu
     * @param val la valeur nutritive de la chevre
     * @param pi le piece actuelle de la chevre
     * @requires nom!=null
     * @requires val >0;
     * @requires points >0;
     * @requires pi != null;
     * @ensures getNon().equals(nom);
     * @ensures getMangerPar()==false;
     * @ensures getgetListAliments()!=null;
     * @ensures getValeurNutritive()==val;
     * @ensures getPieceActuelle()==pi;
     * @ensures getCapitalVie()==points;
     */
    public Chevre(String nom, int points, int val, Piece pi){
        super(nom, points,val,pi);
        this.mangerPar=false;
    }
    
    /**
     * renvoie true si la chevre a ete mange pas un loup,
     * et false sinon
     * @return true si la chevre a ete mange, false sinon
     * @pure
     */
    public boolean getMangerPar(){
        return this.mangerPar;
    }
    
    /**
     * modifie l'attribut mangerPar en lui affectant la 
     * valeur passee en parametres
     * @param m la valeur specifiant si la chevre a ete manger ou non
     */
    public void setMangerPar(boolean m){
        this.mangerPar=m;
    }
    
    /**
     * {@inheritDoc}
     */
    public int getCoutRamasser(){
       return 4;
    }
    
}
