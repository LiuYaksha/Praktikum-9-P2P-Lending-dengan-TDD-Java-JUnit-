package com.p2p.domain;

/**
 * Represents a Lender in the P2P Lending system.
 */
public class Lender {
    private String id;
    private String name;
    private double availableFunds;

    public Lender(String id, String name, double availableFunds) {
        this.id = id;
        this.name = name;
        this.availableFunds = availableFunds;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getAvailableFunds() { return availableFunds; }

    public void setAvailableFunds(double availableFunds) {
        this.availableFunds = availableFunds;
    }

    @Override
    public String toString() {
        return "Lender{id='" + id + "', name='" + name + "', availableFunds=" + availableFunds + "}";
    }
}
