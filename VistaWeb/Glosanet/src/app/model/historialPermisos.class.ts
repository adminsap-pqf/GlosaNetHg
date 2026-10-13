export class HistorialPermisos{
  permiso: string;
  vigenciaPermiso: string;
  idproducto: number;
  fkProducto: number;
  fkPartidas:number;
  flagProductoPartidas: boolean;


  constructor(){
    this.permiso ='';
    this.vigenciaPermiso='';
    this.idproducto=0;
    this.fkProducto=0;
    this.fkPartidas=0;
    this.flagProductoPartidas = false;
  }



}
