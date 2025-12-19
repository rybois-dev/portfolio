package fr.umontpellier.iut.trains;

import fr.umontpellier.iut.trains.plateau.*;

import java.util.Arrays;
import java.util.List;

public enum PlateauExtra {
    HIROSHIMA;

    public List<Tuile> makeTuiles() {
        return switch (this) {
            case HIROSHIMA -> makeTuilesHiroshima();
        };
    }

    public String getNomVille() {
        return switch (this) {
            case HIROSHIMA -> "Hiroshima";
        };
    }

    private static List<Tuile> makeTuilesHiroshima() {
        Tuile[][] array = new Tuile[][] {
                new Tuile[] {
                        new TuileEtoile(4),
                        new TuileVille(1),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileEtoile(2),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileTerrain(TypeTerrain.PLAINE),
                },
                new Tuile[] {
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileVille(2),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileVille(2),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileVille(2),
                },
                new Tuile[] {
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileVille(1),
                        new TuileEtoile(4),
                },
                new Tuile[] {
                        new TuileEtoile(3),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileVille(1),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileVille(2),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileVille(1),
                },
                new Tuile[] {
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileVille(3),
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileVille(3),
                        new TuileVille(2),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileTerrain(TypeTerrain.PLAINE),
                },
                new Tuile[] {
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileVille(3),
                        new TuileVille(2),
                        new TuileTerrain(TypeTerrain.FLEUVE),
                        new TuileEtoile(1),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileVille(2),
                },
                new Tuile[] {
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileEtoile(2),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileVille(2),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileVille(3),
                },
                new Tuile[] {
                        new TuileEtoile(4),
                        new TuileTerrain(TypeTerrain.PLAINE),
                        new TuileEtoile(2),
                        new TuileTerrain(TypeTerrain.MONTAGNE),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileMer(),
                        new TuileEtoile(3),
                        new TuileTerrain(TypeTerrain.PLAINE),
                }
        };
        placerTuiles(array);
        array[0][0].supprimerVoisine(array[0][1]);
        array[3][0].supprimerVoisine(array[4][1]);
        array[3][6].supprimerVoisine(array[4][6]);
        array[3][6].supprimerVoisine(array[4][7]);
        array[4][6].supprimerVoisine(array[4][7]);
        array[5][2].supprimerVoisine(array[5][3]);
        array[5][2].supprimerVoisine(array[6][3]);
        array[6][1].supprimerVoisine(array[7][0]);

        return Arrays.stream(array).flatMap(Arrays::stream).toList();
    }

    private static void placerTuiles(Tuile[][] array) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (j + 1 < array[i].length) { // voisine de droite
                    array[i][j].ajouterVoisine(array[i][j + 1]);
                }
                if (i + 1 < array.length) {
                    if (j < array[i + 1].length) { // voisine du bas
                        array[i][j].ajouterVoisine(array[i + 1][j]);
                    }
                    if (i % 2 == 0 && j > 0) { // voisine bas-gauche (ligne paire)
                        array[i][j].ajouterVoisine(array[i + 1][j - 1]);
                    }
                    if (i % 2 == 1 && j + 1 < array[i + 1].length) { // voisine bas-droite (ligne impaire)
                        array[i][j].ajouterVoisine(array[i + 1][j + 1]);
                    }
                }
            }
        }
    }

    public static String getCoordonnees(int i) {
        int strip = i / 19;
        int stripIndex = i % 19;
        if (stripIndex < 10) {
            return String.format("(%d, %d)", 2 * strip, stripIndex);
        } else {
            return String.format("(%d, %d)", 2 * strip + 1, stripIndex - 10);
        }
    }
}
