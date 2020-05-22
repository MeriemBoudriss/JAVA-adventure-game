package zork;  

/**
 * Un aliment dans un jeu d'aventure.<p>
 * Cette classe fait partie du logiciel Zork, un jeu d'aventure 
 * simple en mode texte.</p> <p>
 * Un aliment represente un objet qui peut etre manger par 
 * les animaux presents dans le jeu.</p><p>
 * il y a deux types d'aliments : d'origine vegetale et d'origine animale.</p>
 * Un aliment est caracterise par sa valeur nutritive et son type ainsi
 * que les autres caracteristiques des objetZork.
 * 
 * @invariant getType() != null;
 * @invariant getValeur()>=0;
 * {@inheritDoc} herite la documentation de la classe ObjetZork
 * 
 * @author Meriem BOUDRISS 
 * @author Reda LAMHATTAT 
 * @version 06/12/2019
 */
public class Aliment extends ObjetZork {
    private int valeurNutritive;
    private TypeAliment type;
   
    /**
     * premier constructeur de la classe
     * initialise un aliment avec le nom,la valeur nutritive et le type,
     * il fait appel au constructeur de la super classe ObjetZork
     * qui prend un seul argument.
     * @param nom le nom de l'aliment
     * @param val la valeur nutritive de l'aliment
     * @param t le type de cet aliment
     * @requires nom !=null;
     * @requires val>0;
     * @requires t!=null;
     * @ensures getNom().equals(nom)
     * @ensures getValeur()==val;
     * @ensures getType().equals(t);
     */
    public Aliment(String nom, int val, TypeAliment t){
        super(nom);
        this.valeurNutritive = val;
        this.type = t;
        this.poids=0;
    }
    
    /**
     *deuxieme constrcuteur de la classe.
     *initialise un aliment avec le nom, le poids, le type et la valeur,
     *il fait appel au constructeur de la super classe qui 
     *prend 2 arguments le nom et le poids,
     *@param nom le nom de l'aliment
     *@param poids le poids de l'aliment
     *@param type le type de l'aliment
     *@param valeur la valeur nutritive de l'aliment
     *@requires nom != null;
     *@requires poids>=0;
     *@requires type != null;
     *@requires valeur >0;
     *@ensures getNom().equals(nom)
     *@ensures getValeur()==valeur;
     *@ensures getType().equals(type);
     *@ensures getPoids()==poids;
     */
    public Aliment(String nom, int poids, TypeAliment type, int valeur){
    	super(nom,poids);
    	this.valeurNutritive=valeur;
    	this.type=type;   	
    }
    
    /**
     * renvoie la valeur nutritive de cet aliment
     * cette valeur sera retire du capital de vie del'animal qui va 
     * manger cet aliment.
     * @return valeurNutritive la valeur nutritive de l'aliment.
     * @pure
     */
    public int getValeur(){
        return this.valeurNutritive;
    }
    
    /**
     * renvoie la description longue de cet aliment
     * @requires description!=null;
     * @requires getValeur()>0;
     * @return descriptionLongue la description detaillee de l'aliment
     * @ensures descriptionLongue()!=null;
     * @pure
     */
    @Override
    public String descriptionLongue(){
        StringBuilder sb=new StringBuilder();
        sb.append(this.description);
        sb.append("(valNut: ");
        sb.append(this.getValeur()+")");
        return sb.toString();
    }
    
    /**
     * renvoie le type de cet aliment
     * @return type le type de l'aliment
     * @pure
     */
    public TypeAliment getType(){
        return this.type;
    }
    
    /**
     * test d'egalite de deux aliments.
     * renvoie true ssi l'objet specifie est un aliment,
     * que les deux aliments sont du meme type;
     * que les deux sont transportables et de meme poids;
     * et qu'ils ont meme description et meme valeur nutritive.
     * @requires estTransportable() <=> (getPoids()>0 && getPoids()< 30);
     * @param ob l'objet que l'on compare avec cet instance.
     * @return true ssi l'objet specifie est un aliment avec les memes
     * caracteristiques que cet instance.
     * @ensures this.equals(ob) <=>(ob instanceof Aliment)
     *                     && (getPoids()==o.getPoids())
     *            && (estTransportable()<=>o.estTransportable()) 
     *            && (description.equals(ob.description))
     *            && (this.getValeur()==ob.getValeur())
     *            && (this.getType().equals(ob.getType()))
     * @pure
     */
    @Override
    public boolean equals(Object ob){
        if(!(ob instanceof Aliment))
            return false;
        Aliment al =(Aliment)ob;
        if(this.estTransportable()){
            if(!al.estTransportable())
                return false;
            if(this.getPoids() != al.getPoids()){
                return false;
            }
        }
        else{
            if(al.estTransportable())
                return false;
        }
        if(! this.description.equals(al.description)){
            return false;
        }
        if(al.getValeur() != this.getValeur()){
            return false;
        }
        return this.getType().equals(al.getType());
    }
    
    /**
     * renvoie un code de hachage pour cette instance
     * @requires getValeur()>0;
     * @requires getType() != null;
     * @requires descriptionCourte()!=null;
     * @return hash le code de hachage de cet instance
     * @pure
     */
    @Override
    public int hashCode(){
        int hash=0;
        hash +=this.getType().hashCode() + 31*getValeur();
        hash+= descriptionCourte().hashCode();
        return hash;
    }
    
    /**
     * renvoie une chaine de caracteres contenant des informations 
     * sur l'etat de l'instance en question
     * @requires description != null;
     * @requires getType()!=null;
     * @requires getValeur()>0;
     * @return une chaine de caracteres representant l'aliment
     * @pure
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.description);
        sb.append(" de type: "+ getType());
        sb.append(" ,Valeur nutritive: "+ getValeur());
        return sb.toString();
    }
}
