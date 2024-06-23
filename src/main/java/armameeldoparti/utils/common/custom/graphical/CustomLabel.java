package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;

/**
 * Custom label class.
 *
 * <p>This class is used to instantiate a custom label that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomLabel extends JLabel {

  // ---------- Private fields -----------------------------------------------------------------------------------------------------------------------

  private Color backgroundColor;
  private Color foregroundColor;

  // ---------- Constructors -------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic label using the established program aesthetics.
   *
   * @param text        The label text.
   * @param tooltipText The label tooltip text
   * @param alignment   The label text alignment.
   */
  public CustomLabel(String text, String tooltipText, int alignment) {
    super(text);
    setBackgroundColor(Constants.COLOR_GREEN_MEDIUM_LIGHT);
    setForegroundColor(getForeground());
    setUpGraphicalProperties(alignment);
    setToolTipText(tooltipText);
  }

  /**
   * Builds a basic label using the established program aesthetics.
   *
   * @param text            The label text.
   * @param tooltipText     The label tooltip text
   * @param backgroundColor The background color for the label.
   * @param foregroundColor The fireground color for the label.
   * @param alignment       The label text alignment.
   */
  public CustomLabel(String text, String tooltipText, Color backgroundColor, Color foregroundColor, int alignment) {
    super(text);
    setBackgroundColor(backgroundColor);
    setForegroundColor(foregroundColor);
    setUpGraphicalProperties(alignment);
    setToolTipText(tooltipText);
  }

  // ---------- Protected methods --------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g.create();

    g2.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    g2.setColor(getBackgroundColor());
    g2.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    g2.dispose();

    super.paintComponent(g);
  }

  @Override
  public JToolTip createToolTip() {
    return new CustomToolTip(this);
  }

  // ---------- Private methods ----------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties of the label in order to fit the program aesthetics.
   *
   * @param alignment The label text alignment.
   */
  private void setUpGraphicalProperties(int alignment) {
    setHorizontalAlignment(alignment);
    setOpaque(false);
    setBorder(new EmptyBorder(Constants.INSETS_GENERAL));
    setBackground(getBackgroundColor());
    setForeground(getForegroundColor());
  }

  // ---------- Getters ------------------------------------------------------------------------------------------------------------------------------

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public Color getForegroundColor() {
    return foregroundColor;
  }

  // ---------- Setters ------------------------------------------------------------------------------------------------------------------------------

  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void setForegroundColor(Color foregroundColor) {
    this.foregroundColor = foregroundColor;
  }
}