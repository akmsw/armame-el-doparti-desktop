package armameeldoparti.utils.common.custom.graphical.ui;

import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import java.awt.Container;
import java.awt.Dimension;
import java.util.Arrays;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import net.miginfocom.swing.MigLayout;

/**
 * A custom option pane UI that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomOptionPaneUI extends BasicOptionPaneUI {

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Creates the custom option pane UI according to the overall program aesthetics.
   */
  public CustomOptionPaneUI() {
    super();
  }

  // ---------- Public methods -----------------------------------------------------------------------------------------------------------------------

  /**
   * The "java:S1172" warning is suppressed since the argument is intentionally unused.
   *
   * @param component Intentionally unused argument.
   *
   * @return A new custom option pane UI.
   */
  @SuppressWarnings("java:S1172")
  public static ComponentUI createUI(JComponent component) {
    return new CustomOptionPaneUI();
  }

  // ---------- Protected methods --------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a button for each string in {@code buttons} and adds them to {@code container}. If {@code buttons} does not contain any string-instance
   * object, an array is retrieved using the message type for the dialog. This array contains the strings that will be used in the buttons to be
   * placed in the dialog.
   *
   * <p>A minimum button size is forced in order to ensure that the buttons showing only anchorages numbers have the same size, since the font used is
   * not monospaced for user readability matters.
   *
   * <p>The "java:S1190" and "java:S117" warnings are suppressed since JDK22 allows the use of unnamed variables.
   *
   * @param container    A container for the buttons.
   * @param buttons      An array with the strings for each button of the dialog.
   * @param initialIndex An initial index used for validation.
   *
   * @see #getButtonsForMessageType(int)
   */
  @Override
  @SuppressWarnings({"java:S1190", "java:S117"})
  protected void addButtonComponents(Container container, Object[] buttons, int initialIndex) {
    if ((buttons == null) || (buttons.length <= 0)) {
      return;
    }

    if (Arrays.asList(buttons)
              .stream()
              .noneMatch(String.class::isInstance)) {
      buttons = getButtonsForMessageType(optionPane.getMessageType());
    }

    final int buttonsNumber = buttons.length;

    JPanel buttonPanel = new JPanel();

    buttonPanel.setLayout(new MigLayout());
    buttonPanel.setOpaque(false);

    for (Object buttonText : buttons) {
      CustomButton customButton = new CustomButton((String) buttonText, Constants.ROUNDED_BORDER_ARC_BUTTON_DIALOG);

      customButton.setMinimumSize(new Dimension(Constants.SIZE_BUTTON_DIALOG_MIN_WIDTH, Constants.SIZE_BUTTON_DIALOG_MIN_HEIGHT));
      customButton.addActionListener(_ -> {
        if (initialIndex >= 0 && initialIndex < buttonsNumber) {
          ((JOptionPane) SwingUtilities.getAncestorOfClass(JOptionPane.class, container))
                                       .setValue(buttonText);
        }
      });

      buttonPanel.add(customButton);
    }

    container.add(buttonPanel, Constants.MIG_LAYOUT_SOUTH);
  }

  // ---------- Private methods ----------------------------------------------------------------------------------------------------------------------

  /**
   * @param messageType The type of the message to be shown in the dialog.
   *
   * @return An array with the strings for each button to be placed in the dialog based on the message type.
   */
  private Object[] getButtonsForMessageType(int messageType) {
    return switch (messageType) {
      case JOptionPane.WARNING_MESSAGE -> new Object[] { UIManager.getString("OptionPane.okButtonText"),
                                                        UIManager.getString("OptionPane.cancelButtonText") };
      case JOptionPane.QUESTION_MESSAGE -> new Object[] { UIManager.getString("OptionPane.yesButtonText"),
                                                          UIManager.getString("OptionPane.noButtonText") };
      default -> new Object[] { UIManager.getString("OptionPane.okButtonText") };
    };
  }
}