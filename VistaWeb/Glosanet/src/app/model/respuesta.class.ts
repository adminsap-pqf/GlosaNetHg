import { Usuario } from "./usuario.class";
import { Cliente } from "./cliente.class";

export class Respuesta {

    usuario: Usuario;
	cliente: Cliente;
	lstDataStage: Array<any>;
	administrador: Array<Usuario> ;
	dof: Date;
  
    constructor(){
        this.usuario = null;
        this.cliente = null;
        this.lstDataStage = [];
        this.administrador = [];
        this.dof = null;
    }

    reset(){
        this.usuario = null;
        this.cliente = null;
        this.lstDataStage = [];
        this.administrador = [];
        this.dof = null;
    }
  }