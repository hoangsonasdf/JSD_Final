package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen extends JFrame {
    public MainScreen() {
        setTitle("Tank 1990");
        setSize(840, 720);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        MainPanel mainPanel = new MainPanel();
        add(mainPanel);

        setVisible(true);
    }

    class MainPanel extends JPanel {
        JLabel onePlayerLabel;
        JLabel twoPlayersLabel;
        JLabel constructionLabel;

        public MainPanel() {
            setLayout(null);  // Use absolute positioning for manual alignment

            // Create "1 PLAYER" label
            onePlayerLabel = new JLabel("1 PLAYER");
            onePlayerLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
            onePlayerLabel.setForeground(Color.YELLOW);
            onePlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            onePlayerLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openGameFrame(1);  // Open GameFrame with 1 player
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    onePlayerLabel.setForeground(Color.WHITE);  // Highlight on hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    onePlayerLabel.setForeground(Color.YELLOW);  // Return to original color
                }
            });

            // Create "2 PLAYERS" label
            twoPlayersLabel = new JLabel("2 PLAYERS");
            twoPlayersLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
            twoPlayersLabel.setForeground(Color.YELLOW);
            twoPlayersLabel.setHorizontalAlignment(SwingConstants.CENTER);
            twoPlayersLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openGameFrame(2);  // Open GameFrame with 2 players
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    twoPlayersLabel.setForeground(Color.WHITE);  // Highlight on hover
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    twoPlayersLabel.setForeground(Color.YELLOW);  // Return to original color
                }
            });

            // Create "CONSTRUCTION" label
            constructionLabel = new JLabel("CONSTRUCTION");
            constructionLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));
            constructionLabel.setForeground(Color.YELLOW);
            constructionLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Add labels to panel
            add(onePlayerLabel);
            add(twoPlayersLabel);
            add(constructionLabel);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.BLACK);

            // Draw the "Tank 1990" title
            g.setColor(Color.RED);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            FontMetrics fm = g.getFontMetrics();
            String title = "TANK 1990";
            int titleX = (getWidth() - fm.stringWidth(title)) / 2;
            int titleY = 100;
            g.drawString(title, titleX, titleY);

            // Calculate positions for options
            int optionYStart = titleY + 60;

            // Set the bounds for each label to be centered
            int labelWidth = 180; // Increase the width for longer text like "CONSTRUCTION"
            onePlayerLabel.setBounds((getWidth() - labelWidth) / 2, optionYStart, labelWidth, 30);
            twoPlayersLabel.setBounds((getWidth() - labelWidth) / 2, optionYStart + 50, labelWidth, 30);
            constructionLabel.setBounds((getWidth() - labelWidth) / 2, optionYStart + 100, labelWidth, 30);

            // Draw score and level
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.PLAIN, 20));
            g.drawString("I-", 30, 30);
            g.drawString("00", 70, 30);
            g.drawString("HI- 20000", getWidth() - 120, 30);
        }

        private void openGameFrame(int players) {
            new GameFrameLevel1(players);  // Open GameFrame with specified number of players
            MainScreen.this.dispose();  // Close the main screen
        }
    }

}
