/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import modele.deplacements.ColonneDeplacement;
import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.deplacements.Gravite;
import modele.deplacements.IA;
import modele.deplacements.Ordonnanceur;

import java.awt.Point;
import java.util.HashMap;

/** Actuellement, cette classe gère les postions
 * (ajouter conditions de victoire, chargement du plateau, etc.)
 */
public class Jeu {

    public static final int SIZE_X = 16;
    public static final int SIZE_Y = 14;
    public int compteur_bombe = 2;
    public int temps = 50;
    public int timer_deplacement = 0;
    public boolean supp = false;
    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private Heros hector;
    private Bot ennemi;

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées

    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);

    public Jeu() {
        initialisationDesEntites();
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }
    
    public Entite[][] getGrille() {
        return grilleEntites;
    }
    
    public Heros getHector() {
        return hector;
    }
    
    private void initialisationDesEntites() {
        hector = new Heros(this);
        addEntite(hector, 0, 10);
        
        // Placement Smicks
        ennemi = new Bot(this);
        addEntite(ennemi, 10, 10);
        

        
        Gravite g = new Gravite();
        g.addEntiteDynamique(hector);
        g.addEntiteDynamique(ennemi);

        

		IA ai = new IA(timer_deplacement);
        ai.addEntiteDynamique(ennemi);
        
        
        Controle4Directions.getInstance().addEntiteDynamique(hector);

        initialisationDesCordes();
        initialisationDesMurs();
        initialisationDesColonnes();
        

        /// Placement Bombes
        addEntite(new Bombe(this), 8, 6);
        addEntite(new Bombe(this), 1, 8);
        ordonnanceur.add(g);     
        ordonnanceur.add(ai);           
        ordonnanceur.add(Controle4Directions.getInstance());
        
    }

    
    private void initialisationDesCordes() {
        for (int x = 0; x < 16; x++) {
        	if(objetALaPosition(new Point(x, 13)) == null){
        		addEntite(new Sol(this), x, 13);
        	}
        }
        for (int x = 0; x <3; x++) {
        	if(objetALaPosition(new Point(x, 12)) == null){
        		addEntite(new Sol(this), x, 12);
        	}
        }
        for (int x = 5; x < 16; x++) {
        	if(objetALaPosition(new Point(x, 12)) == null){
        		addEntite(new Sol(this), x, 12);
        	}
        }
        for (int y = 10; y < 13; y++) {
        	if(objetALaPosition(new Point(4, y)) == null){
        		addEntite(new Corde(this), 4, y);
        	}
        }
        
        for (int y = 9; y < 12; y++) {
        	if(objetALaPosition(new Point(6, y)) == null){
        		addEntite(new Corde(this), 6, y);
        	}
        }
        for (int y = 1; y < 11; y++) {
        	if(objetALaPosition(new Point(13, y)) == null){
        		addEntite(new Corde(this), 13, y);
        	}
        }
        for (int y = 5; y < 11; y++) {
        	if(objetALaPosition(new Point(11, y)) == null){
        		addEntite(new Corde(this), 11, y);
        	}
        }
    }
    
    private void initialisationDesMurs() {
    	// Mur Partie Droite
        for (int x = 7; x < 15; x++) {
        	addEntite(new MurHorizontal(this), x, 11);
        }
        for (int x = 13; x < 16; x++) {
        	addEntite(new MurHorizontal(this), x, 0);
        }
        for (int y = 1; y < 11; y++) {
        	addEntite(new MurVertical(this), 14, y);
        }
        
        for (int x = 5; x < 9; x++) {
        	addEntite(new MurHorizontal(this), x, 8);
        }
        
        for (int x = 8; x < 11; x++) {
        	addEntite(new MurHorizontal(this), x, 7);
        }

         
        for (int x = 7; x < 15; x++) {
        	addEntite(new MurHorizontal(this), x, 11);
        }
        for (int x = 13; x < 16; x++) {
        	addEntite(new MurHorizontal(this), x, 0);
        }
        for (int y = 1; y < 11; y++) {
        	addEntite(new MurVertical(this), 14, y);
        }
        // étage 2
        for (int x = 0; x < 6; x++) {
        	addEntite(new MurHorizontal(this), x, 5);
        	addEntite(new MurHorizontal(this), x, 9);
        }
        for (int x = 5; x < 12; x++) {
        	if (x != 9) {
            	addEntite(new MurHorizontal(this), x, 4);        		
        	}
        }
    }
    
    private void initialisationDesColonnes() {
    	
    	Colonne c1 = new Colonne(this, 3, 12, 3, 4, 2);
    	Colonne c2 = new Colonne(this, 3, 11, 3, 3, 2);
    	Colonne c3 = new Colonne(this, 3, 10, 3, 2, 2);
    	Colonne c4 = new Colonne(this, 3, 9, 3, 1, 2);

    	addEntite(c1, 3, 12);
    	addEntite(c2, 3, 11);
    	addEntite(c3, 3, 10);
    	addEntite(c4, 3, 9);

    	Colonne c5 = new Colonne(this, 9, 6, 2, 3, 2);
    	Colonne c6 = new Colonne(this, 9, 5, 2, 2, 2);
    	Colonne c7 = new Colonne(this, 9, 4, 2, 1, 2);

    	addEntite(c5, 9, 6);
    	addEntite(c6, 9, 5);
    	addEntite(c7, 9, 4);
    	
    	Controle4Directions.getInstance().addEntiteDynamique(c1);
    	Controle4Directions.getInstance().addEntiteDynamique(c2);
    	Controle4Directions.getInstance().addEntiteDynamique(c3);
    	Controle4Directions.getInstance().addEntiteDynamique(c4);
    	Controle4Directions.getInstance().addEntiteDynamique(c5);
    	Controle4Directions.getInstance().addEntiteDynamique(c6);
    	Controle4Directions.getInstance().addEntiteDynamique(c7);
    	
        ordonnanceur.add(Controle4Directions.getInstance());
    }
    
    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        map.put(e, new Point(x, y));
    }
    
    /** Permet par exemple a une entité  de percevoir sont environnement proche et de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }
    
    /** Si le déplacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     * Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = false;
        
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);

        
        if (contenuDansGrille(pCible) && (objetALaPosition(pCible) == null || objetALaPosition(pCible) instanceof Bombe || objetALaPosition(pCible) instanceof Corde || objetALaPosition(pCible) instanceof Bot) ) { // a adapter (collisions murs, etc.)
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité

            
            switch (d) {
            	
                case bas, haut:
                    if (cmptDeplV.get(e) == null) {
                    	if(objetALaPosition(pCourant) instanceof Heros) {
                    		if(objetALaPosition(pCible) instanceof Bot) {
                    			supprimerEntite(pCible, objetALaPosition(pCible));
                    		}
                    		else if(objetALaPosition(pCible) instanceof Bombe) {
                            	compteur_bombe--;
                            }
                        	else{
                    			temps--;
                    		}
                    	}
                    	else if(objetALaPosition(pCourant) instanceof Bot) {
                    		if(objetALaPosition(pCible) instanceof Heros) {
                    			game_over();
                    		}
                    	}                   	

                    	
                        cmptDeplV.put(e, 1);
                        retour = true;
                    }
                    break;
                case gauche, droite:
                    if (cmptDeplH.get(e) == null) {
                    	if(objetALaPosition(pCourant) instanceof Heros) {
                    		if(objetALaPosition(pCible) instanceof Bot) {
                    			game_over();
                    		}
                    		else if(objetALaPosition(pCible) instanceof Bombe) {
                            	compteur_bombe--;
                            }
                        	else{
                        		temps--;
                        	}
                    	}
                    	else if(objetALaPosition(pCourant) instanceof Bot) {
                    		if(objetALaPosition(pCible) instanceof Heros) {
                    			game_over();
                    		}
                    	}  

                    	
                        cmptDeplH.put(e, 1);
                        
                        retour = true;
                    }
                    break;
                case space:
                	if (cmptDeplV.get(e) == null) {
                        cmptDeplV.put(e, 1);
                        retour = true;
                	}
                	break;
        }
        
        if (retour) {
            deplacerEntite(pCourant, pCible, e);
        }

        
        
        }
        
        return retour;
    }
    
    public void update() {
    	initialisationDesCordes();
    	System.out.print(temps + "\n");
    	if(compteur_bombe == 0) {
    		System.out.print("Gagné en : ");
    		System.out.print(50 - temps);
    		System.out.print(" tours");
            this.win();
    	}
    	if(temps <= 0) {
    		this.game_over();
    	}
    }
    
    public void win() {
    	System.exit(0); 
    }
    
    public void game_over() {
    	System.out.print("Perdu");
    	System.exit(0);
    }
    
    void supprimerEntite(Point p, Entite e) {
		System.out.print("ICI\n");
		ordonnanceur.remove_e(e);
		map.remove(e);
		grilleEntites[p.x][p.y] = null;
    }

    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
	        switch(d) {
		        case haut:
		        	if (getGrille()[pCourant.x][pCourant.y] instanceof Heros || getGrille()[pCourant.x][pCourant.y] instanceof Bot) {
		        		pCible = new Point(pCourant.x, pCourant.y - 1);
		        	} else {
		        		pCible = new Point(pCourant.x, pCourant.y);
		        	}
		        	break;
		        case bas :
		        	if (getGrille()[pCourant.x][pCourant.y] instanceof Heros || getGrille()[pCourant.x][pCourant.y] instanceof Bot) {
		        		pCible = new Point(pCourant.x, pCourant.y + 1); 
		        	} else {
		        		pCible = new Point(pCourant.x, pCourant.y);
		        	}
		        	break;
		        case gauche : 
		        	if (getGrille()[pCourant.x][pCourant.y] instanceof Heros || getGrille()[pCourant.x][pCourant.y] instanceof Bot) {
		        		pCible = new Point(pCourant.x - 1, pCourant.y); 
		        	} else {
		        		pCible = new Point(pCourant.x, pCourant.y);
		        	}
		        	break;
		        case droite : 
		        	if (getGrille()[pCourant.x][pCourant.y] instanceof Heros || getGrille()[pCourant.x][pCourant.y] instanceof Bot) {
		        		pCible = new Point(pCourant.x + 1, pCourant.y); 
		        	} else {
		        		pCible = new Point(pCourant.x, pCourant.y);
		        	}
		        	break;
		        case space : 
		        	if (getGrille()[pCourant.x][pCourant.y] instanceof Heros) {
		    			pCible = new Point(pCourant.x, pCourant.y);
		        	} else {
		        		Colonne c = (Colonne) getGrille()[pCourant.x][pCourant.y];
		            	if (c.orient == 2) {
		            		pCible = new Point(pCourant.x, c.posY - c.size);
		        			c.orient = 1;

		        		} else if (c.orient == 1) {
		        			pCible = new Point(pCourant.x, c.posY);
		        			c.orient = 2;
		        		}
		            	
                        if (objetALaPosition(pCible) instanceof Heros) {
	        				game_over();
	        			} else if (objetALaPosition(pCible) instanceof Bot) {
	        				supprimerEntite(pCible, getGrille()[pCible.x][pCible.y]);
	        			}
		            	
		        	}
		        	break;
		    	}
	    
	    return pCible;
    }
    
    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {

	
    	grilleEntites[pCourant.x][pCourant.y] = null;
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
        
    }
    
    /** Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Entite objetALaPosition(Point p) {
        Entite retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }
}
