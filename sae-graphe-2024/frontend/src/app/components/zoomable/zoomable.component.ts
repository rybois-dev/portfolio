import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ZoomCarteService } from '../../services/zoom-carte.service';

@Component({
  selector: 'app-zoomable',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './zoomable.component.html',
  styleUrl: './zoomable.component.scss',
})
export class ZoomableComponent {
  @Input() carte!: string;
  isZoomed: boolean = false;

  constructor(private zoom: ZoomCarteService) {}

  onMouseEnter() {
    this.zoom.afficherCarte(this.carte);
    this.isZoomed = true;
  }

  onMouseLeave() {
    this.zoom.cacherCarte();
    this.isZoomed = false;
  }

  ngOnDestroy() {
    if (this.isZoomed) this.zoom.cacherCarte();
  }
}
