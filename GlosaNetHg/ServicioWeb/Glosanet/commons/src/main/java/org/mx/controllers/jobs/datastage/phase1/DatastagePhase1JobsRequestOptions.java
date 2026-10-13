package org.mx.controllers.jobs.datastage.phase1;

import org.mx.controllers.RequestOptions;

public final class DatastagePhase1JobsRequestOptions extends RequestOptions {

    public static final String DSP1J_ORDERBY_ASC = "+";
    public static final String DSP1J_ORDERBY_DESC = "-";
    public static final String DSP1J_ORDERBYVALUE_CREATIONDATE = "creationDate";
    public static final String DSP1J_ORDERBYVALUE_STATUS = "status";

    //optional paramaters
    private final String filterInitialDate;
    private final String filterFinalDate;
    private final String filterStatus;
    private final String filterCompany;
    private final String orderBy;
    private final String orderBySymbol;

    private DatastagePhase1JobsRequestOptions(DatastagePhase1JobsRequestOptionsBuilder builder) {
        this.page = builder.getPage();
        this.size = builder.getSize();
        this.filterInitialDate = builder.filterInitialDate;
        this.filterFinalDate = builder.filterFinalDate;
        this.filterStatus = builder.filterStatus;
        this.orderBy = builder.orderBy;
        this.orderBySymbol = builder.orderBySymbol;
        this.filterCompany = builder.filterCompany;
    }

    public String getFilterInitialDate() {
        return filterInitialDate;
    }
    public String getFilterFinalDate() {
        return filterFinalDate;
    }
    public String getFilterStatus() {
        return filterStatus;
    }
    public String getFilterCompany() { return filterCompany; }
    public String getOrderBy() {
        return orderBy;
    }
    public String getOrderBySymbol() {
        return orderBySymbol;
    }

    //Builder class
    public static final class DatastagePhase1JobsRequestOptionsBuilder extends RequestOptions {

        //optional paramaters
        private String filterInitialDate;
        private String filterFinalDate;
        private String filterStatus;
        private String filterCompany;
        private String orderBy;
        private String orderBySymbol;

        public DatastagePhase1JobsRequestOptionsBuilder(int page, int size) {
            this.page = page;
            this.size = size;
        }
        public DatastagePhase1JobsRequestOptionsBuilder setFilterInitialDate(String filterInitialDate) {
            this.filterInitialDate = filterInitialDate;
            return this;
        }
        public DatastagePhase1JobsRequestOptionsBuilder setOrderBy(String orderBy) {
            this.orderBy = orderBy;
            return this;
        }
        public DatastagePhase1JobsRequestOptionsBuilder setOrderBySymbol(String orderBySymbol) {
            this.orderBySymbol = orderBySymbol;
            return this;
        }
        public DatastagePhase1JobsRequestOptionsBuilder setFilterFinalDate(String filterFinalDate) {
            this.filterFinalDate = filterFinalDate;
            return this;
        }
        public DatastagePhase1JobsRequestOptionsBuilder setFilterStatus(String filterStatus) {
            this.filterStatus = filterStatus;
            return this;
        }
        public DatastagePhase1JobsRequestOptionsBuilder setFilterCompany(String filterCompany) {
            this.filterCompany = filterCompany;
            return this;
        }

        public DatastagePhase1JobsRequestOptions build() {
            return new DatastagePhase1JobsRequestOptions(this);
        }
    }
}
