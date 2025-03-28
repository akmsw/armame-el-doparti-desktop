package armameeldoparti.utils.common.custom.graphical.ui;

import armameeldoparti.utils.common.Constants;

import javax.swing.JComponent;
import javax.swing.JTextArea;

import javax.swing.plaf.ComponentUI;

import javax.swing.plaf.basic.BasicTextAreaUI;

/**
 * A custom text area UI that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class CustomTextAreaUI extends BasicTextAreaUI {

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom text area UI.
   */
  public static ComponentUI createUI(JComponent component) {
    JTextArea textArea = (JTextArea) component;

    textArea.setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);
    textArea.setLineWrap(true);
    textArea.setWrapStyleWord(true);
    textArea.setEditable(false);
    textArea.setOpaque(false);
    textArea.setFocusable(false);
    textArea.setMargin(Constants.INSETS_GENERAL);

    return new CustomTextAreaUI();
  }
}