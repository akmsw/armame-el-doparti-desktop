package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.border.EmptyBorder;

/**
 * A custom label that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomLabel extends JLabel {

  private int alignment;
  private int fontSize;

  private String hoverText;

  private Color backgroundColor;
  private Color foregroundColor;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic label using the established program aesthetics.
   *
   * @param text      The label text.
   * @param hoverText The label tooltip text.
   * @param alignment The label text alignment.
   */
  public CustomLabel(String text, String hoverText, int alignment) {
    super(text);
    setAlignment(alignment);
    setBackgroundColor(Constants.COLOR_GREEN_MEDIUM_LIGHT);
    setForegroundColor(getForeground());
    setFontSize((int) Constants.SIZE_FONT_DEFAULT);
    setHoverText(hoverText);
    setUpGraphicalProperties();
  }

  /**
   * Builds a basic label using the established program aesthetics.
   *
   * @param text            The label text.
   * @param hoverText       The label tooltip text.
   * @param backgroundColor The background color for the label.
   * @param foregroundColor The fireground color for the label.
   * @param alignment       The label text alignment.
   * @param fontSize        The font size for the label text.
   */
  public CustomLabel(String text, String hoverText, Color backgroundColor, Color foregroundColor, int alignment, int fontSize) {
    super(text);
    setAlignment(alignment);
    setBackgroundColor(backgroundColor);
    setForegroundColor(foregroundColor);
    setFontSize(fontSize);
    setHoverText(hoverText);
    setUpGraphicalProperties();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintComponent(Graphics graphics) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(getBackground());
    graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.dispose();

    super.paintComponent(graphics);
  }

  @Override
  public JToolTip createToolTip() {
    return new CustomToolTip(this);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties of the label in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setHorizontalAlignment(alignment);
    setBorder(new EmptyBorder(Constants.INSETS_LABEL));
    setFont(new Font(getFont().getName(), Font.PLAIN, fontSize));
    setBackground(backgroundColor);
    setForeground(foregroundColor);
    setToolTipText(hoverText);
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public int getAlignment() {
    return alignment;
  }

  public int getFontSize() {
    return fontSize;
  }

  public String getHoverText() {
    return hoverText;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public Color getForegroundColor() {
    return foregroundColor;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setAlignment(int alignment) {
    this.alignment = alignment;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public void setHoverText(String tooltipText) {
    this.hoverText = tooltipText;
  }

  public void setBackgroundColor(Color backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void setForegroundColor(Color foregroundColor) {
    this.foregroundColor = foregroundColor;
  }
}