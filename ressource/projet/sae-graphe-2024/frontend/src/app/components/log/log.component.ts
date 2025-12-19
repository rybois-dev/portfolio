import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  Input,
  SimpleChange,
  SimpleChanges,
  ViewChild,
} from '@angular/core';

@Component({
  selector: 'app-log',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './log.component.html',
  styleUrl: './log.component.scss',
})
export class LogComponent {
  @Input() data!: string[];
  @ViewChild('logContainer') private logContainer!: ElementRef;
  private mutationObserver: MutationObserver | undefined;

  ngAfterViewInit(): void {
    this.mutationObserver = new MutationObserver(() => this.scrollToBottom());
    this.mutationObserver.observe(this.logContainer.nativeElement, {
      childList: true,
      subtree: true,
    });
  }

  scrollToBottom(): void {
    this.logContainer.nativeElement.scrollTop =
      this.logContainer.nativeElement.scrollHeight;
  }
}
