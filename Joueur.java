package zork; 
import java.util.*;

/**
  * <p>Un joueur dans un jeu d'aventure.
  * Cette classe fait partie du logiciel Zork, un jeu d'aventure simple en mode texte </p>
  * <p>Un joueur peut se deplacer dans les pieces, prendre et deposer des objets,
  * adopter et liberer des chiens.
  * Un joueur possede un nom qui le caracterise, un score qui augmente ou diminue au cours
  * du jeu selon les obstacles, un scoreMax qui lui permet de gagner s'il l'atteint, 
  * un poids total des objets qu'il transporte, ainsi que le poidsMax a ne pas depasser.</p>
  * <p>Le joueur possede aussi une liste de chiens adoptes.</p>
  * 
  * @invariant getNom() != null;
  * @invariant getScore() <= getScoreMax();
  * @invariant getPoidsTotal() <= getPoidsMax();
  * @invariant getScore()>=0;
  * @invariant getScoreMax()>0;
  * @invariant getPoidsTotal()>=0;
  * @invariant getPoidsMax()>0;
  * @invariant getNbObjets()>=0;
  * @invariant getNbChien()>=0;
  * @invariant !contient(null);
  * @invariant !contientCh(null);
  * 
  * @author Meriem BOUDRISS 11808654
  * @author Reda LAMHATTAT 11809054
  * @version 1.0
  */
public class Joueur extends Conteneur<ObjetZork>{
    private String nom;
    private int scoreMax;
    private int score;
    private int poidsTotal; 
    private int poidsMax;
    private ArrayList<Chien> listChien;/*les chiens adoptes*/
    /**
     * premier constructeur avec  un seul argument 
     * initialise un joueur avec un nom specifie en parametre
     * en faisant appel au constructeur sans parametres de la super classe
     * pour initialiser la reserve du joueur
     * @param nom le nom du joueur
     * @requires nom != null;
     * @ensures getNom().equals(nom);
     * @ensures getPoidsTotal()==0;
     * @ensures getScore()==1;
     * @ensures getScoreMax()==100;
     * @ensures getPoidsMax()==35;
     * @ensures getNbObjets()==0;
     * @ensures getNbChien()==0;
     * @ensures listChien != null;
     */
    public Joueur (String nom){
        super();
        this.nom=nom;
        this.score=1;
        this.scoreMax=100;
        this.poidsTotal=0;
        this.poidsMax=35;
        listChien= new ArrayList<Chien>();
    }
    
    /**
     * deuxieme constructeur avec deux arguments
     * initialise un joueur avec un nom et  le score maximal
     * en faisant appel au constructeur sans arguments de la super classe
     * pour initialiser la reserve du joueuer
     * @param nom le nom du joueur
     * @param scoreMax le score maximal
     * @requires nom != null;
     * @requires scoreMax>0;
     * @ensures getNom().equals(nom);
     * @ensures listChien != null;
     * @ensures getScoreMax()==scoreMax;
     * @ensures getPoidsTotal()==0;
     * @ensures getPoidsMax()==35;
     * @ensures getScore()==1;
     * @ensures getNbObjets()==0;
     * @ensures getNbChien()==0;
     */
    public Joueur (String nom, int scoreMax){
        super();
        this.nom=nom;
        this.score=1;
        this.poidsTotal=0;
        this.poidsMax=35;
        this.scoreMax=scoreMax;
        listChien= new ArrayList<Chien>();
    }
    
    /**
     * troisieme constructeur avec six arguments
     * initialise un joueur avec un nom, scoreMax, poidsMax, 
     * un tableau d'objets et une liste de chiens ,en faisant appel
     * au constructeur de la super classe qui prend une liste d'objets
     * @param nom le nom du joueur 
     * @param scoreMax le score maximal
     * @param poidsMax le poids maximal a ne pas depasser
     * @param objets tableau d'objets a ajouter dans l'inventaire du joueur
     * @paran nbObjets le nombre d'objets dans le tableau
     * @param lisChien la listedes chiens a adopter
     * @requires nom != null;
     * @requires scoreMax>0;
     * @requires poidsMax>0;
     * @requires objets !=null;
     * @requires nbObjets==objets.length;
     * @requires lisChien != null;
     * @ensures getNom().equals(nom);
     * @ensures getScoreMax()==scoreMax;
     * @ensures getPoidsMax()==poidsMax;
     * @ensures getPoidsTotal()==0;
     * @ensures getScore()==1;
     * @ensures getNbObjets()==nbObjets;
     * @ensures getNbChien()==lisChien.size();
     */
    public Joueur (String nom, int scoreMax,int poidsMax,ArrayList<ObjetZork> objets, int nbObjets, ArrayList<Chien> lisChien){
        super(objets);
        this.nom=nom;
        this.score= 1;
        this.poidsTotal=0;
        this.scoreMax=scoreMax;
        this.poidsMax=poidsMax;
        listChien= new ArrayList<Chien>(lisChien.size());
        for(Chien ch : this.copyCh()){
             listChien.add(ch);
        }
    }
    
    /**
     * renvoie le nom du joueur
     * @return nom le nom du joueur
     * @requires nom!=null;
     * @pure
     */
    public String getNom(){
        return this.nom;
    }
    
    /**
     * renvoie le score courant du joueur
     * @return score le score actuel du joueur au moment de l'appel a la methode
     * @ensures \result >=0;
     * @pure
     */
    public int getScore(){
        return this.score;
    }
    
    /**
     * augmente le score du joueur en lui ajoutant la valeur passee en argument
     * @param score le score a ajouter
     * @requires score >=0;
     * @ensures \old(getScore())<=getScore();
     */
    public void setScore(int score){
        this.score+=score;
    }
    
    /**
     * renvoie le score maximal que le joueur peut atteindre
     * @return scoreMax le score maximal
     * @ensures \result>0;
     * @ensures \result >= getScore();
     * @Pure
     */
    public int getScoreMax(){
        return this.scoreMax;
    }
    
    /**
     * modifie le poids total du joueur 
     * en lui ajoutant le poids du nouvel objet transporte
     * @param p le poids de l'objet
     * @requires p>0;
     * @ensures \old(getPoidsTotal())<=getPoidsTotal();
     */
    public void setPoidsTotal(int p){
        this.poidsTotal +=p;
    }
    
    /**
     * renvoie le poids total des objets transportes par le joueur
     * @return poidsTotal le poids total transpote par le joueur
     * @ensures \result >=0;
     * @ensures \result <= getPoidsMax();
     * @pure
     */
    public int getPoidsTotal(){
        return this.poidsTotal;
    }
    
    /**
     * renvoie le poids maximal que le joueur peut transporter
     * @return poidsMax le poids maximal
     * @ensures \result >0;
     * @ensures \result >= getPoidsTotal();
     * @pure
     */ 
    public int getPoidsMax(){
        return this.poidsMax;
    }
    
    /**
     * renvoie la liste des chiens adoptes par le joueur
     * @requires listChien !=null;
     * @return listChien les chiens adoptes par le joueur
     * @ensures \result != null;
     * @ensures getNbChien() >=0;
     * @pure
     */
    public ArrayList<Chien> getListChien(){
        return this.listChien;
    }
    
    /**
     * renvoie le nombre de chiens adoptes par le joueur
     * @requires listChien != null;
     * @return listChien.size() le nombre de chiens adoptes
     * @ensures \result >=0
     * @pure
     */
    public int getNbChien(){
        return listChien.size();
    }
    
    
    /**
     * renvoie une copie des chiens adoptes par le joueur
     * en utilisant une autre ArrayList et en copiant tous les elements
     * @requires listChien != null;
     * @return lch une copie de la liste des chiens
     * @ensures lch != listChien;
     * @pure 
     */
    public ArrayList<Chien> copyCh(){
        ArrayList<Chien> lch= new ArrayList<Chien>();
        for(Chien h: this.listChien){
            lch.add(h);
        }
        return lch;
    }
    
    /**
     *retire un objet specifie en parametre, de l'inventaire s'il y est 
     *cette methode est une redefinition de la methode retirer dans Conteneur 
     * @param ob l'objets a retirer
     * @requires ob !=null
     * @requires getListObjets() != null;
     * @requires getNbObjets()>0;
     * @requires getPoidsTotal()>0;
     * @requires getScore()>0;
     * @ensures  getListObjets() != null;
     * @ensures \old(getNbObjets())>= getNbObjets();
     * @ensures \old(getPoidsTotal())>= getPoidstotal();
     * @ensures \old(getScore()) >= getScore();
     * @ensures getPoidsTotal() <= getPoidsMax();
     * @ensures getPoidsTotal() >=0; 
     * @ensures getNbObjets()>=0;
     * @ensures getScore()>=0;
     */
    @Override
    public void retirer(ObjetZork ob){
      if(lesObjets.remove(ob)){    
          System.out.println(ob.descriptionCourte()+" depose avec succes");
          this.poidsTotal -= ob.getPoids();
          System.out.println("Votre nouveau poids: "+ this.getPoidsTotal());
          if(ob.descriptionCourte().equals("collierOr")||ob.descriptionCourte().equals("bagueOr")){
              this.score -= (ob.getPoids()+20);
          }else{
              this.score -= (ob.getPoids()+5);
          }
          System.out.println("Votre nouveau score: "+ this.score);
      }
    }
    
    /**
     * teste si un objet est transporte par un des chiens adoptes par le joueur
     * @param l'objet chercher dans l'inventaire des chiens adoptes
     * @requires o != null;
     * @requires getListChien() !=null;
     * @return true si l'objet est transporte par un des chiens, et false sinon
     * @pure
     */
    public boolean isObjetOfChien(ObjetZork o){ 
        for(Chien ch: this.copyCh()){
            if(ch.contientOb(o))
                return true;
        }
        return false;
    }
    
    /**
     * affiche la liste des objets transportes par le joueur
     * sous la forme "joueur possede ces objets: ...."
     * @requires getNom() != null;
     * @requires getNbObjets() >= 0;
     * @requires getListObjets() != null;
     * @pure
     */
    public void afficherListObjets(){
        System.out.println("*****************");
        System.out.println( this.getNom()+ " possede ces objets:");
        for(ObjetZork ob : this.copy()){
            System.out.println(ob.descriptionLongue());
        }
        System.out.println("*****************"+"\n");
    }
    
    /**
     * affiche la liste des chiens adoptes par le joueur
     * sous la forme "joueur a adopte ces objets: milou ..."
     * @requires getNom() != null;
     * @requires getNbChien() >= 0;
     * @requires getListChien() != null;
     * @pure
     */
    public void afficherListChiens(){
        System.out.println("*****************");
        System.out.println( this.getNom()+ " a adopte ces chiens:");
        for(Chien ch : this.copyCh()){
            System.out.println(ch.getNom());
        }
        System.out.println("*****************"+"\n");
    }
    
    /**
     * ajouter un chien a la liste des chiens adoptes par le joueur
     * si le chien est deja adopte, un message est affiche pour informer le joueur
     * @param ch le chien a adopter
     * @requires ch != null;
     * @requires getNbChien()>=0;
     * @ensures getListChien() !=null;
     * @ensures \old(getNbChien()) <= getNbChien();
     * @ensures getNbChien()>0;
     * @ensures !ch.estLibre();
     */
    public void adopChien(Chien ch){
        if(!this.contientCh(ch)){
            listChien.add(ch);
            ch.adoption();
        }
        else{
            System.out.println("Vous avez deja adopter ce chien!");
        }
    }
       
    /**
      *retire le chien passe en argument de la liste des chiens adoptes par le joueur
      *permet de liberer un seul chien a chaque fois
      *@param ch le chien a liberer
      *@requires ch != null;
      *@requires getListChien() != null;
      *@requires getNbChien()>0;
      *@ensures getNbChien()>=0;
      *@ensures \old(getNbChien()) >= getNbChien();
      *@ensures ch.estLibre(); 
      */
    public void libererChien(Chien ch){
        if(contientCh(ch)==true){
           listChien.remove(ch);
           ch.liberation();
        }
    }

    /**
     * teste si le joueur a adopte le chien specifie en parametre
     * @param ch le chien a chercher parmi les chiens adoptes par le joueur
     * @requires ch != null;
     * @requires getNbChien() >= 0;
     * @requires getListChien() != null;
     * @return true si le chien est adopte par le joueur, et false sinon
     * @pure
     */
    public boolean contientCh (Chien ch){
        return listChien.contains(ch);
    }
    
}