export class HistorialImportaciones {

  fk01producto: number;
  cantidad: any;
  unidad: string;
  fk02partidad: number;
  numPermiso: any;
  flagProductoPartidas: boolean;
  idpermiso: any;

  constructor(){
    this.fk01producto =0;
    this.cantidad='';
    this.unidad='';
    this.fk02partidad=0;
    this.numPermiso='';
    this.flagProductoPartidas=false;
    this.idpermiso = 0;
  }


}
