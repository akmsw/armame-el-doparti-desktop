package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.models.enums.Error;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.ui.CustomToolTipUI;
import java.awt.Color;
import java.awt.Component;
import java.awt.IllegalComponentStateException;
import java.awt.Insets;
import java.awt.Window;
import javax.swing.JComponent;
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

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic tooltip using the established program aesthetics.
   *
   * @param component The component that will contain the tooltip.
   */
  public CustomToolTip(JComponent component) {
    setOpaque(false);
    setComponent(component);
    setUI(new CustomToolTipUI());
  }

  // ---------- Public methods -----------------------------------------------------------------------------------------------------------------------

  /**
   * This method is overridden in order to maintain the tooltip graphical properties outside of the frame boundaries.
   *
   * <p>The "java:S1190" warning is suppressed since JDK22 allows the use of unnamed variables.
   */
  @Override
  @SuppressWarnings("java:S1190")
  public void addNotify() {
    super.addNotify();

    Component parent = this.getParent();

    if (parent instanceof JComponent auxComp) {
      auxComp.setOpaque(false);
    }

    try {
      Window window = SwingUtilities.windowForComponent(this);

      if (window != null) {
        window.setBackground(new Color(0, 0, 0, 0));
      }
    } catch (IllegalComponentStateException | UnsupportedOperationException _) {
      CommonFunctions.exitProgram(Error.ERROR_INTERNAL);
    }
  }

  @Override
  public Insets getInsets() {
    return Constants.INSETS_TOOLTIP;
  }
}