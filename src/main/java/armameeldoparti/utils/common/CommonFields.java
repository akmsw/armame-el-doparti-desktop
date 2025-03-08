package armameeldoparti.utils.common;

import armameeldoparti.controllers.Controller;
import armameeldoparti.models.Player;
import armameeldoparti.models.enums.Distribution;
import armameeldoparti.models.enums.Position;
import armameeldoparti.models.enums.ProgramView;
import armameeldoparti.views.View;

import java.awt.GraphicsDevice;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.util.Map;

/**
 * Common-use fields class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public final class CommonFields {

  // ---------- Private fields ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  private static boolean anchoragesEnabled;

  private static Distribution distribution;

  private static GraphicsDevice activeMonitor;

  private static RoundRectangle2D tooltipRectangle;

  private static Map<Position, Integer> playerLimitPerPosition;
  private static Map<Position, List<Player>> playersSets;
  private static Map<ProgramView, Controller<? extends View>> controllersMap;

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Empty, private constructor.
   */
  private CommonFields() {
    // Body not needed
  }

  // ---------- Getters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public static boolean isAnchoragesEnabled() {
    return anchoragesEnabled;
  }

  public static Distribution getDistribution() {
    return distribution;
  }

  public static GraphicsDevice getActiveMonitor() {
    return activeMonitor;
  }

  public static RoundRectangle2D getTooltipRectangle() {
    return tooltipRectangle;
  }

  public static Map<Position, Integer> getPlayerLimitPerPosition() {
    return playerLimitPerPosition;
  }

  public static Map<Position, List<Player>> getPlayersSets() {
    return playersSets;
  }

  /**
   * The "java:S1452" warning is suppressed since the Java compiler can't know at runtime the type of the controlled view.
   */
  @SuppressWarnings("java:S1452")
  public static Map<ProgramView, Controller<? extends View>> getControllersMap() {
    return controllersMap;
  }

  // ---------- Setters -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  public static void setAnchoragesEnabled(boolean anchoragesEnabled) {
    CommonFields.anchoragesEnabled = anchoragesEnabled;
  }

  public static void setDistribution(Distribution distribution) {
    CommonFields.distribution = distribution;
  }

  public static void setActiveMonitor(GraphicsDevice activeMonitor) {
    CommonFields.activeMonitor = activeMonitor;
  }

  public static void setTooltipRectangle(RoundRectangle2D tooltipRectangle) {
    CommonFields.tooltipRectangle = tooltipRectangle;
  }

  public static void setPlayerLimitPerPosition(Map<Position, Integer> playerLimitPerPosition) {
    CommonFields.playerLimitPerPosition = playerLimitPerPosition;
  }

  public static void setPlayersSets(Map<Position, List<Player>> playersSets) {
    CommonFields.playersSets = playersSets;
  }

  public static void setControllersMap(Map<ProgramView, Controller<? extends View>> controllerMap) {
    CommonFields.controllersMap = controllerMap;
  }
}