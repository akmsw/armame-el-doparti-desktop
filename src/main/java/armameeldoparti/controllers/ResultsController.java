package armameeldoparti.controllers;

import armameeldoparti.models.Player;
import armameeldoparti.models.Team;
import armameeldoparti.models.enums.Position;
import armameeldoparti.models.enums.ProgramView;
import armameeldoparti.utils.common.CommonFields;
import armameeldoparti.utils.common.CommonFunctions;
import armameeldoparti.utils.common.Constants;
import armameeldoparti.utils.common.custom.graphical.CustomTable;
import armameeldoparti.utils.mixers.BySkillPointsMixer;
import armameeldoparti.utils.mixers.RandomMixer;
import armameeldoparti.views.ResultsView;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Results view controller.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 0.0.1
 *
 * @since 3.0
 */
public class ResultsController extends Controller<ResultsView> {

  // ---------- Private constants --------------------------------------------------------------------------------------------------------------------

  private static final int TABLE_COLUMNS = 3;

  // ---------- Private fields -----------------------------------------------------------------------------------------------------------------------

  private BySkillPointsMixer bySkillPointsMixer;

  private RandomMixer randomMixer;

  private CustomTable table;

  private Team team1;
  private Team team2;

  private List<Team> teams;

  // ---------- Constructor --------------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the results view controller.
   *
   * @param resultsView View to control.
   */
  public ResultsController(ResultsView resultsView) {
    super(resultsView);

    bySkillPointsMixer = new BySkillPointsMixer();

    randomMixer = new RandomMixer();

    team1 = new Team(1);
    team2 = new Team(2);

    teams = new ArrayList<>();

    setUpListeners();
  }

  // ---------- Public methods -----------------------------------------------------------------------------------------------------------------------

  /**
   * Creates the teams and the results table, applies the needed table format, fills the non-variable table cells and displays the distribution
   * results.
   */
  public void setUp() {
    teams = (CommonFields.getDistribution() == Constants.MIX_RANDOM ? randomMix(Arrays.asList(team1, team2))
                                                                    : bySkillPointsMix(Arrays.asList(team1, team2)));

    view.setTable(new CustomTable(Constants.PLAYERS_PER_TEAM + CommonFields.getDistribution() + 1, TABLE_COLUMNS));
    view.initializeInterface();

    table = (CustomTable) view.getTable();

    overrideTableFormat();
    fillTableFields();
    updateTable();

    table.adjustCells();

    view.pack();
  }

  /**
   * Resets the teams, resets the controlled view to its default values and makes it invisible, and shows the corresponding previous view.
   */
  public void backButtonEvent() {
    resetTeams();
    resetView();

    ProgramView previousView;

    if (CommonFields.getDistribution() == Constants.MIX_RANDOM) {
      previousView = CommonFields.isAnchoragesEnabled() ? ProgramView.ANCHORAGES : ProgramView.NAMES_INPUT;
    } else {
      previousView = ProgramView.SKILL_POINTS;
    }

    CommonFunctions.getController(previousView)
                   .showView();
  }

  /**
   * Resets the teams, redistributes the players with the specified method and updates the results table.
   */
  public void remixButtonEvent() {
    resetTeams();

    teams = randomMix(Arrays.asList(team1, team2));

    updateTable();
  }

  /**
   * Fills the table with the distribution results.
   *
   * <p>The table cells are filled trusting the positions order in the first column (same order as the Position enum).
   *
   * @see armameeldoparti.models.enums.Position
   */
  public void updateTable() {
    var wrapper = new Object() {
      int column = 1;
      int row = 1;
    };

    teams.forEach(
      team -> {
        for (Position position : Position.values()) {
          team.getTeamPlayers()
              .get(position)
              .forEach(player -> table.setValueAt(player.getName(), wrapper.row++, wrapper.column));
        }

        wrapper.column++;
        wrapper.row = 1;
      }
    );

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILL_POINTS) {
      for (int teamIndex = 0; teamIndex < teams.size(); teamIndex++) {
        table.setValueAt(teams.get(teamIndex)
                              .getTeamPlayers()
                              .values()
                              .stream()
                              .flatMap(List::stream)
                              .mapToInt(Player::getSkillPoints)
                              .reduce(0, Math::addExact),
                         table.getRowCount() - 1,
                         teamIndex + 1);
      }
    }
  }

  /**
   * Distributes the players randomly.
   *
   * @param teams Teams to populate randomly.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> randomMix(List<Team> teams) {
    return CommonFields.isAnchoragesEnabled() ? randomMixer.withAnchorages(teams) : randomMixer.withoutAnchorages(teams);
  }

  /**
   * Distributes the players based on their skill points.
   *
   * @param teams Teams to populate by skill points.
   *
   * @return The updated teams with the players distributed.
   */
  public List<Team> bySkillPointsMix(List<Team> teams) {
    return CommonFields.isAnchoragesEnabled() ? bySkillPointsMixer.withAnchorages(teams) : bySkillPointsMixer.withoutAnchorages(teams);
  }

  // ---------- Protected methods --------------------------------------------------------------------------------------------------------------------

  /**
   * Disposes the controlled view and creates a new one to control.
   */
  @Override
  protected void resetView() {
    view.dispose();

    setView(new ResultsView());
    setUpListeners();
  }

  @Override
  protected void setUpInitialState() {
    // Body not needed in this particular controller
  }

  @Override
  protected void setUpListeners() {
    view.getBackButton()
        .addActionListener(_ -> backButtonEvent());
    view.getRemixButton()
        .addActionListener(_ -> remixButtonEvent());
  }

  // ---------- Private methods ----------------------------------------------------------------------------------------------------------------------

  /**
   * Fills the table cells whose texts do not change.
   */
  private void fillTableFields() {
    int rowCount = table.getRowCount() - 1;

    Map<Position, String> positionsMap = CommonFields.getPositionsMap();

    for (int teamIndex = 0; teamIndex < teams.size(); teamIndex++) {
      table.setValueAt("EQUIPO " + (teamIndex + 1), 0, teamIndex + 1);
    }

    for (int row = 1; row < rowCount; row++) {
      table.setValueAt(
        positionsMap.get(
          switch (row) {
            case 1 -> Position.CENTRAL_DEFENDER;
            case 2, 3 -> Position.LATERAL_DEFENDER;
            case 4, 5 -> Position.MIDFIELDER;
            default -> Position.FORWARD;
          }
        ),
        row,
        0
      );
    }

    if (CommonFields.getDistribution() == Constants.MIX_BY_SKILL_POINTS) {
      for (int column = 0; column < teams.size(); column++) {
        table.setValueAt(column == 0 ? positionsMap.get(Position.GOALKEEPER) : "Puntuación del equipo", table.getRowCount() + column - 2, 0);
      }
    } else {
      table.setValueAt(positionsMap.get(Position.GOALKEEPER), table.getRowCount() - 1, 0);
    }
  }

  /**
   * Resets both teams.
   */
  private void resetTeams() {
    teams.forEach(Team::clear);

    CommonFields.getPlayersSets()
                .values()
                .stream()
                .flatMap(List::stream)
                .forEach(player -> player.setTeamNumber(0));
  }

  /**
   * Overrides the table cells format in order to fit the program aesthetics, including text alignment and background and foreground colors.
   *
   * <p>Row 0 and column 0 have dark green background and white foreground. The remaining cells will have black foreground.
   *
   * <p>The background color will be medium green if the cell shows any skill points related information. If the cell contains an anchored player
   * name, its background will be the corresponding from the anchorages colors array. If not, its background will be light green.
   *
   * <p>The cell text will be centered if it shows any skill points related information or a team name. Otherwise, it will be left-aligned.
   */
  private void overrideTableFormat() {
    view.getTable()
        .setDefaultRenderer(
          Object.class,
          new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable myTable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
              JComponent component = (JComponent) super.getTableCellRendererComponent(myTable, value, isSelected, hasFocus, row, column);

              boolean mixBySkill = CommonFields.getDistribution() == Constants.MIX_BY_SKILL_POINTS && row == view.getTable()
                                                                                                                 .getRowCount() - 1;

              component.setOpaque(false);
              component.setBorder(new EmptyBorder(Constants.INSETS_GENERAL));
              
              if (row == 0 && column == 0) {
                component.setBackground(Constants.COLOR_GREEN_MEDIUM);
                
                return component;
              }

              if (row == 0) {
                component.setBackground(Constants.COLOR_GREEN_DARK);
                component.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.CENTER);

                return component;
              }

              if (column == 0) {
                if (mixBySkill) {
                  component.setBackground(Constants.COLOR_GREEN_MEDIUM);
                  component.setForeground(Color.WHITE);

                  ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.CENTER);

                  return component;
                }

                component.setBackground(Constants.COLOR_GREEN_DARK);
                component.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.LEFT);

                return component;
              }

              if (mixBySkill) {
                component.setBackground(Constants.COLOR_GREEN_MEDIUM);
                component.setForeground(Color.WHITE);

                ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.CENTER);

                return component;
              }

              Player playerOnCell = CommonFunctions.retrieveOptional(CommonFields.getPlayersSets()
                                                                                 .values()
                                                                                 .stream()
                                                                                 .flatMap(List::stream)
                                                                                 .filter(player -> player.getName() == value)
                                                                                 .findFirst());

              component.setBackground(playerOnCell.getAnchorageNumber() != 0 ? Constants.COLORS_ANCHORAGES
                                                                                        .get(playerOnCell.getAnchorageNumber() - 1)
                                                                             : Constants.COLOR_GREEN_LIGHT_WHITE);
              component.setForeground(Color.BLACK);

              ((DefaultTableCellRenderer) component).setHorizontalAlignment(SwingConstants.LEFT);

              return component;
            }

            @Override
            protected void paintComponent(Graphics graphics) {
              Graphics2D graphics2d = (Graphics2D) graphics.create();

              graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
              graphics2d.setColor(getBackground());
              graphics2d.fillRoundRect(0,
                                       0,
                                       (getWidth() - 1),
                                       (getHeight() - 1),
                                       Constants.ROUNDED_BORDER_ARC_TABLE_CELLS,
                                       Constants.ROUNDED_BORDER_ARC_TABLE_CELLS);

              super.paintComponent(graphics2d);

              graphics2d.dispose();
            }
          }
        );
  }
}