export class Legend{
    
    titulo: string;
    dataDefault: number;
    dataDouble: number;
    colorDefault: string;
    nameDefault: string;
    nameDouble: string;
    colorDouble: string;
    double: boolean;
    colorLabel:string;
  
    constructor(){
        this.titulo = '';
        this.dataDefault = 0;
        this.dataDouble = 0;
        this.colorDefault = '#FFFFFF';
        this.nameDefault = '';
        this.nameDouble = '';
        this.colorDouble = '#FFFFFF';
        this.double = false;
        this.colorLabel = '#5578C2';
    }

    reset(){
        this.titulo = '';
        this.dataDefault = 0;
        this.dataDouble = 0;
        this.colorDefault = '#FFFFFF';
        this.nameDefault = '';
        this.nameDouble = '';
        this.colorDouble = '#FFFFFF';
        this.double = false;
        this.colorLabel = '#5578C2';
    }
  
  
  
  }