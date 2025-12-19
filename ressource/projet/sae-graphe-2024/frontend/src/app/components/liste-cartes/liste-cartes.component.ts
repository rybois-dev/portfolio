import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { CarteComponent } from '../carte/carte.component';

@Component({
  selector: 'app-liste-cartes',
  standalone: true,
  imports: [
    CommonModule,
    CarteComponent,
  ],
  templateUrl: './liste-cartes.component.html',
  styleUrl: './liste-cartes.component.scss'
})
export class ListeCartesComponent {
  @Input() cartes!: string[];
  @Input() isActive!: boolean;
  @Input() color!: string;
  @Input() size!: "sm" | "lg";

  ngOnInit() {
    this.cartes.sort((a, b) => a.localeCompare(b));
  }

  getHeightClass(): string {
    switch (this.size) {
      case 'sm':
        return 'min-h-20';
      case 'lg':
        return 'min-h-32';
    }
  }
}
