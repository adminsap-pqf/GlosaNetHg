package org.mx.controllers.datastage;

import org.mx.controllers.RequestOptions;

public class VUCEMSolicitudRequestOptions extends RequestOptions {

    //required parameters
    private final Integer idDatastage;

    private VUCEMSolicitudRequestOptions(VUCEMSolicitudRequestOptionsBuilder builder) {
        this.page = builder.getPage();
        this.size = builder.getSize();
        this.idDatastage = builder.idDatastage;
    }

    public Integer getIdDatastage() {
        return idDatastage;
    }

    //Builder class
    public static final class VUCEMSolicitudRequestOptionsBuilder extends RequestOptions {

        //required parameters
        private Integer idDatastage;

        public VUCEMSolicitudRequestOptionsBuilder(Integer idDatastage, int page, int size) {
            this.idDatastage = idDatastage;
            this.page = page;
            this.size = size;
        }

        public VUCEMSolicitudRequestOptions build() {
            return new VUCEMSolicitudRequestOptions(this);
        }
    }
}
