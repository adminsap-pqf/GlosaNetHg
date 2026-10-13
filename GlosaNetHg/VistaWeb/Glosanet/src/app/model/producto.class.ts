export class Producto{

    idProducto: number;
  	idEmpresa: number;
  	nombre: string;
  	origen: string;
  	registro: boolean;
    permiso: boolean;
    codigo: string;
    permiso2: string;
    vigencia: string;
    descripcion: string;
    tipoProducto: number;

    unidad:string;
    cantidad:number;
    totalProducto:number;

    presentacion:string;

    idPrevision:number;
    idPrevisionProducto:number;


    constructor(){
        this.idProducto = 0;
        this.idEmpresa = 0;
        this.nombre = '';
        this.origen = '';
        this.registro = false;
        this.permiso = false;
        this.codigo = '';
        this.permiso2 = '';
        this.vigencia = '';
        this.descripcion ='';
        this.tipoProducto = 0;

        this.unidad = '';
        this.cantidad = 0;

        this.presentacion = '';
        this.idPrevision = 0;
        this.idPrevisionProducto = 0;
        this.totalProducto = 0;
    }

    reset(){
        this.idProducto = 0;
        this.idEmpresa = 0;
        this.nombre = '';
        this.origen = '';
        this.registro = false;
        this.permiso = false;
        this.codigo = '';
        
        this.unidad = '';
        this.cantidad = 0;

        this.presentacion = '';
        this.idPrevision = 0;
        this.idPrevisionProducto = 0;
        this.totalProducto = 0;
    }
  }
