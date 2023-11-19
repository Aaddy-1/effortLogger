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
            "Employee Name: XXX\n" + //
            "Employee ID: XXX\n" + //
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
            "Employee Name: John Doe\n" + //
            "Employee ID: XXX" + //
            "Project: Project X" + //
            "Hours Worked: 40\n" + //
            "");
        assertEquals(false, result);
    } 
    @Test
    public void TestAnonymizationTrue() {
        String anonymized = verifier.anonymizeText("Employee Name: John Doe\n" + //
            "Employee ID: 12345\n" + //
            "Project: Project X\n" + //
            "Hours Worked: 40\n" + //
            "");
        
        String anonymizedText = "Employee Name: XXX\n" + //
            "Employee ID: XXX\n" + //
            "Project: Project X\n" + //
            "Hours Worked: 40\n" + //
            "";
        
            System.out.println(anonymized);
        
        boolean result = anonymized.equals(anonymizedText);
        assertEquals(true, result);
    }

    public void TestAnonymizationFalse() {
        String anonymized = verifier.anonymizeText("Employee Name: John Doe\n" + //
            "Employee ID: 12345\n" + //
            "Project: Project X\n" + //
            "Hours Worked: 40\n" + //
            "");
        
        String anonymizedText = "Employee Name: XXX\n" + //
            "Employee ID: 1234\n" + //
            "Project: Project X\n" + //
            "Hours Worked: 40\n" + //
            "";
        
        boolean result = anonymized.equals(anonymizedText);
        assertEquals(false, result);
    }

}
