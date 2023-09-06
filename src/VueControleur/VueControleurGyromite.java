package VueControleur;

import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.plateau.*;


/** Cette classe a deux fonctions :
 *  (1) Vue : proposer une représentation graphique de l'application (cases graphiques, etc.)
 *  (2) Controleur : écouter les évènements clavier et déclencher le traitement adapté sur le modèle (flèches direction Pacman, etc.))
 *
 */
public class VueControleurGyromite extends JFrame implements Observer {
    private Jeu jeu; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)

    private int sizeX; // taille de la grille affichée
    private int sizeY;

    // icones affichées dans la grille
    private ImageIcon icoHero;
    private ImageIcon icoVide;
    private ImageIcon icoMur_Vertical;
    private ImageIcon icoMur_Horizontal;
    private ImageIcon icoColonneHead;
    private ImageIcon icoColonneMid;
    private ImageIcon icoColonneBottom;
    private ImageIcon icoColonneHeadLocker;
    private ImageIcon icoColonneMidLocker;
    private ImageIcon icoColonneBottomLocker;
    private ImageIcon icoRadis;
    private ImageIcon icoBombe;
    private ImageIcon icoCorde;
    private ImageIcon icoSol;
    private ImageIcon icoSmicks;

    private JLabel[][] tabJLabel; // cases graphique (au moment du rafraichissement, chaque case va être associée à une icône, suivant ce qui est présent dans le modèle)


    public VueControleurGyromite(Jeu _jeu) {
        sizeX = jeu.SIZE_X;
        sizeY = _jeu.SIZE_Y;
        jeu = _jeu;

        chargerLesIcones();
        placerLesComposantsGraphiques();
        ajouterEcouteurClavier();
    }

    private void ajouterEcouteurClavier() {
        addKeyListener(new KeyAdapter() { // new KeyAdapter() { ... } est une instance de classe anonyme, il s'agit d'un objet qui correspond au controleur dans MVC
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {  // on regarde quelle touche a été pressée
                    case KeyEvent.VK_LEFT : Controle4Directions.getInstance().setDirectionCourante(Direction.gauche); break;
                    case KeyEvent.VK_RIGHT : Controle4Directions.getInstance().setDirectionCourante(Direction.droite); break;
                    case KeyEvent.VK_DOWN : Controle4Directions.getInstance().setDirectionCourante(Direction.bas); break;
                    case KeyEvent.VK_UP : Controle4Directions.getInstance().setDirectionCourante(Direction.haut); break;
                    case KeyEvent.VK_SPACE : Controle4Directions.getInstance().setDirectionCourante(Direction.space); break;
                }
            }
        });
    }


    private void chargerLesIcones() {
        icoHero = chargerIcone("Images/Hero.png");
        icoVide = chargerIcone("Images/Vide.png");
        icoMur_Vertical = chargerIcone("Images/Mur_Vertical.png");
        icoMur_Horizontal = chargerIcone("Images/Mur_Horizontal.png");
        icoColonneHead = chargerIcone("Images/Colonne_Head.png");
        icoColonneMid = chargerIcone("Images/Colonne_Mid.png");
        icoColonneBottom = chargerIcone("Images/Colonne_Bottom.png");
        icoColonneHeadLocker = chargerIcone("Images/Colonne_Head_Locker.png");
        icoColonneMidLocker = chargerIcone("Images/Colonne_Mid_Locker.png");
        icoColonneBottomLocker = chargerIcone("Images/Colonne_Bottom_Locker.png");
        icoRadis = chargerIcone("Images/Radis.png");
        icoBombe = chargerIcone("Images/Bombes.png");
        icoCorde = chargerIcone("Images/Corde.png");
        icoSol = chargerIcone("Images/Sol.png");
        icoSmicks = chargerIcone("Images/Ennemi.png");
    }

    private ImageIcon chargerIcone(String urlIcone) {
        BufferedImage image = null;

        try {
            image = ImageIO.read(new File(urlIcone));
        } catch (IOException ex) {
            Logger.getLogger(VueControleurGyromite.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

        return new ImageIcon(image);
    }

    private void placerLesComposantsGraphiques() {
        setTitle("Gyromite");
        setSize(285, 256);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // permet de terminer l'application à la fermeture de la fenêtre

        JComponent grilleJLabels = new JPanel(new GridLayout(sizeY, sizeX)); // grilleJLabels va contenir les cases graphiques et les positionner sous la forme d'une grille
        
        tabJLabel = new JLabel[sizeX][sizeY];

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                JLabel jlab = new JLabel();
                tabJLabel[x][y] = jlab; // on conserve les cases graphiques dans tabJLabel pour avoir un accès pratique à celles-ci (voir mettreAJourAffichage() )
                grilleJLabels.add(jlab);
            }
        }
        add(grilleJLabels);
    }

    
    /**
     * Il y a une grille du côté du modèle ( jeu.getGrille() ) et une grille du côté de la vue (tabJLabel)
     */
    private void mettreAJourAffichage() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                if (jeu.getGrille()[x][y] instanceof Heros) { // si la grille du modèle contient un Pacman, on associe l'icône Pacman du côté de la vue
                    // System.out.println("Héros !");
                    tabJLabel[x][y].setIcon(icoHero);
                } else if (jeu.getGrille()[x][y] instanceof MurHorizontal) {
                    tabJLabel[x][y].setIcon(icoMur_Horizontal);
                } else if (jeu.getGrille()[x][y] instanceof MurVertical) {
                	tabJLabel[x][y].setIcon(icoMur_Vertical);
                } else if (jeu.getGrille()[x][y] instanceof Colonne) {
                	tabJLabel[x][y].setIcon(icoColonneMid);
                	
                	Colonne c = (Colonne) jeu.getGrille()[x][y];
                	
                	if (c.part == 1 && c.orient == 2) {
                		tabJLabel[x][y].setIcon(icoColonneHeadLocker);
                	} else if (c.part == 1) {
                		tabJLabel[x][y].setIcon(icoColonneHead);
                	} else if (c.part == c.size + 1 && c.orient == 1) {
                		tabJLabel[x][y].setIcon(icoColonneBottomLocker);
                	} else if (c.part == c.size + 1) {
                		tabJLabel[x][y].setIcon(icoColonneBottom);
                	}
                } else if (jeu.getGrille()[x][y] instanceof Bombe) {
                	tabJLabel[x][y].setIcon(icoBombe);
                } else if (jeu.getGrille()[x][y] instanceof Corde) {
                	tabJLabel[x][y].setIcon(icoCorde);
                } else if (jeu.getGrille()[x][y] instanceof Sol) {
                	tabJLabel[x][y].setIcon(icoSol);
                } else if (jeu.getGrille()[x][y] instanceof Bot) {
                	tabJLabel[x][y].setIcon(icoSmicks);
                } else if (jeu.getGrille()[x][y] instanceof Radis) {
                	tabJLabel[x][y].setIcon(icoRadis);
                } else {
                    tabJLabel[x][y].setIcon(icoVide);
                }
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        mettreAJourAffichage();
        /*
        SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        mettreAJourAffichage();
                    }
                }); 
        */

    }
}
