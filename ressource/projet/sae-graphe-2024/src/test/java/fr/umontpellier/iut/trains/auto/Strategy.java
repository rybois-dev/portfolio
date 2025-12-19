package fr.umontpellier.iut.trains.auto;

public abstract class Strategy {
    private AutoJeu jeu;

    public Strategy(AutoJeu jeu) {
        this.jeu = jeu;
    }

    public AutoJeu getJeu() {
        return jeu;
    }

    public abstract void update();
}
