import { Eventos } from "./eventos.class";
import { Producto } from "./producto.class";
import { Usuario } from "./usuario.class";
import { Prevision } from "./prevision.class";

export class Permiso{
    idpermiso: number;
    idEmpresa: number;
    permiso: string;
    vigenciaPermiso: string;
    idproducto: number;
    fkProducto: number;
    fkPartidas: number;
    pkHistorialPermisos: number;
    flagProductoPartidas: boolean;
    ffin: Date;
    finicio: Date;
    folioRegistro: string;
    eventos: Array<Eventos>;
    producto: Producto;
    usuario: Usuario;
    tramiteCofepris: string;

    // variables aux
    numEventos: number;
    eventoActual: string;
    estadoCofepris: string;
    cerrados: number;


    citaCofepris:Date;

    cantidad: number;
    unidad: string;
    terminado: number;

    prevision: Prevision;

    previsionExt: boolean;

    
    citaCofeprisAux: Date;
    fechaCita:Date;

    fRealizacionFolio:Date;

    fecha:Date;

    diasNaturales: number = 0;
    vigente: boolean = false;

    constructor(){
        this.idpermiso = 0;
        this.idEmpresa = 0;
        this.permiso = '';
        this.vigenciaPermiso = '';
        this.idproducto = 0;
        this.fkProducto = 0;
        this.fkPartidas = 0;
        this.pkHistorialPermisos = 0;
        this.flagProductoPartidas = false;
        this.ffin = null;
        this.finicio = null;
        this.folioRegistro = '';
        this.eventos = [];
        this.producto = null;
        this.usuario = null;
        this.tramiteCofepris = '';

        // variables aux
        this.numEventos = 0;
        this.eventoActual = '';
        this.estadoCofepris = '';
        this.cerrados = 0;

        this.citaCofepris = null;

        this.cantidad = 0;
        this.unidad = '';
        this.terminado = 0;

        this.prevision = null;
        this.previsionExt = false;

        
        this.citaCofeprisAux = null;

        this.fechaCita = null;

        this.fRealizacionFolio = null;
        this.fecha = null;

        this.diasNaturales = 0;
        this.vigente = false;
    }
}