package armameeldoparti.utils.mixers;

import armameeldoparti.models.Player;
import armameeldoparti.models.Team;
import armameeldoparti.models.enums.Error;
import armameeldoparti.models.enums.Position;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Random distribution class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class RandomMixer extends BasicPlayersMixer {

  // ---------- Constructor -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the random distributor.
   */
  public RandomMixer() {
    // Body not needed.
  }

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * Distributes the players randomly without considering anchorages.
   *
   * <p>Half of the players of each players-set are randomly assigned a team number. The rest of the players are assigned to the opposing team number.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed randomly without considering anchorages.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    shuffleTeamNumbers(teams.size());

    for (Position position : Position.values()) {
      List<Player> playersAtPosition = new ArrayList<>(CommonFields.getPlayersSets().get(position));

      Collections.shuffle(playersAtPosition);

      teams.get(randomTeam1)
           .getTeamPlayers()
           .get(position)
           .addAll(playersAtPosition.subList(0, playersAtPosition.size() / teams.size()));
    }

    teams.get(randomTeam1)
         .getTeamPlayers()
         .values()
         .stream()
         .flatMap(List::stream)
         .forEach(player -> player.setTeamNumber(randomTeam1 + 1));

    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(player -> player.getTeamNumber() == Constants.PLAYER_NO_TEAM_ASSIGNED)
                .forEach(player -> {
                  teams.get(randomTeam2)
                       .getTeamPlayers()
                       .get(player.getPosition())
                       .add(player);

                  player.setTeamNumber(randomTeam2 + 1);
                });

    return teams;
  }

  /**
   * Distributes the players randomly considering anchorages.
   *
   * <p>First, the anchored players are grouped in different lists by their anchorage number, and they are distributed randomly. If a set of anchored players cannot be added to one team, it will be added to the
   * other. Then, the players that are not anchored are distributed randomly. They will be added to a team only if the players per position or the players per team limits are not exceeded.
   *
   * <p>At this point, the anchorages are guaranteed to be possible to distribute by {@link armameeldoparti.controllers.AnchoragesController}, though there are cases where the order in which the anchorages are
   * distributed may affect the availability of teams for the following anchorages. To consider this, a boolean variable is used: if there's no room in any team for certain anchorage, then this variable is used to
   * stop the anchorages distribution, shuffle them and start the distribution again.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed randomly considering anchorages.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    boolean successfulDistribution = false;

    List<List<Player>> anchorages = CommonFunctions.getAnchorages();

    while (!successfulDistribution) {
      Collections.shuffle(anchorages);

      for (List<Player> anchorage : anchorages) {
        int availableTeamNumber = getAvailableTeam(teams, team -> anchorageCanBeAdded(team, anchorage));

        if (availableTeamNumber == -1) {
          teams.forEach(Team::clear);

          successfulDistribution = false;

          break;
        }

        for (Player player : anchorage) {
          player.setTeamNumber(availableTeamNumber + 1);

          teams.get(availableTeamNumber)
               .getTeamPlayers()
               .get(player.getPosition())
               .add(player);
        }

        successfulDistribution = true;
      }
    }

    // Remaining (not anchored) players without an assigned team
    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(player -> player.getTeamNumber() == Constants.PLAYER_NO_TEAM_ASSIGNED)
                .forEach(player -> {
                  int availableTeamNumber = getAvailableTeam(teams, team -> playerCanBeAdded(team, player));

                  // If there's no available team at this point, something went wrong
                  if (availableTeamNumber == -1) {
                    CommonFunctions.exitProgram(Error.ERROR_INTERNAL, new IllegalStateException(Constants.MSG_ERROR_NO_AVAILABLE_TEAM));
                  }

                  player.setTeamNumber(availableTeamNumber + 1);

                  teams.get(availableTeamNumber)
                       .getTeamPlayers()
                       .get(player.getPosition())
                       .add(player);
                });

    return teams;
  }
}