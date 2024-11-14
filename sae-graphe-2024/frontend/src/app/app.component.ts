import { CommonModule } from '@angular/common';
import { Component, HostListener } from '@angular/core';
import { JoueursComponent } from './components/joueurs/joueurs.component';
import { LogComponent } from './components/log/log.component';
import { PlateauComponent } from './components/plateau/plateau.component';
import { PromptComponent } from './components/prompt/prompt.component';
import { ReserveComponent } from './components/reserve/reserve.component';
import { WebsocketService } from './services/websocket.service';
import { JeuType } from './types/api';
import { ZoomCarteComponent } from './components/zoom-carte/zoom-carte.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    CommonModule,
    PlateauComponent,
    ReserveComponent,
    PromptComponent,
    JoueursComponent,
    LogComponent,
    ZoomCarteComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  title = 'frontend';
  data: JeuType | undefined;

  constructor(private ws: WebsocketService) {
    this.ws.message$.subscribe((data) => {
      this.data = data;
    });
  }

  @HostListener('document:keydown.space', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if (event.target instanceof HTMLInputElement) return; // ignore when focus on input
    console.log(event);
    this.ws.sendMessage("");
    return false;  
  }
}
