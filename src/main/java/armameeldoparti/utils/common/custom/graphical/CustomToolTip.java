package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.Error;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.IllegalComponentStateException;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ToolTipUI;

/**
 * Custom tooltip class.
 *
 * <p>This class is used to instantiate a custom tooltip that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomToolTip extends JToolTip {

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic tooltip using the established program aesthetics.
   *
   * @param component The component that will contain the tooltip.
   */
  public CustomToolTip(JComponent component) {
    setOpaque(false);
    setComponent(component);
    setUI(new CustomToolTipUI());
  }

  // ---------- Public methods -----------------------------------------------------------------------------------------------------------------------

  /**
   * This method is overridden in order to maintain the tooltip graphical properties outside of the frame boundaries.
   *
   * <p>The "java:S1190" warning is suppressed since JDK22 allows the use of unnamed variables.
   */
  @Override
  @SuppressWarnings("java:S1190")
  public void addNotify() {
    super.addNotify();

    setOpaque(false);

    Component parent = this.getParent();

    if (parent instanceof JComponent) {
      ((JComponent) parent).setOpaque(false);
    }

    try {
      SwingUtilities.windowForComponent(this)
                    .setBackground(new Color(0, 0, 0, 0));
    } catch (IllegalComponentStateException _) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL);
    }
  }

  @Override
  public Insets getInsets() {
    return Constants.INSETS_TOOLTIP;
  }

  // ---------- Private inner classes ----------------------------------------------------------------------------------------------------------------

  /**
   * Private, internal class that establishes the tooltip UI.
   */
  private class CustomToolTipUI extends ToolTipUI {

    // ---------- Public methods ---------------------------------------------------------------------------------------------------------------------

    @Override
    public void paint(Graphics g, JComponent c) {
      Graphics2D g2 = (Graphics2D) g.create();

      RoundRectangle2D roundedRect = new RoundRectangle2D.Float(0,
                                                                0,
                                                                (c.getWidth() - 1),
                                                                (c.getHeight() - 1),
                                                                Constants.ROUNDED_BORDER_ARC_TOOLTIP,
                                                                Constants.ROUNDED_BORDER_ARC_TOOLTIP);

      // Round rectangle configuration
      g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
      g2.setComposite(AlphaComposite.Clear);
      g2.fill(roundedRect);

      // Background painting
      g2.setComposite(AlphaComposite.Src);
      g2.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
      g2.fill(roundedRect);

      // Text painting
      FontMetrics fm = g2.getFontMetrics();

      String text = ((JToolTip) c).getTipText();

      g2.setColor(Color.WHITE);
      g2.drawString(text, (c.getWidth() - fm.stringWidth(text)) / 2, (c.getHeight() - fm.getHeight()) / 2 + fm.getAscent());
      g2.dispose();
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
      FontMetrics fm = c.getFontMetrics(c.getFont());

      return new Dimension(fm.stringWidth(((JToolTip) c).getTipText()) + getInsets().left + getInsets().right,
                           fm.getHeight() + getInsets().top + getInsets().bottom);
    }
  }
}