package armameeldoparti.utils.mixers;

import static java.util.Comparator.comparingInt;

import armameeldoparti.models.Player;
import armameeldoparti.models.Team;
import armameeldoparti.models.enums.Position;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * By-skill-points distribution class.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class BySkillPointsMixer implements PlayersMixer {

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the by-skill-points players distributor.
   */
  public BySkillPointsMixer() {
    // Body not needed
  }

  // ---------- Public methods -----------------------------------------------------------------------------------------------------------------------

  /**
   * Distributes the players by their skill points without considering anchorages.
   *
   * <p>The players of each position are ordered based on their score, from highest to lowest. The teams are then ordered based on the sum of their
   * players scores so far, from lowest to highest.
   *
   * <p>If the number of players to distribute is 2, the team with less skill points is assigned the player with the highest skill points, and the
   * team with more skill points is assigned the player with the lowest skill points.
   *
   * <p>If the number of players to distribute is 4, two subgroups are made with the players at the list ends, from the outside to the inside. These
   * subsets are then ordered based on their skill points, from highest to lowest. The team with less skill points is assigned the set of players with
   * more skill points. The team with more skill points is assigned the set of players with the lowest skill points.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed by their skill points, without considering anchorages.
   */
  @Override
  public List<Team> withoutAnchorages(List<Team> teams) {
    Map<Position, List<Player>> playersMap = CommonFields.getPlayersSets();

    for (Position position : Position.values()) {
      List<Player> playersSet = new ArrayList<>(playersMap.get(position));

      playersSet.sort(comparingInt(Player::getSkillPoints).reversed()); // Players sorted highest to lowest

      teams.sort(comparingInt(Team::getTeamSkill)); // Teams sorted lowest to highest

      if (playersSet.size() == 2) {
        for (int teamIndex = 0; teamIndex < teams.size(); teamIndex++) {
          teams.get(teamIndex)
               .getTeamPlayers()
               .get(position)
               .add(playersSet.get(teamIndex));
        }
      } else {
        distributeSubsets(teams, playersSet, position);
      }
    }

    if (CommonFunctions.getTeamsSkillDifference(teams) != 0) {
      checkPlayerSwaps(teams);
    }

    return teams;
  }

  /**
   * Distributes the players by their skill points considering anchorages.
   *
   * <p>First, the anchored players are grouped in different lists by their anchorage number, and they are distributed as fair as possible starting
   * with the sets with most anchored players in order to avoid inconsistencies.
   *
   * <p>Then, the players that are not anchored are distributed between the teams as fair as possible based on their skill points. They will be added
   * to a team only if the players per position or the players per team limits are not exceeded.
   *
   * @param teams Teams where to distribute the players.
   *
   * @return The updated teams with the players distributed by their skill points, without considering anchorages.
   */
  @Override
  public List<Team> withAnchorages(List<Team> teams) {
    for (List<Player> anchorage : CommonFunctions.getAnchorages()) {
      teams.sort(comparingInt(Team::getTeamSkill));

      for (Player player : anchorage) {
        player.setTeamNumber(teams.get(0)
                                  .getTeamNumber());

        teams.get(0)
             .getTeamPlayers()
             .get(player.getPosition())
             .add(player);
      }
    }

    List<List<Player>> remainingPlayers = new ArrayList<>(CommonFields.getPlayersSets()
                                                                      .values()
                                                                      .stream()
                                                                      .flatMap(List::stream)
                                                                      .filter(player -> player.getTeamNumber() == 0)
                                                                      .collect(Collectors.groupingBy(Player::getPosition))
                                                                      .values());

    remainingPlayers.sort(comparingInt(List::size));

    for (List<Player> players : remainingPlayers) {
      players.sort(comparingInt(Player::getSkillPoints).reversed());

      if (players.size() == 4) {
        teams.sort(comparingInt(Team::getTeamSkill));

        distributeSubsets(teams, players, players.get(0)
                                                 .getPosition());
      } else {
        for (Player player : players) {
          teams.sort(comparingInt(Team::getTeamSkill));

          int teamNumber = 0;

          if (teams.get(teamNumber)
                   .isPositionFull(player.getPosition())
              || teams.get(teamNumber)
                      .getPlayersCount() + 1 > Constants.PLAYERS_PER_TEAM) {
            teamNumber = 1;
          }

          player.setTeamNumber(teamNumber + 1);

          teams.get(teamNumber)
               .getTeamPlayers()
               .get(player.getPosition())
               .add(player);
        }
      }
    }

    return teams;
  }

  // ---------- Private methods ----------------------------------------------------------------------------------------------------------------------

  /**
   * Performs the subsets distribution in sets with 4+ players as explained in {@link #withoutAnchorages(List)}.
   *
   * @param teams      Teams where to distribute the players.
   * @param playersSet Current working players set.
   * @param position   Current working players position.
   */
  private void distributeSubsets(List<Team> teams, List<Player> playersSet, Position position) {
    List<List<Player>> playersSubsets = new ArrayList<>();

    for (int playerIndex = 0; playerIndex < playersSet.size() / 2; playerIndex++) {
      playersSubsets.add(Arrays.asList(playersSet.get(playerIndex), playersSet.get(playersSet.size() - 1 - playerIndex)));
    }

    // Subsets sorted lowest to highest
    playersSubsets.sort(comparingInt(playersSubset -> playersSubset.stream()
                                                                   .mapToInt(Player::getSkillPoints)
                                                                   .reduce(0, Math::addExact)));

    for (Team team : teams) {
      for (Player player : playersSubsets.get(team.getTeamNumber() - 1)) {
        player.setTeamNumber(team.getTeamNumber());
      }

      team.getTeamPlayers()
          .get(position)
          .addAll(playersSubsets.get(team.getTeamNumber() - 1));
    }
  }

  /**
   * Checks if the players can be swapped between the teams to reduce the skill difference.
   *
   * <p>The "java:S3776" warning is suppressed until a refactor reduces the cognitive complexity of this method from 18 to 15 or less.
   *
   * @param teams Teams where to check the players swaps.
   */
  @SuppressWarnings("java:S3776")
  private void checkPlayerSwaps(List<Team> teams) {
    int currentSkillDifference = CommonFunctions.getTeamsSkillDifference(teams);

    for (Position position : Position.values()) {
      List<Player> team1Players = teams.get(0).getTeamPlayers().get(position);
      List<Player> team2Players = teams.get(1).getTeamPlayers().get(position);

      for (Player playerTeam1 : team1Players) {
        int playerTeam1Index = team1Players.indexOf(playerTeam1);

        for (Player playerTeam2 : team2Players) {
          int playerTeam2Index = team2Players.indexOf(playerTeam2);

          team1Players.set(playerTeam1Index, playerTeam2);
          team2Players.set(playerTeam2Index, playerTeam1);

          int newSkillDifference = CommonFunctions.getTeamsSkillDifference(teams);

          if (newSkillDifference == 0) {
            return;
          }

          if (newSkillDifference >= currentSkillDifference) {
            team1Players.set(playerTeam1Index, playerTeam1);
            team2Players.set(playerTeam2Index, playerTeam2);
          }

          if (newSkillDifference < currentSkillDifference) {
            currentSkillDifference = newSkillDifference;
          }
        }
      }
    }
  }
}