package com.db.terraform.data;

public class Agent {
    private int agentId;
    private String name;
    private String branch;

    public Agent(int agentId, String name, String branch) {
        this.agentId = agentId;
        this.name = name;
        this.branch = branch;
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
}