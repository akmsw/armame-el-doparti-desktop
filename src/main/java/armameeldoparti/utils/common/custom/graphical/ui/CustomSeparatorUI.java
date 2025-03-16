package armameeldoparti.utils.common.custom.graphical.ui;

import armameeldoparti.utils.common.Constants;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.SeparatorUI;

/**
 * A custom separator UI that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomSeparatorUI extends SeparatorUI {

  // ---------- Public methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom separator UI.
   */
  public static ComponentUI createUI(JComponent component) {
    component.setBackground(Constants.COLOR_GREEN_LIGHT);
    component.setForeground(Constants.COLOR_GREEN_MEDIUM);

    return new CustomSeparatorUI();
  }

  @Override
  public void paint(Graphics graphics, JComponent component) {
    Graphics2D graphics2d = (Graphics2D) graphics;

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.fillRoundRect(0, (component.getHeight() / 2), component.getWidth(), 5, Constants.ROUNDED_BORDER_ARC_SEPARATOR, Constants.ROUNDED_BORDER_ARC_SEPARATOR);
    graphics2d.dispose();
  }
}