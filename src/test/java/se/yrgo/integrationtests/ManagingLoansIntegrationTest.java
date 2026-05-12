package se.yrgo.integrationtests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import se.yrgo.domain.*;
import se.yrgo.services.*;
import se.yrgo.domain.enums.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("/application.xml")
@Transactional
public class ManagingLoansIntegrationTest {

    @Autowired
    private LoanManagementService loans;

    @Test
    public void testAddingLoans() {
        loans.create(
                new Loan(
                        new Skin(
                                "AK-47 | Redline",
                                Type.RIFLE,
                                Rarity.CLASSIFIED,
                                Condition.FIELDTESTED,
                                15.99),
                        new Player(
                                "s1mple",
                                null)));

        int loansInDb = loans.getAllLoans().size();
        assertEquals(1, loansInDb);
    }
}