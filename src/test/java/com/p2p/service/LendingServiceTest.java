package com.p2p.service;

import com.p2p.domain.Lender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for LendingService.
 */
class LendingServiceTest {

    private LendingService service;
    private Lender lender;

    @BeforeEach
    void setUp() {
        service  = new LendingService();
        lender   = new Lender("L001", "Budi", 10_000_000);
    }

    @Test
    void testProcessLoan_approved() {
        boolean result = service.processLoan(lender, 5_000_000, "Ani");
        assertTrue(result, "Loan should be approved when funds are sufficient");
        assertEquals(5_000_000, lender.getAvailableFunds(), 0.01,
                "Remaining funds should be 5,000,000 after loan");
    }

    @Test
    void testProcessLoan_rejected_insufficientFunds() {
        boolean result = service.processLoan(lender, 20_000_000, "Cici");
        assertFalse(result, "Loan should be rejected when funds are insufficient");
    }

    @Test
    void testCalculateInterest() {
        double interest = service.calculateInterest(10_000_000, 5, 2);
        assertEquals(1_000_000, interest, 0.01, "Interest should be 1,000,000");
    }
}
