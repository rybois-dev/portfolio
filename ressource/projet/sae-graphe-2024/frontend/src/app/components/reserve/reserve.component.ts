import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { PileReserveComponent } from '../pile-reserve/pile-reserve.component';
import { JeuType } from '../../types/api';

@Component({
  selector: 'app-reserve',
  standalone: true,
  imports: [
    CommonModule,
    PileReserveComponent,
  ],
  templateUrl: './reserve.component.html',
  styleUrl: './reserve.component.scss'
})
export class ReserveComponent {
  @Input() data!: JeuType['reserve'];
}
