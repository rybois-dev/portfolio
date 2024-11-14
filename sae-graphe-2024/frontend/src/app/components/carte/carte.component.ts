import { Component, Input } from '@angular/core';
import { NormalizePipe } from '../../pipes/normalize.pipe';
import { WebsocketService } from '../../services/websocket.service';
import { ZoomCarteService } from '../../services/zoom-carte.service';
import { ZoomableComponent } from '../zoomable/zoomable.component';

@Component({
  selector: 'app-carte',
  standalone: true,
  imports: [
    NormalizePipe,
    ZoomableComponent,
  ],
  templateUrl: './carte.component.html',
  styleUrl: './carte.component.scss',
})
export class CarteComponent {
  @Input() carte!: string;
  @Input() isActive!: boolean;
  @Input() isMultiple!: boolean;
  @Input() size!: 'sm' | 'lg';
  normalizePipe = new NormalizePipe();

  constructor(private ws: WebsocketService, private zoom: ZoomCarteService) {}

  getImageUrl(): string {
    return `/assets/cartes/${this.normalizePipe.transform(this.carte)}.jpg`;
  }

  getHeightClass(): string {
    switch (this.size) {
      case 'sm':
        return 'h-20';
      case 'lg':
        return 'h-32';
    }
  }

  getMultipleMarginClass(): string {
    if (!this.isMultiple) return '';
    switch (this.size) {
      case 'sm':
        return '-mr-12';
      case 'lg':
        return '-mr-20';
    }
  }

  onClick() {
    if (this.isActive) {
      this.ws.sendMessage(this.carte);
    }
  }
}
