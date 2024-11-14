export type CouleurJoueur = 'JAUNE' | 'ROUGE' | 'VERT' | 'BLEU';

export type JeuType = {
  tuiles: TuileType[];
  ville: 'Tokyo' | 'Osaka';
  instruction: string;
  joueurCourant: number;
  log: string[];
  joueurs: JoueurType[];
  boutons: BoutonType[];
  reserve: { carte: string; nombre: number }[];
};

export type TuileType = {
  rails: CouleurJoueur[];
  nbGares?: number;
};

export type CarteType = string;

export type JoueurType = {
  nom: string;
  couleur: CouleurJoueur;
  actif: boolean;
  argent: number;
  rails: number;
  scoreTotal: number;
  nbJetonsRails: number;
  main: string[];
  cartesRecues: CarteType[];
  defausse: CarteType[];
  cartesEnJeu: CarteType[];
  pioche: CarteType[];
  listeEffets: string[];
};

export type BoutonType = {
  label: string;
  valeur: string;
};