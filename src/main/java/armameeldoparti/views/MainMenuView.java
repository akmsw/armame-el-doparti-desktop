package armameeldoparti.views;

import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomButton;
import armameeldoparti.utils.common.custom.graphical.CustomLabel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import net.miginfocom.layout.CC;

/**
 * Main menu view class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class MainMenuView extends View {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private JButton startButton;
  private JButton helpButton;
  private JButton contactButton;
  private JButton issuesButton;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the main menu view.
   */
  public MainMenuView() {
    super(Constants.TITLE_VIEW_MAIN_MENU, Constants.MIG_LAYOUT_WRAP);

    initializeInterface();
  }

  // ---------- Protected methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  @Override
  protected void initializeInterface() {
    addBackground();
    addButtons();
    add(masterPanel);
    pack();
  }

  @Override
  protected void addButtons() {
    setStartButton(new CustomButton("Comenzar"));
    setHelpButton(new CustomButton("Ayuda"));
    setContactButton(new CustomButton("Contacto"));
    setIssuesButton(new CustomButton("Reportes y sugerencias"));

    masterPanel.add(startButton, Constants.MIG_LAYOUT_GROWX);
    masterPanel.add(helpButton, Constants.MIG_LAYOUT_GROWX);
    masterPanel.add(contactButton, new CC().width("50%").split());
    masterPanel.add(issuesButton, new CC().width("50%"));
  }

  // ---------- Private methods ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Adds the background image and labels to the main menu view.
   */
  private void addBackground() {
    addBackgroundImage();
    addLabel(Constants.PROGRAM_TITLE, null, Constants.MIG_LAYOUT_ALIGN_CENTER, Constants.COLOR_GREEN_LIGHT, Constants.COLOR_GREEN_DARK, Constants.SIZE_FONT_TITLE_LABEL);
    addLabel(Constants.PROGRAM_AUTHOR, null, Constants.MIG_LAYOUT_ALIGN_CENTER, Constants.COLOR_GREEN_LIGHT, Color.WHITE, Constants.SIZE_FONT_AUTHOR_LABEL);
    addLabel(Constants.PROGRAM_VERSION, Constants.TOOLTIP_MSG_PROGRAM_VERSION, Constants.MIG_LAYOUT_ALIGN_RIGHT, Constants.COLOR_GREEN_LIGHT, Constants.COLOR_GREEN_DARK, Constants.SIZE_FONT_VERSION_LABEL);
  }

  /**
   * Adds the background image to the panel.
   */
  private void addBackgroundImage() {
    masterPanel.add(new JLabel(null, Constants.ICON_BACKGROUND, SwingConstants.CENTER), Constants.MIG_LAYOUT_GROWX);
  }

  /**
   * Creates a basic label for the main menu view.
   *
   * @param text            The label text.
   * @param tooltipText     The label tooltip text.
   * @param constraints     The label MiG Layout constraints.
   * @param backgroundColor The color used for the label background.
   * @param foregroundColor The color used for the label foreground.
   * @param fontSize        The font size for the label text.
   */
  private void addLabel(String text, String tooltipText, String constraints, Color backgroundColor, Color foregroundColor, int fontSize) {
    masterPanel.add(new CustomLabel(text.toLowerCase(), tooltipText, backgroundColor, foregroundColor, SwingConstants.CENTER, fontSize), constraints);
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public JButton getStartButton() {
    return startButton;
  }

  public JButton getHelpButton() {
    return helpButton;
  }

  public JButton getContactButton() {
    return contactButton;
  }

  public JButton getIssuesButton() {
    return issuesButton;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public void setStartButton(JButton startButton) {
    this.startButton = startButton;
  }

  public void setHelpButton(JButton helpButton) {
    this.helpButton = helpButton;
  }

  public void setContactButton(JButton contactButton) {
    this.contactButton = contactButton;
  }

  public void setIssuesButton(JButton issuesButton) {
    this.issuesButton = issuesButton;
  }
}