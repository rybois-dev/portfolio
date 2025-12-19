import { TestBed } from '@angular/core/testing';

import { ZoomCarteService } from './zoom-carte.service';

describe('ZoomCarteService', () => {
  let service: ZoomCarteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZoomCarteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
