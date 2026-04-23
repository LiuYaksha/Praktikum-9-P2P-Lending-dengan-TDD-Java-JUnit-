package com.p2p.service;

import com.p2p.domain.Lender;

/**
 * Service class for P2P Lending business logic.
 */
public class LendingService {

    /**
     * Processes a loan request between a lender and a borrower.
     * Returns true if the lender has sufficient funds.
     */
    public boolean processLoan(Lender lender, double loanAmount, String borrowerName) {
        if (lender.getAvailableFunds() >= loanAmount) {
            lender.setAvailableFunds(lender.getAvailableFunds() - loanAmount);
            System.out.println("Loan approved: " + borrowerName
                    + " received Rp" + loanAmount
                    + " from " + lender.getName());
            return true;
        }
        System.out.println("Loan rejected: insufficient funds.");
        return false;
    }

    /**
     * Calculates simple interest.
     * @param principal  Loan principal
     * @param ratePercent Annual interest rate in percent
     * @param years      Duration in years
     * @return Total interest amount
     */
    public double calculateInterest(double principal, double ratePercent, int years) {
        return principal * (ratePercent / 100) * years;
    }
}
