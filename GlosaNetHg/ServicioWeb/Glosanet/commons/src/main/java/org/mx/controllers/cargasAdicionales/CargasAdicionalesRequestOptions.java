package org.mx.controllers.cargasAdicionales;

import org.mx.controllers.RequestOptions;

public class CargasAdicionalesRequestOptions extends RequestOptions {
    //required parameters
    private final int idPedimento;

    public CargasAdicionalesRequestOptions(CargasAdicionalesOptionBuilder builder) {
        this.idPedimento=builder.idPedimento;
    }

    //Builder class

    public static final class CargasAdicionalesOptionBuilder extends RequestOptions{
        //required parameters
        private int idPedimento;

        public CargasAdicionalesOptionBuilder(int idPedimento, int page,int size) {
            this.idPedimento = idPedimento;
            this.size=size;
            this.page=page;
        }

        public CargasAdicionalesOptionBuilder setIdPedimento(int idPedimento) {
            this.idPedimento = idPedimento;
            return this;
        }

       public CargasAdicionalesRequestOptions build(){
            return new CargasAdicionalesRequestOptions(this);
       }
    }
}
