package modele.deplacements;

import modele.plateau.Bot;
import modele.plateau.Colonne;
import modele.plateau.EntiteDynamique;

public class IA extends RealisateurDeDeplacement {
	private int timer;
	
	public IA(int timer){
		super();
		this.timer = timer;
	}
	
    protected boolean realiserDeplacement() { 
    	boolean ret = false;
    	timer++;
    	for (EntiteDynamique e : lstEntitesDynamiques) {
    		//if(e instanceof B) {
    			if(e instanceof Bot) {
		    		if(timer == 6) {	
						int n = (int)Math.round(Math.random());
						if(n==1) {
							e.avancerDirectionChoisie(Direction.gauche);
							ret = true;
							timer = 0;
						}
						else {
							e.avancerDirectionChoisie(Direction.droite);
							ret = true;
							timer = 0;
						}
		    		}
    			}
    			if(e instanceof Colonne) {
    				if(timer==6) {
    					    					
    				}
    			}
    		//}
    	}
		return ret;
    }
}
