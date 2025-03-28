package armameeldoparti.views;

import armameeldoparti.models.Player;
import armameeldoparti.models.enums.Position;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import armameeldoparti.utils.common.custom.graphical.CustomLabel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * Skill points input view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class SkillPointsInputView extends View {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private JButton backButton;
  private JButton finishButton;
  private JButton resetSkillPointsButton;

  private transient Map<JSpinner, JLabel> labelsMap;
  private transient Map<Player, JSpinner> spinnersMap;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the skill points input view.
   */
  public SkillPointsInputView() {
    super(Constants.TITLE_VIEW_SKILL_POINTS_INPUT, null);

    setSpinnersMap(new HashMap<>());
    setLabelsMap(new HashMap<>());
    initializeInterface();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void initializeInterface() {
    addSpinners();
    addButtons();
    add(masterPanel);
    pack();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void addButtons() {
    setBackButton(new CustomButton("Atrás"));
    setFinishButton(new CustomButton("Finalizar"));
    setResetSkillPointsButton(new CustomButton("Reiniciar puntuaciones"));

    masterPanel.add(finishButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN));
    masterPanel.add(resetSkillPointsButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN));
    masterPanel.add(backButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN));
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Adds the spinners to their corresponding panel.
   */
  private void addSpinners() {
    for (Position position : Position.values()) {
      masterPanel.add(new CustomLabel(CommonFunctions.capitalize(Constants.MAP_POSITIONS.get(position)), null, SwingConstants.CENTER),
                      CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN));

      List<Player> players = new ArrayList<>(CommonFields.getPlayersSets().get(position));

      players.sort(Comparator.comparing(player -> player.getPosition().ordinal()));

      for (Player player : players) {
        spinnersMap.put(player, new JSpinner(new SpinnerNumberModel(Constants.SKILL_INI, Constants.SKILL_MIN, Constants.SKILL_MAX, Constants.SKILL_STEP)));

        labelsMap.put(spinnersMap.get(player), new JLabel(player.getName()));

        masterPanel.add(labelsMap.get(spinnersMap.get(player)), Constants.MIG_LAYOUT_PUSHX);
        masterPanel.add(spinnersMap.get(player), players.indexOf(player) % 2 != 0 ? Constants.MIG_LAYOUT_WRAP : null);
      }
    }
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getFinishButton() {
    return finishButton;
  }

  public JButton getResetSkillPointsButton() {
    return resetSkillPointsButton;
  }

  public Map<JSpinner, JLabel> getLabelsMap() {
    return labelsMap;
  }

  public Map<Player, JSpinner> getSpinnersMap() {
    return spinnersMap;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setFinishButton(JButton finishButton) {
    this.finishButton = finishButton;
  }

  public void setResetSkillPointsButton(JButton resetSkillPointsButton) {
    this.resetSkillPointsButton = resetSkillPointsButton;
  }

  public void setLabelsMap(Map<JSpinner, JLabel> labelsMap) {
    this.labelsMap = labelsMap;
  }

  public void setSpinnersMap(Map<Player, JSpinner> spinnersMap) {
    this.spinnersMap = spinnersMap;
  }
}