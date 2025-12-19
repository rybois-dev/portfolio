import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ZoomCarteService {
  private carteZoomSubject = new Subject<string | undefined>();
  public carteZoom$ = this.carteZoomSubject.asObservable();

  afficherCarte(carte: string) {
    this.carteZoomSubject.next(carte);
  }

  cacherCarte() {
    this.carteZoomSubject.next(undefined);
  }
}
