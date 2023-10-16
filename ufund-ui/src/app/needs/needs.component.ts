import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';

@Component({
  selector: 'app-needs',
  templateUrl: './needs.component.html',
  styleUrls: ['./needs.component.css']
})
export class NeedsComponent implements OnInit {
  needs: Need[] = [];

  constructor(private needService: NeedService) { }

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.needService.getNeeds()
    .subscribe(needs => this.needs = needs);
  }

  add(name: string,needPrice: string, needQuantity: string, needId: string): void {
    name = name.trim();
    const price:number = parseFloat(needPrice);
    const quantity:number = parseFloat(needQuantity);
    const id:number = parseFloat(needId);

    if (!name) { return; }
    this.needService.addNeed({name,price,quantity,id} as Need)
      .subscribe(need => {
        this.needs.push(need);
      });
  }

  delete(need: Need): void {
    console.log(1)
    this.needs = this.needs.filter(h => h !== need);
    this.needService.deleteNeed(need.id).subscribe();
  }

}
