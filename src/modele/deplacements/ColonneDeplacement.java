package modele.deplacements;

import modele.plateau.Bot;
import modele.plateau.Colonne;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Jeu;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des colonnes se dÃƒÂ©placent dans la direction dÃƒÂ©finie
 * (vÃƒÂ©rifier "collisions" avec le hÃƒÂ©ros)
 */
public class ColonneDeplacement extends RealisateurDeDeplacement {
	
	public int size;

	@Override
	protected boolean realiserDeplacement() {
		boolean ret = false;
		for (EntiteDynamique e : lstEntitesDynamiques) {
    		//if(e instanceof B) {
    			if(e instanceof Colonne) {
    				ret = true;
    			}
		
		}
		return ret;
	}

}