package zork;
 
import java.util.*;
/**
 *  Classe principale du jeu "Zork". <p>
 *
 *  Zork est un jeu d'aventure avec une interface en mode
 *  texte: le joueur peut se deplacer dans les differentes pieces, prendre des objets et les deposer,
 *  il y a aussi des chiens de compagnie, il peut en adopter plusieus et aussi les liberer.</p><p>
 *  Cartaines pieces contiennent des objets precieux, la mission du joueur est de chercher 
 *  les objets de valeurs, tout en faisant attention aux obstacles.</p>
 *
 *  Pour jouer a ce jeu, creer une instance de cette classe et appeler sa
 *  methode "jouer". </p> <p>
 *
 *  Cette classe cree et initialise des instances de toutes les autres classes:
 *  elle cree toutes les pieces, cree l'analyseur syntaxique et demarre le jeu.
 *  Elle se charge aussi d'executer les commandes que lui renvoie l'analyseur
 *  syntaxique.</p>
 *
 * @author     Michael Kolling
 * @author     Marc Champesme (pour la traduction francaise)
 * @author     Meriem BOUDRISS  (pour l'amelioration)
 * @author     Reda LAMHATTAT  (pour l'amelioration)
 * @version    1.2
 * @since      March 2000
 */
public class Jeu {
    private AnalyseurSyntaxique analyseurSyntaxique;
    private Piece pieceCourante;
    private ArrayList<Animal> lesAnimaux;
    /*les objets du garage*/
    ObjetZork marteau= new ObjetZork("marteau",2);
    ObjetZork velo = new ObjetZork("velo",7);
    ObjetZork pneu = new ObjetZork("pneu",4);
    ObjetZork outilsDeBricolage = new ObjetZork("outilsDeBricolage",4);
     /*les objets de la cuisine*/
    ObjetZork micrOnde = new ObjetZork("micrOnde",8);
    ObjetZork assiettes = new ObjetZork("assiettes",3);
    ObjetZork croquettes = new ObjetZork("croquettes",2);
    ObjetZork frigo = new ObjetZork("frigo",35);
   
    /*les objets dans le salon*/
    ObjetZork tele = new ObjetZork("tele",15);
    ObjetZork canape = new ObjetZork("canape",20);
    ObjetZork vase = new ObjetZork("vase",3);
    ObjetZork abatJour = new ObjetZork("abatJour",2);
    
    /*permet de finir le jeu*/
    ObjetZork terminer=new ObjetZork("terminer");
    /*permet de finir le jeu en cas des loups*/
    ObjetZork finirLoup=new ObjetZork("finirLoup");
    
    /*les objets dans la salle de jeu*/
    ObjetZork playstation4 = new ObjetZork("playstation4",1);
    ObjetZork fifa20 = new ObjetZork("fifa20",1);
    ObjetZork piano = new ObjetZork("piano",16);
    ObjetZork guitare = new ObjetZork("guitare",4);

    /*les objets dans le bureau*/
    ObjetZork ordinateur = new ObjetZork("ordinateur",5);
    ObjetZork chaise1 = new ObjetZork("chaise1",3);
    ObjetZork imprimante = new ObjetZork("imprimante",4);
    ObjetZork chaise2 = new ObjetZork("chaise2",3);
    
    /*les objets dans la chambre des parents*/
    ObjetZork collierOr = new ObjetZork("collierOr",1);
    ObjetZork lit = new ObjetZork("lit",50);/*non transportable*/
    ObjetZork bagueOr = new ObjetZork("bagueOr",1);
    
    /*la clef est ajouter aleatoirement dans une des pieces*/
    ObjetZork clef= new ObjetZork("clef",1);
    
    private boolean termine = false;
    private Joueur joueur= new Joueur("joueur");
    
    /**
     * <p> Cree le jeu et initialise la carte du jeu (i.e.les pieces).
     */
    public Jeu() {
        creerPieces();
        analyseurSyntaxique = new AnalyseurSyntaxique();
    }
    
    /**
     * renvoie une copie de la liste des animaux 
     * presents dans le jeu
     * @return lanim la copie de la liste des animaux
     * @requires this.lesAnimaux != null;
     * @ensures lanim != null;
     * @pure
     */
    public ArrayList<Animal> getLesAnimaux(){
        ArrayList<Animal> lanim =new ArrayList<Animal>();
        for(Animal in : this.lesAnimaux){
            lanim.add(in);
        }
        return lanim;
    }
    
    /**
     * renvoie la liste des chiens disponibles pour l'adoption
     * @return res la liste des chiens libres
     * @requires this.lesAnimaux != null;
     * @ensures res != null;
     * @pure
     */
    public ArrayList<Chien> getChienDispo(){
       ArrayList<Chien>res=new ArrayList<Chien>();
       for(Animal a: this.lesAnimaux){
           if(a.getClass().equals(Chien.class))
                res.add((Chien)a);
       }
       return res;
    }
    
    /**
     *  Cree toutes les pieces et relie leurs sorties les unes aux autres.
     *  ajoute les objets dans les pieces correspondantes.
     *  ajoute la clef aleatoirement dans une des pieces (sauf l'entree).
     *  cree toutes les animaux et les ajoute dans les pieces.
     *  cree les aliments et les repartie dans les pieces
     *  le jeu commence devant l'entree de la maison
     *  @ensures pieceCourante != null;
     */
    public void creerPieces() {
        // creation des pieces
        Piece salon = new Piece("salon");
        Piece salleDeJeu = new Piece("salleDeJeu");
        Piece cuisine = new Piece("cuisine");
        Piece chambreDesParents = new Piece("chambreDesParents");
        Piece entree = new Piece("entree");
        Piece garage = new Piece("garage");
        Piece bureau= new Piece("bureau");

        // initialise les sorties des pieces
        entree.setSorties(salon, garage, null, cuisine);
        cuisine.setSorties(chambreDesParents, entree, null, null);
        garage.setSorties(salleDeJeu, null, null, entree);
        salleDeJeu.setSorties(bureau, null, garage, salon);
        salon.setSorties(null, salleDeJeu, entree, chambreDesParents);
        chambreDesParents.setSorties(null, salon, cuisine, null);
        bureau.setSorties(null, null, salleDeJeu, null);
        // le jeu commence devant l'entree de l'appartement
        pieceCourante = entree;
        
        //creation + ajout des aliments
        Aliment al1= new Aliment("tomate",5,TypeAliment.VEGETAL);
        Aliment al2= new Aliment("poisson",10,TypeAliment.ANIMAL);
        Aliment al3= new Aliment("viande",7,TypeAliment.ANIMAL);
        Aliment al4= new Aliment("herbes",10,TypeAliment.VEGETAL);
        Aliment al5= new Aliment("poulet",12,TypeAliment.ANIMAL);
        Aliment al6= new Aliment("carottes",6,TypeAliment.VEGETAL);
        Aliment al7= new Aliment("insectes",8,TypeAliment.ANIMAL);
        Aliment al8= new Aliment("plantes",13,TypeAliment.VEGETAL);
        Aliment al9= new Aliment("epinards",15,TypeAliment.VEGETAL);
        Aliment al10= new Aliment("oeufs",6,TypeAliment.ANIMAL);
        Aliment al11= new Aliment("Champignons",6,TypeAliment.VEGETAL);
        Aliment al12= new Aliment("canard",12,TypeAliment.ANIMAL);
        Aliment al13= new Aliment("pigeon",9,TypeAliment.ANIMAL);
        Aliment al14= new Aliment("pomme",7,TypeAliment.VEGETAL);
        //ajout des objets dans le garage
        garage.ajouter(velo);
        garage.ajouter(pneu);
        garage.ajouter(outilsDeBricolage);
        garage.ajouter(marteau);
        garage.ajouter(al8);
        garage.ajouter(al7);
        
        //ajout des objets dans la cuisine
        cuisine.ajouter(micrOnde);
        cuisine.ajouter(assiettes);
        cuisine.ajouter(croquettes);
        cuisine.ajouter(frigo);
        cuisine.ajouter(al1);
        cuisine.ajouter(al3);
        //ajout des objets dans le salon
        salon.ajouter(tele);
        salon.ajouter(canape);
        salon.ajouter(vase);
        salon.ajouter(abatJour);
        salon.ajouter(al14);
        salon.ajouter(al2);
        //ajout des objets dans le bureau
        bureau.ajouter(ordinateur);
        bureau.ajouter(chaise1);
        bureau.ajouter(chaise2);
        bureau.ajouter(imprimante);
        bureau.ajouter(al11);
        bureau.ajouter(al10);
        //ajout des objets dans la salle de jeu
        salleDeJeu.ajouter(playstation4);
        salleDeJeu.ajouter(fifa20);
        salleDeJeu.ajouter(piano);
        salleDeJeu.ajouter(guitare);
        salleDeJeu.ajouter(al12);
        salleDeJeu.ajouter(al6);
        //ajout des objets dans le bureau
        chambreDesParents.ajouter(collierOr);
        chambreDesParents.ajouter(lit);
        chambreDesParents.ajouter(bagueOr);
        chambreDesParents.ajouter(al9);
        chambreDesParents.ajouter(al13);
        //dans entree
        entree.ajouter(al4);
        entree.ajouter(al5);
        ///////////////////
        this.lesAnimaux=new ArrayList<Animal>();
        Animal loup1 = new Loup("Loup: loup1",100,10,50,garage);
        garage.ajouterAnimal(loup1);
        Animal loup2 = new Loup("Loup: loup2",100,5,50,entree);
        entree.ajouterAnimal(loup2);
        Animal chevre1= new Chevre("Chevre: belle",50,10,bureau);
        bureau.ajouterAnimal(chevre1);
        Animal chevre2= new Chevre("Chevre: douce",50,10,entree);
        entree.ajouterAnimal(chevre2);
        Animal chevre3= new Chevre("Chevre: zoe",50,10,cuisine);
        cuisine.ajouterAnimal(chevre3);
        Animal chevre4= new Chevre("Chevre: maya",50,10,salon);
        salon.ajouterAnimal(chevre4);
        lesAnimaux.add(loup1);
        lesAnimaux.add(loup2);
        lesAnimaux.add(chevre1);
        lesAnimaux.add(chevre2);
        lesAnimaux.add(chevre3);
        lesAnimaux.add(chevre4);
        //chiens
        Chien chien1 = new Chien("milou",8,entree);
        entree.ajouterAnimal(chien1);
        Chien chien2 = new Chien("enzo",20,entree);
        entree.ajouterAnimal(chien2);
        Chien chien3 = new Chien("romeo",10,entree);
        entree.ajouterAnimal(chien3);
        Chien chien4 = new Chien("charlie",15,entree);
        entree.ajouterAnimal(chien4);
        Chien chien5 = new Chien("chichi",25,entree);
        entree.ajouterAnimal(chien5);
        lesAnimaux.add(chien5);
        lesAnimaux.add(chien1);
        lesAnimaux.add(chien2);
        lesAnimaux.add(chien3);
        lesAnimaux.add(chien4);
        ///////////////////
        //ajout aleatoire de la clef
        int rnd= (int) (1+(Math.random()*5));
        switch(rnd) {
            case 1:
                garage.ajouter(clef);
                break;
            case 5:
                salon.ajouter(clef);
                break;
            case 2:
                cuisine.ajouter(clef);
                break;
            case 3:
                salleDeJeu.ajouter(clef);
                break; 
            case 4:
                bureau.ajouter(clef);
                break;
            default:
                System.out.println("clef n'est pas ajoutee");
        }
    }

    /**
     *  Pour lancer le jeu. Boucle jusqu'a la fin du jeu.
     *  le jeu se termine soit avec la commande "quitter"
     *  soit le joueur a gagne lorsqu'il atteint le scoreMax
     *  soit le joueur a perdu lorsqu'il a perdu tous ses points
     *  soit le joueur a perdu en choisissant le marteau pour ouvrir
     *  la chambre et donc l'objet terminer permet de terminer le jeu
     *  soit le joueur choisi de se deplacer dans une piece qui
     *  contient des loups et donc l'objet finirLoup permet 
     *  de finir le jeu.
     *  @requires pieceCourante != null;
     *  @requires joueur != null;
     *  @ensures (termine==true)<=>(joueur.getScore()>=joueur.getScoreMax())
     *              ||(joueur.getScore()==0) ||(pieceCourante.contient(termier))
     *              || (pieceCourante.contient(finirLoup))
     */
     public void jouer() {
        afficherMsgBienvennue();
        // Entree dans la boucle principale du jeu. Cette boucle lit
        // et execute les commandes entrees par l'utilisateur, jusqu'a
        // ce que la commande choisie soit la commande "quitter"
        while (!termine) {
            Commande commande = analyseurSyntaxique.getCommande();
            termine = traiterCommande(commande);
            System.out.println("\n"+"piece Courante: "+ pieceCourante.descriptionCourte());
            System.out.println(pieceCourante.descriptionSorties());
            if(joueur.getScore()>=joueur.getScoreMax()){
                System.out.println("VOUS AVEZ GAGNE");
                System.out.println("vous avez atteint le score maximum, admirez vos gains!!");
                System.out.println("la porte de l'appartement est ouverte! vous pouvez sortir tranquillement!!");
                termine=true;
            }
            if(joueur.getScore()<=0){
                System.out.println("\n VOUS AVEZ PERDU :( x(");
                System.out.println("Vous avez perdu tous vos points!");
                System.out.println("votre score est a "+joueur.getScore());
                termine=true;
            }
            if(pieceCourante.contient(finirLoup)){
                System.out.println("\nVOUS AVEZ PERDU :( x(");
                System.out.println("LES LOUPS NE SONT PAS DES ANIMAUX DOMESTIQUES!");
                termine=true;
            }
            if(pieceCourante.contient(terminer)){
                System.out.println("VOUS AVEZ PERDU, LES VOISINS ONT ENTENDU LE BRUIT DU MARTEAU,LA POLICE A ENTOURE LA MAISON");
                termine=true;
            }
        }
        System.out.println("Merci d'avoir jouer.  Au revoir.");
    }

    /**
     *  Affiche le message d'accueil pour le joueur, sa mission et comment acceder aux commandes en cas de besoin.
     *  @requires pieceCourante.descriptionSorties() != null;
     *  @pure
     */
    public void afficherMsgBienvennue() {
        System.out.println();
        System.out.println("Bienvenu dans le monde de Zork !");
        System.out.println("Zork est un nouveau jeu d'aventure,\n");
        System.out.println("votre mission comme voleur est de ramasser les objets de valeur");
        System.out.println("et fuir avant l'arrivee de la police!!\n");
        System.out.println("ATTENTION, ne faites pas du bruit, sinon les voisins vont appeler la police");
        System.out.println("il existe des chiens de compagnie, vous pouvez en adopter plusieurs.\n");
        System.out.println("Pour cela, tapez \"adopter chien\"");
        System.out.println("Tapez 'aide' si vous avez besoin d'aide.");
        System.out.println();
        pieceCourante.descriptionLongue();
        System.out.println(pieceCourante.descriptionSorties());
    }
  
    /**
     *  Execute la commande specifiee. Si cette commande termine le jeu, la valeur
     *  true est renvoyee, sinon false est renvoyee
     *  si la deuxieme partie de la commande n'est pas entree par l'utilisateur,
     *  un message d'erruer est affiche.
     *  
     * @param  commande  La commande a executer
     * @return          true si cette commande termine le jeu ; false sinon.
     */
     public boolean traiterCommande(Commande commande) {
        if (commande.estInconnue()) {
            System.out.println("Je ne comprends pas ce que vous voulez...");
            return false;
        }
        String motCommande = commande.getMotCommande();
        if (motCommande.equals("aide")) {
            afficherAide();
        } else if (motCommande.equals("aller")) {
            deplacerVersAutrePiece(commande);
        } else if (motCommande.equals("quitter")) {
            if (commande.aSecondMot()) {
                System.out.println("Quitter quoi ?");
            } else {
                return true;
            }
        }
        else if(motCommande.equals("info")){
            information();
        }
        else if(!commande.aSecondMot()){
            switch(commande.getMotCommande()){
                case ("aller"):
                    System.out.println("Aller ou? (ajouter une direction exp: aller NORD)");
                    break;
                case ("afficher"):
                    System.out.println("afficher quoi? (objetsPiece/objetsJoueur/objetsChiens/etatdesanimaux/positionanm)");
                    break;
                case ("adopter"):
                    System.out.println("adopter quoi? (chien)");
                    break;
                case ("liberer"):
                    System.out.println("liberer quoi? (chien)");
                    break;
                case ("prendreObParCh"):
                    System.out.println("precisez le nom de l'objet (exp: prendreObparCh velo)");
                    break;
                case ("prendre"):
                    System.out.println("prendre quoi? precisez l'objet(exp: prendre velo)");
                    break;
                case ("deposer"):
                    System.out.println("deposer quoi? precisez l'objet(exp: deposer velo)");
                    break;
                case ("deposerObParCh"):
                    System.out.println("precisez le nom de l'objet (exp: deposerObParCh velo)");
                    break;
                case ("nbObjets"):
                    System.out.println("nbObjets de quoi? (pieceCourante/inventaire/chiens)");
                    break;
                default:
                    System.out.println("je ne comprends pas ce que vous voulez!");
            }
        } 
        else if(motCommande.equals("afficher")){
            affichage(commande);
        }
        else if(motCommande.equals("adopter")){
            adopterDesChiens(commande);
        }
        else if(motCommande.equals("liberer")){
            libererUnChien(commande);
        }
        else if(motCommande.equals("prendreObParCh")){
            prendreUnObjetsParUnChien(commande);
        }
        else if (motCommande.equals("prendre")){
            prendreUnObjetParUnJoueur(commande);
        }
        else if(motCommande.equals("deposerObParCh")){
            deposerUnObjetParUnChien(commande);
        }
        else if (motCommande.equals("deposer")){
            deposerObjetParUnJoueur(commande);
        }
        else if(motCommande.equals("nbObjets")){  
            nombreObjets(commande);
        }
        return false;
    }
    // implementations des commandes utilisateur:
    /**
     *  Affichage de l'aide. Affiche notament la liste des commandes utilisables.
     *  @pure
     */
    public void afficherAide() {
        System.out.println("Vous etes perdu. Vous etes seul. Vous errez");
        System.out.println();
        System.out.println("Les commandes reconnues sont:");
        analyseurSyntaxique.afficherToutesLesCommandes();
    }
    
    /**
     * Affichage des informations sur l'etat du jeu, le score, le poids et les objets transportes
     * @pure
     */
    public void information(){
        System.out.println("Score: "+joueur.getScore());
        System.out.println("Poids total: "+joueur.getPoidsTotal());
        System.out.println("Objets transportes: ");
        joueur.afficherListObjets();
        System.out.println("Chiens adoptes: ");
        joueur.afficherListChiens();
        System.out.println("Objets transportes par les chiens: ");
        for(Chien ch : joueur.copyCh()){
                ch.afficherListObjetsCh();
        }
    }
    
    /**
     * <p>Affichage des objets.
     * Affiche les objets dans la piece,ou ceux transportes par le joueur, ou les chiens.
     * affiche aussi l'etat des animaux (points de vie/reserve/...) 
     * et leur position dans les pieces,
     * selon la commande entree</p>
     * @param commande dont le second mot est la liste a afficher
     * @pure
     */
    public void affichage(Commande commande){ 
        String s =commande.getSecondMot();
        if(s.equals("objetsPiece")){
            pieceCourante.descriptionLongue();
        }
        else if(s.equals("objetsJoueur")){
            joueur.afficherListObjets();
        }
        else if(s.equals("objetsChiens")){
            for(Chien ch : joueur.copyCh()){
                ch.afficherListObjetsCh();
            }
        }
        else if(s.equals("etatdesanimaux")){
            for(Animal a: this.getLesAnimaux()){
                System.out.println(a.descriptionLongue());
            }
        }
        else if(s.equals("positionanm")){
            System.out.println("");
            for(Animal a: this.getLesAnimaux()){
                AbstractAnimal anm=(AbstractAnimal)a;
                System.out.println(a.getNom()+" est dans "+ anm.getPieceActuelle().descriptionCourte());
            }
        }
        else{
            System.out.println("erreur de saisi. options disponible:");
            System.out.println("(afficher objetsPiece/objetsJoueur/objetsChiens/etat des animaux/positionanm)");
        }
    }
    
    /**
     * Affiche le nombres des objets soit dans la piece, soit transportes par le joueur ou les chiens
     * selon la commande entree par l'utilisateur
     * @param commande dont le second mot est la liste 
     * dont on cherche le nombre d'elements
     * @pure
     */
    public void nombreObjets(Commande commande){
        String s =commande.getSecondMot();
        switch (s){
            case ("pieceCourante"):
                System.out.println(pieceCourante.descriptionCourte()+" contient " +pieceCourante.getNbObjets()+" objets \n");
                System.out.println(" et "+pieceCourante.getNbAliments()+" aliments");
                break;
            case("inventaire"):
                System.out.println("Vous transportez "+joueur.getNbObjets()+" objets \n");
                break;
            case("chiens"):
                for(Chien ch: joueur.copyCh()){
                    System.out.println(ch.getNom()+" transporte "+ch.getNbObjets()+" objets");
                }
                break;
            default: 
                System.out.println("\n");
        }
    }
    
    /**
     * ajoute l'objet specifie par la commande dans l'inventaire du joueur
     * si l'objet n'est pas dans la piece courante, un message d'erreur est affiche
     * affiche aussi l'inventaire du joueur apres l'ajout.
     * les animaux et les chiens libres effectuent les actions (ramasser/manger/chasser/deplacer).
     * @param commande dont le second mot est l'objet a ajouter 
     */
    public void prendreUnObjetParUnJoueur(Commande commande){
        String s =commande.getSecondMot();
        int x=0;
        for(ObjetZork ob : pieceCourante.copy()){
            if(s.equals(ob.descriptionCourte())){
                   if(joueur.contient(ob)==true){
                       System.out.println("Vous avez deja cette objet!\n");
                   }
                   if((joueur.getPoidsTotal()+ob.getPoids()) <= joueur.getPoidsMax()){
                        if(!ob.estTransportable()){
                           System.out.println("objet non transportable!");
                        }
                        else{
                            joueur.ajouter(ob);
                            System.out.println(ob.descriptionCourte()+" pris avec succes");
                            joueur.setPoidsTotal(ob.getPoids());
                            System.out.println("votre nouveau poids: "+ joueur.getPoidsTotal());
                            if(ob.descriptionCourte().equalsIgnoreCase("collierOr") || ob.descriptionCourte().equalsIgnoreCase("bagueOr")){
                                joueur.setScore(ob.getPoids()+20);  /*plus de points pour les objets precieux*/
                            }
                            else {  
                                joueur.setScore(ob.getPoids()+5);
                            }
                            joueur.setScore(-1);
                            System.out.println("votre nouveau score: "+ joueur.getScore());
                        }
                   }
                    else{ 
                        System.out.println(joueur.getNom()+ " ne peut pas transporter cette objet");
                        System.out.println("il va depasser le poids maximum");
                   }
                   if(joueur.contient(ob)){
                       pieceCourante.retirer(ob);
                       x++;
                       effectuerAction();
                       effectuerDeplacement();
                    }
                    else if(!joueur.contient(ob) && (((joueur.getPoidsTotal()+ob.getPoids()) > joueur.getPoidsMax()) || !ob.estTransportable())){
                        x=4;
                    }
            }
        }
        joueur.afficherListObjets();
        if(x==0){
                System.out.println("cet objet n'est pas dans "+pieceCourante.descriptionCourte()+"\n");
        }
    }
    
    /**
     * retire l'objet specifie par la commande de l'inventaire du joueur
     * l'objet retirer est a ajouter dans la piece courante
     * si l'objet n'est pas transporte par le joueur, un message d'erreur est affiche
     * les animaux et les chiens libres effectuent les actions (ramasser/manger/chasser/deplacer).
     * @param commande dont le second mot est l'objet a retirer
     */
    public void deposerObjetParUnJoueur(Commande commande){
        String s =commande.getSecondMot();
        int x=0;
        for(ObjetZork o :joueur.copy()){
            if(s.equals(o.descriptionCourte())){
                joueur.retirer(o);
                if(!pieceCourante.contient(o)){
                    pieceCourante.ajouter(o);
                    effectuerAction();
                    effectuerDeplacement();
                }
                else{
                    System.out.println("cet objet est deja dans la piece!");
                }
                x++;
            }
        }
        if(x==0){
                System.out.println("Vous ne transportez pas cet objet!\n");
        }
    }
    
    /**
     * ajoute l'objet specifie par la commande dans l'inventaire du chien 
     * le joueur choisit un des chiens adoptes pour transporter cet objet.
     * si le chien n'est pas adoptes, un message est affiche.
     * si le joueur n'as pas encore adopter des chiens, un message est affiche.
     * si h.getCapitalVie()<=0; le joueur doit soit 
     * liberer le chien, soit lui donner a manger.
     * @param commande dont le second mot est l'objet a prendre
     */
    public void prendreUnObjetsParUnChien(Commande commande){
        if(joueur.getNbChien()>0){
            String s =commande.getSecondMot();
            int x=0,y=0;
            System.out.println("choisir le chien");
            Scanner rep= new Scanner(System.in);
            joueur.afficherListChiens();
            String answer=rep.nextLine();
            rep.close();
            for(ObjetZork o: pieceCourante.copy()){
                if(s.equals(o.descriptionCourte())){
                    for(Chien h: joueur.copyCh()){
                        if(answer.equals(h.getNom())){
                            y=2;
                            if(h.getCapitalVie()<=0){
                                x=9;
                                System.out.println(h.getNom()+" a faim, chercher lui a manger ou liberer le: (manger/liberer)?");
                                Scanner rp= new Scanner(System.in);
                                String res = rp.nextLine();
                                rp.close();
                                if(res.equals("liberer")){
                                    Commande com=new Commande("liberer","chien");
                                    libererUnChien(com);
                                    if(h.getNbObjets()>0)
                                        System.out.println("Vous perdez donc tous les objets que "+ h.getNom()+" transportait!");
                                }
                                else if(res.equalsIgnoreCase("manger")){
                                    if(joueur.contient(croquettes) || joueur.isObjetOfChien(croquettes)){
                                        System.out.println("Super! vous avez ramener les croquettes de la cuisine");
                                        System.out.println("Super, vous etes pres pour reprendre la course");
                                        System.out.println("maintenant "+h.getNom()+" peut prendre d'autres objets!");
                                        joueur.setScore(1);
                                        h.setCapitalVie(30);
                                        System.out.println("Votre nouveau score: "+joueur.getScore());
                                    }
                                    else{
                                        System.out.println("Vous devez lui chercher a manger");
                                    }
                                }
                                else{
                                    System.out.println("j'ai pas compris!");
                                }
                            }
                            else{
                                h.ajouterObToCh(o);
                                if(h.contientOb(o)){
                                    pieceCourante.retirer(o);
                                    x++;
                                    if(o.descriptionCourte().equalsIgnoreCase("collierOr")||o.descriptionCourte().equalsIgnoreCase("bagueOr")){
                                        joueur.setScore(o.getPoids()+20);
                                    }
                                    else{
                                        joueur.setScore(o.getPoids()+5);
                                    }
                                    System.out.println("votre nouveau score: "+ joueur.getScore());
                                }
                                else if(!h.contientOb(o)&& (((h.getPoidsTotal()+o.getPoids())>h.getPoidsMax())||((h.getNbObjets()+1)>5)||!o.estTransportable())){
                                    x=5;
                                }
                            }  
                        }
                    }
                    if(y==0){
                           System.out.println("chien inconnu!"); 
                        }
                }
            } 
            if(x==0){
                System.out.println("cet objet n'est pas dans "+pieceCourante.descriptionCourte()+"\n");
            }
        }
        else {
            System.out.println("Vous avez pas encore adopter des chiens!");
        }
    }
    
    /**
     * retire l'objet specifie par la commande de l'inventaire du chien qui le transporte
     * l'objet retirer est ajouter dans la piece courante
     * si l'objet n'est pas transporte par les chiens, un message d'erreur est affiche
     * @param commande dont le second mot est l'objet a retirer
     */
    public void deposerUnObjetParUnChien(Commande commande){
        String s =commande.getSecondMot();
        int x=0;
        joueur.afficherListChiens();
        for(Chien h: joueur.copyCh()){
            for(ObjetZork o: h.getObjChien()){
                if(s.equals(o.descriptionCourte())){
                    h.retirerObOfCh(o);
                    if(!h.contientOb(o)){
                        if(!pieceCourante.contient(o)){
                            pieceCourante.ajouter(o);
                        }
                        else{
                            System.out.println("cet objet est deja dans la piece!");
                        }
                        x++;
                        if(o.descriptionCourte().equals("collierOr")||o.descriptionCourte().equals("bagueOr")){
                                    joueur.setScore(-(o.getPoids()+20));
                        }
                        else{
                            joueur.setScore(-(o.getPoids()+5));
                        }
                        System.out.println("votre nouveau score: "+ joueur.getScore());
                    }
                }
            }
        }
        if(x==0){
            System.out.println("Aucun chien ne transporte cet objet!\n");
        }
    }
    
    /**
     * ajoute les chiens specifies par le joueur dans la liste des chiens adoptes
     * si l'utilisateur rentre un nom invalide, un message d'erreur est affiche
     * @param commande dont le second mot est le mot chien 
     * si l'utilisateur ne rentre pas le mot chien, un message est affiche
     * les animaux et les chiens libres effectuent les actions (ramasser/manger/chasser/deplacer).
     */
    public void adopterDesChiens(Commande commande){
        String s =commande.getSecondMot();
        if(s.equals("chien")){
            if(getChienDispo().size()!=0){
                int x=0;
                System.out.println("les chiens disponibles sont:\n");
                for(Chien h : getChienDispo()){
                    System.out.println("=>"+h.descriptionCourte());
                }
                System.out.println("\n A vous de choisir(vous pouvez adopter plusieurs)");
                System.out.println("Combien de chien vous voulez?");
                Scanner rep=new Scanner(System.in);
                int reponse=0;
                try{
                    reponse=rep.nextInt();
                }catch(InputMismatchException e){
                    System.out.println("Vous devez entrer un chiffre!");
                }
                while(reponse>getChienDispo().size()){
                    System.out.println("il n'y a que "+getChienDispo().size()+" chiens disponibles!");
                    System.out.println("Combien de chien vous voulez?");
                    reponse= rep.nextInt();
                }
                rep.close();
                for(int i=1; i<=reponse;i++){
                    System.out.println("entrez le nom du "+i+"e :");
                    Scanner res=new Scanner(System.in);
                    String reponseBis=res.nextLine();
                    res.close();
                    for(Chien h: getChienDispo()){
                       if(reponseBis.equalsIgnoreCase(h.getNom())){
                           joueur.adopChien(h);
                           if(joueur.contientCh(h)){
                               lesAnimaux.remove(h);
                               x++;
                           }
                       }
                    }
                    if(x==0){
                        System.out.println("ce chien n'est pas disponible");
                    }else{
                       effectuerAction();
                       effectuerDeplacement();
                    }
                    joueur.afficherListChiens();
                }
            }
            else{
               System.out.println("Pas de chiens disponibles pour le moment.");
            }
        }   
        else{
            System.out.println("j'ai pas compris!");
        }
    }
    
    /**
     * retire le chien specifie de la liste des chiens adoptes
     * les objets qu'il transportait seront ajouter dans la piece courante
     * le chien liberer est ajoute a la liste des chiens disponibles
     * si le chien n'est pas adopte, un message est affiche
     * les animaux et les chiens libres effectuent les actions (ramasser/manger/chasser/deplacer).
     * @param commande dont le second mot est le mot chien
     * @ensures h.getPoidsTotal()==0;
     * @ensures h.getNbObjets()==0;
     * @ensures \old(joueur.getScore()) >= joueur.getScore();
     * @ensures joueur.getScore()>=0;
     */
    public void libererUnChien(Commande commande){
        String s =commande.getSecondMot();
        if(s.equalsIgnoreCase("chien")){
            int x=0;
            joueur.afficherListChiens();
            if(joueur.getListChien().size()==0){
                System.out.println("Aucun chien adopte!");
            }else{
                System.out.println("quel chien voulez-vous liberer?");
                Scanner rep = new Scanner(System.in);
                String answer = rep.nextLine();
                rep.close();
                for(Chien h : joueur.copyCh()){
                    if(answer.equalsIgnoreCase(h.getNom())){
                        joueur.libererChien(h);
                        if(!joueur.contientCh(h)){
                            pieceCourante.ajouterListT(h.copyObCh());
                            for(ObjetZork j: h.copyObCh()){
                                if(j.descriptionCourte().equals("collierOr") || j.descriptionCourte().equals("bagueOr")){
                                    joueur.setScore(-(j.getPoids()+20));
                                }else{
                                    joueur.setScore(-(j.getPoids()+5));
                                }
                                h.setCapitalVie(20);
                                h.viderListObCh();
                            }
                            System.out.println("votre nouveau score: "+ joueur.getScore()+"\n");
                            pieceCourante.descriptionLongue();
                            if(h.estVivant()){
                                lesAnimaux.add(h);
                            }
                            x++;
                            effectuerAction();
                            effectuerDeplacement();
                        }
                    }
                }
                if(x==0){
                    System.out.println("vous n'avez pas adopte ce chien!");
                }
                joueur.afficherListChiens();
            }
        }
    }

    /**
     * les animaux present dans le jeu ainsi que les chiens libres
     * effectue un deplecement a partir de leur piece courante 
     * vers une piece voisine.
     * leurs capitales de vie sont diminues du cout de l'action "deplacer"
     * si la'nimal meurt apres cette ection il reste dans sa piece actuelle
     * et n'effectue plus d'actions.
     * @requires lesAnimaux != null;
     */
    public void effectuerDeplacement(){
        System.out.println("\n");
        for(Animal anm: this.getLesAnimaux()){
            if(anm.estVivant()) {
                if(anm.getClass().equals(Chien.class)){
                    Chien h=(Chien)anm;
                    if(h.estLibre()) {
                        Piece p=h.getPieceActuelle();
                        h.deplacerDepuis(h.getPieceActuelle());
                        System.out.println(h.getNom()+" passe de "+p.descriptionCourte()+" a "+h.getPieceActuelle().descriptionCourte());
                    }
                }
                else{
                    AbstractAnimal am=(AbstractAnimal)anm;
                    Piece p=am.getPieceActuelle();
                    am.deplacerDepuis(am.getPieceActuelle());
                    System.out.println(am.getNom()+" passe de "+p.descriptionCourte()+" a "+am.getPieceActuelle().descriptionCourte());
                }
            }
     }
    }
    
    /**
     * les animaux presents dans le jeu ainsi que les chiens(libres ou non)
     * ramasse des aliments dans leur piece actuelle, 
     * choisissent un aliment de leur reserve et le mange
     * si l'animal meurt apers l'action "ramasser" 
     * il reste dans sa piece actuelle et ne fait plus d'actions.
     * si l'animal est un loup, il chasse une chevre si elle est 
     * presente dans le piece et il la mange.
     * @requires lesAnimaux != null;
     */
    public void effectuerAction(){
        if(pieceCourante.getListAliments().size()>0){
            for(Animal anml: getLesAnimaux()){ 
                if(anml.estVivant()){
                    if(anml.getClass().equals(Loup.class)){
                        Loup lp=(Loup)anml;
                        Animal pr=lp.chasser(lp.pieceActuelle);
                        if(pr!=null && lp.estVivant()){
                           System.out.println(lp.getNom()+" a chasse "+pr.getNom());
                           Chevre o= (Chevre)pr;
                           if(o.getMangerPar()==true)
                            this.lesAnimaux.remove(pr);
                        }
                        if(pr==null){
                           ramasserManger(lp);
                        }
                    }
                    else{
                        AbstractAnimal a=(AbstractAnimal)anml;
                        ramasserManger(a);
                    } 
                 }
            }
            ///les chiens adoptes
            for(Chien anm : joueur.getListChien()){
                if(anm.estVivant()){
                   ramasserManger(anm);
                   if(!anm.estVivant()){
                       joueur.libererChien(anm);
                    }
                }
            }   
        }
    }
    
    /**
     * les animaux presents dans la piece ramasse au plus 3 aliments,
     * les placent dans leurs reserves, et en choisissent un
     * pour le manger.
     * un message est affiche apres chaque action.
     */
    public void ramasserManger(AbstractAnimal a){
        try{
           for(int i=0;i<3;i++){
              Aliment t=a.ramasserDans(a.getPieceActuelle());
              if((a instanceof Chien) && a.getCapitalVie()<=0){
                lesAnimaux.remove(a);
              }
              if(t!=null)
                System.out.println(a.getNom()+" a ramasse "+t.descriptionCourte());
           }
           Aliment q= a.manger();
           if(q!=null)
                System.out.println(a.getNom()+" a mange "+q.descriptionCourte());
        }catch(IllegalStateException e){
            System.out.println(a.getNom()+" est mort"+" ,ses points de vie "+a.getCapitalVie() );
        }
    }
    
    /**
     *  Tente d'aller dans la direction specifiee par la commande. Si la piece
     *  courante possede une sortie dans cette direction, la piece correspondant a
     *  cette sortie devient la piece courante, dans les autres cas affiche un
     *  message d'erreur.
     *  si la direction specifiee par la commande est la chambre des parents,
     *  le joueur doit choisir la clef ou le marteau pour l'ouvrir,
     *  s'il choisit le marteau, il perd et sinon la porte s'ouvre et
     *  le joueur a acces a la chambre des parents.
     *  si le joueur choisit de se deplacer dans une piece qui
     *  contient des loups, il va pedre.
     *  
     * @param  commande  Commande dont le second mot specifie la direction a suivre
     */
    public void deplacerVersAutrePiece(Commande commande) {
        if (!commande.aSecondMot()) {
            /* si la commande ne contient pas de second mot, nous ne savons pas ou aller*/
            System.out.println("Aller ou?(ajouter une direction exp: aller NORD)");
            return;
        }
        String direction = commande.getSecondMot();
        Direction d = null;
        try {
           d = Direction.valueOf(direction);
        } catch (IllegalArgumentException e) {
             System.out.println("Pour indiquer une direction entrez une des chaines de caracteres suivantes:");
             for (Direction dok : Direction.values()) {
                 System.out.println("-> \"" + dok + "\"");
             }
             return;
         }
        /* Tentative d'aller dans la direction indiquee.*/
        Piece pieceSuivante = pieceCourante.pieceSuivante(d);
        int k=0;
        /*introduction des obstacles*/
        if (pieceSuivante == null) {
            System.out.println("Il n'y a pas de porte dans cette direction!");
        }
        else {
            if(joueur.getNbChien()==0){
                for(Animal an : getLesAnimaux()){
                    if(an.getClass().equals(Loup.class)){
                        AbstractAnimal lp = (AbstractAnimal) an;
                        if(lp.getPieceActuelle().descriptionCourte().equals(pieceSuivante.descriptionCourte())){
                            k++;
                        }
                    }
                }
            }
        
            if(pieceCourante!=null && k==0){
                if(pieceSuivante.descriptionCourte().equals("chambreDesParents")){
                    System.out.println("Vous tentez d'aller dans la chambre des parents, elle est fermee a cle!");
                    if((joueur.contient(marteau) && joueur.contient(clef)) || (joueur.isObjetOfChien(marteau) && joueur.isObjetOfChien(clef))){
                         System.out.println("Vous voulez ouvrir la porte par la clef ou le marteau (marteau/clef)");
                         Scanner rep= new Scanner(System.in);
                         String reponse=rep.nextLine();
                         rep.close();
                         if(reponse.equalsIgnoreCase("marteau")){
                             pieceCourante.ajouter(terminer);
                         }
                         else if(reponse.equalsIgnoreCase("clef")){
                            System.out.println("la porte est ouverte!");
                            Piece pieceAvant=pieceCourante;
                            pieceCourante = pieceSuivante;
                            effectuerAction();
                            effectuerDeplacement();
                            joueur.afficherListObjets();
                            for(Chien h: joueur.copyCh()){
                                /*description qui specifie le deplacement des chiens dans les pieces*/
                                System.out.println(h.messageDeplacement()+pieceAvant.descriptionCourte()+" a "+ pieceCourante.descriptionCourte());
                            }
                            System.out.println("");
                            pieceCourante.descriptionLongue();
                            System.out.println("vous etes dans la chambre des parents,il existe des objets precieux");
                            System.out.println("si vous sortez, la chambre va se fermer automatiquement");
                         }
                    }
                    else if( joueur.contient(marteau) || joueur.contient(clef) || joueur.isObjetOfChien(clef) || joueur.isObjetOfChien(marteau) ){
                        if(joueur.contient(clef) || joueur.isObjetOfChien(clef)){
                            System.out.println("Vous avez la clef,voulez-vous l'utiliser pour entrer dans la chambre?(oui/non)");
                            Scanner rep= new Scanner(System.in);
                            String reponse=rep.nextLine();
                            rep.close();
                            if(reponse.equalsIgnoreCase("oui")){
                                System.out.println("la porte est ouverte!");
                                Piece pieceAvant=pieceCourante;
                                pieceCourante = pieceSuivante;
                                effectuerAction();
                                effectuerDeplacement();
                                joueur.afficherListObjets();
                                for(Chien h: joueur.copyCh()){
                                    System.out.println(h.messageDeplacement()+pieceAvant.descriptionCourte()+" a "+ pieceCourante.descriptionCourte());
                                }
                                System.out.println("");
                                pieceCourante.descriptionLongue();
                                System.out.println("vous etes dans la chambre des parents,il existe des objets precieux");
                                System.out.println("si vous sortez, la chambre va se fermer automatiquement");
                            }
                            else{
                                System.out.println("la porte s'ouvre soit avec la clef, soit le marteau!");
                            }
                        }
                        else{
                            System.out.println("Vous voulez ouvrir la porte avec le marteau (oui/non)");
                            Scanner rep= new Scanner(System.in);
                            String reponse=rep.nextLine();
                            rep.close();
                            if(reponse.equalsIgnoreCase("oui")){
                                 pieceCourante.ajouter(terminer);
                            }
                            else if(reponse.equalsIgnoreCase("non")){
                                System.out.println("il faut alors chercher la clef pour ouvrir la chambre des parents");
                            }
                        }
                    }
                    else if(!(joueur.contient(marteau) || joueur.contient(clef)) && !( joueur.isObjetOfChien(clef) || joueur.isObjetOfChien(marteau))){
                        System.out.println("il faut chercher la \"clef\" ou le \"marteau\" pour ouvrir la porte");
                    }   
                }
                else {
                    Piece pieceAvant =pieceCourante;
                    pieceCourante = pieceSuivante;
                    effectuerAction();
                    effectuerDeplacement();
                    joueur.afficherListObjets();
                    for(Chien h: joueur.copyCh()){
                        System.out.println(h.messageDeplacement()+pieceAvant.descriptionCourte()+" a "+ pieceCourante.descriptionCourte());
                    }
                    System.out.println("");
                    pieceCourante.descriptionLongue();
                }
            }
            
            if(pieceSuivante!=null && k!=0){
                System.out.println("Il y a des loups dans la piece desiree, VOULEZ-VOUS QUAND MEME Y ALLER ? (oui/non)");
                Scanner rep= new Scanner(System.in);
                String reponse=rep.nextLine();
                rep.close();
                if(reponse.equalsIgnoreCase("oui")){
                    pieceCourante.ajouter(finirLoup);
                }
                else if(reponse.equalsIgnoreCase("non")){
                    System.out.println("bon choix,choisissez une autre direction et n'oubliez pas que les loups se baladent dans les pieces");
                }
                else {
                    System.out.println("j'ai pas compris, il fallait repondre oui ou non");
                }
            }
        }
    }
}
