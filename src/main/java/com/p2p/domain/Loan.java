package com.p2p.domain;

/**
 * Class ini merepresentasikan pinjaman (Loan) dalam sistem P2P Lending.
 */
public class Loan {

    // Enum untuk status loan
    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private Status status;

    // Saat loan dibuat, status awal adalah PENDING
    public Loan() {
        this.status = Status.PENDING;
    }

    // Getter untuk membaca status loan
    public Status getStatus() {
        return status;
    }

    // Setter untuk mengubah status loan
    public void setStatus(Status status) {
        this.status = status;
    }

    // =========================
    // DOMAIN BEHAVIOR
    // =========================
    // Fowler: Replace Conditional Logic
    // Service tidak lagi set state langsung, domain mulai "rich"
    public void approve() {
        this.status = Status.APPROVED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }
}
