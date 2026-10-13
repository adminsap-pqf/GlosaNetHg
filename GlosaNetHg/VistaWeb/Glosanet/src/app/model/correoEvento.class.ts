export class CorreoEvento {

    idCorreo:number;
    idEvento:number;
    correo:string
    
    constructor(){
        this.idCorreo = 0;
        this.idEvento = 0;
        this.correo = '';
    }

    reset(){
        this.idCorreo = 0;
        this.idEvento = 0;
        this.correo = '';
    }
  
  }