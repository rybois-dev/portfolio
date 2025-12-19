import { Component, Input, OnInit } from '@angular/core';
import { JoueurType } from '../../types/api';
import { ListeCartesComponent } from '../liste-cartes/liste-cartes.component';
import { TooltipComponent } from '../tooltip/tooltip.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-joueur',
  standalone: true,
  imports: [ListeCartesComponent, TooltipComponent, CommonModule],
  templateUrl: './joueur.component.html',
  styleUrl: './joueur.component.scss',
})
export class JoueurComponent implements OnInit {
  @Input() data!: JoueurType;
  color!: string;

  ngOnInit(): void {
    switch (this.data.couleur) {
      case 'JAUNE':
        this.color = 'yellow';
        break;
      case 'ROUGE':
        this.color = 'red';
        break;
      case 'VERT':
        this.color = 'green';
        break;
      case 'BLEU':
        this.color = 'blue';
        break;
    }
  }
}
