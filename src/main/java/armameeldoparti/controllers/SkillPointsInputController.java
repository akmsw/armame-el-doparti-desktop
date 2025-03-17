package armameeldoparti.controllers;

import armameeldoparti.models.enums.Position;
import armameeldoparti.models.enums.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.SkillPointsInputView;

/**
 * Skill points input view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class SkillPointsInputController extends Controller<SkillPointsInputView> {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the skill points input view controller.
   *
   * @param skillPointsInputView View to control.
   */
  public SkillPointsInputController(SkillPointsInputView skillPointsInputView) {
    super(skillPointsInputView);
    setUpListeners();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the entered skill points for each player, makes the controlled view invisible and shows the results view.
   */
  public void finishButtonEvent() {
    hideView();

    view.getSpinnersMap().forEach((player, spinner) -> player.setSkillPoints((int) spinner.getValue()));

    CommonFunctions.getController(ProgramView.RESULTS).showView();
  }

  /**
   * Sets 0 skill points to every player and resets every spinner value to the minimum assignable skill point.
   */
  public void resetSkillPointsButtonEvent() {
    resetSkillPoints();
  }

  /**
   * Resets the controlled view to its default values, makes it invisible and shows the corresponding next view.
   */
  public void backButtonEvent() {
    resetView();
    hideView();

    CommonFunctions.getController(CommonFields.isAnchoragesEnabled() ? ProgramView.ANCHORAGES : ProgramView.NAMES_INPUT).showView();
  }

  /**
   * Updates the players name labels.
   */
  public void updateNameLabels() {
    for (Position position : Position.values()) {
      CommonFields.getPlayersSets()
                  .get(position)
                  .forEach(player -> view.getLabelsMap()
                                         .get(view.getSpinnersMap().get(player))
                                         .setText(player.getName()));
    }

    view.pack();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void showView() {
    centerView();
    updateNameLabels();

    view.setVisible(true);
  }

  @Override
  protected void resetView() {
    resetSkillPoints();
  }

  @Override
  protected void setUpInitialState() {
    // Body not needed in this particular controller
  }

  @Override
  protected void setUpListeners() {
    view.getBackButton().addActionListener(_ -> backButtonEvent());
    view.getFinishButton().addActionListener(_ -> finishButtonEvent());
    view.getResetSkillPointsButton().addActionListener(_ -> resetSkillPointsButtonEvent());
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Sets 0 skill points to every player and resets every spinner value to the minimum skill point.
   */
  private void resetSkillPoints() {
    view.getSpinnersMap()
        .forEach((player, spinner) -> {
          player.setSkillPoints(Constants.PLAYER_NO_SKILL_POINTS_ASSIGNED);
          spinner.setValue(Constants.SKILL_MIN);
        });
  }
}