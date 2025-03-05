package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.enums.Error;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.ui.CustomToolTipUI;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.IllegalComponentStateException;
import java.awt.Insets;
import java.awt.Window;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;

/**
 * A custom tooltip that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomToolTip extends JToolTip {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic tooltip using the established program aesthetics.
   *
   * @param component The component that will contain the tooltip.
   */
  public CustomToolTip(JComponent component) {
    // Initialize the tooltip round rectangle to avoid showing white background the first time the tooltip is shown due to null initial value
    if (CommonFields.getTooltipRectangle() == null) {
      FontMetrics fontMetrics = getFontMetrics(getFont());

      String tooltipText = component.getToolTipText();

      float roundRectangleInitialWidth = (fontMetrics.stringWidth(tooltipText) + Constants.INSETS_TOOLTIP.left + Constants.INSETS_TOOLTIP.right);
      float roundRectangleInitialHeight = (fontMetrics.getHeight() + Constants.INSETS_TOOLTIP.top + Constants.INSETS_TOOLTIP.bottom);

      CommonFields.setTooltipRectangle(new RoundRectangle2D.Float(0, 0, roundRectangleInitialWidth, roundRectangleInitialHeight, (Constants.ROUNDED_BORDER_ARC_TOOLTIP), (Constants.ROUNDED_BORDER_ARC_TOOLTIP)));
    }

    setOpaque(false);
    setComponent(component);
    setUI(new CustomToolTipUI());
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * This method is overridden in order to maintain the tooltip graphical properties outside of the frame boundaries.
   */
  @Override
  public void addNotify() {
    super.addNotify();

    Window window = SwingUtilities.windowForComponent(this);

    if (window != null && !(window instanceof JFrame)) {
      Component parent = getParent();

      if (parent instanceof JComponent parentComponent) {
        parentComponent.setOpaque(false);
      }

      try {
        window.setShape(CommonFields.getTooltipRectangle());
      } catch (IllegalComponentStateException | UnsupportedOperationException exception) {
        CommonFunctions.exitProgram(Error.ERROR_INTERNAL, exception.getStackTrace());
      }
    }
  }

  @Override
  public Insets getInsets() {
    return Constants.INSETS_TOOLTIP;
  }
}