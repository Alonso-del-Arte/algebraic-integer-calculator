/*
 * Copyright (C) 2023 Alonso del Arte
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
package clipboardops;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Graphics2D;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the ImageSelection class.
 * @author Alonso del Arte
 */
public class ImageSelectionTest implements ClipboardOwner {
    
    private static BufferedImage image;
    
    private static ImageSelection imgSel;
    
    private static TestImagePanel imgWindow;
    
    private static Clipboard sysClip;
    
    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("Test class lost ownership of the clipboard");
    }
    
    /**
     * Prints out to the console a list of "data flavors" the system clipboard 
     * can give the contents in. If there is {@link DataFlavor#stringFlavor}, 
     * the String will also appear in the console output.
     */
    private static void reportClip() {
        DataFlavor[] currFlavs = sysClip.getAvailableDataFlavors();
        for (DataFlavor flav : currFlavs) {
            System.out.print("* " + flav.toString());
            if (flav.equals(DataFlavor.stringFlavor)) {
                String fromClip;
                try {
                    fromClip = (String) sysClip.getData(DataFlavor
                            .stringFlavor);
                    System.out.print(" --> \"" + fromClip + "\"");
                } catch (UnsupportedFlavorException ufe) {
                    System.out.println("Encountered " + ufe.getClass().getName() 
                            + " for " + DataFlavor.stringFlavor
                                    .getHumanPresentableName());
                    System.out.println(ufe.getMessage());
                } catch (IOException ioe) {
                    System.out.println("Encountered " + ioe.getClass().getName() 
                            + " trying to read text from the clipboard");
                    System.out.println(ioe.getMessage());
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /**
     * Queries the system clipboard prior to the tests, sets up an image to use 
     * in the tests.
     */
    @BeforeClass
    public static void setUpClass() {
        imgWindow = new TestImagePanel();
        sysClip = imgWindow.getToolkit().getSystemClipboard();
        System.out.println("Clipboard had the following data flavors:");
        reportClip();
        String initClipMsg = "This message was placed by test setup";
        StringSelection strSel = new StringSelection(initClipMsg);
        sysClip.setContents(strSel, strSel);
        image = new BufferedImage(TestImagePanel.PANEL_WIDTH, 
                TestImagePanel.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = image.createGraphics();
        imgWindow.paint(graph);
        imgSel = new ImageSelection(image);
    }
    
    /**
     * Puts the test instance of ImageSelection into the system clipboard.
     */
    @Before
    public void setUp() {
        sysClip.setContents(imgSel, imgSel);
    }
    
    /**
     * Test of the getTransferDataFlavors function, of the ImageSelection class.
     */
    @Test
    public void testGetTransferDataFlavors() {
        System.out.println("getTransferDataFlavors");
        ImageSelection sel = new ImageSelection(image);
        DataFlavor[] expecteds = {DataFlavor.imageFlavor};
        DataFlavor[] actuals = sel.getTransferDataFlavors();
        assertArrayEquals(expecteds, actuals);
    }

    /**
     * Test of the isDataFlavorSupported function, of the ImageSelection class. 
     * Only {@link DataFlavor#imageFlavor} should register as supported, the 
     * others should not.
     */
    @Test
    public void testIsDataFlavorSupported() {
        System.out.println("isDataFlavorSupported");
        ImageSelection sel = new ImageSelection(image);
        String msg = "Data flavor " 
                + DataFlavor.imageFlavor.getHumanPresentableName() 
                + " should be supported";
        assert sel.isDataFlavorSupported(DataFlavor.imageFlavor) : msg;
    }
    
    private static void checkFlavorNotSupported(DataFlavor flavor) {
        ImageSelection sel = new ImageSelection(image);
        String msg = "Data flavor " + flavor.getHumanPresentableName() 
                + " should not be supported";
        assert !sel.isDataFlavorSupported(flavor) : msg;
    }
    
    /**
     * Another test of the isDataFlavorSupported function, of the ImageSelection 
     * class. Only {@link DataFlavor#imageFlavor} should register as supported,  
     * the others should not. That includes the deprecated {@link 
     * DataFlavor#plainTextFlavor}, which, however, has not been marked for 
     * removal.
     */
    @SuppressWarnings("deprecation")
    @Test
    public void testDataFlavorSupportedNotSupported() {
        DataFlavor[] unsupportedFlavors = {DataFlavor.allHtmlFlavor, 
            DataFlavor.fragmentHtmlFlavor, DataFlavor.javaFileListFlavor, 
            DataFlavor.selectionHtmlFlavor, DataFlavor.stringFlavor, 
            DataFlavor.plainTextFlavor};
        for (DataFlavor flavor : unsupportedFlavors) {
            checkFlavorNotSupported(flavor);
        }
    }

    /**
     * Test of the getTransferData function, of the ImageSelection class.
     */
    @Test
    public void testGetTransferData() {
        System.out.println("getTransferData");
        ImageSelection sel = new ImageSelection(image);
        try {
            Object actual = sel.getTransferData(DataFlavor.imageFlavor);
            assertEquals(image, actual);
        } catch (UnsupportedFlavorException | IOException e) {
            String message = e.getClass().getName() 
                    + " shouldn't have happened with image flavor";
            fail(message);
        }
    }

    /**
     * Test of lostOwnership method, of class ImageSelection.
     */
    @Ignore
    @Test
    public void testLostOwnership() {
        System.out.println("lostOwnership");
//        ImageSelection sel = new ImageSelection(image);
        fail("Haven't written test yet.");
    }
    
    /**
     * Test of hasOwnership method, of class ImageSelection.
     */
    @Test
    public void testHasOwnership() {
        System.out.println("hasOwnership");
        ImageSelection sel = new ImageSelection(image);
        assert sel.hasOwnership();
        String testClipMsg = "This message was placed by test class";
        StringSelection strSel = new StringSelection(testClipMsg);
        sysClip.setContents(strSel, this);
        assert !sel.hasOwnership();
    }
    
    /**
     * Reports on the contents of the clipboard after each test.
     */
    @After
    public void tearDown() {
        System.out.println("Clipboard now has the following data flavors:");
        reportClip();
    }

    /**
     * Closes the window with the test image.
     */
    @AfterClass
    public static void tearDownClass() {
        imgWindow.closePanel();
    }

}
