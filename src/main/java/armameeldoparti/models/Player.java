package armameeldoparti.models;

import armameeldoparti.models.enums.Position;

/**
 * Players class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 1.0
 */
public class Player {

  // ---------- Private fields -----------------------------------------------------------------------------------------------------------------------

  private boolean isAnchored;

  private int anchorageNumber;
  private int skillPoints;
  private int teamNumber;

  private String name;

  private Position position;

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic player with the received parameters.
   *
   * @param name     Player name.
   * @param position Player position.
   */
  public Player(String name, Position position) {
    setName(name);
    setPosition(position);
    setAnchored(false);
    setTeamNumber(0);
    setAnchorageNumber(0);
    setSkillPoints(0);
  }

  // ---------- Public methods -----------------------------------------------------------------------------------------------------------------------

  /**
   * String representation of a Player object. Format used for error reporting.
   * 
   *  @see armameeldoparti.utils.common.CommonFunctions#generateErrorReport(armameeldoparti.models.enums.Error)
   */
  @Override
  public String toString() {
    return "Position: " + getPosition().toString() + System.lineSeparator()
           + "\t\tName: " + getName() + System.lineSeparator()
           + "\t\tIs anchored: " + isAnchored() + System.lineSeparator()
           + "\t\tAnchorage number: " + getAnchorageNumber() + System.lineSeparator()
           + "\t\tSkill points: " + getSkillPoints() + System.lineSeparator()
           + "\t\tTeam number: " + getTeamNumber() + System.lineSeparator();
  }

  // ---------- Getters ------------------------------------------------------------------------------------------------------------------------------

  public boolean isAnchored() {
    return isAnchored;
  }

  public int getAnchorageNumber() {
    return anchorageNumber;
  }

  public int getSkillPoints() {
    return skillPoints;
  }

  public int getTeamNumber() {
    return teamNumber;
  }

  public String getName() {
    return name;
  }

  public Position getPosition() {
    return position;
  }

  // ---------- Setters ------------------------------------------------------------------------------------------------------------------------------

  public void setAnchored(boolean isAnchored) {
    this.isAnchored = isAnchored;
  }

  public void setAnchorageNumber(int anchorageNumber) {
    this.anchorageNumber = anchorageNumber;
  }

  public void setSkillPoints(int skillPoints) {
    this.skillPoints = skillPoints;
  }

  public void setTeamNumber(int teamNumber) {
    this.teamNumber = teamNumber;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPosition(Position position) {
    this.position = position;
  }
}