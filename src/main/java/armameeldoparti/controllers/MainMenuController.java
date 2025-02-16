package armameeldoparti.controllers;

import armameeldoparti.models.enums.ProgramView;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.MainMenuView;

/**
 * Main menu view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class MainMenuController extends Controller<MainMenuView> {

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the main menu view controller.
   *
   * @param mainMenuView View to control.
   */
  public MainMenuController(MainMenuView mainMenuView) {
    super(mainMenuView);
    setUpListeners();
  }

  // ---------- Public methods -----------------------------------------------------------------------------------------------------------------------

  @Override
  public void showView() {
    super.showView();
  }

  /**
   * Makes the controlled view invisible and shows the help view.
   */
  public void helpButtonEvent() {
    HelpController controller = (HelpController) CommonFunctions.getController(ProgramView.HELP);

    hideView();

    controller.updatePage();
    controller.showView();
  }

  /**
   * Makes the controlled view invisible and shows the names input view.
   */
  public void startButtonEvent() {
    hideView();

    CommonFunctions.getController(ProgramView.NAMES_INPUT)
                   .showView();
  }

  /**
   * Opens the browser on the contact URL.
   */
  public void contactButtonEvent() {
    CommonFunctions.browserRedirect(Constants.URL_CONTACT);
  }

  /**
   * Opens the browser on the issues URL.
   */
  public void issuesButtonEvent() {
    CommonFunctions.browserRedirect(Constants.URL_ISSUES);
  }

  // ---------- Protected methods --------------------------------------------------------------------------------------------------------------------

  @Override
  protected void resetView() {
    // Body not needed in this particular controller
  }

  @Override
  protected void setUpInitialState() {
    // Body not needed in this particular controller
  }

  /**
   * The "java:S1190" and "java:S117" warnings are suppressed since JDK22+ allows the use of unnamed variables.
   */
  @Override
  @SuppressWarnings({"java:S1190", "java:S117"})
  protected void setUpListeners() {
    view.getStartButton()
        .addActionListener(_ -> startButtonEvent());
    view.getHelpButton()
        .addActionListener(_ -> helpButtonEvent());
    view.getContactButton()
        .addActionListener(_ -> contactButtonEvent());
    view.getIssuesButton()
        .addActionListener(_ -> issuesButtonEvent());
  }
}