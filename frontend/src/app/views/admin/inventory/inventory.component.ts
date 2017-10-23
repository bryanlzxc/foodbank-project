import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'inventory',
    templateUrl: './inventory.component',
    styleUrls: [ 'inventory.css' ]
})

export class InventoryComponent implements OnInit {

    public test1;
    public test2;

    constructor () {
        this.test2 = "test3";
    }

    ngOnInit () {
        this.test1 = "test1";
        this.test2 = "test2";
    }

}
