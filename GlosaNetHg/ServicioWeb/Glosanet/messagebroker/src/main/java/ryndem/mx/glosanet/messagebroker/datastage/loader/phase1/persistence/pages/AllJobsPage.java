package ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.persistence.pages;

import ryndem.mx.glosanet.messagebroker.datastage.loader.phase1.model.dto.AllJobsDTO;

import java.util.List;

public class AllJobsPage {

    private List<AllJobsDTO> results;
    private Integer total;

    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public List<AllJobsDTO> getResults() {
        return results;
    }
    public void setResults(List<AllJobsDTO> results) {
        this.results = results;
    }
}
