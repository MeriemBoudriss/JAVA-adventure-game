package zork;
 
import java.util.*;
/**
 * <p>Un chien dans un jeu d'aventure.</p>
 * Cette classe fait partie du logiciel Zork, un jeu d'aventure simple 
 * en mode texte.</p>
 *<p>Un chien represente un animal de compagnie pour chaque joueur
 * chaque joueur peut adopter un ou plusieurs chiens presents au debut du jeu 
 * en tapant la commande adopter chien
 * elle est aussi accessible au cours du jeu.
 * les chiens adoptes se deplacent au meme temps que le joueur dans les pieces
 * le joueur peut liberer les chiens adoptes.
 * les chiens peuvent ramasser des aliments dans leur reserve et les manger,
 * les chiens libres peuvent se deplacer librement dans les pieces.
 * Un chien a les memes caracteristiques que les autres animaux 
 * et qui sont decrites dans la classe AbstractAnimal. En plus,
 * des caracteristiques specifiques au chien liberte, poids...
 * Un chien peut ramasser au maximun 5 objets</p>
 * 
 * {@inheritDoc}
 * 
 * @invariant  getPoidsMax() > 0;
 * @invariant  getPoidsTotal() >= 0;
 * @invariant  getPoidsMax() >= getPoidsTotal();
 * @invariant  getNom() != null;
 * @invariant  !contient(null);
 * @invariant  getNbObjetsCh()>=0;
 * 
 * @author     Meriem BOUDRISS 
 * @version    26/12/2019
 * @since      october 2019
 */
public class Chien extends AbstractAnimal implements Adoptable{
    private boolean liberte;
    private Direction directionProposee;
    private Piece pieceProposee;
    private int poidsMax;
    private int poidsTotal;
    ArrayList<ObjetZork> objChien;
    /**
     * constructeur avec 3 parametres qui
     * initialise un chien avec un nom, poidsMax et sa piece actuelle 
     * utilise le constructeur de la super classe avec 4 arguments,
     * @param nom le nom du chien
     * @param poidsMax le poids maximum d'objets que le chien peut transporter
     * @param pi la piece actuelle dans laquelle se trouve le chien
     * @requires nom != null;
     * @requires poidsMax>0;
     * @requires pi!=null;
     * @ensures getNom().equals(nom);
     * @ensures objChien != null;
     * @ensures getPoidsMax() == poidsMax;
     * @ensures getNbObjets()==0;
     * @ensures this.pieceActuelle==pi;
     * @ensures this.getListAliments()!=null;
     * @ensures this.getObjChien()!=null;
     * @ensures estLibre();
     */
    public Chien(String nom, int poidsMax, Piece pi){
        super(nom,50,10,pi);
        this.poidsMax=poidsMax;
        this.poidsTotal=0;
        this.liberte=true;
        this.directionProposee=null;
        this.pieceProposee=null;
        this.objChien = new ArrayList<ObjetZork>();
    }
    
    
    /**
     * constructeur a 2 parametres qui initialise
     * un chien avecle nom et une collection d'aliments 
     * a placer dans sa reserve.
     * @param nom le nom du chien
     * @param alime la collection d'aliments
     * @requires nom != null;
     * @requires alimen != null;
     * @ensures getNom().equals(nom);
     * @ensures objChien != null;
     * @ensures getPoidsMax() == 25;
     * @ensures getNbObjets()==0;
     * @ensures this.pieceActuelle==null;
     * @ensures this.getListAliments()!=null;
     * @ensures this.getObjChien()!=null;
     * @ensures estLibre();
     */
    public Chien(String nom, Collection<Aliment>alime){
        super(nom,alime);
        this.objChien = new ArrayList<ObjetZork>();
        this.poidsMax=25;
        this.poidsTotal=0;
        this.liberte=true;
        this.pieceActuelle=null;
        this.directionProposee=null;
        this.pieceProposee=null;
    }
    
    /**
     * renvoie true si le chien est libre et false sinon
     * @return true si le chien est libre et false sinon
     * @pure
     */
    public boolean estLibre(){
        return this.liberte;
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean adoption(){
        if(estLibre()==true){
            this.liberte=false;
            return true;
        }
        else{
            System.out.println("animal deja adopter");
            return false; 
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public boolean liberation(){
        if(estLibre()==false){
            this.liberte=true;
            return true;
        }
        else{
            System.out.println("animal deja liberer");
            return false;
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void proposerSortie(Piece p, Direction d){
        if(p==null || d==null)
            throw new NullPointerException("la piece doit etre non nulle");
        if(p.pieceSuivante(d)==null)
            throw new IllegalArgumentException("pas de sortie dans cette direction");
        if(!estLibre())
            this.directionProposee=choisirSortie(p);
        this.directionProposee=d;
        this.pieceProposee=p;
    }
    
    /**
     * {@inheritDoc}
     */
    public Direction getSortieProposee(Piece p){
        if(p==null)
            throw new NullPointerException("la piece ne doit pas etre nulle!");
        return this.directionProposee;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Direction choisirSortie(Piece p){
        if(p==null)
                throw new NullPointerException("la piece ne doit pas etre nulle!");
        
        if(!estLibre()){
            return getSortieProposee(p);
        }
        else{
           for(Direction dir: p.getSorties()){
               return dir;
            }
        } 
        return null;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Piece deplacerDepuis(Piece p){
        if(p==null)
            throw new NullPointerException("la piece doit etre non nulle");
        if(!estVivant()){
            throw new IllegalStateException("l'animal ne peut pas se deplacer, il est mort!");
        }
        if(!p.contientAnimal(this)){
            throw new IllegalArgumentException("cet animal n'est pas dans la piece!"); 
        }
        Piece room=p.pieceSuivante(choisirSortie(p));
        if(room==null){
               return null;/*pas de deplacement*/
        }
        p.retirerAnimal(this);
        room.ajouterAnimal(this);
        this.setCapitalVie(getCapitalVie()-getCoutDeplacement());
        if(!estVivant()){
          mourirDans(room);
        }
        this.directionProposee=null;
        this.pieceActuelle=room;
        return room;
    }
    
    /**
     * renvoie le poids maximal que le chien peut transporter
     * @return poidsMax le poids maximal
     * @ensures \result >0;
     * @ensures \result >= getPoidsTotal();
     * @pure
     */
    public int getPoidsMax(){
        return this.poidsMax;
    }
    
    /**
     * renvoie le poids total des objets transportes par le chien
     * @return poidsTotal le poids total transporte par le chien
     * @ensures \result >=0;
     * @ensures \result <= getPoidsMax();
     * @pure
     */
    public int getPoidsTotal(){
        return this.poidsTotal;
    }
    
    /**
     * renvoie une description courte du chien specifiant son nom 
     * et sont poidsMax,
     * sous la forme chien X peut ramasser jusqu'a Y kgs.
     * @requires getNom() != null;
     * @requires getPoidsMax() >0;
     * @requires getPoidsMax() >= getPoidsTotal();
     * @return une description presentant le chien
     * @ensures \result !=null;
     * @pure
     */
    @Override
    public String descriptionCourte(){
        StringBuilder sb=new StringBuilder();
        sb.append(getNom());
        sb.append(" peut ramasser jusqu'a "+this.getPoidsMax()+"kgs.");
        return sb.toString();
    }
    
    /**
     * renvoie une chaine de caracters qui decrit le deplacement du chien quand il change de piece,
     * la suite de la description specifiant les pieces est ajoutee directement lors de l'appel 
     * a cette methode dans la classe Jeu.
     * @requires getNom()!=null;
     * @return une breve description sur le deplacement du chien
     * @ensures \result != null;
     * @pure
     */
    public String messageDeplacement(){
        return this.getNom()+" passe de ";
    }/*message de deplacement du chien quand le joueur change de piece */
    
    /**
     * modifie le poids total de l'inventaire du chien
     * @param poids le nouveau poids a affecter a l'attribut poidsTotal du chien
     * @requires poids >=0;
     * @ensures getPoidsTotal()== poids;
     */
    public void setPoidsTotal(int poids){
        this.poidsTotal=poids;
    }
    
    /**
     * Renvoie le cout d'une action "ramasser" de ce chen. Lors de chaque
     * ramassage d'aliment effectue par ce chien, 
     * son capital vie sera diminue de ce cout.
     * 
     * @return le cout d'une action "ramasser" pour ce chien
     * 
     * @pure
     */
    public int getCoutRamasser(){
        return 4;
    }
    
    /**
     * vide la liste des objets transportes par le chien
     * @requires lesObjets !=null;
     * @requires getNbObjets()>=0;
     * @ensures lesObjChien.isEmpty()==true;
     * @ensures getPoidsTotal()==0;
     */
    public void viderListObCh(){
        this.objChien.clear();
        setPoidsTotal(0);
    }
    
    /**
     * renvoie la liste des objets transportee par le chien
     * @return objChien la liste des objets transportee par ce chien
     * @pure
     */
    public ArrayList<ObjetZork> getObjChien(){
        return this.objChien;        
    }
    
    /**
     *renvoie le nombre d'objets transpportes par le chien
     *@requires lesObjChien != null;
     *@ensures objChien.size()>=0
     *@return objChien.size() le nombre d'objets courant dans l'invantaire du chien
     *@pure
     */
    public int getNbObjetsCh(){
        return objChien.size();
    }
    
    /**
       * renvoie le nombre d'exemplaire d'un objet specifique transporte par le chien
      * @param ob l'objet dont on cherche le nombre d'exemplaire
      * @requires ob != null
      * @requires getNbObjetsCh() >= 0;
      * @requires getObjChien() != null;
      * @return n le nombre d'exemplaire de l'objet en question
      * @ensures n >=0;
      * @pure
      */
    public int contientCombienDeOb(ObjetZork ob){
        int n=0;
        for(ObjetZork obj : objChien){
            if(obj.equals(ob)){
                n++;
            }
        }
        return n;
    }
    
    /**
     * teste si le chien transporte l'objet specifie en parametre
     * @param ob l'objet a chercher dans l'inventaire du chien
     * @requires ob != null;
     * @requires getNbObjetsCh() >= 0;
     * @requires getObjChien() != null;
     * @return true si l'objet est transporte par le chien, et false sinon
     * @pure
     */
    public boolean contientOb (ObjetZork ob){
        return objChien.contains(ob);
    }
    
    /**
     * renvoie une copie de la liste des objets transportes par le chien
     * en utilisant une autre instance de ArrayList<ObjetZork>
     * et en recopions les aliments.
     * @requires lesObjChien !=null;
     * @return res une copie de la liste des objets
     * @ensures res != lesObjChien
     * @pure
     */
    public ArrayList<ObjetZork> copyObCh(){
        ArrayList<ObjetZork> res=new ArrayList<ObjetZork>();
        for(ObjetZork o: this.objChien){
            res.add(o);
        }
        return res;
    }
    
    /**
     * affiche la liste des objets transportes par le chien,
     * sous la forme "chien X transporte les objets : fifa20(1kgs)..."
     * @requires getNom() != null;
     * @requires getListObjets() != null;
     * @requires getNbObjets() >= 0;
     * @pure
     */
    public void afficherListObjetsCh(){
        System.out.println(this.getNom()+" transporte ces objets:");
        for(ObjetZork ob : this.getObjChien()){
            System.out.println(ob.descriptionLongue());
        }
        System.out.println("\n");
    }
    
    /**
     * ajoute un nouvel objet dans la liste d'objets transpotres par le chien
     * @param ob l'objet a ajouter 
     * @requires ob != null;
     * @requires getNbObjets() >= 0;
     * @ensures  getListObjets() != null;
     * @ensures \old(getNbObjets()) =< getNbObjets();
     * @ensures \old(getPoidsTotal()) =< getPoidstotal();
     * @ensures \old(getCapitalVie()) >= getCapitalVie();
     * @ensures getPoidsTotal() <= getPoidsMax();
     * @ensures getnbObjets()<=5;
     * @ensures getNbObjets()>0;
     */
    public void ajouterObToCh(ObjetZork ob){
        /*chien ne peux pas ramasser plus que 5 objets*/
        if(getNbObjetsCh() < 5){
           if(this.contientOb(ob)==true){
                System.out.println("Votre chien transporte deja cet objet!");
           }
           if((this.getPoidsTotal()+ob.getPoids()) <= this.getPoidsMax()){
                    objChien.add(ob);
                    this.poidsTotal += ob.getPoids();
                    this.pointsVie -=10;
                    System.out.println("Nouveau poids transporte par ce chien: "+ this.getPoidsTotal());
           }
           else{
                System.out.println(this.getNom()+" ne peut pas transporter cet objet\n");
                System.out.println("il depasse le poids maximum\n");
           }
        }   
        else{
           System.out.println("Votre chien ne peut pas ramasser plus que 5 objets!");    
        }
    }
    
    /**
     * retire l'objet specifie en parametre, de la liste d'objets 
     * transportes par le chien si elle contient cet objet
     * @param ob l'objets a retirer
     * @requires ob !=null
     * @requires getListObjets() != null;
     * @requires getNbObjets()>0;
     * @requires getNbObjets()<=5;
     * @ensures  getListObjets() != null;
     * @ensures \old(getNbObjets()) >= getNbObjets();
     * @ensures \old(getPoidsTotal()) >= getPoidstotal();
     * @ensures \old(getCapitalVie()) =< getCapitalVie();
     * @ensures getPoidsTotal() <= getPoidsMax();
     * @ensures getPoidsTotal() >=0; 
     * @ensures getNbObjets()>=0;
     */
    
    public void retirerObOfCh(ObjetZork ob){ 
        if(this.contientOb(ob)== true){
            if(objChien.remove(ob)==true){
                this.poidsTotal -= ob.getPoids();
                this.pointsVie +=10;
                System.out.println("Nouveau poids transporte par le chien: "+ this.getPoidsTotal());
            }
        }
        else{
            System.out.println("Votre chien ne transporte pas cet objet!");
        }
    }
}
