/*
 * Copyright (C) 2024 Alonso del Arte
 *
 * This program is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU General Public License as published by the Free Software 
 * Foundation, either version 3 of the License, or (at your option) any later 
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more 
 * details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fileops;

import static calculators.NumberTheoreticFunctionsCalculator.randomNumber;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the FileChooserWithOverwriteGuard class.
 * @author Alonso del Arte
 */
public class FileChooserWithOverwriteGuardTest implements ActionListener {
    
    private static final String TEMP_DIR_PATH 
            = System.getProperty("java.io.tmpdir");
    
    private static final String DUP_FILE_SEPS = "" + File.separatorChar 
            + File.separatorChar;
    
    private static final String EXAMPLE_FILE_PATH = (TEMP_DIR_PATH 
            + File.separatorChar + "EXAMPLE" + System.currentTimeMillis() 
            + ".txt").replace(DUP_FILE_SEPS, File.separator);
    
    private static final File EXISTING_FILE = new File(EXAMPLE_FILE_PATH);
    
    private static final boolean FILE_ALREADY_EXISTED = EXISTING_FILE.exists();
    
    private static final String STAND_BY_COMMAND = "standBy";
    
    private ActionEvent mostRecentEvent = null;
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        if (FILE_ALREADY_EXISTED) {
            System.out.println("File " + EXISTING_FILE.getAbsolutePath() 
                    + " already existed");
        } else {
            if (EXISTING_FILE.createNewFile()) {
                try (BufferedWriter writer 
                        = new BufferedWriter(new FileWriter(EXISTING_FILE))) {
                    String str = "This message was placed by setUpClass()\n";
                    writer.write(str);
                    System.out.println("Successfully created " 
                            + EXISTING_FILE.getAbsolutePath() 
                            + " and wrote to it");
                }
            } else {
                System.err.println("Unable to create " + EXAMPLE_FILE_PATH);
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        this.mostRecentEvent = event;
    }
    
    private void sendStandByEvent() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_FIRST, 
                STAND_BY_COMMAND);
        this.actionPerformed(event);
    }
    
    private static File inventFileWithNewFilename() {
        int bound = Integer.MAX_VALUE / 256;
        File file = EXISTING_FILE;
        while (file.exists()) {
            int number = randomNumber(bound);
            String pathname = TEMP_DIR_PATH + File.separatorChar + "file" 
                    + number + ".txt";
            file = new File(pathname);
        }
        return file;
    }
    
    /**
     * Test of the approveSelection procedure, of the 
     * FileChooserWithOverwriteGuard class.
     */
    @Test
    public void testApproveSelection() {
        System.out.println("approveSelection");
        this.sendStandByEvent();
        JFileChooser chooser = new FileChooserWithOverwriteGuard();
        chooser.addActionListener(this);
        File file = inventFileWithNewFilename();
        chooser.setSelectedFile(file);
        chooser.approveSelection();
        String expected = JFileChooser.APPROVE_SELECTION;
        String actual = this.mostRecentEvent.getActionCommand();
        String message = "Approve selection of file " + file.getAbsolutePath() 
                + " which does not already exist should trigger command \"" 
                + expected + "\"";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testApproveSelectionCallsAskToOverwriteExistingFile() {
        int[] responses = {JOptionPane.YES_OPTION, JOptionPane.NO_OPTION, 
            JOptionPane.CANCEL_OPTION, JOptionPane.CLOSED_OPTION};
        for (int responseCode : responses) {
            MockFileChooser chooser = new MockFileChooser(responseCode);
            chooser.setSelectedFile(EXISTING_FILE);
            chooser.approveSelection();
            int expected = 1;
            int actual = chooser.askToOverwriteCallCount;
            String message = "Since file " + EXISTING_FILE.getAbsolutePath() 
                    + " already exists, user should have been asked";
            assertEquals(message, expected, actual);
        }
    }
    
    @Test
    public void testApproveSelectionCancelOptionCancelsSelection() {
        this.sendStandByEvent();
        JFileChooser chooser = new MockFileChooser(JOptionPane.CANCEL_OPTION);
        chooser.addActionListener(this);
        chooser.setSelectedFile(EXISTING_FILE);
        chooser.approveSelection();
        String expected = JFileChooser.CANCEL_SELECTION;
        String actual = this.mostRecentEvent.getActionCommand();
        String message = "Canceling selection of existing file " 
                + EXISTING_FILE.getAbsolutePath() + " should trigger command \"" 
                + expected + "\"";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testApproveSelectionCanAllowOverwrite() throws IOException {
        this.sendStandByEvent();
        JFileChooser chooser = new MockFileChooser(JOptionPane.YES_OPTION);
        chooser.addActionListener(this);
        chooser.setSelectedFile(EXISTING_FILE);
        chooser.approveSelection();
        String expected = JFileChooser.APPROVE_SELECTION;
        String actual = this.mostRecentEvent.getActionCommand();
        String message = "Approving selection of existing file " 
                + EXISTING_FILE.getAbsolutePath() + " should trigger command \"" 
                + expected + "\"";
        assertEquals(message, expected, actual);
        try (BufferedWriter writer 
                = new BufferedWriter(new FileWriter(EXISTING_FILE, true))) {
            String str = "This message was placed by allow overwrite test";
            writer.write(str);
        }
    }
    
    @Test
    public void testApproveSelectionClosedOptionCancelsSelection() {
        this.sendStandByEvent();
        JFileChooser chooser = new MockFileChooser(JOptionPane.CLOSED_OPTION);
        chooser.addActionListener(this);
        chooser.setSelectedFile(EXISTING_FILE);
        chooser.approveSelection();
        String expected = JFileChooser.CANCEL_SELECTION;
        String actual = this.mostRecentEvent.getActionCommand();
        String message = "Closing dialog for selection of existing file " 
                + EXISTING_FILE.getAbsolutePath() + " should trigger command \"" 
                + expected + "\"";
        assertEquals(message, expected, actual);
    }
    
    @Test
    public void testApproveSelectionNoOptionDoesNotIssueCommand() {
        this.sendStandByEvent();
        JFileChooser chooser = new MockFileChooser(JOptionPane.NO_OPTION);
        chooser.addActionListener(this);
        chooser.setSelectedFile(EXISTING_FILE);
        chooser.approveSelection();
        String expected = STAND_BY_COMMAND;
        String actual = this.mostRecentEvent.getActionCommand();
        String message = "No on dialog for selection of existing file " 
                + EXISTING_FILE.getAbsolutePath() 
                + " should not trigger any command";
        assertEquals(message, expected, actual);
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

    @AfterClass
    public static void tearDownClass() throws IOException {
        System.out.println("About to read contents of " 
                + EXISTING_FILE.getAbsolutePath());
        Scanner scanner = new Scanner(EXISTING_FILE);
        while (scanner.hasNext()) {
            System.out.println(">> " + scanner.nextLine());
        }
        if (FILE_ALREADY_EXISTED) {
            System.out.println("Since " + EXISTING_FILE.getAbsolutePath() 
                    + " already existed, tearDownClass() will leave it alone");
        } else {
            if (EXISTING_FILE.delete()) {
                System.out.println("Successfully deleted " + EXAMPLE_FILE_PATH);
            } else {
                System.err.println("Unable to delete " 
                        + EXISTING_FILE.getAbsolutePath());
            }
        }
    }
    
    private static class MockFileChooser extends FileChooserWithOverwriteGuard {
        
        int askToOverwriteCallCount = 0;
        
        private final int responseToMock;
        
        @Override
        int getOverwriteQuestionResponse(String filename) {
            this.askToOverwriteCallCount++;
            return this.responseToMock;
        }

        MockFileChooser(int responseCode) {
            this.responseToMock = responseCode;
        }
    
    }
    
}
