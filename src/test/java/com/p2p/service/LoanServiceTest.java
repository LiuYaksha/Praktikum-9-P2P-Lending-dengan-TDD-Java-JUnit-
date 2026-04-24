package com.p2p.service;

import com.p2p.domain.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Unit tests for LoanService — TDD Test Cases (TC-01 ~ TC-04)
 */
class LoanServiceTest {

    // Logger untuk LoanServiceTest
    private static final Logger logger = LogManager.getLogger(LoanServiceTest.class);

    // ==========================================================
    // TC-01: shouldRejectLoanWhenBorrowerNotVerified
    // ==========================================================
    // SCENARIO:
    // - Borrower tidak terverifikasi (KYC = false)
    // - Ketika borrower mengajukan pinjaman
    // - Maka sistem harus menolak dengan melempar exception
    // ==========================================================
    @Test
    void shouldRejectLoanWhenBorrowerNotVerified() {

        logger.info("===== TC-01: shouldRejectLoanWhenBorrowerNotVerified =====");

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower belum lolos proses KYC
        Borrower borrower = new Borrower(false, 700);
        logger.info("Arrange — Borrower(verified=false, creditScore=700)");

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(1000);
        logger.info("Arrange — amount={}", amount);

        // =========================
        // Act & Assert
        // =========================
        // Borrower mencoba mengajukan loan → harus exception
        logger.info("Act — Memanggil createLoan() dengan borrower belum verified...");
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });

        logger.info("Assert — IllegalArgumentException berhasil dilempar");
        logger.info("===== TC-01 PASSED =====\n");
    }

    // ==========================================================
    // TC-02: shouldRejectLoanWhenAmountIsZeroOrNegative
    // ==========================================================
    // SCENARIO:
    // - Borrower valid (verified, credit score bagus)
    // - Amount <= 0
    // - Maka sistem harus menolak dengan melempar exception
    // ==========================================================
    @Test
    void shouldRejectLoanWhenAmountIsZeroOrNegative() {

        logger.info("===== TC-02: shouldRejectLoanWhenAmountIsZeroOrNegative =====");

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower sudah terverifikasi
        Borrower borrower = new Borrower(true, 700);
        logger.info("Arrange — Borrower(verified=true, creditScore=700)");

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman TIDAK valid (nol dan negatif)
        BigDecimal zeroAmount = BigDecimal.ZERO;
        BigDecimal negativeAmount = BigDecimal.valueOf(-500);
        logger.info("Arrange — zeroAmount={}, negativeAmount={}", zeroAmount, negativeAmount);

        // =========================
        // Act & Assert
        // =========================
        // Amount = 0 → harus exception
        logger.info("Act — Memanggil createLoan() dengan amount=0...");
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, zeroAmount);
        });
        logger.info("Assert — Exception berhasil dilempar untuk amount=0");

        // Amount < 0 → harus exception
        logger.info("Act — Memanggil createLoan() dengan amount=-500...");
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, negativeAmount);
        });
        logger.info("Assert — Exception berhasil dilempar untuk amount=-500");

        logger.info("===== TC-02 PASSED =====\n");
    }

    // ==========================================================
    // TC-03: shouldApproveLoanWhenCreditScoreHigh
    // ==========================================================
    // SCENARIO:
    // - Borrower verified
    // - Credit score >= 600 (threshold)
    // - Maka status loan = APPROVED
    // ==========================================================
    @Test
    void shouldApproveLoanWhenCreditScoreHigh() {

        logger.info("===== TC-03: shouldApproveLoanWhenCreditScoreHigh =====");

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower verified dengan credit score tinggi
        Borrower borrower = new Borrower(true, 750);
        logger.info("Arrange — Borrower(verified=true, creditScore=750)");

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(5000);
        logger.info("Arrange — amount={}", amount);

        // =========================
        // Act (Action)
        // =========================
        logger.info("Act — Memanggil createLoan()...");
        Loan loan = loanService.createLoan(borrower, amount);
        logger.info("Act — Loan dibuat, status={}", loan.getStatus());

        // =========================
        // Assert (Expected Result)
        // =========================
        assertEquals(Loan.Status.APPROVED, loan.getStatus());
        logger.info("Assert — Status loan = APPROVED ✓");

        logger.info("===== TC-03 PASSED =====\n");
    }

    // ==========================================================
    // TC-04: shouldRejectLoanWhenCreditScoreLow
    // ==========================================================
    // SCENARIO:
    // - Borrower verified
    // - Credit score < 600 (threshold)
    // - Maka status loan = REJECTED
    // ==========================================================
    @Test
    void shouldRejectLoanWhenCreditScoreLow() {

        logger.info("===== TC-04: shouldRejectLoanWhenCreditScoreLow =====");

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower verified tapi credit score rendah
        Borrower borrower = new Borrower(true, 400);
        logger.info("Arrange — Borrower(verified=true, creditScore=400)");

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(5000);
        logger.info("Arrange — amount={}", amount);

        // =========================
        // Act (Action)
        // =========================
        logger.info("Act — Memanggil createLoan()...");
        Loan loan = loanService.createLoan(borrower, amount);
        logger.info("Act — Loan dibuat, status={}", loan.getStatus());

        // =========================
        // Assert (Expected Result)
        // =========================
        assertEquals(Loan.Status.REJECTED, loan.getStatus());
        logger.info("Assert — Status loan = REJECTED ✓");

        logger.info("===== TC-04 PASSED =====\n");
    }
}
