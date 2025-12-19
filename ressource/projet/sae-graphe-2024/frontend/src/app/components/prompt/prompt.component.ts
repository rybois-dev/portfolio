import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { WebsocketService } from '../../services/websocket.service';
import { BoutonType } from '../../types/api';

@Component({
  selector: 'app-prompt',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  templateUrl: './prompt.component.html',
  styleUrl: './prompt.component.scss',
})
export class PromptComponent {
  @Input() instruction!: string;
  @Input() boutons!: BoutonType[];
  message: string = '';
  consoleForm = this.fb.group({
    "command": '',
  });
  
  constructor(private ws: WebsocketService, private fb: FormBuilder) {
  }

  onSubmit() {
    if (this.consoleForm.value.command != undefined) {
      this.ws.sendMessage(this.consoleForm.value.command);
      this.consoleForm.reset();
    }
  }
  sendMessage() {
    this.ws.sendMessage(this.message);
  }

  sendPass() {
    this.ws.sendMessage('');
  }

  onClickButton(bouton: BoutonType) {
    this.ws.sendMessage(bouton.valeur);
  }
}
