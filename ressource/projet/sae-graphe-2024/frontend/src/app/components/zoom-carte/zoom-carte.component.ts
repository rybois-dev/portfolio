import { Component } from '@angular/core';
import { ZoomCarteService } from '../../services/zoom-carte.service';
import { CommonModule } from '@angular/common';
import { NormalizePipe } from '../../pipes/normalize.pipe';

@Component({
  selector: 'app-zoom-carte',
  standalone: true,
  imports: [
    CommonModule,
    NormalizePipe,
  ],
  templateUrl: './zoom-carte.component.html',
  styleUrl: './zoom-carte.component.scss',
})
export class ZoomCarteComponent {
  carte: string | undefined;
  normalize = new NormalizePipe();
  
  constructor(private zoomCarteService: ZoomCarteService) {
    this.zoomCarteService.carteZoom$.subscribe((carte) => {
      this.carte = carte;
    });
  }

  getImageUrl(carte: string): string {
    return `/assets/cartes/${this.normalize.transform(carte)}.jpg`;
  }
}
