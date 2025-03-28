package armameeldopartidesktop.views;

import javax.swing.JButton;
import javax.swing.JTable;

import armameeldopartidesktop.models.enums.Distribution;
import armameeldopartidesktop.utils.common.CommonFields;
import armameeldopartidesktop.utils.common.CommonFunctions;
import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomButton;

/**
 * Results view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class ResultsView extends View {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private JButton backButton;
  private JButton remixButton;

  private JTable table;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds an empty results view.
   */
  public ResultsView() {
    super(getUpdatedFrameTitle(), Constants.MIG_LAYOUT_WRAP);

    setBackButton(new CustomButton("Atrás"));
    setRemixButton(new CustomButton("Redistribuir"));
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  public void initializeInterface() {
    setTitle(getUpdatedFrameTitle());
    addTable();
    addButtons();
    add(masterPanel);
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void addButtons() {
    if (CommonFields.getDistribution() == Distribution.MIX_RANDOM) {
      masterPanel.add(remixButton, Constants.MIG_LAYOUT_GROWX);
    }

    masterPanel.add(backButton, Constants.MIG_LAYOUT_GROWX);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @return The updated frame title based on the chosen distribution and the anchorages option.
   */
  private static String getUpdatedFrameTitle() {
    return String.join(" - ", (CommonFields.getDistribution() == Distribution.MIX_RANDOM) ? "Aleatorio" : "Por puntuaciones", CommonFields.isAnchoragesEnabled() ? "Con anclajes" : "Sin anclajes");
  }

  /**
   * Adds the results table in the view panel.
   */
  private void addTable() {
    masterPanel.add(table, CommonFunctions.buildMigLayoutConstraints(Constants.MIG_LAYOUT_PUSH, Constants.MIG_LAYOUT_GROW, Constants.MIG_LAYOUT_SPAN, Constants.MIG_LAYOUT_CENTER));
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public JButton getBackButton() {
    return backButton;
  }

  public JButton getRemixButton() {
    return remixButton;
  }

  public JTable getTable() {
    return table;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setRemixButton(JButton remixButton) {
    this.remixButton = remixButton;
  }

  public void setTable(JTable table) {
    this.table = table;
  }
}