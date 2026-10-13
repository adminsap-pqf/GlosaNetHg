package org.mx.controllers.registros;

import org.mx.controllers.RequestOptions;

public final class RegistrosRequestOptions extends RequestOptions {

    //required parameters
    private final int idCompany;

    //optional paramaters
    private final String tab;
    private final String manager;
    private final String managerEvent;
    private final String keyword;
    private final boolean withFolio;
    private final boolean withoutFolio;
    private final String statusCofepris;
    private final String filterEventName;
    private final String filterEventDate;

    private RegistrosRequestOptions(RegistrosRequestOptionsBuilder builder) {
        this.page = builder.getPage();
        this.size = builder.getSize();
        this.idCompany = builder.idCompany;
        this.tab = builder.tab;
        this.manager = builder.manager;
        this.managerEvent = builder.managerEvent;
        this.keyword = builder.keyword;
        this.withFolio = builder.withFolio;
        this.withoutFolio = builder.withoutFolio;
        this.statusCofepris = builder.statusCofepris;
        this.filterEventName = builder.filterEventName;
        this.filterEventDate = builder.filterEventDate;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public String getTab() {
        return tab;
    }

    public String getManager() {
        return manager;
    }

    public String getManagerEvent() {
        return managerEvent;
    }

    public String getKeyword() {
        return keyword;
    }

    public boolean isWithFolio() {
        return withFolio;
    }

    public boolean isWithoutFolio() {
        return withoutFolio;
    }

    public String getStatusCofepris() {
        return statusCofepris;
    }

    public String getFilterEventName() {
        return filterEventName;
    }

    public String getFilterEventDate() {
        return filterEventDate;
    }

    //Builder class
    public static final class RegistrosRequestOptionsBuilder extends RequestOptions {

        //required parameters
        private int idCompany;

        //optional paramaters
        private String tab;
        private String manager;
        private String managerEvent;
        private String keyword;
        private boolean withFolio;
        private boolean withoutFolio;
        private String statusCofepris;
        private String filterEventName;
        private String filterEventDate;

        public RegistrosRequestOptionsBuilder(int idCompany, int page, int size) {
            this.idCompany = idCompany;
            this.page = page;
            this.size = size;
        }

        public RegistrosRequestOptionsBuilder setTab(String tab) {
            this.tab = tab;
            return this;
        }

        public RegistrosRequestOptionsBuilder setManager(String manager) {
            this.manager = manager;
            return this;
        }

        public RegistrosRequestOptionsBuilder setManagerEvent(String managerEvent) {
            this.managerEvent = managerEvent;
            return this;
        }

        public RegistrosRequestOptionsBuilder setKeyword(String keyword) {
            this.keyword = keyword;
            return this;
        }

        public RegistrosRequestOptionsBuilder setWithFolio(boolean withFolio) {
            this.withFolio = withFolio;
            return this;
        }

        public RegistrosRequestOptionsBuilder setWithoutFolio(boolean withoutFolio) {
            this.withoutFolio = withoutFolio;
            return this;
        }

        public RegistrosRequestOptionsBuilder setStatusCofepris(String statusCofepris) {
            this.statusCofepris = statusCofepris;
            return this;
        }

        public RegistrosRequestOptionsBuilder setFilterEventName(String filterEventName) {
            this.filterEventName = filterEventName;
            return this;
        }

        public RegistrosRequestOptionsBuilder setFilterEventDate(String filterEventDate) {
            this.filterEventDate = filterEventDate;
            return this;
        }

        public RegistrosRequestOptions build() {
            return new RegistrosRequestOptions(this);
        }

    }
}
