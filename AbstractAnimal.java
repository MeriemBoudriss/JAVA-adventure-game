package zork; 
import java.util.*;
    
    /**
     * Les animaux dans le jeu Zork.
     * cette classe abstraite est une implementation de l'interface
     * Animal qui permet de manipuler des animaux dans le jeu Zork
     * elle est abstraite car elle contient la methode 
     * abstraite getCoutRamasser() dont l'implementation depend de 
     * chaque animal.
     * les animaux sont caracteries par le nom,les points de vie,
     * le max des points de vie, la valeur nutritive dans le cas ou cette
     * animal sera mange par un autre, et la piece actuelle.
     * {@inheritDoc}
     * 
     * @author Meriem BOUDRISS 11808654
     * @author Reda LAMHATTAT 11809054
     * @version 10/12/2019
     */
    public abstract class AbstractAnimal extends Conteneur<Aliment> implements Animal{
       protected String nom;
       protected int pointsVie;
       protected int maxPointsVie;
       protected int valeurNut;
       protected Piece pieceActuelle;
       
       /**
        * premier constructeur de la classe.
        * initialise un animal avec le nom passe en argument,
        * il fait appel au constructeur de la super classe Conteneur
        * pour initialiser la reserve des aliments de cet animal,
        * @requires nom !=null;
        * @param nom le nom de l'animal;
        * @ensures getNom().equals(nom);
        * @ensures this.getPieceActuelle()==null;
        * @ensures getValeurNutritive()==10;
        * @ensures getMaxCapitalVie()==100;
        * @ensures getCapitalVie()==30;
        * @ensures getListAliments()!=null;
        */
       public AbstractAnimal(String nom){
           super();
           this.nom=nom;
           this.pointsVie=30;
           this.maxPointsVie=100;
           this.valeurNut=10;
           this.pieceActuelle=null;
       }
       
       /**
        * deuxieme constructeur de la classe.
        * initialise un animal avec le nom, et la liste
        * d'aliments passes en arguments.
        * il fait appel au constructeur sans argument de la super classe.
        * @param nom le nom de l'animal
        * @param alime la liste des aliments
        * @requires nom !=null;
        * @requires alime !=null;
        * @ensures getListAliments() != null;
        * @ensures getNom().equals(nom);
        * @ensures this.getPieceActuelle()==null;
        * @ensures getValeurNutritive()==10;
        * @ensures getMaxCapitalVie()==100;
        * @ensures getCapitalVie()==30;
        */
       public AbstractAnimal(String nom, Collection<? extends Aliment>alime){
           super();
           this.nom=nom;
           for(Aliment al: alime){
               this.lesObjets.add(al);
           }
           this.maxPointsVie=100;
           this.pointsVie=30;
           this.valeurNut=10;
           this.pieceActuelle=null;
       }
        
       /**
        * troisieme constructeur de la classe.
        * initialise un animal avec le nom, points de vie, 
        * valeur nitrutuve et sa piece actuelle.
        * il fait appel au constructeur sans arguments de la super classe
        * pour intialiser la reserve des aliments.
        * @param nom lenom de l'animal
        * @param ptVie les points de vie que l'animal a au depart du jeu
        * @param pi la piece dans laquelle se trouve l'animal
        * @param valNut la valeur nutritive de l'animal
        * @requires pi != null;
        * @requires nom != null;
        * @requires ptVie >0;
        * @requires valNut >0;
        * @ensures getValeurNutritive()==valNut;
        * @ensures getNom().equals(nom);
        * @ensures getListAliments() != null;
        * @ensures getPieceActuelle()==pi;
        * @ensures getMaxCapitalVie()==100;
        * @ensures getCapitalVie()==30;
        */
       public AbstractAnimal(String nom, int ptVie, int valNut, Piece pi){
           super();
           this.nom=nom;
           this.pointsVie=ptVie;
           this.maxPointsVie= 100;
           this.valeurNut= valNut;
           this.pieceActuelle=pi;
       }
       
       /**
        * quatrieme constructeur de la classe.
        * initialise un animal avec le nom, points de vie, 
        * max points de vie, valeur nitritive et une liste d'aliments
        * a place dans sa reserve.
        * il faut appel au constructeur de la super classe pour 
        * initialiser la reserve des aliments.
        * @param nom le nom de l'animal
        * @param ptVie les points de vie que l'animal a au depart du jeu
        * @param maxPtVie le maximum des points de vie pour cet animal
        * @param valNut la valeur nutritive de cet animal
        * @param alime la liste des aliments  place dans sa reserve
        * @requires nom != null;
        * @requires ptVie >0;
        * @requires maxPtVie >0;
        * @requires alime != null;
        * @requires valNut >0;
        * @ensures getListAliments()!=null;
        * @ensures getValeurNutritive()==valNut;
        * @ensures getNom().equals(nom);
        * @ensures getMaxCapitalVie()==maxPtVie;
        * @ensures getCapitalVie()==30;
        * @ensures getNbAliments()>=0;
        */
       public AbstractAnimal(String nom,int ptVie,int maxPtVie,int valNut, Aliment[]alime){
          super();
          this.nom = nom;
          this.pointsVie = ptVie;
          this.maxPointsVie = maxPtVie;
          this.pieceActuelle=null;
          this.valeurNut = valNut;
          for(Aliment f : alime){
              this.lesObjets.add(f);
          }
       }
       
       /**
        * redifinition de la methode iterator()
        * elle renvoie l'iterator de la super classe ConteneurIterator().
        * elle renvoie un iterator qui permet de parcourir les aliments 
        * de la reseve.
        * @return ConteneurIterator() un interator pour parcourir la reserve.
        * @ensures \result != null;
        */
       @Override
       public Iterator<Aliment> iterator(){
           return ConteneurIterator();
       }
       
       /**
        * {@inheritDoc}
        */
       public String getNom(){
           return this.nom;
       }
       
       /**
        * {@inheritDoc}
        * @ensures \result != null;
        */
       public String descriptionCourte(){
            return getNom() +", points de vie: "+ getCapitalVie();
       }    
       
       /**
        * {@inheritDoc}
        * @ensures \result != null;
        */
       public String descriptionLongue(){
            StringBuilder sb = new StringBuilder();
            sb.append(getNom());
            sb.append(", Points de vie: "+ getCapitalVie());
            if(this.getClass().equals(Chien.class)){
               Chien hh=(Chien)this;
               sb.append(" ,reverse d'objets: "+hh.getObjChien().toString());
            }
            sb.append(" ,reverse d'aliments: "+this.getListAliments().toString());
            return sb.toString();
       }
       
       /**
        * {@inheritDoc}
        */
       public int getCapitalVie(){
            return this.pointsVie;
       }
       
       /**
        * {@inheritDoc}
        */
       public int getMaxCapitalVie(){
            return this.maxPointsVie;
       }
       
       /**
        * modifie les points de vie de l'animal par la nouvelle 
        * valeur passe en arguments
        * @param vie le nouveau capitalVie de l'animal
        * @requires vie >=0;
        * @ensures getCapitalVie()==vie;
        */
       public void setCapitalVie(int vie){
           this.pointsVie=vie;
       }
       
       /**
        * {@inheritDoc}
        */
       public boolean estVivant(){
            if(this.getCapitalVie()>0){
                return true;
            }
            return false;
       }
       
       /**
        * {@inheritDoc}
        */
       public void mourirDans(Piece p){
            if(p==null){
                throw new NullPointerException("La piece doit etre non nulle!");
            }
            setCapitalVie(0);
            for(Aliment ali : this.getListAliments()){
                p.ajouter(ali);
            }
            if(this.getClass().equals(Chien.class)){
                Chien hh=(Chien)this;
                for(ObjetZork obj : hh.getObjChien()){
                    p.ajouter(obj);
                }
                hh.viderListObCh();
            }
            this.lesObjets.clear();
            this.pieceActuelle=p;
       }
       
       /**
        * {@inheritDoc}
        */
        public int getValeurNutritive(){
           return this.valeurNut;
       }
       
       /**
        * {@inheritDoc}
        */
       public boolean peutManger(Aliment al){
           if(al==null)
                return false;
           return getRegimeAlimentaire().contains(al.getType());
       }
       /*la methode getNbAliments() est deja definit dans la super 
          classe Conteneur*/
       /**
        * {@inheritDoc}
        * l'objet passe en agrument peut etre un aliment ou un objetZork
        */
       public  int getNbOccurences(Object obj){
           if(!(obj instanceof Aliment) || !(obj instanceof ObjetZork)){
                return 0;
           }
           int n=0;
           for(Object o: this.lesObjets){
               if(obj.equals(o)){
                   n++;
               }
           }
           return n;
       }
       /*la methode contient est deja defini dans la classe Conteneur<T>*/
       /**
        * {@inheritDoc}
        */
       public boolean reserveEstVide(){
            if(getNbAliments()==0){
                return true;
            }
            return false;
       }
       
       /**
        * {@inheritDoc}
        */
       public Aliment choisirAliment(Piece p){
           if(p==null){
             throw new NullPointerException("piece doit etre non nulle!");  
           }
           if(p.getNbAliments()>0){
               for(Aliment al: p.getListAliments()){
                   if(peutManger(al)){
                       return al;
                    }
               }
           }
           return null;
        }
       
       /**
        * {@inheritDoc}
        * diminuer le capital de vie de l'animal de ce cout
        * pour chaque action de ramassage;
        * cette methode est implementee dans les sous-classes car 
        * elle depend de chaque animal.
        */
       public abstract int getCoutRamasser();
       
       /**
        * {@inheritDoc}
        */
       public Aliment ramasserDans(Piece p){
          if(p==null){
             throw new NullPointerException("la piece ne doit pas etre nulle!");
          }
          if(!estVivant()){
             throw new IllegalStateException("l'animal ne peut pas ramasser des aliments!");
          }
          Aliment res= choisirAliment(p);
          if(!this.contientUnAl(res)){
              this.ajouter(res);
              this.setCapitalVie(getCapitalVie() - getCoutRamasser());
          }
          if(getCapitalVie()<=0){
              mourirDans(p);
              return null;
          }
          else{
             p.retirer(res); 
          }
          return res;
       }
       
       /**
        * renvoie la piece actuelle dans laquelle se trouve l'animal
        * @return la piece actuelle dans laquelle se trouve l'animal
        * @ensures \result != null;
        * @ensures \result.contientAnimal(this);
        * @pure
        */
       public Piece getPieceActuelle(){
           return this.pieceActuelle;
        }
        
        /**
         * {@inheritDoc}
         */
        public int getCoutDeplacement(){
          return getCoutRamasser()+2;
       }  
   
       /**
        * {@inheritDoc}
        */
       public Piece deplacerDepuis(Piece p){
           if(p==null){
                 throw new NullPointerException("la piece ne doit pas etre nulle!");
           }
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
           this.pieceActuelle=room;
           return room;
       }
   
       /**
        * {@inheritDoc}
        * renvoie la premiere sortie possible de la piece
        * actulle de l'animal
        * et renvoie null si aucune direction n'est possible
        */
       public Direction choisirSortie(Piece p){
           if(p==null)
                throw new NullPointerException("la piece ne doit pas etre nulle!");
           
           for(Direction dir: p.getSorties()){
              if(dir!=null)
                    return dir;
            }
           return null;
      }
   
      /**
       * {@inheritDoc}
       */
      public Set<TypeAliment> getRegimeAlimentaire(){
          Set<TypeAliment> res= new HashSet<TypeAliment>();
          if(this.getClass().equals(Chien.class)){
              res.add(TypeAliment.VEGETAL);
              res.add(TypeAliment.ANIMAL);
          }
          else if(this.getClass().equals(Chevre.class))
              res.add(TypeAliment.VEGETAL);
          else if(this.getClass().equals(Loup.class))
              res.add(TypeAliment.ANIMAL);
          return res;
      }
         
       /**
        * renvoie le premier aliment present dans la reserve de l'animal
        * et correspondant a son regime alimenataire.
        * renvoie null si la reserve est vide.
        * {@inheritDoc}
        */
       public Aliment getAliment(){
           if(this.getNbAliments()>0){
                for(Aliment alm : this.getListAliments()){
                    if(this.peutManger(alm))
                        return alm;
                }
           }
           return null;
       }
       
       /**
        * {@inheritDoc}
        */
       public Aliment manger(){
           if(!estVivant())
                throw new IllegalStateException("l'animal est mort, il ne peut pas manger");
           Aliment am=getAliment();
           if(am!=null){
               this.retirer(am);
               this.setCapitalVie(getCapitalVie()+am.getValeur());
           }
           return am;
       }
}