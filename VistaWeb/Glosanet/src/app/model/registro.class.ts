import { Eventos } from "./eventos.class";
import { Producto } from "./producto.class";
import { Usuario } from "./usuario.class";

export class Registro {

    idRegistro: number;
    producto: Producto;
    idEmpresa: number;
    finicio: Date
    ffin: Date;
    folioRegistro: string;
    tramiteCofepris: string;
    prorroga: boolean
    descripcion: string;
    eventos: Array<Eventos>
    usuario: Usuario;
    terminado: boolean;

    finicioProrroga: Date;
    ffinProrroga: Date;
    
    estadoCofepris:string;

    fecha:Date;

    constructor(){
        this.idRegistro = 0;
        this.idEmpresa = 0;
        this.finicio = null;
        this.ffin = null;
        this.folioRegistro = '';
        this.tramiteCofepris = '';
        this.prorroga = false;
        this.descripcion = '';
        this.eventos = [];
        this.producto = null;
        this.usuario = null;
        this.finicioProrroga = null;
        this.ffinProrroga = null;
        this.estadoCofepris = '';
        this.terminado = false;

        this.fecha = new Date();
    }

    reset(){
        this.idRegistro = 0;
        this.idEmpresa = 0;
        this.finicio = null;
        this.ffin = null;
        this.folioRegistro = '';
        this.tramiteCofepris = '';
        this.prorroga = false;
        this.descripcion = '';
        this.eventos = [];
        this.producto = null;
        this.usuario = null;

        this.finicioProrroga = null;
        this.ffinProrroga = null;
        this.estadoCofepris = '';
        this.terminado = false;

        //this.fecha = null;
    }
  
  }


