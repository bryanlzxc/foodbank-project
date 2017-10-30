import { Component, OnInit } from '@angular/core';
import { SearchPipe } from './../search.pipe';
import { TablesService } from '../../../services/tables.service';

@Component({
    selector: 'vol-dash',
    templateUrl: './dashboard.component.html',
    styleUrls: [ './dashboard.component.css' ],
    providers: [TablesService]
})

export class VolunteerDashboardComponent implements OnInit{
    rows = [];
    columns = [];
    temp = [];

    constructor(private service: TablesService) { }

    ngOnInit() {
        this.columns = this.service.getDataConf();
        this.rows = this.temp = this.service.getAll();
    }

    onPacked(item){
        item.packed=true;
    }

    updateFilter(event) {
    const val = event.target.value.toLowerCase();
    var columns = Object.keys(this.temp[0]);
    // Removes last "$$index" from "column"
    columns.splice(columns.length -1);

    // console.log(columns);
    if(!columns.length)
      return;

    const rows = this.temp.filter(function(d) {
      for(let i = 0; i <= columns.length; i++) {
        var column = columns[i];
        // console.log(d[column]);
        if(d[column] && d[column].toString().toLowerCase().indexOf(val) > -1) {
          return true;
        }
      }
    });

    this.rows = rows;

  }
}
