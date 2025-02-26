package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JSeparator;

/**
 * A custom separator that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomSeparator extends JSeparator {

  // ---------- Protected methods --------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintComponent(Graphics graphics) {
    Graphics2D graphics2d = (Graphics2D) graphics;

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.fillRoundRect(0, (getHeight() / 2), getWidth(), 5, Constants.ROUNDED_BORDER_ARC_SEPARATOR, Constants.ROUNDED_BORDER_ARC_SEPARATOR);
  }
}