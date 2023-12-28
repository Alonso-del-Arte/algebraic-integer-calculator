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
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.Image;
import java.io.IOException;

/**
 * This is like <code>StringSelection</code>, but for images drawn using AWT and 
 * Swing. This class is immutable, but an earlier version that also implemented 
 * <code>ClipboardOwner</code> was not. Another caveat is that the 
 * <code>Image</code> class is very much mutable.
 * @author Alonso del Arte, based on a tutorial at ProgramCreek.com (the 
 * specific page I was looking at seems to have been moved).
 */
public class ImageSelection implements Transferable {
    
    private final Image heldImage;
    
    private static final DataFlavor[] FLAVOR_ARRAY = {DataFlavor.imageFlavor};
    
    /**
     * Gives a list of "data flavors" supported by this class.
     * @return An array containing just one element: 
     * <code>DataFlavor.imageFlavor</code>.
     */
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return FLAVOR_ARRAY;
    }

    /**
     * Determines whether a given "data flavor" is supported by this class.
     * @param flavor The flavor for which a determination is needed.
     * @return True if the flavor is <code>DataFlavor.imageFlavor</code>, false 
     * otherwise.
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.imageFlavor.equals(flavor);
    }

    /**
     * Retrieves the data stored by this image selection. It may be necessary to 
     * cast the return value to <code>Image</code>.
     * @param flavor The expected "data flavor," preferably 
     * <code>DataFlavor.imageFlavor</code>.
     * @return An <code>Image</code> presented as an <code>Object</code>.
     * @throws UnsupportedFlavorException Thrown if the flavor is not 
     * <code>DataFlavor.imageFlavor</code>.
     * @throws IOException Thrown if some malfunction prevents retrieval.
     */
    @Override
    public Image getTransferData(DataFlavor flavor) 
            throws UnsupportedFlavorException, IOException {
        if (!flavor.equals(DataFlavor.imageFlavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return this.heldImage;
    }
    
    /**
     * Sole constructor.
     * @param image The image to be made available to the system clipboard. This 
     * image can't be changed, except by creating a new 
     * <code>ImageSelection</code> object.
     */
    public ImageSelection(Image image) {
        this.heldImage = image;
    }
    
}
