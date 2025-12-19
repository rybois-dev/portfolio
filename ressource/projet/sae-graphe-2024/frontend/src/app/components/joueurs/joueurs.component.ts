import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { JeuType } from '../../types/api';
import { JoueurComponent } from '../joueur/joueur.component';

@Component({
  selector: 'app-joueurs',
  standalone: true,
  imports: [CommonModule, JoueurComponent],
  templateUrl: './joueurs.component.html',
  styleUrl: './joueurs.component.scss',
})
export class JoueursComponent {
  @Input() data!: JeuType['joueurs'];
}
