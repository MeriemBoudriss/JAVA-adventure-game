 package zork;
 
import java.util.*;
/**
 * Conteneur<T extends ObjetZork> est une classe generique pour 
 * la manipulation des ArrayList<ObjetZork> et aussi des sous-classe
 * de ObjetZork comme Aliment.
 * les classes heritantes de Conteneur<T> sont Joueur, Piece, 
 * AbstractAnimal et toute autre classe utilisant un ArrayList pour la
 * reserve des objets ou des aliments.
 * @invariant getNbObjets()>=0;
 * @invariant getNbAliments()>=0;
 * @invariant getConteneur() != null;
 * @invariant !contient(null);
 * @invariant !contientUnAl(null);
 * @invariant contientCombienDe(T ob)>=0;
 * @invariant getListObjets() != null;
 * @invariant getListAliments() != null;
 * 
 * @author Meriem BOURDRISS 11808654
 * @version 07/12/2019
 */
public class Conteneur<T extends ObjetZork>{
    protected ArrayList<T>lesObjets;
    
    /**
     * constructeur de la classe generique Conteneur<T>
     * initialise un conteneur decrit par une ArrayList<T>.
     * @ensures lesObjets!=null
     */
    public Conteneur(){
        lesObjets = new ArrayList<T>();
    }
    
    /**
     * deuxieme constructeur de le classe generique Conteneur<T>
     * initialise un conteneur avec une list qui est une ArrayList<T>
     * d'elements de type T, cette liste est passee en arguments
     * @param list la liste des elements a ajouter
     * @requires list !=null; 
     * @ensures lesObjets!=null;
     */
    public Conteneur(ArrayList<T>list){
        lesObjets = new ArrayList<T>();
        for(T in: list){
            lesObjets.add(in);
        }
    }
    
    /**
     * renvoie la liste des Objets
     * @return lesObjets la liste des objets
     * @pure
     */
    public ArrayList<T> getConteneur(){
        return this.lesObjets;        
    }
    
    /**
     *renvoie le nombre d'objets dans le conteneur
     *qui correspond a la liste d'objets pour piece 
     *et a l'inventaire pour joueur
     *@requires lesObjets != null;
     *@return n le nombre d'objets courant dans le conteneur
     *@ensures \result>=0;
     *@pure
     */
    public int getNbObjets(){
        int n=0;
        for(T in : this.lesObjets){
            if(in instanceof ObjetZork)
                n++;
        }
        return n;
    }
    
    /**
     * renvoie lenombre d'aliments presents dans le conteneur
     * @return n le nombre d'aliments presents dans la liste.
     * @requires lesObjets != null;
     * @ensures \result>=0;
     * @pure
     */
    public int getNbAliments(){
        int n=0;
        for(T in : this.lesObjets){
            if(in instanceof Aliment)
                n++;
        }
        return n;
    }
    
    /**
     * teste si un objet est dans le conteneur
     * @param ob l'objet a chercher dans le conteneur
     * @requires ob != null
     * @requires getListObjets() !=null;
     * @requires getNbObjets()>=0;
     * @return true si l'objet est dans le conteneur, et false sinon
     * @pure
     */
    public boolean contient (Object ob){
        return lesObjets.contains(ob);
    }
    
    /**
     * teste si un aliment est dans le conteneur
     * @param al l'aliment a chercher dans le conteneur
     * @requires al != null
     * @requires getListAliments() !=null;
     * @requires getNbAliments()>=0;
     * @return true si l'aliment est dans le conteneur, et false sinon
     * @pure
     */
    public boolean contientUnAl(Aliment al){
        return lesObjets.contains(al);
    }
    
    /**
     * renvoie une copie de la liste d'elements(objet/aliment)
     * @requires lesObjets !=null
     * @return arr la copie de la liste des elements
     * @ensures arr != lesObjets
     * @return une copie de la liste d'elements presents dans le conteneur
     * @pure
     */
    public ArrayList<T> copy(){
        ArrayList<T> arr= new ArrayList<T>();
        for(T in : lesObjets){
            arr.add(in);
        }
        return arr;
    }
    
    /**
     * renvoie un iterator qui permet de parcourir la liste des objets
     * @return lesObjets.iterator() uniterator permettant de parcourir la liste.
     * @requires lesObjets != null;
     * @ensures \result != null;
     */
    public Iterator<T> ConteneurIterator(){
        return lesObjets.iterator();
    }
    
    
    /**
     * renvoie le nombre d'occurence d'un element 
     * passe en arguments(objets/aliment)
     * @param ob l'element dont on cherche le nombre d'occurence 
     * @requires ob != null;
     * @requires getNbObjets()>=0;
     * @requires getNbAliments()>=0;
     * @requires getListObjets() !=null;
     * @requires getListAliments() !=null;
     * @ensures n >= 0
     * @return n un entier qui represente le nombre d'exemplaires 
     * d'un element dans la liste
     * @pure
     */
    public int contientCombienDe(T ob){
        int n=0;
        for(T obj : this.copy()){
            if(obj.equals(ob)){
                n++;
            }
        }
        return n;
    }
    
    /**
     *renvoie la liste des objetZork presents dans le conteneur
     *@requires lesObjets != null
     *@requires getNbObjets()>=0;
     *@return res la liste des objets
     *@ensures \result != null;
     *@pure
     */
    public ArrayList<ObjetZork> getListObjets(){
        ArrayList<ObjetZork> res= new ArrayList<ObjetZork>();
        for(T in: this.lesObjets){
            if(in  instanceof ObjetZork )
                res.add(in);
        }
        return res;
    }
     
    /**
     *renvoie la liste des aliments presents dans le conteneur
     *@requires lesObjets != null
     *@requires getNbAliments()>=0;
     *@return res la liste des aliments
     *@ensures \result != null;
     *@pure
     */
    public ArrayList<Aliment> getListAliments(){
        ArrayList<Aliment> res= new ArrayList<Aliment>();
        for(T in: this.lesObjets){
            if(in instanceof Aliment)
                res.add((Aliment)in);
        }
        return res;
    }
   
    /**
     * ajoute un nouvel element(objet ou aliment) dans la liste d'objets conteneur
     * @param ob l'objet a ajouter 
     * @requires ob!=null
     * @requires getNbObjets() >=0;
     * @requires getNbAliments() >=0;
     * @ensures \old(getNbObjets()) =< getNbObjets() || \old(getNbAliments()) =< getNbAliments();
     * @ensures getNbObjets() >0;
     * @ensures getNbAliments() >0;
     * @ensures getListObjets() != null || getListAliments() !=null;
     */
    public void ajouter(T ob){
            lesObjets.add(ob);
    }
    
    /**
     * retire l'element specifie (soit aliment ou objet) de la liste des 
     * elements conteneur
     * @param ob l'element a ajouter
     * @requires ob != null
     * @requires getListObjets() != null;
     * @requires getListAliments() != null;
     * @ensures contient(ob) => \old(getNbObjets()) > getNbObjets() || 
     *                             \old(getNbAliments()) > getNbAliments();
     */
    public void retirer (T ob){
        lesObjets.remove(ob);
    }
    
   /**
     *ajoute tous les elements(aliments ou objetsZork) de la liste l
     *dans la liste lesObjets
     *@param l la liste d'aliments/ objetsZork a ajouter
     *@requires l != null;
     *@requires l.size()>=0;
     *@ensures \old(getNbAliments()) =< getNbAliments() || 
     *                      \old(getNbObjets()) =< getNbObjets();
     */
   public void ajouterListT(ArrayList<T>l){
        for(T in: l){
            lesObjets.add(in);
        }
   }
}
