import { Component } from '@angular/core';
import { SearchPipe } from './../search.pipe';

@Component({
    selector: 'vol-dash',
    templateUrl: './dashboard.component.html',
    styleUrls: [ './dashboard.component.css' ],
})

export class VolunteerDashboardComponent {
    itemList = [
        {
            "name": "Beef Mince",
            "category": "Meat",
            "qty": 25,
        },
        {
            "name": "Brie",
            "category": "Cheese",
            "qty": 29,
        },
        {
            "name": "Camembert",
            "category": "Cheese",
            "qty": 17,
        },
        {
            "name": "Cauliflower",
            "category": "Vegatables",
            "qty": 22,
        },
        {
            "name": "Cheddar",
            "category": "Cheese",
            "qty": 18,
        },
        {
            "name": "Chicken Breast",
            "category": "Meat",
            "qty": 11,
        },
        {
            "name": "Chicken Thighs",
            "category": "Meat",
            "qty": 17,
        },
        {
            "name": "Emmental",
            "category": "Cheese",
            "qty": 17,
        },
        {
            "name": "Fusilli Pasta",
            "category": "Pasta, rice etc",
            "qty": 43,
        },
        {
            "name": "Gorgonzola",
            "category": "Cheese",
            "qty": 18,
        },
        {
            "name": "Beef Mince",
            "category": "Meat",
            "qty": 25,
        },
        {
            "name": "Brie",
            "category": "Cheese",
            "qty": 29,
        },
        {
            "name": "Camembert",
            "category": "Cheese",
            "qty": 17,
        },
        {
            "name": "Cauliflower",
            "category": "Vegatables",
            "qty": 22,
        },
        {
            "name": "Cheddar",
            "category": "Cheese",
            "qty": 18,
        },
        {
            "name": "Chicken Breast",
            "category": "Meat",
            "qty": 11,
        },
        {
            "name": "Chicken Thighs",
            "category": "Meat",
            "qty": 17,
        },
        {
            "name": "Emmental",
            "category": "Cheese",
            "qty": 17,
        },
        {
            "name": "Fusilli Pasta",
            "category": "Pasta, rice etc",
            "qty": 43,
        },
        {
            "name": "Gorgonzola",
            "category": "Cheese",
            "qty": 18,
        }
    ]

    editQty = false;
    selectedIndex;
    onEditQty(index){
        this.selectedIndex = index;
    }

    newQty;
    onUpdateQty(index){
        console.log(index);
        console.log(this.newQty);
        this.itemList[index].qty = this.newQty;
        this.selectedIndex = null;
    }
}
