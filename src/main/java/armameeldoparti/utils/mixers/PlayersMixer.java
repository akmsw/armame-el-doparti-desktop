package armameeldoparti.utils.mixers;

import armameeldoparti.models.Team;

import java.util.List;

/**
 * Interface that specifies the players distribution methods.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
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