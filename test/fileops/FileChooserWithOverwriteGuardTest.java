/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fileops;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the FileChooserWithOverwriteGuard class.
 * @author Alonso del Arte
 */
public class FileChooserWithOverwriteGuardTest {
    
    @Test
    public void testApproveSelectionCallsAskToOverwriteExistingFile() {
        MockFileChooser fileChooser 
                = new MockFileChooser(JOptionPane.YES_OPTION);
        fail("HAVEN'T WRITTEN TEST YET");
    }
    
    /**
     * Test of the getOverwriteQuestionResponse function, of the 
     * FileChooserWithOverwriteGuard class.
     */
//    @Test
    public void testGetOverwriteQuestionResponse() {
        System.out.println("getOverwriteQuestionResponse");
        String filename = "";
        FileChooserWithOverwriteGuard instance = new FileChooserWithOverwriteGuard();
        int expResult = 0;
        int result = instance.getOverwriteQuestionResponse(filename);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of the approveSelection procedure, of the 
     * FileChooserWithOverwriteGuard class.
     */
//    @Test
    public void testApproveSelection() {
        System.out.println("approveSelection");
        FileChooserWithOverwriteGuard instance = new FileChooserWithOverwriteGuard();
        instance.approveSelection();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    private static class MockFileChooser extends FileChooserWithOverwriteGuard {
        
        int callCount = 0;
        
        private final int responseToMock;
        
        @Override
        int getOverwriteQuestionResponse(String filename) {
            this.callCount++;
            return this.responseToMock;
        }

        MockFileChooser(int responseCode) {
            this.responseToMock = responseCode;
        }
    
    }
    
}
