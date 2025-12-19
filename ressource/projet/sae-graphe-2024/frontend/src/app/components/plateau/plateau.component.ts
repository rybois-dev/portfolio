import { Component, Input } from '@angular/core';
import { CouleurJoueur, TuileType} from '../../types/api';
import { CommonModule } from '@angular/common';
import { WebsocketService } from '../../services/websocket.service';

@Component({
  selector: 'app-plateau',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './plateau.component.html',
  styleUrl: './plateau.component.scss',
})
export class PlateauComponent {
  @Input() tuiles!: TuileType[];
  @Input() ville!: "Tokyo" | "Osaka";

  constructor(private ws: WebsocketService) {}

  private getCoordinates(i: number): [number, number] {
    const strip = ~~(i / 19);
    const j = i % 19;
    if (j < 10) {
      return [2*strip, j];
    } else {
      return [2*strip + 1, j - 10];
    }
  }

  getShiftX(i: number) {
    const [row, col] = this.getCoordinates(i);
    switch (this.ville) {
      case 'Osaka':
        return 74 + 63.5 * col + (row % 2) * 32;
      case 'Tokyo':
        return 73 + 62.75 * col + (row % 2) * 32;
    }
  }

  getShiftY(i: number) {
    const [row, _] = this.getCoordinates(i);
    switch (this.ville) {
      case 'Osaka':
        return 57 + 54.5 * row;
      case 'Tokyo':
        return 55 + 54.5 * row;
    }
  }

  getCouleurRail(rail: CouleurJoueur) {
    switch(rail) {
      case 'BLEU':
        return 'bg-blue-500';
      case 'ROUGE':
        return 'bg-red-500';
      case 'VERT':
        return 'bg-green-500';
      case 'JAUNE':
        return 'bg-yellow-500';
    }
  }

  counter(i: number) {
    return new Array(i);
  }

  onClick(i: number) {
    this.ws.sendMessage(`TUILE:${i}`);
  }
}
