package org.mx.modelo.comun.bo;

import java.util.List;

public class Responsables {
    private List<String> manager;
    private List<String> managerEvent;

    public List<String> getManager() {
        return manager;
    }

    public void setManager(List<String> manager) {
        this.manager = manager;
    }

    public List<String> getManagerEvent() {
        return managerEvent;
    }

    public void setManagerEvent(List<String> managerEvent) {
        this.managerEvent = managerEvent;
    }
}
