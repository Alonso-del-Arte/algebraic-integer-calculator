/*
 * Copyright (C) 2019 Alonso del Arte
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
package viewers;

import algebraics.AlgebraicInteger;
import algebraics.IntegerRing;
import clipboardops.ImageSelection;
import fileops.FileChooserWithOverwriteGuard;
import fileops.PNGFileFilter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;

/**
 * A Swing component in which to draw diagrams of prime numbers in various rings 
 * of algebraic integers. Includes facilities for saving diagrams to files and 
 * for placing diagrams on the clipboard. Does not include any actual drawing 
 * logic, that's for the subclasses to take care of.
 * @author Alonso del Arte
 */
public abstract class RingDisplay extends JPanel implements ActionListener, MouseMotionListener {
    
    /**
     * How many RingDisplay windows are open during the current JVM session.
     */
    private static int windowCount = 0;
    
    /**
     * The default number of pixels per unit interval. The protected variable 
     * pixelsPerUnitInterval is initialized to this value.
     */
    public static final int DEFAULT_PIXELS_PER_UNIT_INTERVAL = 40;
    
    /**
     * The minimum pixels per unit interval. Trying to set pixels per unit 
     * interval below this value will cause an exception.
     */
    public static final int MINIMUM_PIXELS_PER_UNIT_INTERVAL = 2;
    
    /**
     * The minimum pixels per unit interval for which the program will draw 
     * grids.
     */
    protected static final int MINIMUM_PIXELS_PER_UNIT_INTERVAL_TO_DRAW_GRIDS = 5;
    
    /**
     * The maximum pixels per unit interval. Even on an 8K display, this value 
     * might be much too large. Trying to set pixels per unit interval above 
     * this value will cause an exception.
     */
    public static final int MAXIMUM_PIXELS_PER_UNIT_INTERVAL = 6400;
    
    /**
     * The minimum horizontal pixel dimension for the canvas in which to draw 
     * the diagram. This should be small even on moderately obsolete mobile 
     * devices.
     */
    public static final int RING_CANVAS_HORIZ_MIN = 100;
    
    /**
     * The minimum vertical pixel dimension for the canvas in which to draw the 
     * diagram. This should be small even on moderately obsolete mobile devices.
     */
    public static final int RING_CANVAS_VERTIC_MIN = 178;
    
    /**
     * The default horizontal pixel dimension for the canvas in which to draw 
     * the diagram. This fills up most of the screen on a 1440 by 900 (16:10 
     * aspect ration) display.
     */
    public static final int RING_CANVAS_DEFAULT_HORIZ_MAX = 1280;
    
    /**
     * The default vertical pixel dimension for the canvas in which to draw the 
     * diagram. This fills up most of the screen on a 1440 by 900 (16:10 aspect 
     * ratio) display.
     */
    public static final int RING_CANVAS_DEFAULT_VERTIC_MAX = 720;
    
    /**
     * The default vertical pixel dimension for the canvas in which to draw the 
     * diagram when the given ring does not contain imaginary or complex 
     * numbers.
     */
    public static final int PURELY_REAL_RING_CANVAS_DEFAULT_VERTIC_MAX = 180;
        
    /**
     * The default pixel radius for the dots in the diagram.
     */
    public static final int DEFAULT_DOT_RADIUS = 5;
    
    /**
     * The minimum pixel radius for the dots in the diagram.
     */
    public static final int MINIMUM_DOT_RADIUS = 1;
    
    /**
     * The maximum pixel radius for the dots in the diagram.
     */
    public static final int MAXIMUM_DOT_RADIUS = 128;
    
    /**
     * The usual step by which to increment or decrement pixels by unit 
     * interval. This step can be increased or decreased, either through the 
     * menu or with a keyboard shortcut.
     */
    public static final int DEFAULT_ZOOM_INTERVAL = 5;
    
    /**
     * The smallest step by which to increment or decrement pixels by unit 
     * interval. Once this step is reached, the relevant menu item is disabled 
     * and the corresponding keyboard shortcut is ignored.
     */
    public static final int MINIMUM_ZOOM_STEP = 1;
    
    /**
     * The largest step by which to increment or decrement pixels by unit 
     * interval. Once this step is reached, the relevant menu item is disabled 
     * and the corresponding keyboard shortcut is ignored.
     */
    public static final int MAXIMUM_ZOOM_STEP = 48;
    
    /**
     * The default background color when the program starts. In a future 
     * version, there will be a dialog accessed through a menu that will enable 
     * the user to change this color. For now, the only way to change it is by 
     * changing this constant.
     */
    public static final Color DEFAULT_CANVAS_BACKGROUND_COLOR = new Color(2107440); // A dark blue
    
    /**
     * The default color for the "half-integer" grid. In a future version, there 
     * will be a dialog accessed through a menu that will enable the user to 
     * change this color. This setting is only relevant when the program is 
     * displaying a diagram with "half-integers" at a zoom level sufficient to 
     * cause the drawing of grid lines.
     */
    public static final Color DEFAULT_HALF_INTEGER_GRID_COLOR = Color.DARK_GRAY;
    
    /**
     * The default color for the "full" integer grid. In a future version, there 
     * will be a dialog accessed through a menu that will enable the user to 
     * change this color. This setting is only relevant when the program is 
     * displaying a diagram with at a zoom level sufficient to cause the drawing 
     * of grid lines.
     */
    public static final Color DEFAULT_INTEGER_GRID_COLOR = Color.BLACK;
    
    /**
     * The default color for 0.
     */
    public static final Color DEFAULT_ZERO_COLOR = Color.BLACK;
    
    /**
     * The default color for units. In <b>Z</b>[<i>i</i>], this would include 
     * <i>i</i> and -<i>i</i>. In <b>Z</b>[&omega;], this would include &omega;, 
     * 1 + &omega;, -1 - &omega; and -&omega;. In all rings, this includes 1 and 
     * -1.
     */
    public static final Color DEFAULT_UNIT_COLOR = Color.WHITE;
    
    /**
     * The default color for primes believed to be inert. If a prime does split 
     * but its prime factors are not in the current diagram view, the program is 
     * unaware of them. Though I have yet to think of a single example where it 
     * might actually be the case that the program erroneously identifies a 
     * prime as inert.
     */
    public static final Color DEFAULT_INERT_PRIME_COLOR = Color.CYAN;
    
    /**
     * The default color for primes confirmed split. If a prime's splitting 
     * factors are in the current diagram view, the program uses this color for 
     * the split prime. For example, if the program is displaying a diagram of 
     * <b>Z</b>[&omega;] and &minus;7/2 + (&radic;&minus;3)/2 (which has norm 
     * 13) is in view, then 13 is confirmed split and colored accordingly if 
     * it's in view. Note, however, that, given a prime <i>p</i> &equiv; 2 mod 
     * 3, the number &minus;<i>p</i>/2 + (<i>p</i>&radic;&minus;3)/2 is not a 
     * splitting factor of <i>p</i>, since it is <i>p</i>&omega; and &omega; is 
     * a unit. Therefore <i>p</i> should be colored with the inert prime color.
     */
    public static final Color DEFAULT_SPLIT_PRIME_COLOR = Color.BLUE;
    
    /**
     * The default color for numbers that would be prime in a ring of algebraic 
     * degree greater than 2 but less than the degree of the ring of the current 
     * diagram. This color will probably not be used in diagrams of quadratic 
     * rings, but should be used in diagrams of quartic rings.
     */
    public static final Color DEFAULT_SPLIT_MID_DEGREE_PRIME_COLOR = new Color(24831);
    
    /**
     * The default color for primes that are ramified. Regardless of the current 
     * diagram view, the program checks if gcd(<i>p</i>, <i>d</i>) > 1. So, for 
     * example, if the program is displaying a diagram of <b>Z</b>[&radic;-5], 
     * then 5 will be shown in this color if it's in view.
     */
    public static final Color DEFAULT_RAMIFIED_PRIME_COLOR = Color.GREEN;
    
    /**
     * The default color for numbers that are ramified in a ring of algebraic 
     * degree greater than 2 but also at less than the degree of the ring of the 
     * current diagram. This color will probably not be used in diagrams of 
     * quadratic rings, but should be used in diagrams of quartic rings.
     */
    public static final Color DEFAULT_RAMIFIED_MID_DEGREE_PRIME_COLOR = new Color(65376);
    
    /**
     * How wide to make the readouts.
     */
    public static final int DEFAULT_READOUT_FIELD_COLUMNS = 20;
    
    /**
     * The maximum number of previous rings the program will remember for 
     * history in any given run.
     */
    public static final int MAXIMUM_HISTORY_ITEMS = 128;
    
    protected boolean alreadySetUp = false;
    
    /**
     * If the subclass can only display one ring, the subclass constructor 
     * should set this protected flag to false. Otherwise the subclass can 
     * simply rely on the default setting of true.
     */
    protected boolean includeRingChoice = true;
    
    /**
     * If the subclass does not include updateable readouts, the subclass 
     * constructor should set this protected flag to false. Otherwise the 
     * subclass can simply rely on the default setting of true.
     */
    protected boolean includeReadoutsUpdate = true;
    
    /**
     * If the subclass gives the user the ability to change whether the readouts 
     * use an alternate notation or not, the subclass constructor should set 
     * this protected flag to false. Otherwise the subclass can simply rely on 
     * the default setting of true. However, if {@link #includeReadoutsUpdate 
     * includeReadoutsUpdate} is false, then this setting does not matter.
     */
    protected boolean includeThetaToggle = true;
    
    /**
     * The actual pixels per unit interval setting, should be initialized to 
     * DEFAULT_PIXELS_PER_UNIT_INTERVAL in the constructor. Use 
     * setPixelsPerUnitInterval(int pixelLength) to change, making sure 
     * pixelLength is greater than or equal to MINIMUM_PIXELS_PER_UNIT_INTERVAL 
     * but less than or equal to MAXIMUM_PIXELS_PER_UNIT_INTERVAL.
     */
    protected int pixelsPerUnitInterval;
    
    /**
     * The actual pixels per basic imaginary interval setting. This setting 
     * depends on pixelsPerUnitInterval.
     */
    protected int pixelsPerBasicImaginaryInterval;
    
    /**
     * The ring of the currently displayed diagram.
     */
    protected IntegerRing diagramRing;
    
    protected AlgebraicInteger mouseAlgInt;
        
    /**
     * When diagramRing.d1mod4 is true, some users may prefer to see 
     * "half-integers" notated with theta notation rather than fractions with 2s 
     * for denominators. With this preference turned on and d = -3, omega will 
     * be used rather than theta. Remember that omega = -1/2 + sqrt(-3)/2 and 
     * theta = 1/2 + sqrt(d)/2.
     */
    protected boolean preferenceForThetaNotation;
    
    protected int ringCanvasHorizMax;
    protected int ringCanvasVerticMax;
    
    /**
     * Half the thickness of the lines or the radius of the dots.
     */
    protected int dotRadius;
    
    /**
     * The thickness of the lines or the diameter of the dots.
     */
    protected int dotDiameter;
    
    protected int zoomStep;
    
    protected Color backgroundColor, halfIntegerGridColor, integerGridColor;
    protected Color zeroColor, unitColor, inertPrimeColor, splitPrimeColor, splitPrimeMidDegreeColor, ramifiedPrimeColor, ramifiedPrimeMidDegreeColor;
    
    /**
     * The x of the coordinate pair (x, y) for 0 + 0i on the currently displayed 
     * diagram.
     */
    protected int zeroCoordX;
    
    /**
     * The y of the coordinate pair (x, y) for 0 + 0i on the currently displayed 
     * diagram.
     */
    protected int zeroCoordY;
    
    /* For when the program has the ability to display diagrams that don't 
       necessarily have 0 in the center. */
    // private boolean zeroCentered, zeroInView;
    
    protected JFrame ringFrame;
    
    protected JMenuItem increaseDMenuItem, decreaseDMenuItem;
    protected JMenuItem prevDMenuItem, nextDMenuItem;
    protected JMenuItem zoomInMenuItem, zoomOutMenuItem;
    protected JMenuItem decreaseZoomStepMenuItem, increaseZoomStepMenuItem;
    protected JMenuItem decreaseDotRadiusMenuItem, increaseDotRadiusMenuItem;
    protected JCheckBoxMenuItem preferThetaNotationMenuItem, toggleReadOutsEnabledMenuItem;
    
    protected JTextField algIntReadOut, algIntTraceReadOut, algIntNormReadOut, algIntPolReadOut;
    
    /**
     * Keeps track of whether or not the user has saved a diagram before. 
     * Applies only during the current session.
     */
    protected boolean haveSavedBefore = false;
    
    /**
     * Points to the directory where the user has previously saved a diagram to 
     * in the current session. Probably empty or null if haveSavedBefore is 
     * false.
     */
    private String prevSavePathname;

    /**
     * The history list, with which to enable to user to view previous diagrams.
     */
    protected final List<IntegerRing> discrHistory;
    
    /**
     * Where we are at in the history list.
     */
    protected short currHistoryIndex;
    
    /**
     * Changes how many pixels there are per basic imaginary interval. This 
     * procedure, as implemented by a subclass, will be automatically called by 
     * {@link #setPixelsPerUnitInterval(int)}. If you don't actually need to use 
     * basic imaginary intervals, implement as a do-nothing procedure. 
     * Regardless, this should never be called by a class outside the 
     * RingDisplay class hierarchy.
     */
    protected abstract void setPixelsPerBasicImaginaryInterval();
        
    /**
     * Change how many pixels there are per unit interval. If you also need to 
     * concomitantly change how many pixels there are per basic imaginary 
     * interval, implement {@link #setPixelsPerBasicImaginaryInterval()} with a 
     * non-empty procedure body. If you override this procedure and you need to 
     * also change the setting for pixels per basic imaginary interval, either 
     * call super or call the basic imaginary interval procedure directly.
     * @param pixelLength An integer greater than or equal to 
     * {@link #MINIMUM_PIXELS_PER_UNIT_INTERVAL} but less than or equal to 
     * {@link #MAXIMUM_PIXELS_PER_UNIT_INTERVAL}.
     * @throws IllegalArgumentException If pixelLength is outside the range 
     * specified above.
     */
    public void setPixelsPerUnitInterval(int pixelLength) {
        if (pixelLength < MINIMUM_PIXELS_PER_UNIT_INTERVAL) {
            throw new IllegalArgumentException("Pixels per unit interval needs to be set to greater than " + (MINIMUM_PIXELS_PER_UNIT_INTERVAL - 1));
        }
        if (pixelLength > MAXIMUM_PIXELS_PER_UNIT_INTERVAL) {
            throw new IllegalArgumentException("Pixels per unit interval needs to be set to less than " + (MAXIMUM_PIXELS_PER_UNIT_INTERVAL + 1));
        }
        this.pixelsPerUnitInterval = pixelLength;
        this.setPixelsPerBasicImaginaryInterval();
    }
    
    /**
     * Merely passes a Graphics object up the class hierarchy to a Swing 
     * component. Thus many graphical chores we take for granted (like 
     * repainting the background after a menu is dismissed) are taken care of.
     * @param g A Graphics object passed from a subclass.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
    
    /**
     * Function to change the size of the canvas on which the ring diagrams are 
     * drawn. I have not completely thought this one through, and I certainly 
     * haven't tested it.
     * @param newHorizMax The new width of the ring window. This needs to be at 
     * least equal to RING_CANVAS_HORIZ_MIN.
     * @param newVerticMax The new height of the ring window. This needs to be 
     * at least equal to RING_CANVAS_VERTIC_MIN.
     * @throws IllegalArgumentException If either newHorizMax is less than 
     * RING_CANVAS_HORIZ_MIN or newVerticMax is less than 
     * RING_CANVAS_VERTIC_MIN.
     */
    public void changeRingWindowDimensions(int newHorizMax, int newVerticMax) {
        if (newHorizMax < RING_CANVAS_HORIZ_MIN || newVerticMax < RING_CANVAS_VERTIC_MIN) {
            throw new IllegalArgumentException("New window dimensions need to be equal or greater than supplied minimums.");
        }
        this.ringCanvasHorizMax = newHorizMax;
        this.ringCanvasVerticMax = newVerticMax;
    }
    
    /**
     * Function to change the background color. I have not tested this one yet.
     * @param newBackgroundColor Preferably a color that will contrast nicely 
     * with the foreground points but which the grids can blend into.
     */
    public void changeBackgroundColor(Color newBackgroundColor) {
        this.backgroundColor = newBackgroundColor;
    }
    
    /**
     * Function to change the grid colors.
     * @param newHalfIntegerGridColor Applicable only for certain rings. In 
     * choosing this color, keep in mind that, when applicable, the 
     * "half-integer" grid is drawn first.
     * @param newIntegerGridColor In choosing this color, keep in mind that, 
     * when applicable, the "full" integer grid is drawn second, after the 
     * "half-integer" grid.
     */
    public void changeGridColors(Color newHalfIntegerGridColor, Color newIntegerGridColor) {
        this.halfIntegerGridColor = newHalfIntegerGridColor;
        this.integerGridColor = newIntegerGridColor;
    }
    
     /**
     * Function to change the colors of the points.
     * @param newZeroColor The color for the point 0.
     * @param newUnitColor The color for the units. In most imaginary quadratic 
     * rings, this color will only be used for &minus;1 and 1.
     * @param newInertPrimeColor The color for inert primes, or at least primes 
     * having no splitting factors in view and known to not ramify.
     * @param newSplitPrimeColor The color for confirmed split primes.
     * @param newRamifiedPrimeColor The color for primes that are factors of the 
     * discriminant.
     */
    public void changePointColors(Color newZeroColor, Color newUnitColor, Color newInertPrimeColor, Color newSplitPrimeColor, Color newRamifiedPrimeColor) {
        this.zeroColor = newZeroColor;
        this.unitColor = newUnitColor;
        this.inertPrimeColor = newInertPrimeColor;
        this.splitPrimeColor = newSplitPrimeColor;
        this.ramifiedPrimeColor = newRamifiedPrimeColor;
    }
    
    /**
     * Changes the dot diameter according to the dot radius. This may be inlined 
     * upon compilation, but in the source it is necessary as a separate call so 
     * as to simplify adjusting the formula.
     */
    private void changeDotDiameter() {
        this.dotDiameter = 2 * this.dotRadius;
    }
   
    /**
     * Function to change the dot radius.
     * @param newDotRadius Needs to be at least {@link #MINIMUM_DOT_RADIUS 
     * MINIMUM_DOT_RADIUS} pixels.
     * @throws IllegalArgumentException If newDotRadius is less than 
     * MINIMUM_DOT_RADIUS.
     */
    public void changeDotRadius(int newDotRadius) {
        boolean dotRadiusOutOfBounds = false;
        String exceptionMessage = "Dot radius must be ";
        if (newDotRadius < MINIMUM_DOT_RADIUS) {
            dotRadiusOutOfBounds = true;
            exceptionMessage = exceptionMessage + "at least " + MINIMUM_DOT_RADIUS + " pixel";
            if (MINIMUM_DOT_RADIUS > 1) {
                exceptionMessage += "s.";
            } else {
                exceptionMessage += ".";
            }
            throw new IllegalArgumentException(exceptionMessage);
        } else {
            if (newDotRadius > MAXIMUM_DOT_RADIUS) {
                dotRadiusOutOfBounds = true;
                exceptionMessage = exceptionMessage + "no more than " + MAXIMUM_DOT_RADIUS + " pixels.";
            }
        }
        if (dotRadiusOutOfBounds) {
            throw new IllegalArgumentException(exceptionMessage);
        }
        this.dotRadius = newDotRadius;
        this.changeDotDiameter();
    }
    
    /**
     * Function to change the zoom step.
     * @param newZoomStep Needs to be at least {@link #MINIMUM_ZOOM_STEP 
     * MINIMUM_ZOOM_STEP} pixels. 
     * @throws IllegalArgumentException If newZoomStep is less than 
     * MINIMUM_ZOOM_STEP.
     */
    public void changeZoomStep(int newZoomStep) {
        boolean zoomStepOutOfBounds = false;
        String exceptionMessage = "Zoom interval must be ";
        if (newZoomStep < MINIMUM_ZOOM_STEP) {
            zoomStepOutOfBounds = true;
            exceptionMessage = exceptionMessage + "at least " + MINIMUM_ZOOM_STEP + " pixel";
            if (MINIMUM_ZOOM_STEP > 1) {
                exceptionMessage += "s.";
            } else {
                exceptionMessage += ".";
            }
        } else {
            if (newZoomStep > MAXIMUM_ZOOM_STEP) {
                zoomStepOutOfBounds = true;
                exceptionMessage = exceptionMessage + "no more than " + MAXIMUM_ZOOM_STEP + " pixels.";
            }
        }
        if (zoomStepOutOfBounds) {
            throw new IllegalArgumentException(exceptionMessage);
        }
        this.zoomStep = newZoomStep;
    }
    
    /**
     * Function to change the coordinates of the point 0. I have not yet 
     * implemented a meaningful use for this function.
     * @param newCoordX The new x-coordinate for 0.
     * @param newCoordY The new y-coordinate for 0.
     */
    public void changeZeroCoords(int newCoordX, int newCoordY) {
        this.zeroCoordX = newCoordX;
        this.zeroCoordY = newCoordY;
        // zeroCentered = (this.zeroCoordX == (int) Math.floor(this.ringCanvasHorizMax/2) && this.zeroCoordY == (int) Math.floor(this.ringCanvasVerticMax/2));
        // zeroInView = ((this.zeroCoordX > -1) && (this.zeroCoordY > -1) && (this.zeroCoordX <= this.ringCanvasHorizMax) && (this.zeroCoordY <= this.ringCanvasVerticMax));
    }
    
    /**
     * Prompts the user for a filename and saves the currently displayed diagram 
     * with that filename as a Portable Network Graphics (PNG) file. The 
     * suggested filename (which the user can change) will consist of the ring's 
     * label (be sure to override {@link 
     * algebraics.IntegerRing#toFilenameString()} so that it uses only 
     * characters appropriate for a filename), followed by "pxui" to stand for 
     * "pixels per unit interval", the number of pixels per unit interval and 
     * the *.png extension.
     */
    public void saveDiagramAs() {
        BufferedImage diagram = new BufferedImage(this.ringCanvasHorizMax, this.ringCanvasVerticMax, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = diagram.createGraphics();
        this.paint(graph);
        String suggestedFilename = this.diagramRing.toFilenameString() + "pxui" + this.pixelsPerUnitInterval + ".png";
        File diagramFile = new File(suggestedFilename);
        FileChooserWithOverwriteGuard fileChooser = new FileChooserWithOverwriteGuard();
        FileFilter pngFilter = new PNGFileFilter();
        fileChooser.addChoosableFileFilter(pngFilter);
        if (haveSavedBefore) {
            fileChooser.setCurrentDirectory(new File(prevSavePathname));
        }
        fileChooser.setSelectedFile(diagramFile);
        int fcRet = fileChooser.showSaveDialog(this);
        String notificationString;
        switch (fcRet) {
            case JFileChooser.APPROVE_OPTION:
                diagramFile = fileChooser.getSelectedFile();
                String filePath = diagramFile.getAbsolutePath();
                prevSavePathname = filePath.substring(0, filePath.lastIndexOf(File.separator));
                haveSavedBefore = true;
                try {
                    ImageIO.write(diagram, "PNG", diagramFile);
                } catch (IOException ioe) {
                    notificationString = "Image input/output exception occurred:\n " + ioe.getMessage();
                    JOptionPane.showMessageDialog(ringFrame, notificationString);
                }
                break;
            case JFileChooser.CANCEL_OPTION:
                notificationString = "File save canceled.";
                JOptionPane.showMessageDialog(ringFrame, notificationString);
                break;
            case JFileChooser.ERROR_OPTION:
                notificationString = "An error occurred trying to choose a file to save to.";
                JOptionPane.showMessageDialog(ringFrame, notificationString);
                break;
            default:
                notificationString = "Unexpected option " + fcRet + " from file chooser.";
                JOptionPane.showMessageDialog(ringFrame, notificationString);
        }
    }
    
    /**
     * Switches to a different ring. This procedure should not be overridden 
     * unless strictly necessary. Generally it will be best to rely on callers 
     * to provide a ring of the appropriate implementation of IntegerRing.
     * @param ring An IntegerRing object, preferably of the same class as the 
     * one used to construct the RingDisplay subclass. If it's not, something 
     * like a {@link ClassCastException} could arise at some point down the 
     * line.
     */
    protected void switchToRing(IntegerRing ring) {
        this.ringFrame.setTitle("Ring Diagram for " + ring.toString());
        this.setRing(ring);
        this.repaint();
    }
    
    /**
     * Function to update the history of previously viewed diagrams.
     * @param ring The ring to add to the history.
     */
    protected void updateRingHistory(IntegerRing ring) {
        if (currHistoryIndex == discrHistory.size() - 1) {
            discrHistory.add(ring);
            currHistoryIndex++;
            if (!prevDMenuItem.isEnabled()) {
                prevDMenuItem.setEnabled(true);
            }
        } else {
            currHistoryIndex++;
            discrHistory.add(currHistoryIndex, ring);
            while (currHistoryIndex < discrHistory.size() - 1) {
                discrHistory.remove(currHistoryIndex + 1); // Remove the "forward arrow" history
            }
            nextDMenuItem.setEnabled(false);
        }
        if (currHistoryIndex > MAXIMUM_HISTORY_ITEMS) {
            discrHistory.remove(0); // Forget the first item
        }
    }
    
    /**
     * Function to ask user to enter a new number in order to select a new ring.
     */
    public abstract void chooseDiscriminant();

    /**
     * Function to choose for discriminant the next higher number. If this 
     * brings us up to the highest permissible number, then the "Increase 
     * discriminant" menu item ought to be disabled.
     */
    public abstract void incrementDiscriminant();

    /**
     * Function to choose for discriminant the next lower number. If this 
     * brings us up to the lowest permissible number, then the "Decrease 
     * discriminant" menu item ought to be disabled.
     */
    public abstract void decrementDiscriminant();
    
    /**
     * Bring up the previously displayed diagram.
     */
    public void previousDiscriminant() {
        currHistoryIndex--;
        switchToRing(discrHistory.get(currHistoryIndex));
        if (currHistoryIndex == 0) {
            prevDMenuItem.setEnabled(false);
        }
        if (!nextDMenuItem.isEnabled()) {
            nextDMenuItem.setEnabled(true);
        }
    }

    /**
     * Bring up the next displayed diagram.
     */
    public void nextDiscriminant() {
        currHistoryIndex++;
        switchToRing(discrHistory.get(currHistoryIndex));
        if (currHistoryIndex == discrHistory.size() - 1) {
            nextDMenuItem.setEnabled(false);
        }
        if (!prevDMenuItem.isEnabled()) {
            prevDMenuItem.setEnabled(true);
        }
    }
    
    /**
     * Copies the readouts of the algebraic integer, trace, norm and polynomial 
     * to the clipboard.
     */
    public void copyReadoutsToClipboard() {
        String agregReadouts = mouseAlgInt.toString();
        agregReadouts = agregReadouts + ", Trace: " + mouseAlgInt.trace() + ", Norm: " + mouseAlgInt.norm() + ", Polynomial: " + mouseAlgInt.minPolynomialString();
        StringSelection ss = new StringSelection(agregReadouts);
        this.getToolkit().getSystemClipboard().setContents(ss, ss);
    }
    
    /**
     * Copies the currently displayed diagram to the clipboard as a {@link 
     * BufferedImage}, of type {@link BufferedImage#TYPE_INT_RGB}.
     */
    public void copyDiagramToClipboard() {
        BufferedImage diagram = new BufferedImage(this.ringCanvasHorizMax, this.ringCanvasVerticMax, BufferedImage.TYPE_INT_RGB);
        Graphics2D graph = diagram.createGraphics();
        this.paint(graph);
        ImageSelection imgSel = new ImageSelection(diagram);
        this.getToolkit().getSystemClipboard().setContents(imgSel, imgSel);
    }
    
    /**
     * Checks whether the Zoom in and Zoom out menu items are enabled or not, 
     * and whether they should be, enabling them or disabling them as needed.
     */
    protected void checkViewMenuEnablements() {
        if (this.zoomInMenuItem.isEnabled() && (this.pixelsPerUnitInterval > (MAXIMUM_PIXELS_PER_UNIT_INTERVAL - zoomStep))) {
            this.zoomInMenuItem.setEnabled(false);
        }
        if (!this.zoomInMenuItem.isEnabled() && (this.pixelsPerUnitInterval <= (MAXIMUM_PIXELS_PER_UNIT_INTERVAL - zoomStep))) {
            this.zoomInMenuItem.setEnabled(true);
        }
        if (this.zoomOutMenuItem.isEnabled() && (this.pixelsPerUnitInterval < (MINIMUM_PIXELS_PER_UNIT_INTERVAL + zoomStep))) {
            this.zoomOutMenuItem.setEnabled(false);
        }
        if (!this.zoomOutMenuItem.isEnabled() && (this.pixelsPerUnitInterval >= (MINIMUM_PIXELS_PER_UNIT_INTERVAL + zoomStep))) {
            this.zoomOutMenuItem.setEnabled(true);
        }
    }
    
    /**
     * Zooms in on the diagram. This is done by reducing pixelsPerUnitInterval 
 by zoomStep and calling repaint().
     */
    public void zoomIn() {
        int newPixelsPerUnitInterval = this.pixelsPerUnitInterval + this.zoomStep;
        if (newPixelsPerUnitInterval <= MAXIMUM_PIXELS_PER_UNIT_INTERVAL) {
            this.setPixelsPerUnitInterval(newPixelsPerUnitInterval);
            this.repaint();
            if ((newPixelsPerUnitInterval + this.zoomStep) > MAXIMUM_PIXELS_PER_UNIT_INTERVAL) {
                this.zoomInMenuItem.setEnabled(false);
            }
        }
        if (!this.zoomOutMenuItem.isEnabled() && (newPixelsPerUnitInterval >= (MINIMUM_PIXELS_PER_UNIT_INTERVAL + this.zoomStep))) {
            this.zoomOutMenuItem.setEnabled(true);
        }
    }
    
    /**
     * Zooms out on the diagram. This is done by increasing 
 pixelsPerUnitInterval by zoomStep and calling repaint().
     */
    public void zoomOut() {
        int newPixelsPerUnitInterval = this.pixelsPerUnitInterval - this.zoomStep;
        if (newPixelsPerUnitInterval >= MINIMUM_PIXELS_PER_UNIT_INTERVAL) {
            this.setPixelsPerUnitInterval(newPixelsPerUnitInterval);
            this.repaint();
            if ((newPixelsPerUnitInterval - zoomStep) < MINIMUM_PIXELS_PER_UNIT_INTERVAL) {
                this.zoomOutMenuItem.setEnabled(false);
            }
        }
        if (!this.zoomInMenuItem.isEnabled() && (newPixelsPerUnitInterval <= (MAXIMUM_PIXELS_PER_UNIT_INTERVAL - this.zoomStep))) {
            this.zoomInMenuItem.setEnabled(true);
        }
    }
    
    /**
     * Just a little message dialog to let the user know what the zoom interval 
     * is now.
     */
    protected void informZoomStepChange() {
        String notificationString = "Zoom step is now " + this.zoomStep + ".\nThere are " + this.pixelsPerUnitInterval + " pixels per unit interval.";
        JOptionPane.showMessageDialog(ringFrame, notificationString);
    }
    
    /**
     * Decreases the zoom interval. The zoom interval is decremented by 1, 
     * taking care that it does not become less than {@link 
     * #MINIMUM_ZOOM_STEP MINIMUM_ZOOM_STEP}.
     */
    public void decreaseZoomStep() {
        int newZoomStep = this.zoomStep - 1;
        boolean newZoomStepFlag = false;
        if (newZoomStep >= MINIMUM_ZOOM_STEP) {
            this.changeZoomStep(newZoomStep);
            newZoomStepFlag = true;
            if (this.zoomStep == MINIMUM_ZOOM_STEP) {
                this.decreaseZoomStepMenuItem.setEnabled(false);
            }
        }
        if (newZoomStepFlag) {
            this.informZoomStepChange();
        }
        if (!this.increaseZoomStepMenuItem.isEnabled() && (newZoomStep < MAXIMUM_ZOOM_STEP)) {
            this.increaseZoomStepMenuItem.setEnabled(true);
        }
        this.checkViewMenuEnablements();
    }

    /**
     * Increases the zoom interval. The zoom interval is incremented by 1, 
     * taking care that it does not become more than {@link 
     * #MAXIMUM_ZOOM_STEP MAXIMUM_ZOOM_STEP}.
     */
    public void increaseZoomStep() {
        int newZoomStep = this.zoomStep + 1;
        boolean newZoomStepFlag = false;
        if (newZoomStep <= MAXIMUM_ZOOM_STEP) {
            this.changeZoomStep(newZoomStep);
            newZoomStepFlag = true;
            if (this.zoomStep == MAXIMUM_ZOOM_STEP) {
                this.increaseZoomStepMenuItem.setEnabled(false);
            }
        }
        if (newZoomStepFlag) {
            this.informZoomStepChange();
        }
        if (!this.decreaseZoomStepMenuItem.isEnabled() && (newZoomStep > MINIMUM_ZOOM_STEP)) {
            this.decreaseZoomStepMenuItem.setEnabled(true);
        }
        this.checkViewMenuEnablements();
    }

    /**
     * Decreases the dot radius for 0, units and primes on the diagram. The dot 
     * radius is decreased by 1 pixel, taking care that it does not become less 
     * than {@link #MINIMUM_DOT_RADIUS MINIMUM_DOT_RADIUS}.
     */
    public void decreaseDotRadius() {
        int newDotRadius = this.dotRadius - 1;
        if (newDotRadius >= MINIMUM_DOT_RADIUS) {
            this.changeDotRadius(newDotRadius);
            this.repaint();
            if (this.dotRadius == MINIMUM_DOT_RADIUS) {
                this.decreaseDotRadiusMenuItem.setEnabled(false);
            }
        }
        if (!this.increaseDotRadiusMenuItem.isEnabled() && (newDotRadius < MAXIMUM_DOT_RADIUS)) {
            this.increaseDotRadiusMenuItem.setEnabled(true);
        }
    }

    /**
     * Increases the dot radius for 0, units and primes on the diagram. The dot 
     * radius is increased by 1 pixel, taking care that it does not become more 
     * than {@link #MAXIMUM_DOT_RADIUS MAXIMUM_DOT_RADIUS}.
     */
    public void increaseDotRadius() {
        int newDotRadius = this.dotRadius + 1;
        if (newDotRadius <= MAXIMUM_DOT_RADIUS) {
            this.changeDotRadius(newDotRadius);
            this.repaint();
            if (this.dotRadius == MAXIMUM_DOT_RADIUS) {
                this.increaseDotRadiusMenuItem.setEnabled(false);
            }
        }
        if (!this.decreaseDotRadiusMenuItem.isEnabled() && (newDotRadius > MINIMUM_DOT_RADIUS)) {
            this.decreaseDotRadiusMenuItem.setEnabled(true);
        }
    }
    
    /**
     * Resets pixels per unit interval, dot radius and zoom interval. This does 
     * not change the discriminant, nor whether or not readouts are updated, nor 
     * the preference for theta notation.
     */
    public void resetViewDefaults() {
        /* Since the program does not yet allow the user to change colors, the 
           following three lines are for now unnecessary.
        changeBackgroundColor(DEFAULT_CANVAS_BACKGROUND_COLOR);
        changeGridColors(DEFAULT_HALF_INTEGER_GRID_COLOR, DEFAULT_INTEGER_GRID_COLOR);
        changePointColors(DEFAULT_ZERO_COLOR, DEFAULT_UNIT_COLOR, DEFAULT_INERT_PRIME_COLOR, DEFAULT_SPLIT_PRIME_COLOR, DEFAULT_RAMIFIED_PRIME_COLOR);
        */
        this.setPixelsPerUnitInterval(DEFAULT_PIXELS_PER_UNIT_INTERVAL);
        this.changeZoomStep(DEFAULT_ZOOM_INTERVAL);
        this.changeDotRadius(DEFAULT_DOT_RADIUS);
        this.repaint();
        // Now to check if any menu items need to be enabled or disabled
        this.checkViewMenuEnablements(); // This takes care of the Zoom in and Zoom out menu items
        if (!this.increaseZoomStepMenuItem.isEnabled() && (this.zoomStep < MAXIMUM_ZOOM_STEP)) {
            this.increaseZoomStepMenuItem.setEnabled(true);
        }
        if (!this.decreaseZoomStepMenuItem.isEnabled() && (this.zoomStep > MINIMUM_ZOOM_STEP)) {
            this.decreaseZoomStepMenuItem.setEnabled(true);
        }
        if (!this.increaseDotRadiusMenuItem.isEnabled() && (this.dotRadius < MAXIMUM_DOT_RADIUS)) {
            this.increaseDotRadiusMenuItem.setEnabled(true);
        }
        if (!this.decreaseDotRadiusMenuItem.isEnabled() && (this.dotRadius > MINIMUM_DOT_RADIUS)) {
            this.decreaseDotRadiusMenuItem.setEnabled(true);
        }
    }
    
    /**
     * Enable or disable the use of theta notation in the readout field for 
     * integer when the discriminant is congruent to 1 modulo 4. Of course the
     * updating of readouts has to be enabled for this to be any of consequence.
     */
    public void toggleThetaNotation() {
        this.preferenceForThetaNotation = this.preferThetaNotationMenuItem.isSelected();
    }
    
    /**
     * Enable or disable updating of the readout fields for integer, trace, norm 
     * and polynomial.
     */
    public void toggleReadOutsEnabled() {
        if (this.toggleReadOutsEnabledMenuItem.isSelected()) {
            this.addMouseMotionListener(this);
        } else {
            this.removeMouseMotionListener(this);
        }
    }
    
    /**
     * Override this to set the user manual URL.
     * @return The URL where the user manual can be found.
     */
    public abstract URI getUserManualURL();
    
    /**
     * Uses the default Web browser to show the user manual. If the default 
     * browser is not available for whatever reason, a message to that effect is 
     * shown in a dialog box and/or on the console.
     */
    public void showUserManual() {
        URI url = this.getUserManualURL();
        String urlStr = url.toString();
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(url);
            } catch (IOException ioe) {
                String problemMessage = "Sorry, unable to open URL\n<" + urlStr + ">\n\"" + ioe.getMessage() + "\"";
                JOptionPane.showMessageDialog(this.ringFrame, problemMessage);
                System.err.println(problemMessage);
            }
        } else {
            String noDesktopMessage = "Sorry, unable to open URL\n<" + urlStr + ">\nDefault Web browser is not available from this program.";
            JOptionPane.showMessageDialog(this.ringFrame, noDesktopMessage);
            System.err.println(noDesktopMessage);
        }
    }
    
    /**
     * Override this to set the about box message.
     * @return A message with the name of the program, version number, copyright 
     * date and author name.
     */
    public abstract String getAboutBoxMessage();
    
    /**
     * Shows the About box, a simple MessageDialog from JOptionPage. If 
     * available, writes the same information to the console.
     */
    public void showAboutBox() {
        String aboutBoxTitle = "About";
        String aboutMessage = this.getAboutBoxMessage();
        JOptionPane.showMessageDialog(this.ringFrame, aboutMessage, aboutBoxTitle, JOptionPane.PLAIN_MESSAGE);
        System.out.println(aboutMessage);
    }

    /**
     * Function to handle menu events. Unrecognized commands will be printed to 
     * the console with a message to that effect.
     * @param ae Object giving information about the menu item selected.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        String cmd = ae.getActionCommand();
        switch (cmd) {
            case "saveDiagramAs":
                this.saveDiagramAs();
                break;
            case "close":
                RingDisplay.windowCount--;
                this.ringFrame.dispose();
                break;
            case "quit":
                System.exit(0);
                break;
            case "chooseD":
                this.chooseDiscriminant();
                break;
            case "incrD":
                this.incrementDiscriminant();
                break;
            case "decrD":
                this.decrementDiscriminant();
                break;
            case "prevD":
                this.previousDiscriminant();
                break;
            case "nextD":
                this.nextDiscriminant();
                break;
            case "copyReadouts":
                this.copyReadoutsToClipboard();
                break;
            case "copyDiagram":
                this.copyDiagramToClipboard();
                break;
            case "zoomIn":
                this.zoomIn();
                break;
            case "zoomOut":
                this.zoomOut();
                break;
            case "decrZoomInterval":
                this.decreaseZoomStep();
                break;
            case "incrZoomInterval":
                this.increaseZoomStep();
                break;
            case "decrDotRadius":
                this.decreaseDotRadius();
                break;
            case "incrDotRadius":
                this.increaseDotRadius();
                break;
            case "defaultView":
                String messageForOKCancel = "This will reset pixels per unit interval, dot radius and zoom interval,\nbut not discriminant nor whether readouts are updated.";
                String titleForOKCancel = "Reset view defaults?";
                int okCancelReply = JOptionPane.showConfirmDialog(ringFrame, messageForOKCancel, titleForOKCancel, JOptionPane.OK_CANCEL_OPTION);
                if (okCancelReply == JOptionPane.OK_OPTION) {
                    resetViewDefaults();
                }
                break;
            case "toggleTheta":
                this.toggleThetaNotation();
                break;
            case "toggleReadOuts":
                this.toggleReadOutsEnabled();
                break;
            case "showUserManual":
                this.showUserManual();
                break;
            case "about":
                this.showAboutBox();
                break;
            default:
                System.out.println("Command " + cmd + " not recognized.");
        }
    }
    
    /**
     * Retrieves the ring currently displayed. This is mainly for testability purposes.
     * @return The ring currently displayed
     */
    public IntegerRing getRing() {
        return this.diagramRing;
    }

    /**
     * Sets the ring for which to draw a diagram of. Just in case, {@link 
     * #setPixelsPerBasicImaginaryInterval()} will be called to ensure the 
     * number of pixels by basic imaginary interval is adjusted accordingly.
     * @param ring The ring to set.
     */
    protected void setRing(IntegerRing ring) {
        this.diagramRing = ring;
        this.setPixelsPerBasicImaginaryInterval();
    }
    
    private JMenuBar setUpMenuBar() {
        JMenuBar ringWindowMenuBar;
        JMenu ringWindowMenu;
        JMenuItem ringWindowMenuItem, saveFileMenuItem, quitMenuItem;
        JMenuItem chooseDMenuItem, copyReadOutsToClipboardMenuItem, copyDiagramToClipboardMenuItem;
        JMenuItem resetViewDefaultsMenuItem;
        JMenuItem showManualMenuItem, aboutMenuItem;
        // Determine if this is a Mac OS X system, needing different keyboard shortcuts
        boolean macOSFlag;
        int maskCtrlCommand;
        String operSysName = System.getProperty("os.name");
        macOSFlag = operSysName.equals("Mac OS X");
        if (macOSFlag) {
            maskCtrlCommand = Event.META_MASK;
        } else {
            maskCtrlCommand = Event.CTRL_MASK;
        }
        // Set up the menu bar, menus and menu items
        ringWindowMenuBar = new JMenuBar();
        ringWindowMenu = new JMenu("File");
        ringWindowMenu.setMnemonic(KeyEvent.VK_F);
        ringWindowMenu.getAccessibleContext().setAccessibleDescription("Menu for file operations");
        ringWindowMenuBar.add(ringWindowMenu);
        ringWindowMenuItem = new JMenuItem("Save diagram as...");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Save currently displayed diagram to a PNG file");
        saveFileMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        saveFileMenuItem.setActionCommand("saveDiagramAs");
        saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, maskCtrlCommand + Event.SHIFT_MASK));
        saveFileMenuItem.addActionListener(this);
        ringWindowMenu.addSeparator();
        ringWindowMenuItem = new JMenuItem("Close");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Close the window");
        ringWindowMenuItem.setActionCommand("close");
        ringWindowMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, maskCtrlCommand));
        ringWindowMenuItem.addActionListener(this);
        ringWindowMenu.add(ringWindowMenuItem);
        ringWindowMenuItem = new JMenuItem("Quit");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Exit the program");
        quitMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        quitMenuItem.setActionCommand("quit");
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, maskCtrlCommand));
        quitMenuItem.addActionListener(this);
        ringWindowMenu = new JMenu("Edit");
        ringWindowMenu.setMnemonic(KeyEvent.VK_E);
        ringWindowMenu.getAccessibleContext().setAccessibleDescription("Menu to change certain parameters");
        ringWindowMenuBar.add(ringWindowMenu);
        if (this.includeRingChoice) {
            ringWindowMenuItem = new JMenuItem("Choose parameter d...");
            ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Let user enter new choice for ring discriminant");
            chooseDMenuItem = ringWindowMenu.add(ringWindowMenuItem);
            chooseDMenuItem.setActionCommand("chooseD");
            chooseDMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, maskCtrlCommand));
            chooseDMenuItem.addActionListener(this);
            ringWindowMenu.addSeparator();
            ringWindowMenuItem = new JMenuItem("Increment parameter d");
            ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Increment the discriminant to choose another ring");
            this.increaseDMenuItem = ringWindowMenu.add(ringWindowMenuItem);
            this.increaseDMenuItem.setActionCommand("incrD");
            if (macOSFlag) {
                this.increaseDMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, maskCtrlCommand));
            } else {
                this.increaseDMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, maskCtrlCommand));
            }
            this.increaseDMenuItem.addActionListener(this);
            ringWindowMenuItem = new JMenuItem("Decrement parameter d");
            ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Decrement the discriminant to choose another ring");
            this.decreaseDMenuItem = ringWindowMenu.add(ringWindowMenuItem);
            this.decreaseDMenuItem.setActionCommand("decrD");
            if (macOSFlag) {
                this.decreaseDMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, maskCtrlCommand));
            } else {
                this.decreaseDMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, maskCtrlCommand));
            }
            this.decreaseDMenuItem.addActionListener(this);
            ringWindowMenu.addSeparator();
        }
        ringWindowMenuItem = new JMenuItem("Copy readouts to clipboard");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Copy the readouts (integer, trace, norm, polynomial) to the clipboard");
        copyReadOutsToClipboardMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        copyReadOutsToClipboardMenuItem.setActionCommand("copyReadouts");
        copyReadOutsToClipboardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, maskCtrlCommand + Event.SHIFT_MASK));
        copyReadOutsToClipboardMenuItem.addActionListener(this);
        ringWindowMenuItem = new JMenuItem("Copy diagram to clipboard");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Copy the currently displayed diagram to the clipboard so that it's accessible to other applications");
        copyDiagramToClipboardMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        copyDiagramToClipboardMenuItem.setActionCommand("copyDiagram");
        copyDiagramToClipboardMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, maskCtrlCommand + Event.ALT_MASK));
        copyDiagramToClipboardMenuItem.addActionListener(this);
    /*    ringWindowMenu.addSeparator();
        THIS IS FOR WHEN I GET AROUND TO ADDING THE CAPABILITY TO CHANGE GRID, POINT COLORS
        ringWindowMenuItem = new JMenuItem("Preferences...");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Bring up a dialogue to adjust preferences");
        ringWindowMenu.add(ringWindowMenuItem); 
    */
        ringWindowMenu = new JMenu("View");
        ringWindowMenu.setMnemonic(KeyEvent.VK_V);
        ringWindowMenu.getAccessibleContext().setAccessibleDescription("Menu to zoom in or zoom out");
        ringWindowMenuBar.add(ringWindowMenu);
        if (this.includeRingChoice) {
            ringWindowMenuItem = new JMenuItem("Previous parameter d");
            ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("View the diagram for the previous discriminant");
            this.prevDMenuItem = ringWindowMenu.add(ringWindowMenuItem);
            this.prevDMenuItem.setActionCommand("prevD");
            this.prevDMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, maskCtrlCommand)); // Originally VK_LEFT for Windows, that keyboard shortcut did not work
            this.prevDMenuItem.addActionListener(this);
            this.prevDMenuItem.setEnabled(false);
            ringWindowMenuItem = new JMenuItem("Next parameter d");
            ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("View the diagram for the next discriminant");
            this.nextDMenuItem = ringWindowMenu.add(ringWindowMenuItem);
            this.nextDMenuItem.setActionCommand("nextD");
            this.nextDMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, maskCtrlCommand)); // Originally VK_RIGHT for Windows, that keyboard shortcut did not work
            this.nextDMenuItem.addActionListener(this);
            this.nextDMenuItem.setEnabled(false);
            ringWindowMenu.addSeparator();
        }
        ringWindowMenuItem = new JMenuItem("Zoom in");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Zoom in, by increasing pixels per unit interval");
        this.zoomInMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        this.zoomInMenuItem.setActionCommand("zoomIn");
        if (macOSFlag) {
            this.zoomInMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_EQUALS, Event.SHIFT_MASK));
        } else {
            this.zoomInMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ADD, Event.CTRL_MASK));
        }
        this.zoomInMenuItem.addActionListener(this);
        if (this.pixelsPerUnitInterval > (MAXIMUM_PIXELS_PER_UNIT_INTERVAL - zoomStep)) {
            this.zoomInMenuItem.setEnabled(false);
        }
        ringWindowMenuItem = new JMenuItem("Zoom out");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Zoom out, by decreasing pixels per unit interval");
        this.zoomOutMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        this.zoomOutMenuItem.setActionCommand("zoomOut");
        if (macOSFlag) {
            this.zoomOutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, 0));
        } else {
            this.zoomOutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SUBTRACT, Event.CTRL_MASK));
        }
        this.zoomOutMenuItem.addActionListener(this);
        if (this.pixelsPerUnitInterval < (MINIMUM_PIXELS_PER_UNIT_INTERVAL + zoomStep)) {
            this.zoomInMenuItem.setEnabled(false);
        }
        ringWindowMenu.addSeparator();
        ringWindowMenuItem = new JMenuItem("Decrease zoom step");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Decrease the zoom step used by the zoom in and zoom out functions");
        this.decreaseZoomStepMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        this.decreaseZoomStepMenuItem.setActionCommand("decrZoomInterval");
        this.decreaseZoomStepMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, maskCtrlCommand + Event.SHIFT_MASK));
        this.decreaseZoomStepMenuItem.addActionListener(this);
        if (this.zoomStep == MINIMUM_ZOOM_STEP) {
            this.decreaseZoomStepMenuItem.setEnabled(false);
        }
        ringWindowMenuItem = new JMenuItem("Increase zoom step");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Increase the zoom step used by the zoom in and zoom out functions");
        this.increaseZoomStepMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        this.increaseZoomStepMenuItem.setActionCommand("incrZoomInterval");
        this.increaseZoomStepMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, maskCtrlCommand + Event.SHIFT_MASK));
        this.increaseZoomStepMenuItem.addActionListener(this);
        if (this.zoomStep == MAXIMUM_ZOOM_STEP) {
            increaseZoomStepMenuItem.setEnabled(false);
        }
        ringWindowMenu.addSeparator();
        String dotsOrLinesText;
        if (this.diagramRing.isPurelyReal()) {
            dotsOrLinesText = "line thickness";
        } else {
            dotsOrLinesText = "dot radius";
        }
        String menuItemText = "Decrease " + dotsOrLinesText;
        ringWindowMenuItem = new JMenuItem(menuItemText);
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Decrease the dot radius used to draw the points on the grids");
        this.decreaseDotRadiusMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        this.decreaseDotRadiusMenuItem.setActionCommand("decrDotRadius");
        this.decreaseDotRadiusMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, maskCtrlCommand));
        this.decreaseDotRadiusMenuItem.addActionListener(this);
        if (this.dotRadius == MINIMUM_DOT_RADIUS) {
            this.decreaseDotRadiusMenuItem.setEnabled(false);
        }
        menuItemText = "Increase " + dotsOrLinesText;
        ringWindowMenuItem = new JMenuItem(menuItemText);
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Increase the dot radius used to draw the points on the grids");
        this.increaseDotRadiusMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        this.increaseDotRadiusMenuItem.setActionCommand("incrDotRadius");
        this.increaseDotRadiusMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, maskCtrlCommand));
        this.increaseDotRadiusMenuItem.addActionListener(this);
        if (this.dotRadius == MAXIMUM_DOT_RADIUS) {
            this.increaseDotRadiusMenuItem.setEnabled(false);
        }
        ringWindowMenu.addSeparator();
        ringWindowMenuItem = new JMenuItem("Reset view defaults");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Reset defaults for zoom level, zoom interval and dot radius");
        resetViewDefaultsMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        resetViewDefaultsMenuItem.setActionCommand("defaultView");
        resetViewDefaultsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
        resetViewDefaultsMenuItem.addActionListener(this);
        ringWindowMenu.addSeparator();
        if (this.includeThetaToggle) {
            this.preferThetaNotationMenuItem = new JCheckBoxMenuItem("Use theta notation in readouts", false);
            this.preferThetaNotationMenuItem.getAccessibleContext().setAccessibleDescription("Toggle whether theta notation is used or not in the integer readout.");
            this.preferThetaNotationMenuItem.setActionCommand("toggleTheta");
            this.preferThetaNotationMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, 0));
            this.preferThetaNotationMenuItem.addActionListener(this);
            ringWindowMenu.add(this.preferThetaNotationMenuItem);
        }
        if (this.includeReadoutsUpdate) {
            this.toggleReadOutsEnabledMenuItem = new JCheckBoxMenuItem("Update readouts", false);
            this.toggleReadOutsEnabledMenuItem.getAccessibleContext().setAccessibleDescription("Toggle whether the trace, norm and polynomial readouts are updated.");
            this.toggleReadOutsEnabledMenuItem.setActionCommand("toggleReadOuts");
            if (macOSFlag) {
                this.toggleReadOutsEnabledMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0)); // On the Mac, fn-F2 is too much of a hassle, in my opinion.
            } else {
                this.toggleReadOutsEnabledMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0)); // Decided Ctrl-F2 is too uncomfortable, so changed it to just F2.
            }
            this.toggleReadOutsEnabledMenuItem.addActionListener(this);
            ringWindowMenu.add(this.toggleReadOutsEnabledMenuItem);
        }
        ringWindowMenu = new JMenu("Help");
        ringWindowMenu.setMnemonic(KeyEvent.VK_H);
        ringWindowMenu.getAccessibleContext().setAccessibleDescription("Menu to provide help and documentation");
        ringWindowMenuBar.add(ringWindowMenu);
        ringWindowMenuItem = new JMenuItem("User Manual...");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Use default Web browser to show user manual");
        showManualMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        showManualMenuItem.setActionCommand("showUserManual");
        showManualMenuItem.addActionListener(this);
        ringWindowMenuItem = new JMenuItem("About...");
        ringWindowMenuItem.getAccessibleContext().setAccessibleDescription("Information about this program");
        aboutMenuItem = ringWindowMenu.add(ringWindowMenuItem);
        aboutMenuItem.setActionCommand("about");
        aboutMenuItem.addActionListener(this);
        return ringWindowMenuBar;
    }
    
    private JPanel setUpReadOuts() {
        JPanel readOutsPane = new JPanel();
        this.algIntReadOut = new JTextField(DEFAULT_READOUT_FIELD_COLUMNS);
        this.algIntReadOut.setText("0");
        this.algIntReadOut.setEditable(false);
        readOutsPane.add(this.algIntReadOut);
        readOutsPane.add(new JLabel("Trace: "));
        this.algIntTraceReadOut = new JTextField(DEFAULT_READOUT_FIELD_COLUMNS);
        this.algIntTraceReadOut.setText("0");
        this.algIntTraceReadOut.setEditable(false);
        readOutsPane.add(this.algIntTraceReadOut);
        readOutsPane.add(new JLabel("Norm: "));
        this.algIntNormReadOut = new JTextField(DEFAULT_READOUT_FIELD_COLUMNS);
        this.algIntNormReadOut.setText("0");
        this.algIntNormReadOut.setEditable(false);
        readOutsPane.add(algIntNormReadOut);
        readOutsPane.add(new JLabel("Polynomial: "));
        this.algIntPolReadOut = new JTextField(DEFAULT_READOUT_FIELD_COLUMNS);
        this.algIntPolReadOut.setText("x");
        this.algIntPolReadOut.setEditable(false);
        readOutsPane.add(algIntPolReadOut);
        return readOutsPane;
    }
   
    /**
     * Sets up the JFrame in which the various ring diagrams will be drawn.
     */
    protected void setUpRingFrame() {
        if (this.alreadySetUp) {
            throw new RuntimeException("setUpRingFrame was already called, don't need to call it again.");
        }
        this.ringFrame = new JFrame("Ring Diagram for " + this.diagramRing.toString());
        this.ringFrame.setJMenuBar(this.setUpMenuBar());
        this.setBackground(this.backgroundColor);
        this.setPreferredSize(new Dimension(this.ringCanvasHorizMax, this.ringCanvasVerticMax));
        this.ringFrame.add(this, BorderLayout.CENTER);
        this.ringFrame.add(this.setUpReadOuts(), BorderLayout.PAGE_END);
        this.ringFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ringFrame.pack();
        this.ringFrame.setVisible(true);
        this.alreadySetUp = true;
    }
    
    /**
     * Starts up a JFrame in which to display the ring diagram. Many details 
     * should have already been set in the RingDisplay constructor if not the 
     * subclass constructor.
     */
    public void startRingDisplay() {
        this.setPixelsPerBasicImaginaryInterval(); // Just in case
        this.setUpRingFrame();
        RingDisplay.windowCount++;
    }
    
    /**
     * Constructor. Most of the work is setting the various instance fields to 
     * their default values.
     * @param ring The ring to first display. Subclasses will pass up (through a 
     * super call) a ring of the appropriate class from an algebraics 
     * subpackage.
     */
    public RingDisplay(IntegerRing ring) {
        this.pixelsPerUnitInterval = DEFAULT_PIXELS_PER_UNIT_INTERVAL;
        this.pixelsPerBasicImaginaryInterval = this.pixelsPerUnitInterval;
        this.ringCanvasHorizMax = RING_CANVAS_DEFAULT_HORIZ_MAX;
        this.ringCanvasVerticMax = RING_CANVAS_DEFAULT_VERTIC_MAX;
        this.backgroundColor = DEFAULT_CANVAS_BACKGROUND_COLOR;
        this.halfIntegerGridColor = DEFAULT_HALF_INTEGER_GRID_COLOR;
        this.integerGridColor = DEFAULT_INTEGER_GRID_COLOR;
        this.zeroColor = DEFAULT_ZERO_COLOR;
        this.unitColor = DEFAULT_UNIT_COLOR;
        this.inertPrimeColor = DEFAULT_INERT_PRIME_COLOR;
        this.splitPrimeColor = DEFAULT_SPLIT_PRIME_COLOR;
        this.splitPrimeMidDegreeColor = DEFAULT_SPLIT_MID_DEGREE_PRIME_COLOR;
        this.ramifiedPrimeColor = DEFAULT_RAMIFIED_PRIME_COLOR;
        this.ramifiedPrimeMidDegreeColor = DEFAULT_RAMIFIED_MID_DEGREE_PRIME_COLOR;
        this.zeroCoordX = (int) Math.floor(this.ringCanvasHorizMax/2);
        this.zeroCoordY = (int) Math.floor(this.ringCanvasVerticMax/2);
        // this.zeroCentered = true;
        // this.zeroInView = true;
        this.dotRadius = DEFAULT_DOT_RADIUS;
        this.dotDiameter = 2 * this.dotRadius;
        this.zoomStep = DEFAULT_ZOOM_INTERVAL;
        this.preferenceForThetaNotation = false;
        this.diagramRing = ring;
        this.discrHistory = new ArrayList<>();
        this.discrHistory.add(this.diagramRing);
        this.currHistoryIndex = 0;
    }
        
}
