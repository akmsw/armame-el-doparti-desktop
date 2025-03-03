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

  // ---------- Public methods -----------------------------------------------------------------------------------------------------------------------

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
  public void paint(Graphics graphics, JComponent component) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    RoundRectangle2D roundedRect = new RoundRectangle2D.Float(0, 0, component.getWidth(), component.getHeight(), Constants.ROUNDED_BORDER_ARC_TOOLTIP, Constants.ROUNDED_BORDER_ARC_TOOLTIP);

    // Round rectangle configuration
    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setComposite(AlphaComposite.Clear);
    graphics2d.fill(roundedRect);

    // Background painting
    graphics2d.setComposite(AlphaComposite.Src);
    graphics2d.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
    graphics2d.fill(roundedRect);

    // Text painting
    FontMetrics fontMetric = graphics2d.getFontMetrics();

    String text = ((JToolTip) component).getTipText();

    graphics2d.setColor(Color.WHITE);
    graphics2d.drawString(text, ((component.getWidth() - fontMetric.stringWidth(text)) / 2), (((component.getHeight() - fontMetric.getHeight()) / 2) + fontMetric.getAscent()));
    graphics2d.dispose();
  }

  @Override
  public Dimension getPreferredSize(JComponent component) {
    FontMetrics fontMetric = component.getFontMetrics(component.getFont());

    Insets insets = component.getInsets();

    return new Dimension((fontMetric.stringWidth(((JToolTip) component).getTipText()) + insets.left + insets.right), (fontMetric.getHeight() + insets.top + insets.bottom));
  }
}