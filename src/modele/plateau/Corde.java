package modele.plateau;

public class Corde extends EntiteStatique {
    public Corde(Jeu _jeu) { super(_jeu); }
    
    @Override
    public boolean peutPermettreDeMonterDescendre() { return true; };
    public boolean peutEtreEcrase() { return false; }
    public boolean peutServirDeSupport() { return true; }

}