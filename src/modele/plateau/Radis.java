package modele.plateau;

public class Radis extends EntiteStatique {
    public Radis(Jeu _jeu) { super(_jeu); }
    
    @Override
    public boolean peutServirDeSupport() { return false; }
    public boolean peutPermettreDeMonterDescendre() { return false; };
}