package armameeldopartidesktop.views;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import armameeldopartidesktop.utils.common.Constants;
import armameeldopartidesktop.utils.common.custom.graphical.CustomButton;
import armameeldopartidesktop.utils.common.custom.graphical.CustomLabel;
import armameeldopartidesktop.utils.common.custom.graphical.CustomScrollPane;
import net.miginfocom.layout.CC;

/**
 * Help view class.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public class HelpView extends View {

  // ---------- Private constants -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static final int TEXT_AREA_ROWS = 20;
  private static final int TEXT_AREA_COLUMNS = 30;

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private JButton previousPageButton;
  private JButton nextPageButton;
  private JButton backButton;

  private JLabel pageTitleLabel;
  private JLabel readingProgressLabel;

  private JScrollPane scrollPane;

  private JTextArea textArea;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the help view.
   */
  public HelpView() {
    super(Constants.TITLE_VIEW_HELP, Constants.MIG_LAYOUT_WRAP);

    initializeInterface();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void initializeInterface() {
    addPageTitleLabel();
    addTextArea();
    addReadingProgressLabel();
    addButtons();
    add(masterPanel);
    pack();
  }

  @Override
  protected void addButtons() {
    setPreviousPageButton(new CustomButton("Anterior"));
    setNextPageButton(new CustomButton("Siguiente"));
    setBackButton(new CustomButton("Volver al menú principal"));

    masterPanel.add(previousPageButton, new CC().width("50%").split());
    masterPanel.add(nextPageButton, new CC().width("50%").wrap());
    masterPanel.add(backButton, Constants.MIG_LAYOUT_GROWX);
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Adds the text field where to display the current page title.
   */
  private void addPageTitleLabel() {
    setPageTitleLabel(new CustomLabel(null, null, SwingConstants.CENTER));

    masterPanel.add(pageTitleLabel, Constants.MIG_LAYOUT_GROWX);
  }

  /**
   * Adds the text area where to display the instructions of the program.
   */
  private void addTextArea() {
    setTextArea(new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS));
    setScrollPane(new CustomScrollPane(textArea));

    masterPanel.add(scrollPane);
  }

  /**
   * Adds the reading progress label.
   */
  private void addReadingProgressLabel() {
    setReadingProgressLabel(new CustomLabel(null, null, SwingConstants.CENTER));

    masterPanel.add(readingProgressLabel, Constants.MIG_LAYOUT_GROWX);
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public JButton getPreviousPageButton() {
    return previousPageButton;
  }

  public JButton getNextPageButton() {
    return nextPageButton;
  }

  public JButton getBackButton() {
    return backButton;
  }

  public JLabel getPageTitleLabel() {
    return pageTitleLabel;
  }

  public JLabel getReadingProgressLabel() {
    return readingProgressLabel;
  }

  public JScrollPane getScrollPane() {
    return scrollPane;
  }

  public JTextArea getTextArea() {
    return textArea;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setPreviousPageButton(JButton previousPageButton) {
    this.previousPageButton = previousPageButton;
  }

  public void setNextPageButton(JButton nextPageButton) {
    this.nextPageButton = nextPageButton;
  }

  public void setBackButton(JButton backButton) {
    this.backButton = backButton;
  }

  public void setPageTitleLabel(JLabel pageTitleTextField) {
    this.pageTitleLabel = pageTitleTextField;
  }

  public void setReadingProgressLabel(JLabel pagesCounter) {
    this.readingProgressLabel = pagesCounter;
  }

  public void setScrollPane(JScrollPane scrollPane) {
    this.scrollPane = scrollPane;
  }

  public void setTextArea(JTextArea textArea) {
    this.textArea = textArea;
  }
}