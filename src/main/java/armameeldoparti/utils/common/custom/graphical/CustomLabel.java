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
    setOpaque(false);
    setHorizontalAlignment(alignment);
    setBorder(new EmptyBorder(Constants.INSETS_LABEL));
    setBackground(Constants.COLOR_GREEN_MEDIUM_LIGHT);
    setForeground(getForeground());
    setFont(new Font(getFont().getName(), Font.PLAIN, (int) Constants.FONT_SIZE));
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
   * @param fontSize        The font size for the label text.
   */
  public CustomLabel(String text, String tooltipText, Color backgroundColor, Color foregroundColor, int alignment, int fontSize) {
    super(text);
    setOpaque(false);
    setHorizontalAlignment(alignment);
    setBorder(new EmptyBorder(Constants.INSETS_LABEL));
    setBackground(backgroundColor);
    setForeground(foregroundColor);
    setFont(new Font(getFont().getName(), Font.PLAIN, fontSize));
    setToolTipText(tooltipText);
  }

  // ---------- Protected methods --------------------------------------------------------------------------------------------------------------------

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
}