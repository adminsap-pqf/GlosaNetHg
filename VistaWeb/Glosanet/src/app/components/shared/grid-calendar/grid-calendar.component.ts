import { Component, OnInit, Input } from '@angular/core';
import { DatosGenerales } from '../../../model/datosGenerales.class';

/* Dev Tools */
import { DEBUG } from "../../../settings/app.settings";

@Component({
  selector: 'gn-grid-calendar',
  templateUrl: './grid-calendar.component.html',
  styleUrls: ['./grid-calendar.component.scss']
})
export class GridCalendarComponent implements OnInit {
  lstData: any[] = [];
  lstDataStage: any[] = [];
  lstAux: DatosGenerales[] = [];
  color: String = "azul";
  index:number = 0;
  ultimoDS:Date = new Date(0);

  @Input() year: any = "";
  @Input() lstDataInput: DatosGenerales[]=[];
  @Input() idCliente: any;
  @Input() rfc: any;

  constructor() {}

  ngOnChanges(){
    if(this.year != ""){
      this.lstData = [];
      this.index = 0;
      this.lstDataStage = [];
      DEBUG && console.log(this.ultimoDS);
      DEBUG && console.log(this.lstDataInput);
      for(let i=0 ; i<12 ; i++){
        let fecha: Date = new Date(this.year,i,1);
        this.lstData.push(fecha);
        this.lstAux = [];
        for (let item of this.lstDataInput){
          if (new Date(item.dataStage.fua) > this.ultimoDS) this.ultimoDS = new Date(item.dataStage.fua);
          let dateInicio: Date = new Date(item.finicio);
          let dateFin: Date = new Date(item.ffin);
          if (dateInicio.getFullYear() == dateFin.getFullYear()){
            if (i >= dateInicio.getMonth() && i <= dateFin.getMonth()){
              this.lstAux.push(item);
            }
          }else{
            this.lstAux.push(item);
          }
        }
        this.lstDataStage.push(this.lstAux);
      }
    }
  }

  ngOnInit() {
  }

}
