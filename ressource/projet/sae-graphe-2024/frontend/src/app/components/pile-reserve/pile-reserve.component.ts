import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { NormalizePipe } from '../../pipes/normalize.pipe';
import { WebsocketService } from '../../services/websocket.service';
import { ZoomCarteService } from '../../services/zoom-carte.service';
import { ZoomableComponent } from '../zoomable/zoomable.component';

@Component({
  selector: 'app-pile-reserve',
  standalone: true,
  imports: [
    CommonModule,
    ZoomableComponent,
  ],
  templateUrl: './pile-reserve.component.html',
  styleUrl: './pile-reserve.component.scss'
})
export class PileReserveComponent {
  @Input() carte!: string;
  @Input() nombre!: number;
  private normalizePipe = new NormalizePipe();

  constructor(private ws: WebsocketService, private zoom: ZoomCarteService) { }

  getImageUrl(): string {
    return `/assets/cartes/${this.normalizePipe.transform(this.carte)}.jpg`;
  }

  onClick() {
    this.ws.sendMessage(`ACHAT:${this.carte}`);
  }
}
