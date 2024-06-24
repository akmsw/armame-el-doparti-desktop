package armameeldoparti.utils.common.custom.graphical.ui;

import armameeldoparti.utils.common.Constants;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.ToolTipUI;

/**
 * A custom tooltip UI that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomToolTipUI extends ToolTipUI {

  // ---------- Public methods ---------------------------------------------------------------------------------------------------------------------

  /**
   * The "java:S1172" warning is suppressed since the argument is intentionally unused.
   *
   * @param component Intentionally unused argument.
   *
   * @return A new custom tooltip UI.
   */
  @SuppressWarnings("java:S1172")
  public static ComponentUI createUI(JComponent component) {
    return new CustomToolTipUI();
  }

  @Override
  public void paint(Graphics g, JComponent c) {
    Graphics2D g2 = (Graphics2D) g.create();

    RoundRectangle2D roundedRect = new RoundRectangle2D.Float(0, 0, c.getWidth(), c.getHeight(),
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

    Insets insets = c.getInsets();

    return new Dimension(fm.stringWidth(((JToolTip) c).getTipText()) + insets.left + insets.right,
                         fm.getHeight() + insets.top + insets.bottom);
  }
}