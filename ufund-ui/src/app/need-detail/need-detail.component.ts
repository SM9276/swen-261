
import { Need } from '../need';
import { Component, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { NeedService } from '../need.service';
@Component({
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrls: ['./need-detail.component.css']
})
export class NeedDetailComponent {
  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private location: Location
  ) {}
  @Input() need?: Need;
  ngOnInit(): void {
    this.getNeed();
  }
  
  getNeed(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.needService.getNeed(id)
      .subscribe(need => this.need = need);
  }
  goBack(): void {
    this.location.back();
  }
  save(): void {
    if (this.need) {
      this.needService.updateNeed(this.need)
        .subscribe(() => this.goBack());
    }
  }
}
