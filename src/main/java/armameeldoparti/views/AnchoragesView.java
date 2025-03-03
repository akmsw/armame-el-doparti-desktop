package armameeldoparti.views;

import armameeldoparti.models.Player;
import armameeldoparti.models.enums.Position;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import armameeldoparti.utils.common.custom.graphical.CustomCheckBox;
import armameeldoparti.utils.common.custom.graphical.CustomLabel;
import armameeldoparti.utils.common.custom.graphical.CustomScrollPane;
import armameeldoparti.utils.common.custom.graphical.CustomTextArea;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

/**
 * Anchorages view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class AnchoragesView extends View {

  // ---------- Private constants --------------------------------------------------------------------------------------------------------------------

  private static final int TEXT_AREA_ROWS = 10;
  private static final int TEXT_AREA_COLUMNS = 9;

  // ---------- Private fields -----------------------------------------------------------------------------------------------------------------------

  private JButton backButton;
  private JButton clearAnchoragesButton;
  private JButton deleteAnchorageButton;
  private JButton deleteLastAnchorageButton;
  private JButton finishButton;
  private JButton newAnchorageButton;

  private JPanel leftPanel;
  private JPanel rightPanel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  private List<JButton> anchorageButtons;

  /**
   * Map that associates each checkboxes list with its corresponding position.
   */
  private Map<Position, List<JCheckBox>> checkboxesMap;

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the anchorages view.
   */
  public AnchoragesView() {
    super("Anclaje de jugadores", Constants.MIG_LAYOUT_WRAP_2);

    setLeftPanel(new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP_2)));
    setRightPanel(new JPanel(new MigLayout(Constants.MIG_LAYOUT_WRAP)));
    setTextArea(new CustomTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS));
    setScrollPane(new CustomScrollPane(textArea));
    setAnchorageButtons(new ArrayList<>());
    setCheckboxesMap(new EnumMap<>(Position.class));

    for (Position position : Position.values()) {
      checkboxesMap.put(position, new ArrayList<>());
    }

    initializeInterface();
  }

  // ---------- Protected methods --------------------------------------------------------------------------------------------------------------------

  @Override
  protected void initializeInterface() {
    masterPanel.add(leftPanel, Constants.MIG_LAYOUT_WEST);
    masterPanel.add(rightPanel, Constants.MIG_LAYOUT_EAST);

    addCheckBoxes();
    addTextArea();
    addButtons();
    add(masterPanel);
    pack();
  }

  @Override
  protected void addButtons() {
    setBackButton(new CustomButton("Atrás", Constants.ROUNDED_BORDER_ARC_GENERAL));
    setClearAnchoragesButton(new CustomButton("Limpiar anclajes", Constants.ROUNDED_BORDER_ARC_GENERAL));
    setDeleteAnchorageButton(new CustomButton("Borrar un anclaje", Constants.ROUNDED_BORDER_ARC_GENERAL));
    setDeleteLastAnchorageButton(new CustomButton("Borrar último anclaje", Constants.ROUNDED_BORDER_ARC_GENERAL));
    setFinishButton(new CustomButton("Finalizar", Constants.ROUNDED_BORDER_ARC_GENERAL));
    setNewAnchorageButton(new CustomButton("Anclar", Constants.ROUNDED_BORDER_ARC_GENERAL));

    anchorageButtons.add(finishButton);
    anchorageButtons.add(newAnchorageButton);
    anchorageButtons.add(deleteAnchorageButton);
    anchorageButtons.add(deleteLastAnchorageButton);
    anchorageButtons.add(clearAnchoragesButton);

    leftPanel.add(finishButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROWX, Constants.MIG_LAYOUT_SPAN));
    leftPanel.add(backButton, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROWX, Constants.MIG_LAYOUT_SPAN));

    rightPanel.add(newAnchorageButton, Constants.MIG_LAYOUT_GROW);
    rightPanel.add(deleteAnchorageButton, Constants.MIG_LAYOUT_GROW);
    rightPanel.add(deleteLastAnchorageButton, Constants.MIG_LAYOUT_GROW);
    rightPanel.add(clearAnchoragesButton, Constants.MIG_LAYOUT_GROW);
  }

  // ---------- Private methods ----------------------------------------------------------------------------------------------------------------------

  /**
   * Adds the players checkboxes and their position labels.
   */
  private void addCheckBoxes() {
    CommonFields.getPlayersSets()
                .forEach((position, playersSet) -> {
                  fillCheckboxesSet(playersSet, checkboxesMap.get(position));
                  addCheckboxesSet(checkboxesMap.get(position), CommonFunctions.capitalize(CommonFields.getPositionsMap().get(position)));
                });
  }

  /**
   * Adds the text area where to display the anchorages.
   */
  private void addTextArea() {
    rightPanel.add(scrollPane, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_SPAN2, Constants.MIG_LAYOUT_PUSH, Constants.MIG_LAYOUT_GROW));
  }

  /**
   * Fills the checkboxes sets.
   *
   * @param playersSet Players sets from where to obtain the names.
   * @param cbSet      Check boxes set to fill.
   */
  private void fillCheckboxesSet(List<Player> playersSet, List<JCheckBox> cbSet) {
    playersSet.forEach(player -> cbSet.add(new CustomCheckBox(player.getName())));
  }

  /**
   * Adds the checkboxes to the view with a label that specifies the corresponding position.
   *
   * @param cbSet     Check boxes to add.
   * @param labelText Label text.
   */
  private void addCheckboxesSet(List<JCheckBox> cbSet, String labelText) {
    leftPanel.add(new CustomLabel(labelText, null, SwingConstants.CENTER), CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_GROWX, Constants.MIG_LAYOUT_SPAN));

    cbSet.forEach(checkbox -> leftPanel.add(checkbox, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_ALIGN_LEFT, Constants.MIG_LAYOUT_PUSHX)));
  }

  // ---------- Getters ------------------------------------------------------------------------------------------------------------------------------

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getClearAnchoragesButton() {
    return clearAnchoragesButton;
  }

  public JButton getDeleteAnchorageButton() {
    return deleteAnchorageButton;
  }

  public JButton getDeleteLastAnchorageButton() {
    return deleteLastAnchorageButton;
  }

  public JButton getFinishButton() {
    return finishButton;
  }

  public JButton getNewAnchorageButton() {
    return newAnchorageButton;
  }

  public JPanel getLeftPanel() {
    return leftPanel;
  }

  public JPanel getRightPanel() {
    return rightPanel;
  }

  public JScrollPane getScrollPane() {
    return scrollPane;
  }

  public JTextArea getTextArea() {
    return textArea;
  }

  public List<JButton> getAnchorageButtons() {
    return anchorageButtons;
  }

  public Map<Position, List<JCheckBox>> getCheckboxesMap() {
    return checkboxesMap;
  }

  // ---------- Setters ------------------------------------------------------------------------------------------------------------------------------

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setClearAnchoragesButton(JButton clearAnchoragesButton) {
    this.clearAnchoragesButton = clearAnchoragesButton;
  }

  public void setDeleteAnchorageButton(JButton deleteAnchorageButton) {
    this.deleteAnchorageButton = deleteAnchorageButton;
  }

  public void setDeleteLastAnchorageButton(JButton deleteLastAnchorageButton) {
    this.deleteLastAnchorageButton = deleteLastAnchorageButton;
  }

  public void setFinishButton(JButton finishButton) {
    this.finishButton = finishButton;
  }

  public void setNewAnchorageButton(JButton newAnchorageButton) {
    this.newAnchorageButton = newAnchorageButton;
  }

  public void setLeftPanel(JPanel leftPanel) {
    this.leftPanel = leftPanel;
  }

  public void setRightPanel(JPanel rightPanel) {
    this.rightPanel = rightPanel;
  }

  public void setScrollPane(JScrollPane scrollPane) {
    this.scrollPane = scrollPane;
  }

  public void setTextArea(JTextArea textArea) {
    this.textArea = textArea;
  }

  public void setAnchorageButtons(List<JButton> anchorageButtons) {
    this.anchorageButtons = anchorageButtons;
  }

  public void setCheckboxesMap(Map<Position, List<JCheckBox>> checkboxesMap) {
    this.checkboxesMap = checkboxesMap;
  }
}