package com.db.terraform.data;

public class Agent {
    private int agentId;
    private String name;
    private String branch;
    private String created;
    private String updated;

    public Agent(int agentId, String name, String branch, String created, String updated) {
        this.agentId = agentId;
        this.name = name;
        this.branch = branch;
        this.created = created;
        this.updated = updated;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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