import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { JeuType } from '../types/api';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  websocket: WebSocket;
  private messagesSubject = new Subject<JeuType>();
  public message$ = this.messagesSubject.asObservable();

  constructor() { 
    this.websocket = new WebSocket('ws://localhost:3232');
    this.websocket.onmessage = (event) => {
      const data = JSON.parse(event.data);
      this.messagesSubject.next(data);
      (window as any).data = data;
    };
  }

  sendMessage(message: string) {
    console.log(`Message envoyé: "${message}"`);
    this.websocket.send(message);
  }
}
