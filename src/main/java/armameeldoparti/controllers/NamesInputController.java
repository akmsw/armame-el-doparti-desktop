package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.enums.Distribution;
import armameeldoparti.models.enums.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.views.NamesInputView;

import java.awt.Component;
import java.awt.event.ItemEvent;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.naming.InvalidNameException;
import javax.naming.LimitExceededException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Names input view controller class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class NamesInputController extends Controller<NamesInputView> {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the names input view controller.
   *
   * @param namesInputView View to control.
   */
  public NamesInputController(NamesInputView namesInputView) {
    super(namesInputView);
    setUpListeners();
    setUpInitialState();
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Resets the combobox to the initial state and gives it the view focus.
   */
  public void resetComboBox() {
    JComboBox<String> comboBox = view.getComboBox();

    comboBox.setSelectedIndex(0);
    comboBox.requestFocusInWindow();
  }

  /**
   * Resets the controlled view to its default values, turns the anchorages flags to false, makes the controlled view invisible and shows the main menu view.
   */
  public void backButtonEvent() {
    resetView();

    CommonFields.setAnchoragesEnabled(false);

    CommonFunctions.getController(ProgramView.MAIN_MENU)
                   .showView();
  }

  /**
   * Asks the user for the players distribution method, makes the controlled view invisible and shows the corresponding following view.
   *
   * @param parentComponent Graphical component where the dialog windows associated with the event should be displayed.
   */
  public void mixButtonEvent(Component parentComponent) {
    hideView();

    CommonFields.setDistribution(view.getRandomRadioButton().isSelected() ? Distribution.MIX_RANDOM : Distribution.MIX_BY_SKILL_POINTS);

    // Distribution with anchorages
    if (CommonFields.isAnchoragesEnabled()) {
      ((AnchoragesController) CommonFunctions.getController(ProgramView.ANCHORAGES)).updateCheckboxesText();

      CommonFunctions.getController(ProgramView.ANCHORAGES).showView();

      return;
    }

    // Random distribution without anchorages
    if (CommonFields.getDistribution() == Distribution.MIX_RANDOM) {
      CommonFunctions.getController(ProgramView.RESULTS).showView();

      return;
    }

    // By skill points distribution without anchorages
    CommonFunctions.getController(ProgramView.SKILL_POINTS).showView();
  }

  /**
   * Applies the user input as the name of the player associated to the text field.
   *
   * @param playerIndex The index of the player which name will be the text field input.
   * @param playersSet  The set of players corresponding to the selected combobox option.
   * @param text        The user input.
   */
  public void textFieldEvent(int playerIndex, List<Player> playersSet, String text) {
    playersSet.get(playerIndex).setName(text);

    updateTextArea();
    validateMixButtonEnable();
  }

  /**
   * Validates the user input given that it cannot be blank, contain only numbers, have more than {@code Constants.MAX_NAME_LEN} characters or be repeated.
   *
   * @param string The string to validate.
   *
   * @throws LimitExceededException   When the input exceeds the maximum number of characters allowed.
   * @throws IllegalArgumentException When the input contains special characters.
   * @throws InvalidNameException     When the input is an already existing name.
   */
  private void validateUserInput(String string) throws IllegalArgumentException, InvalidNameException, LimitExceededException {
    if (string.isBlank()) {
      throw new IllegalArgumentException(Constants.MSG_ERROR_STRING_BLANK);
    }

    if (isNumericString(string.replace("\s", ""))) {
      throw new IllegalArgumentException(Constants.MSG_ERROR_STRING_NUMERIC);
    }

    if (containsSpecialCharacters(string)) {
      throw new IllegalArgumentException(Constants.MSG_ERROR_NAME_INVALID);
    }

    if (string.length() > Constants.MAX_NAME_LEN) {
      throw new LimitExceededException(Constants.MSG_ERROR_NAME_LENGTH);
    }

    if (alreadyExists(string)) {
      throw new InvalidNameException(Constants.MSG_ERROR_NAME_ALREADY_EXISTS);
    }
  }

  /**
   * Updates the shown text field according to the selected combobox option.
   *
   * @param selectedOption Combobox selected option.
   */
  public void comboBoxEvent(String selectedOption) {
    updateTextFields(selectedOption);
  }

  /**
   * Since there can be only one distribution method at a time: if one radio button is selected, the other is unselected automatically. Then, if the conditions are met, the mix button is enabled.
   *
   * @param event Radio button click event.
   */
  public void radioButtonEvent(ItemEvent event) {
    if (event.getStateChange() == ItemEvent.SELECTED) {
      (event.getSource() == view.getRandomRadioButton() ? view.getBySkillPointsRadioButton() : view.getRandomRadioButton()).setSelected(false);
    }

    validateMixButtonEnable();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void resetView() {
    hideView();
    clearPlayersNames();

    view.getAnchoragesCheckbox().setSelected(false);
    view.getComboBox().setSelectedIndex(0);
    view.getComboBox().requestFocusInWindow();
    view.getTextArea().setText(null);
    view.getMixButton().setEnabled(false);
    view.getRandomRadioButton().setSelected(false);
    view.getBySkillPointsRadioButton().setSelected(false);

    updateTextFields(view.getComboBox().getItemAt(0));
  }

  @Override
  protected void setUpInitialState() {
    view.getMixButton()
        .setEnabled(false);
  }

  @Override
  protected void setUpListeners() {
    view.getMixButton().addActionListener(_ -> mixButtonEvent(view));
    view.getBackButton().addActionListener(_ -> backButtonEvent());
    view.getRandomRadioButton().addItemListener(this::radioButtonEvent);
    view.getBySkillPointsRadioButton().addItemListener(this::radioButtonEvent);
    view.getComboBox().addActionListener(event -> comboBoxEvent((String) Objects.requireNonNull(((JComboBox<?>) event.getSource()).getSelectedItem())));
    view.getAnchoragesCheckbox().addActionListener(_ -> CommonFields.setAnchoragesEnabled(!CommonFields.isAnchoragesEnabled()));
    view.getTextFieldsMap()
        .forEach((player, textFieldsSet) ->
          textFieldsSet.forEach(textField ->
            textField.addActionListener(event -> {
                String text = textField.getText().trim();

                try {
                  validateUserInput(text);
                  textFieldEvent(textFieldsSet.indexOf(textField), CommonFields.getPlayersSets().get(player), text.toUpperCase());
                } catch (IllegalArgumentException | LimitExceededException | InvalidNameException exception) {
                  CommonFunctions.showMessageDialog(CommonFunctions.getComponentFromEvent(event), exception.getMessage(), JOptionPane.INFORMATION_MESSAGE);

                  textField.setText(CommonFields.getPlayersSets()
                                                .get(player)
                                                .get(textFieldsSet.indexOf(textField))
                                                .getName());
                }
              }
            )
          )
        );
  }

  /**
   * Makes the controlled view visible.
   *
   * <p>Updates the view state according to the combobox initial state, and makes it visible.
   */
  @Override
  protected void showView() {
    updateTextFields(Objects.requireNonNull(view.getComboBox().getSelectedItem(), Constants.MSG_ERROR_NULL_GUI_RESOURCE).toString());
    centerView();
    resetComboBox();

    view.setVisible(true);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * The mix button is enabled only when every condition needed to distribute the players is met.
   *
   * @see #readyToDistribute()
   */
  private void validateMixButtonEnable() {
    view.getMixButton()
        .setEnabled(readyToDistribute());
  }

  /**
   * Updates the text displayed in the read-only text area.
   *
   * <p>The order in which the players are displayed in this text area corresponds to the order of the Position enum.
   *
   * @see armameeldoparti.models.enums.Position
   */
  private void updateTextArea() {
    view.getTextArea()
        .setText(null);

    List<Player> players = CommonFields.getPlayersSets()
                                       .entrySet()
                                       .stream()
                                       .flatMap(playersSet -> playersSet.getValue()
                                                                        .stream()
                                                                        .filter(player -> !player.getName().equals(Constants.PLAYER_NO_NAME_ASSIGNED)))
                                       .sorted(Comparator.comparing(player -> player.getPosition().ordinal()))
                                       .toList();

    for (int playerIndex = 0; playerIndex < players.size(); playerIndex++) {
      view.getTextArea().append((playerIndex + 1) + " - " + players.get(playerIndex).getName() + (playerIndex < (Constants.PLAYERS_TOTAL - 1) ? System.lineSeparator() : ""));
    }
  }

  /**
   * Toggles the text fields visibility.
   *
   * @param selectedOption Combobox selected option.
   */
  private void updateTextFields(String selectedOption) {
    JPanel leftTopPanel = view.getLeftTopPanel();

    // Removes the text fields from the view's top left panel
    view.getTextFieldsMap()
        .values()
        .stream()
        .flatMap(Collection::stream)
        .filter(textField -> textField.getParent() == leftTopPanel)
        .forEach(leftTopPanel::remove);
    view.getTextFieldsMap()
        .get(CommonFunctions.getCorrespondingPosition(Constants.MAP_POSITIONS, selectedOption.toUpperCase()))
        .forEach(textField -> leftTopPanel.add(textField, Constants.MIG_LAYOUT_GROWX));

    leftTopPanel.revalidate();
    leftTopPanel.repaint();
  }

  /**
   * Clears the players names and text fields.
   */
  private void clearPlayersNames() {
    view.getTextFieldsMap()
        .values()
        .stream()
        .flatMap(List::stream)
        .forEach(textField -> textField.setText(null));

    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .forEach(player -> player.setName(Constants.PLAYER_NO_NAME_ASSIGNED));
  }

  /**
   * Checks if there is already a player with the specified name.
   *
   * @param name Name to validate.
   *
   * @return Whether there is already a player with the specified name.
   */
  private boolean alreadyExists(String name) {
    return CommonFields.getPlayersSets()
                       .values()
                       .stream()
                       .flatMap(Collection::stream)
                       .anyMatch(player -> player.getName().equalsIgnoreCase(name));
  }

  /**
   * Checks if every player has a valid non-empty name assigned and the players distribution method has been chosen.
   *
   * @return Whether every condition needed to distribute the players is met.
   */
  private boolean readyToDistribute() {
    return !alreadyExists(Constants.PLAYER_NO_NAME_ASSIGNED) && distributionMethodHasBeenChosen();
  }

  /**
   * Checks if any players distribution method has been chosen.
   *
   * @return Whether the user has chosen a players distribution method.
   */
  private boolean distributionMethodHasBeenChosen() {
    return view.getRandomRadioButton().isSelected() || view.getBySkillPointsRadioButton().isSelected();
  }

  /**
   * Checks if the given string contains only numbers.
   *
   * @param string The string to validate.
   *
   * @return Whether the given string contains only numbers.
   */
  private boolean isNumericString(String string) {
    return Pattern.matches(Constants.REGEX_NUMERIC_STRING, string);
  }

  /**
   * Checks if the given string contains special characters.
   *
   * @param string The string to validate.
   *
   * @return Whether the given string contains special characters.
   */
  private boolean containsSpecialCharacters(String string) {
    return Pattern.matches(Constants.REGEX_SPECIAL_CHARACTERS, string);
  }
}