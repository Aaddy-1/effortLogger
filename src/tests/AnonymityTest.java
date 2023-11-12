package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import main.AnonymityVerifier;

public class AnonymityTest {
    AnonymityVerifier verifier =  new AnonymityVerifier();
    @Test
    public void TestVerifyAnonymizationTrue() {
        boolean result = verifier.verifyAnonymization(
            "Employee Name: John Doe\n" + //
            "Employee ID: 12345\n" + //
            "Project: Project X\n" + //
            "Hours Worked: 40\n" + //
            "", 
            "Employee Name: XXXX XXX\n" + //
            "Employee ID: #####\n" + //
            "Project: Project X\n" + //
            "Hours Worked: 40\n" + //
            "");
        assertEquals(true, result);
    }
    @Test
    public void TestVerifyAnonymizationFalse() {
        boolean result = verifier.verifyAnonymization(
            "Employee Name: John Doe\n" + //
            "Employee ID: 12345\n" + //
            "Project: Project X\n" + //
            "Hours Worked: 40\n" + //
            "", 
            "Employee Name: XXXXX\n" + //
            "Employee ID: #####" + //
            "Project: Project X" + //
            "Hours Worked: 40\n" + //
            "");
        assertEquals(false, result);
    } 

}
