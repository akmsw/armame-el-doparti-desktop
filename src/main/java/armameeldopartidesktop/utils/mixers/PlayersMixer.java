package armameeldopartidesktop.utils.mixers;

import java.util.List;

import armameeldopartidesktop.models.Team;

/**
 * Interface that specifies the players distribution methods.
 *
 * @since 3.0.0
 *
 * @version 1.0.0
 *
 * @author Bonino, Francisco Ignacio.
 */
public interface PlayersMixer {

  // ---------- Abstract public methods -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Distributes the players without considering anchorages.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed without considering anchorages.
   */
  List<Team> withoutAnchorages(List<Team> teams);

  /**
   * Distributes the players considering anchorages.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed considering anchorages.
   */
  List<Team> withAnchorages(List<Team> teams);
}