package armameeldopartidesktop.controllers;

import java.awt.Rectangle;

import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.views.View;

/**
 * Abstract class that specifies the basic methods for interaction between controllers and their assigned views.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public abstract class Controller<T extends View> {

  // ---------- Protected fields --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  protected T view;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the view controller.
   *
   * @param view View to control.
   */
  protected Controller(T view) {
    setView(view);
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Centers the controlled view on the current active monitor.
   */
  protected final void centerView() {
    Rectangle activeMonitorBounds = CommonFields.getActiveMonitor()
                                                .getDefaultConfiguration()
                                                .getBounds();

    view.setLocation((((activeMonitorBounds.width - view.getWidth()) / 2) + activeMonitorBounds.x), (((activeMonitorBounds.height - view.getHeight()) / 2) + activeMonitorBounds.y));
  }

  /**
   * Makes the controlled view invisible.
   */
  protected final void hideView() {
    view.setVisible(false);

    CommonFunctions.updateActiveMonitorFromView(view);
  }

  /**
   * Makes the controlled view visible.
   */
  protected void showView() {
    centerView();

    view.setVisible(true);
  }

  // ---------- Abstract protected methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Resets the controlled view to its default values.
   */
  protected abstract void resetView();

  /**
   * Sets up the controller and GUI components initial state, if needed.
   */
  protected abstract void setUpInitialState();

  /**
   * Sets up the GUI components event listeners.
   */
  protected abstract void setUpListeners();

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  protected T getView() {
    return view;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  protected void setView(T view) {
    this.view = view;
  }
}