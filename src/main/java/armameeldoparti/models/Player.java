package armameeldoparti.models;

import armameeldoparti.models.enums.Position;
import armameeldoparti.utils.common.Constants;

/**
 * Player class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 3.0.0
 *
 * @since 1.0.0
 */
public class Player {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private int anchorageNumber;
  private int skillPoints;
  private int teamNumber;

  private String name;

  private Position position;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a basic player with the received parameters.
   *
   * @param name     Player name.
   * @param position Player position.
   */
  public Player(String name, Position position) {
    setName(name);
    setPosition(position);
    setTeamNumber(Constants.PLAYER_NO_TEAM_ASSIGNED);
    setAnchorageNumber(Constants.PLAYER_NO_ANCHORAGE_ASSIGNED);
    setSkillPoints(Constants.PLAYER_NO_SKILL_POINTS_ASSIGNED);
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * String representation of a Player object. Format used for error reporting.
   *
   * @return A string representation of a Player object.
   *
   * @see armameeldoparti.utils.common.CommonFunctions#generateErrorReport(armameeldoparti.models.enums.Error)
   */
  @Override
  public String toString() {
    return "Position: " + getPosition().toString() + System.lineSeparator()
           + "\t\tName: " + getName() + System.lineSeparator()
           + "\t\tAnchorage number: " + getAnchorageNumber() + System.lineSeparator()
           + "\t\tSkill points: " + getSkillPoints() + System.lineSeparator()
           + "\t\tTeam number: " + getTeamNumber() + System.lineSeparator();
  }

  /**
   * @return Whether the player has an anchoraged assigned or not.
   */
  public boolean isAnchored() {
    return anchorageNumber != Constants.PLAYER_NO_ANCHORAGE_ASSIGNED;
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
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

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

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