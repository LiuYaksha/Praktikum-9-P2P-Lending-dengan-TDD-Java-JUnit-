package com.p2p.service;

import com.p2p.domain.*;

import java.math.BigDecimal;

public class LoanService {

    public Loan createLoan(Borrower borrower, BigDecimal amount) {

        // =========================
        // VALIDASI (delegasi ke domain)
        // =========================
        validateBorrower(borrower);
        validateAmount(amount);

        // =========================
        // CREATE LOAN (domain object)
        // =========================
        Loan loan = new Loan();

        // =========================
        // BUSINESS ACTION (domain behavior)
        // =========================
        if (borrower.getCreditScore() >= 600) {
            loan.approve();
        } else {
            loan.reject();
        }

        return loan;
    }

    // =========================
    // PRIVATE VALIDATION METHODS
    // =========================

    // Fowler: Extract Method — memisahkan validation logic
    // Fowler: Move Method — menggunakan domain method canApplyLoan()
    private void validateBorrower(Borrower borrower) {
        if (!borrower.canApplyLoan()) {
            throw new IllegalArgumentException("Borrower not verified");
        }
    }

    // Validasi jumlah pinjaman (TC-02)
    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Loan amount must be positive");
        }
    }
}
