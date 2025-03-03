package armameeldoparti.utils.common.custom.graphical;

import armameeldoparti.utils.common.Constants;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.text.DefaultCaret;

/**
 * A custom spinner that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class CustomSpinner extends JSpinner {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic spinner that fits the established program aesthetics.
   *
   * @param spinnerNumberModel The number model used for the spinner.
   */
  public CustomSpinner(SpinnerNumberModel spinnerNumberModel) {
    super(spinnerNumberModel);
    setUpGraphicalProperties();
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Configures the graphical properties of the spinner in order to fit the program aesthetics.
   */
  private void setUpGraphicalProperties() {
    JFormattedTextField spinnerTextField = ((DefaultEditor) getEditor()).getTextField();

    spinnerTextField.setEditable(false);
    spinnerTextField.setCaret(
      new DefaultCaret() {
        @Override
        public boolean isVisible() {
          return false;
        }

        @Override
        public void setSelectionVisible(boolean visible) {
          // Selection should not be visible
        }

        @Override
        public boolean isSelectionVisible() {
          return false;
        }
      }
    );

    setOpaque(false);
    setBorder(
      new AbstractBorder() {
        @Override
        public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
          Graphics2D graphics2d = (Graphics2D) graphics;

          graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
          graphics2d.setColor(Constants.COLOR_GREEN_LIGHT_WHITE);
          graphics2d.fillRoundRect(x, y, (width - 1), (height - 1), Constants.ROUNDED_BORDER_ARC_SPINNER, Constants.ROUNDED_BORDER_ARC_SPINNER);
        }

        @Override
        public Insets getBorderInsets(Component component) {
          return Constants.INSETS_GENERAL;
        }
      }
    );
    setUI(new BasicSpinnerUI() {
      @Override
      protected Component createPreviousButton() {
        Component previousButton = new CustomArrowButton(SwingConstants.SOUTH);

        previousButton.setName("Spinner.previousButton");

        this.installPreviousButtonListeners(previousButton);

        return previousButton;
      }

      @Override
      protected Component createNextButton() {
        Component nextButton = new CustomArrowButton(SwingConstants.NORTH);

        nextButton.setName("Spinner.nextButton");

        this.installNextButtonListeners(nextButton);

        return nextButton;
      }
    });
  }
}