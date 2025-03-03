package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JButton;

/**
 * A custom button that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomButton extends JButton {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private int arc;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic rounded button using the established program aesthetics.
   *
   * @param text The text to display on the button.
   * @param arc  The round borders arc.
   */
  public CustomButton(String text, int arc) {
    super(text);
    setArc(arc);
    setUpGraphicalProperties();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public Insets getInsets() {
    return Constants.INSETS_GENERAL;
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintComponent(Graphics graphics) {
    if (getModel().isPressed()) {
      graphics.setColor(Constants.COLOR_GREEN_MEDIUM);
    } else if (getModel().isRollover()) {
      graphics.setColor(Constants.COLOR_GREEN_DARK_MEDIUM);
    } else {
      graphics.setColor(isEnabled() ? getBackground() : Constants.COLOR_GREEN_MEDIUM);
    }

    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), arc, arc);
    graphics2d.dispose();

    super.paintComponent(graphics);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties of the button in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setFocusPainted(false);
    setContentAreaFilled(false);
    setBorderPainted(false);
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getArc() {
    return arc;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private void setArc(int arc) {
    this.arc = arc;
  }
}