package zork;
 
import java.util.*;


/**
 * Un loup dans un jeu d'aventure Zork.<p>
 * Cette classe fait partie du logiciel Zork, un jeu d'aventure simple
 * en mode texte.</p> <p>
 * <p>Un loup represente un animal qui effectue des actions dans le jeu,
 * un loup peut ramasser des aliments et les manger, 
 * il peut chasser des chevre et les manger
 * et aussi se deplacer dans les pieces du jeu.
 * un loup a les memes caracteristiques que les autres animaux 
 * et qui sont decrites dans la classe AbstractAnimal.</p>
 * 
 * {@inheritDoc}
 *
 * @author Meriem BOUDRISS 11808654
 * @version 10/12/2019
 */
public class Loup extends AbstractAnimal implements Chasseur{
   
   /**
    * premier constructeur de la classe.
    * initialise un loup avec le nom, max des pints de vie, 
    * les points de vie et la piece actuelle.
    * il fait appel au constructeur de la super classe 
    * qui prend 4 arguments.
    * @param nom le nom du loup
    * @param maxPtVie le maximum des points de vieque le loup peut voir
    * @param valNut la valeur nutritive de ce loup
    * @param ptVie les points de vie que leoup a au depart
    * @param pi la piece actuelle du loup
    * @requires nom != null;
    * @requires pi !=null;
    * @requires maxPtVie>0;
    * @requires valNut >0;
    * @requires ptVie>0;
    * @ensures this.getPieceActuelle()==pi;
    * @ensures getListAliments() !=null;
    * @ensures getNom().equals(nom);
    * @ensures getValeurNutritive()==valNut;
    * @ensures getMaxCapitalVie()==maxPtVie;
    * @ensures getCapitalVie()==ptVie;
     */
   public Loup(String nom, int maxPtVie, int valNut, int ptVie,Piece pi){
      super(nom,ptVie,valNut,pi);
   }
   
   /**
    * deuxieme constructeur de la classe 
    * initialise un loup avec le nom, les points de vie 
    * et une liste d'aliments a placer dans sa reserve.
    * il fait appel au constructeur de la super classe qui prend 2 arguments
    * @requires nom != null;
    * @requires ptVie>0;
    * @requires alime != null;
    * @ensures getNom().equals(nom);
    * @ensures getListAliments() !=null;
    * @ensures getCapitalVie()==ptVie;
    */
   public Loup(String nom, Collection<Aliment>alime){
      super(nom,alime);
      this.pointsVie = 50;
   }
   
   /**
    * {@inheritDoc}
    */
   public int getCoutRamasser(){
       return 5;
   }
   
   /**
    * {@inheritDoc}
    */
   public Animal choisirProie(Piece p){
       if(p==null)
            throw new NullPointerException("la piece ne doit pas etre nulle");
       for(Animal a: p.getListAnimaux()){
           if(this.peutChasser(a))
                return a;
        }
       return null;
   }
   
   /**
    * {@inheritDoc}
    */
   public Set<Class<? extends Animal>> getRegimeAlimentaireChasse(){
       Set<Class<? extends Animal>> set= new HashSet<Class<? extends Animal>>();
       set.add(Chevre.class);
       return set;
   }
   
   /**
    * {@inheritDoc}
    */
   public boolean peutChasser(Animal ani){
      if(ani==null)
           return false;
      return getRegimeAlimentaireChasse().contains(ani.getClass());
   }
    
    /**
     * {@inheritDoc}
     */
   public Animal chasser(Piece p){
       if(p==null){
           throw new NullPointerException("la piece ne doit pas etre null");
        }
       if(!this.estVivant()){
           throw new IllegalStateException("le loup est mort, il ne peut pas chasser");
        }
       Animal a=choisirProie(p);
       if(a!=null && a.estVivant()){
           a.mourirDans(p);
           this.setCapitalVie(getCapitalVie()-getCoutChasse());
           if(!this.estVivant())
                this.mourirDans(p);
           else{
               int val=getCapitalVie()+a.getValeurNutritive();
               if(val<this.getMaxCapitalVie())
                    this.setCapitalVie(val);
               else
                    this.setCapitalVie(this.getMaxCapitalVie());
               p.retirerAnimal(a);
               Chevre h=(Chevre)a;
               h.setMangerPar(true);
           }
       }
       else if(a!=null && !a.estVivant()){
           int val=getCapitalVie()+a.getValeurNutritive();
           if(val<this.getMaxCapitalVie())
                 this.setCapitalVie(val);
           else
                 this.setCapitalVie(this.getMaxCapitalVie());
           p.retirerAnimal(a);
           Chevre h=(Chevre)a;
           h.setMangerPar(true);
       }
       return a;
    }
   
    /**
     * {@inheritDoc}
     */
    public int getCoutChasse(){
       return 2*getCoutDeplacement()+2;
    }
   
}