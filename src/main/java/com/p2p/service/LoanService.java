package com.p2p.service;

import com.p2p.domain.*;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoanService {

    // Logger untuk LoanService
    private static final Logger logger = LogManager.getLogger(LoanService.class);

    public Loan createLoan(Borrower borrower, BigDecimal amount) {

        logger.info("=== Memulai proses createLoan ===");
        logger.info("Borrower verified: {}, Credit Score: {}, Amount: {}",
                borrower.isVerified(), borrower.getCreditScore(), amount);

        // =========================
        // VALIDASI (delegasi ke domain)
        // =========================
        validateBorrower(borrower);
        validateAmount(amount);

        // =========================
        // CREATE LOAN (domain object)
        // =========================
        Loan loan = new Loan();
        logger.debug("Loan object dibuat dengan status awal: {}", loan.getStatus());

        // =========================
        // BUSINESS ACTION (domain behavior)
        // =========================
        if (borrower.getCreditScore() >= 600) {
            loan.approve();
            logger.info("Loan APPROVED — Credit Score {} >= 600", borrower.getCreditScore());
        } else {
            loan.reject();
            logger.warn("Loan REJECTED — Credit Score {} < 600", borrower.getCreditScore());
        }

        logger.info("=== Proses createLoan selesai — Status: {} ===", loan.getStatus());
        return loan;
    }

    // =========================
    // PRIVATE VALIDATION METHODS
    // =========================

    // Fowler: Extract Method — memisahkan validation logic
    // Fowler: Move Method — menggunakan domain method canApplyLoan()
    private void validateBorrower(Borrower borrower) {
        if (!borrower.canApplyLoan()) {
            logger.error("VALIDASI GAGAL: Borrower belum terverifikasi (KYC = false)");
            throw new IllegalArgumentException("Borrower not verified");
        }
        logger.debug("Validasi borrower PASSED — KYC terverifikasi");
    }

    // Validasi jumlah pinjaman (TC-02)
    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            logger.error("VALIDASI GAGAL: Jumlah pinjaman tidak valid (amount = {})", amount);
            throw new IllegalArgumentException("Loan amount must be positive");
        }
        logger.debug("Validasi amount PASSED — amount = {}", amount);
    }
}
