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

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JPanel;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of the ImageSelection class.
 * @author Alonso del Arte
 */
public class ImageSelectionTest {
    
    private static final BufferedImage IMAGE;
    
    static {
        TestImagePanel panel = new TestImagePanel();
        IMAGE = new BufferedImage(TestImagePanel.PANEL_WIDTH, 
                TestImagePanel.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = IMAGE.createGraphics();
        panel.paint(g);
        panel.closePanel();
    }
    
    @SuppressWarnings("deprecation")
    private static final DataFlavor[] UNSUPPORTED_FLAVORS 
            = {DataFlavor.allHtmlFlavor, DataFlavor.fragmentHtmlFlavor, 
                DataFlavor.javaFileListFlavor, DataFlavor.selectionHtmlFlavor, 
                DataFlavor.stringFlavor, DataFlavor.plainTextFlavor};
    
    /**
     * Test of the getTransferDataFlavors function, of the ImageSelection class.
     */
    @Test
    public void testGetTransferDataFlavors() {
        System.out.println("getTransferDataFlavors");
        ImageSelection sel = new ImageSelection(IMAGE);
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
        ImageSelection sel = new ImageSelection(IMAGE);
        String msg = "Data flavor " 
                + DataFlavor.imageFlavor.getHumanPresentableName() 
                + " should be supported";
        assert sel.isDataFlavorSupported(DataFlavor.imageFlavor) : msg;
    }
    
    private static void checkFlavorNotSupported(DataFlavor flavor) {
        ImageSelection sel = new ImageSelection(IMAGE);
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
    @Test
    public void testDataFlavorSupportedNotSupported() {
        for (DataFlavor flavor : UNSUPPORTED_FLAVORS) {
            checkFlavorNotSupported(flavor);
        }
    }

    /**
     * Test of the getTransferData function, of the ImageSelection class.
     */
    @Test
    public void testGetTransferData() {
        System.out.println("getTransferData");
        ImageSelection sel = new ImageSelection(IMAGE);
        try {
            Object actual = sel.getTransferData(DataFlavor.imageFlavor);
            assertEquals(IMAGE, actual);
        } catch (UnsupportedFlavorException | IOException e) {
            String message = e.getClass().getName() 
                    + " shouldn't have happened with image flavor";
            fail(message);
        }
    }
    
    /**
     * Another test of the getTransferData function, of the ImageSelection 
     * class. Using the wrong data flavor should cause an exception.
     */
    @Test
    public void testGetTransferDataRejectsWrongFlavor() {
        ImageSelection sel = new ImageSelection(IMAGE);
        for (DataFlavor flavor : UNSUPPORTED_FLAVORS) {
            String msgPart = "Trying to get image data with flavor " 
                    + flavor.toString();
            try {
                Image improper = sel.getTransferData(flavor);
                String message = msgPart + " should not have given " 
                        + improper.toString();
                fail(message);
            } catch (UnsupportedFlavorException ufe) {
                System.out.println(msgPart 
                        + " correctly caused UnsupportedFlavorException");
                String expected = flavor.getHumanPresentableName();
                String actual = ufe.getMessage();
                assertEquals(expected, actual);
                System.out.println("\"" + actual + "\"");
            } catch (RuntimeException re) {
                String message = msgPart + " should not have caused " 
                        + re.getClass().getName(); 
                fail(message);
            } catch (IOException ioe) {
                String message = msgPart 
                        + " should not have caused IOException"; 
                fail(message);
            }
        }
    }

}
