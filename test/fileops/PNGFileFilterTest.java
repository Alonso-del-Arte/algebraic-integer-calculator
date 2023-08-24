/*
 * Copyright (C) 2021 Alonso del Arte
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

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Just a straightforward test of the PNGFileFilter for the JFileChooser.
 * @author Alonso del Arte
 */
public class PNGFileFilterTest {
    
    private static final PNGFileFilter FILTER = new PNGFileFilter();

    /**
     * Test of the accept function, of the PNGFileFilter class. The file filter 
     * should accept PNG files.
     */
    @Test
    public void testAccept() {
        System.out.println("accept");
        File file = new File("image.png");
        String msg = "PNGFileFilter should accept " + file.getName();
        System.out.println(msg);
        assert FILTER.accept(file) : msg;
    }

    /**
     * Another test of the accept function, of the PNGFileFilter class. The file 
     * filter should accept PNG files, regardless of the case of the extension.
     */
    @Test
    public void testAcceptUpperCaseExtension() {
        File file = new File("image.PNG");
        String msg = "PNGFileFilter should accept " + file.getName();
        System.out.println(msg);
        assert FILTER.accept(file) : msg;
    }

    /**
     * Another test of the accept function, of the PNGFileFilter class. The file 
     * filter should accept PNG files and reject all other files, even if they  
     * are graphics files like JPEG.
     */
    @Test
    public void testRejectPNG() {
        File file = new File("image.jpeg");
        String msg = "PNGFileFilter should reject " + file.getName();
        System.out.println(msg);
        assertFalse(msg, FILTER.accept(file));
    }

    /**
     * Another test of the accept function, of the PNGFileFilter class. The file 
     * filter should accept PNG files and reject all other files, such as 
     * Microsoft Word files.
     */
    @Test
    public void testRejectMicrosoftWordFormat() {
        File file = new File("document.doc");
        String msg = "PNGFileFilter should reject " + file.getName();
        System.out.println(msg);
        assertFalse(msg, FILTER.accept(file));
    }

    /**
     * Another test of the accept function, of the PNGFileFilter class. The file 
     * filter should accept PNG files and also accept directories, because 
     * directories might contain PNG files.
     */
    @Test
    public void testAcceptDirectory() {
        String homeDir = System.getProperty("user.home");
        File dir = new File(homeDir);
        String msg = "PNGFileFilter should accept directory " + homeDir;
        System.out.println(msg);
        assertTrue(msg, FILTER.accept(dir));
    }

    /**
     * Test of getDescription method, of class PNGFileFilter. The description 
     * provided to JFileChooser should include the file extension *.png or 
     *.PNG.
     */
    @Test
    public void testGetDescription() {
        System.out.println("getDescription");
        String description = FILTER.getDescription();
        System.out.println("PNGFileFilter description is \"" + description 
                + "\"");
        String formatName = "Portable Network Graphics";
        String msg = "Filter description should include \"" + formatName + "\"";
        assertTrue(msg, description.contains(formatName));
        msg = "Filter description should include \"png\" or \"PNG\"";
        assertTrue(msg, description.toLowerCase().contains("png"));
    }
    
}
