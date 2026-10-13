package org.mx.controllers.datastage;

import org.mx.controllers.RequestOptions;

public class DatastageRequestOptions extends RequestOptions {

    //required parameters
    private final int idCompany;
    private final int idDatastage;

    //optional parameters
    private final String filterInitialDate;
    private final String filterFinalDate;
    private final String filterTipoOperacion;
    private final String filterTipoPedimento;
    private final String searchTerm;
    private final String pedimento;

    private DatastageRequestOptions(DatastageRequestOptionsBuilder builder) {
        this.page = builder.getPage();
        this.size = builder.getSize();
        this.idCompany = builder.idCompany;
        this.idDatastage = builder.idDatastage;
        this.filterInitialDate = builder.filterInitialDate;
        this.filterFinalDate = builder.filterFinalDate;
        this.filterTipoOperacion = builder.filterTipoOperacion;
        this.filterTipoPedimento = builder.filterTipoPedimento;
        this.searchTerm = builder.searchTerm;
        this.pedimento = builder.pedimento;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public int getIdDatastage() {
        return idDatastage;
    }

    public String getFilterInitialDate() {
        return filterInitialDate;
    }

    public String getFilterFinalDate() {
        return filterFinalDate;
    }

    public String getFilterTipoOperacion() {
        return filterTipoOperacion;
    }

    public String getFilterTipoPedimento() {
        return filterTipoPedimento;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public String getPedimento() {
        return pedimento;
    }

    //Builder class
    public static final class DatastageRequestOptionsBuilder extends RequestOptions {

        //required parameters
        private int idCompany;
        private int idDatastage;

        //optional parameters
        private String filterInitialDate;
        private String filterFinalDate;
        private String filterTipoOperacion;
        private String filterTipoPedimento;
        private String searchTerm;
        private String pedimento;

        public DatastageRequestOptionsBuilder(int idCompany, int idDatastage, int page, int size) {
            this.idCompany = idCompany;
            this.idDatastage = idDatastage;
            this.page = page;
            this.size = size;
        }

        public DatastageRequestOptionsBuilder setFilterInitialDate(String filterInitialDate) {
            this.filterInitialDate = filterInitialDate;
            return this;
        }

        public DatastageRequestOptionsBuilder setFilterFinalDate(String filterFinalDate) {
            this.filterFinalDate = filterFinalDate;
            return this;
        }

        public DatastageRequestOptionsBuilder setFilterTipoOperacion(String filterTipoOperacion) {
            this.filterTipoOperacion = filterTipoOperacion;
            return this;
        }

        public DatastageRequestOptionsBuilder setFilterTipoPedimento(String filterTipoPedimento) {
            this.filterTipoPedimento = filterTipoPedimento;
            return this;
        }

        public DatastageRequestOptionsBuilder setSearchTerm(String searchTerm) {
            this.searchTerm = searchTerm;
            return this;
        }

        public DatastageRequestOptionsBuilder setPedimento(String pedimento) {
            this.pedimento = pedimento;
            return this;
        }

        public DatastageRequestOptions build() {
            return new DatastageRequestOptions(this);
        }
    }
}
