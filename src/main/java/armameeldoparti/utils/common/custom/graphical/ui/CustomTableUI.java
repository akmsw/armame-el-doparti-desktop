package armameeldoparti.utils.common.custom.graphical.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTableUI;
import javax.swing.table.DefaultTableCellRenderer;

import armameeldoparti.utils.common.Constants;

/**
 * A custom table UI that fits the overall program aesthetics.
 *
 * @author Bonino, Francisco Ignacio.
 *
 * @version 1.0.0
 *
 * @since 3.0.0
 */
public class CustomTableUI extends BasicTableUI {

  // ---------- Public methods ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /**
   * @param component Component to which to apply the custom UI.
   *
   * @return A new custom table UI.
   */
  public static ComponentUI createUI(JComponent component) {
    JTable table = (JTable) component;

    table.setOpaque(false);
    table.setCellSelectionEnabled(false);
    table.setRowSelectionAllowed(false);
    table.setColumnSelectionAllowed(false);
    table.setEnabled(false);
    table.setGridColor(Constants.COLOR_GREEN_LIGHT);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    table.setDefaultRenderer(
      Object.class,
      new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
          Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

          if (component instanceof JComponent tableCellRenderer) {
            tableCellRenderer.setOpaque(false);
            tableCellRenderer.setBorder(new EmptyBorder(Constants.INSETS_GENERAL));

            if (row == 0) {
              tableCellRenderer.setBackground(Constants.COLOR_GREEN_DARK);
              tableCellRenderer.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) tableCellRenderer).setHorizontalAlignment(SwingConstants.CENTER);

              return tableCellRenderer;
            }

            if (column == 0) {
              tableCellRenderer.setBackground(Constants.COLOR_GREEN_DARK);
              tableCellRenderer.setForeground(Color.WHITE);

              ((DefaultTableCellRenderer) tableCellRenderer).setHorizontalAlignment(SwingConstants.LEFT);

              return tableCellRenderer;
            }

            tableCellRenderer.setBackground(Constants.COLOR_GREEN_LIGHT_WHITE);
            tableCellRenderer.setForeground(Color.BLACK);
          }

          return component;
        }

        @Override
        protected void paintComponent(Graphics graphics) {
          Graphics2D graphics2d = (Graphics2D) graphics.create();

          graphics2d.setRenderingHints(Constants.MAP_RENDERING_HINTS);
          graphics2d.setColor(getBackground());
          graphics2d.fillRoundRect(0, 0, (getWidth() - 1), (getHeight() - 1), Constants.ROUNDED_BORDER_ARC_TABLE_CELLS, Constants.ROUNDED_BORDER_ARC_TABLE_CELLS);

          super.paintComponent(graphics2d);
        }
      }
    );

    return new CustomTableUI();
  }
}