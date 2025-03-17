package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;

import javax.swing.JRadioButton;

/**
 * A custom radio button that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class CustomRadioButton extends JRadioButton {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic radio button using the established program aesthetics.
   *
   * @param text The text shown in the radio button.
   */
  public CustomRadioButton(String text) {
    super(text);
    setUpGraphicalProperties();
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties of the radio button in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    setBackground(Constants.COLOR_GREEN_LIGHT);
    setFocusable(false);
    setIcon(Constants.ICON_RB_E_US_UF);
    setSelectedIcon(Constants.ICON_RB_E_S_UF);
    setDisabledIcon(Constants.ICON_RB_D_US);
    setDisabledSelectedIcon(Constants.ICON_RB_D_S);
    setRolloverIcon(Constants.ICON_RB_E_US_F);
    setRolloverSelectedIcon(Constants.ICON_RB_E_S_F);
    setPressedIcon(Constants.ICON_RB_E_US_P);
  }
}