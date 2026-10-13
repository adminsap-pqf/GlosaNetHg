export class Notificacion {

    idNotificacion:number;
    idEvento:number;
    finicio:Date;
    cantPeriodo:number;
    tipoPeriodo:string;
    terminado:boolean;
    recurrencia:string;
    startDate:Date;
    startDateText:string;

    constructor(){
        this.idNotificacion = 0;
        this.idEvento = 0;
        this.finicio = null;
        this.cantPeriodo = 0;
        this.tipoPeriodo = '';
        this.terminado = false;
        this.recurrencia = '';
        this.startDate = null;
        this.startDateText = '';
    }

    reset(){
        this.idNotificacion = 0;
        this.idEvento = 0;
        this.finicio = null;
        this.cantPeriodo = 0;
        this.tipoPeriodo = '';
        this.terminado = false;
        this.recurrencia = '';
        this.startDate = null;
        this.startDateText = '';
    }
  
  }
