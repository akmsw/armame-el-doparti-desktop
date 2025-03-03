package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * A custom scroll pane that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomScrollPane extends JScrollPane {

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic scroll pane using the established program aesthetics.
   *
   * @param textArea Text area associated to the scroll pane that will be controlled.
   */
  public CustomScrollPane(JTextArea textArea) {
    super(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    setUpGraphicalProperties();
  }

  // ---------- Protected methods --------------------------------------------------------------------------------------------------------------------

  @Override
  protected void paintBorder(Graphics graphics) {
    Graphics2D graphics2d = (Graphics2D) graphics.create();

    graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
    graphics2d.setColor(Constants.COLOR_GREEN_LIGHT_WHITE);
    graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_GENERAL, Constants.ROUNDED_BORDER_ARC_GENERAL);
    graphics2d.dispose();
  }

  // ---------- Private methods ----------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties of the scroll pane in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setOpaque(false);
    setBorder(new EmptyBorder(Constants.INSETS_GENERAL));
    getViewport().setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);
    getVerticalScrollBar().setUI(new BasicScrollBarUI() {
      @Override
      protected void configureScrollBarColors() {
        this.thumbColor = Constants.COLOR_GREEN_DARK;
        this.trackColor = Constants.COLOR_GREEN_MEDIUM;
      }

      @Override
      protected JButton createDecreaseButton(int orientation) {
        return new CustomArrowButton(orientation);
      }

      @Override
      protected JButton createIncreaseButton(int orientation) {
        return new CustomArrowButton(orientation);
      }

      @Override
      protected void paintThumb(Graphics graphics, JComponent component, Rectangle thumbBounds) {
        Graphics2D graphics2d = (Graphics2D) graphics.create();

        graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
        graphics2d.setColor(Constants.COLOR_GREEN_DARK);
        graphics2d.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, Constants.ROUNDED_BORDER_ARC_SCROLLBAR, Constants.ROUNDED_BORDER_ARC_SCROLLBAR);
        graphics2d.dispose();
      }

      @Override
      protected void paintTrack(Graphics graphics, JComponent component, Rectangle trackBounds) {
        Graphics2D graphics2d = (Graphics2D) graphics.create();

        graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
        graphics2d.setColor(Constants.COLOR_GREEN_MEDIUM);
        graphics2d.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, Constants.ROUNDED_BORDER_ARC_SCROLLBAR, Constants.ROUNDED_BORDER_ARC_SCROLLBAR);
        graphics2d.dispose();
      }
    });
    getVerticalScrollBar().setOpaque(false);
  }
}