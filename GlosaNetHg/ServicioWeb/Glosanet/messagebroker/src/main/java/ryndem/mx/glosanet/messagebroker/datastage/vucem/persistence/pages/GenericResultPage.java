package ryndem.mx.glosanet.messagebroker.datastage.vucem.persistence.pages;

import java.util.List;

public class GenericResultPage {
    private List<Object> results;
    private Integer total;

    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public List<Object> getResults() {
        return results;
    }
    public void setResults(List<Object> results) {
        this.results = results;
    }
}
