package com.db.terraform.data;

public class Contract {
    private int contract_id;
    private int customer_id;
    private int agent_id;
    private String start_date;
    private String end_date;
    private String created;
    private String updated;

    public Contract(int contract_id, int customer_id, int agent_id, String start_date, String end_date, String created, String updated) {
        this.contract_id = contract_id;
        this.customer_id = customer_id;
        this.agent_id = agent_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.created = created;
        this.updated = updated;
    }

    public int getContract_id() {
        return contract_id;
    }

    public void setContract_id(int contract_id) {
        this.contract_id = contract_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}