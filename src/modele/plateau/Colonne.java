package modele.plateau;

public class Colonne extends EntiteDynamique {
    public int posX; // position X de la colonne
    public int posY; // position Y de la colonne
    public int size; // taille de la colonne ... 3/4
    public int part; // 1 le haut | dernier bas
    public int orient; // 1 en haut | 2 en bas
    public boolean state;
	public Colonne(Jeu _jeu, int _posX, int _posY, int _size, int _part, int _orient) { 
    	super(_jeu); 
    	posX = _posX;
    	posY = _posY;
    	size = _size;
    	part = _part;
    	orient = _orient;
    }

    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}


