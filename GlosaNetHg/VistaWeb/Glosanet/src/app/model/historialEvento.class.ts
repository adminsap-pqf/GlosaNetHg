
export class HistorialEvento {

    idHistorial:number;
    idUsuario:number;
    idEvento:number;
    fecha:Date;
    comentarios:string;
    
    constructor(){
        this.idHistorial = 0;
        this.idUsuario = 0;
        this.idEvento = 0;
        this.fecha = null;
        this.comentarios = '';
    }

    reset(){
        this.idHistorial = 0;
        this.idUsuario = 0;
        this.idEvento = 0;
        this.fecha = null;
        this.comentarios = '';
    }
  
  }

