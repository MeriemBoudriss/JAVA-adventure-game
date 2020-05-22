package zork;
 
/**
*<p>Un objet dans un jeu d'aventure</p>
*<p>Cette class fait partie du logiciel Zork,un jeu d'aventure simple en texte</p>
*<p>dans le logiciel Zork, un objet peut se trouver dans une piece et etre transporter par un 
*joueur s'il est transpodtable.
*un objet est transportable ssi poids >0 && poids <30
*@invariant getPoids()>0;
*@invariant estTransportable() <=> (getPoids()>0 && getPoids()< 30);
*@invariant descriptionCourte()!=null;
*
*@author  Meriem BOUDRISS 11808654 
*@author  Reda LAMHATTAT  11809054
*/
public class ObjetZork {
    protected int poids;
    protected boolean transportable;
    protected String description;
    
    //constructeurs
    /**
    *un objet transportable identifie par la chaine de caracteres specifie 
    *et dont le poids est l'entier specifie
    *@param description la chaine de caracteres qui specifie cet objet
    *@param poids le poids de l'objet
    *@requires description != null
    *@requires poids >0;
    *@ensures descriptionCourte().equals(description);
    *@ensures estTransportable() <=> (poids>0 && poids< 30);
    *@ensures estTransportable();
    *@ensures getPoids()==poids;
    */
    public ObjetZork (String description, int poids){
       this.description= description;
       this.poids = poids;
       if(poids< 30){
           this.transportable=true;
        }
    }
    
    /**
    *initialise un objet transportable identifie par la chaine de caracteres specifiee
    *@param description la chaine de caracteres qui specifie cet objet
    *@requires description != null
    *@ensures descriptionCourte().equals(description);
    *@ensures getPoids()==0;
    *@ensures !estTransportable();
    */
    public ObjetZork (String description){
        this.description= description;
    }
    //methodes
    /**
     * renvoie une description detaillee de l'objet,son nom, son poids en kgs
     * @requires description!=null;
     * @requires getPoids()>0;
     * @ensures descriptionLongue()!=null;
     * @return descriptionLongue description detaillee de l'objet
     * @pure
     */
    public String descriptionLongue(){
        String descriptionLongue= description+ "(";
        descriptionLongue += poids + "kgs)";
        return descriptionLongue;
    }
    
    /**
     * renvoie une chaine de caracteres representant l'objet (i.e. son nom)
     * @return une description courte de l'objet
     * @ensures descriptionCourte() != null;
     * @pure
     */
    public String descriptionCourte(){
        return description;
    }
    
    /**
     * renvoie le poids de l'objet en question
     * @return poids le poids de l'objet
     * @ensures getPoids()>0;
     * @pure
     */
    public int getPoids(){
        return poids;
    }
    
    /**
     * test si un ojbetZork est transportable ou non
     * @requires getPoids()>0;
     * @return true si l'objet est transportable, et false sinon
     * @ensures estTransportable() <=> (getPoids()>0 && getPoids()< 30);
     * @pure
     */
    public boolean estTransportable(){
        return transportable;
    }
    
    /**
    * renvoie true ssi l'objet specifie est un objetZork,
    * que les 2 objets sont transportables ou bien non transportables     
    * et s'ils ont le meme poids et la meme description
    * @param o l'objet que l'on compare avec cet instance
    * @requires estTransportable() <=> (getPoids()>0 && getPoids()< 30);
    * @return true ssi l'objet specifie est un objetZork possedent les memes parametres
    * @ensures  this.equals(o) <=> (o instanceof ObjetZork) && (getPoids()==o.getPoids())
    *                               && (estTransportable()<=>o.estTransportable()) && 
    *                               (description.equals(ob.description))
    * @pure
    */
     public boolean equals(Object o){
        if(! ( o  instanceof ObjetZork))
            return false;
        ObjetZork ob = (ObjetZork) o;
        if(estTransportable()){
            if(!ob.estTransportable()){
                return false;
            }
            if(getPoids() != ob.getPoids()){
                return false;
            }
        }
        else{
            if(ob.estTransportable()){
                return false;
            }
        }
        return description.equals(ob.description);
    }
    
    /**
    * renvoie un code de hachage pour cette instance
    * @requires estTransportable() <=> (getPoids()>0 && getPoids()< 30);
    * @requires getPoids()>0;
    * @return un code de hachage pour cette instance
    * @pure
    */
    public int hashCode(){
        if(!estTransportable()){
            return description.hashCode();
        }
        return description.hashCode()+31*(getPoids()+1);
    }
    
    /**
     * renvoie une chaine de caracteres contenant des informations 
     * sur l'etat de l'instance en question
     * elle contient tous les attributs de l'objets
     * @requires description != null;
     * @requires estTransportable() <=> (getPoids()>0 && getPoids()< 30);
     * @requires descriptionCourte() != null;
     * @requires getPoids()>0;
     * @return   une chaine de caracteres representant l'objet
     * @pure
       */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.description);
        sb.append(": Poids: "+ this.getPoids());
        if(estTransportable()){
            sb.append(", il est tansportable,");
        }
        else{
            sb.append(", il n'est pas tansportable,");
        }
        return sb.toString();
    }
}