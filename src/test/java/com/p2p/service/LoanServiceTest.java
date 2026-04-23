package com.p2p.service;

import com.p2p.domain.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

/**
 * Unit tests for LoanService — TDD Test Cases (TC-01 ~ TC-04)
 */
class LoanServiceTest {

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

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower belum lolos proses KYC
        Borrower borrower = new Borrower(false, 700);

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(1000);

        // =========================
        // Act & Assert
        // =========================
        // Borrower mencoba mengajukan loan → harus exception
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, amount);
        });
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

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower sudah terverifikasi
        Borrower borrower = new Borrower(true, 700);

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman TIDAK valid (nol dan negatif)
        BigDecimal zeroAmount = BigDecimal.ZERO;
        BigDecimal negativeAmount = BigDecimal.valueOf(-500);

        // =========================
        // Act & Assert
        // =========================
        // Amount = 0 → harus exception
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, zeroAmount);
        });

        // Amount < 0 → harus exception
        assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(borrower, negativeAmount);
        });
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

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower verified dengan credit score tinggi
        Borrower borrower = new Borrower(true, 750);

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(5000);

        // =========================
        // Act (Action)
        // =========================
        Loan loan = loanService.createLoan(borrower, amount);

        // =========================
        // Assert (Expected Result)
        // =========================
        assertEquals(Loan.Status.APPROVED, loan.getStatus());
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

        // =========================
        // Arrange (Initial Condition)
        // =========================
        // Borrower verified tapi credit score rendah
        Borrower borrower = new Borrower(true, 400);

        // Service untuk pengajuan loan
        LoanService loanService = new LoanService();

        // Jumlah pinjaman valid
        BigDecimal amount = BigDecimal.valueOf(5000);

        // =========================
        // Act (Action)
        // =========================
        Loan loan = loanService.createLoan(borrower, amount);

        // =========================
        // Assert (Expected Result)
        // =========================
        assertEquals(Loan.Status.REJECTED, loan.getStatus());
    }
}
