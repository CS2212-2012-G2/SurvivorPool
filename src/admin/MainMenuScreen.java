package admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainMenuScreen {
	private JFrame frame;
	private JLabel textBanner; // labels with the panels
	private Font font; // fonts to style the labels
	private JButton buttonSeason, buttonBonus, buttonPlayer, buttonContestant; // buttons
	private JPanel panel;
	private String skin;
	
	public MainMenuScreen(String Tempskin) {
		this.skin = Tempskin;
		
		frame = new JFrame("Main Menu");
		/*
		JLabel contentPane = new JLabel();
		contentPane.setIcon(new javax.swing.ImageIcon("src/UWOSurvivorAdmin/MainMenu.png"));
		contentPane.setLayout( new BorderLayout() );
		frame.setContentPane( contentPane );
		*/
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(5, 115, 5, 115));
		panel.setLayout(new GridLayout(5, 1, 5, 5));
		
		/* banner */
		textBanner = new JLabel("SURVIVOR", SwingConstants.CENTER); // large title banner
		font = new Font("Verdana", Font.BOLD, 40);
		textBanner.setOpaque(false);
		textBanner.setFont(font);
		panel.add(textBanner);

		/* season menu button */
		buttonSeason = new JButton("Season Menu"); // the continue button
		buttonSeason.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				/* push to the next frame */
				new SeasonMenuScreen(skin);
			}
		});
		panel.add(buttonSeason);

		/* bonus menu button */
		buttonBonus = new JButton("Bonus Question Menu"); // the continue button
		buttonBonus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				/* push to the next frame */
				new BonusMenuScreen(skin);
			}
		});
		panel.add(buttonBonus);

		/* player menu button */
		buttonPlayer = new JButton("Player Menu"); // the continue button
		buttonPlayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				/* push to the next frame */
				new PlayerMenuScreen(skin);
			}
		});
		panel.add(buttonPlayer);

		/* Contestant menu button */
		buttonContestant = new JButton("Contestant Menu"); // the continue button
		buttonContestant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				/* push to the next frame */
				new ContestantMenuScreen(skin);
			}
		});
		panel.add(buttonContestant);
		
		/* menu bar */
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenu menuTheme = new JMenu("Theme");
		/* new game menu button */
		JMenuItem menuItem = new JMenuItem("Main Menu");
		menuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new MainMenuScreen(skin);
			}
		});
		/* exit menu button */
		JMenuItem menuItem2 = new JMenuItem("Exit");
		menuItem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		/* theme selection buttons */
		JMenuItem menuItem3 = new JMenuItem("Sunset");
		menuItem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skin = "Sunset";
				updateTheme();
			}
		});

		JMenuItem menuItem4 = new JMenuItem("Underwater");
		menuItem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skin = "Underwater";
				updateTheme();
			}
		});

		JMenuItem menuItem5 = new JMenuItem("Jungle");
		menuItem5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				skin = "Jungle";
				updateTheme();
			}
		});
		
		updateTheme();
		
		/* build menu bar */
		menuFile.add(menuItem);
		menuFile.add(menuItem2);
		menuTheme.add(menuItem3);
		menuTheme.add(menuItem4);
		menuTheme.add(menuItem5);
		menuBar.add(menuFile);
		menuBar.add(menuTheme);
		frame.setJMenuBar(menuBar);
		
		/* build frame */
		frame.setSize(640, 430);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Survivor Admin");
		frame.setLocationRelativeTo(null);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	private void updateTheme(){
		if (skin.equals("Sunset")){
			panel.setBackground(Color.orange);
			textBanner.setForeground(Color.black);
			buttonSeason.setBackground(Color.red);
			buttonSeason.setForeground(Color.black);
			buttonBonus.setBackground(Color.red);
			buttonBonus.setForeground(Color.black);
			buttonPlayer.setBackground(Color.red);
			buttonPlayer.setForeground(Color.black);
			buttonContestant.setBackground(Color.red);
			buttonContestant.setForeground(Color.black);
		} else if (skin.equals("Underwater")){
			panel.setBackground(Color.cyan);
			textBanner.setForeground(Color.white);
			buttonSeason.setBackground(Color.blue);
			buttonSeason.setForeground(Color.white);
			buttonBonus.setBackground(Color.blue);
			buttonBonus.setForeground(Color.white);
			buttonPlayer.setBackground(Color.blue);
			buttonPlayer.setForeground(Color.white);
			buttonContestant.setBackground(Color.blue);
			buttonContestant.setForeground(Color.white);
		} else {
			panel.setBackground(Color.green);
			textBanner.setForeground(Color.blue);
			buttonSeason.setBackground(Color.white);
			buttonSeason.setForeground(Color.blue);
			buttonBonus.setBackground(Color.white);
			buttonBonus.setForeground(Color.blue);
			buttonPlayer.setBackground(Color.white);
			buttonPlayer.setForeground(Color.blue);
			buttonContestant.setBackground(Color.white);
			buttonContestant.setForeground(Color.blue);
		}
	}
	
	// main method
	public static void main(String[] args) {
		new MainMenuScreen("Jungle"); // push the first screen
	}
}
