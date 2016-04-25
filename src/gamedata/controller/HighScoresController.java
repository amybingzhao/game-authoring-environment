package gamedata.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
import gamedata.HighScoresParser;
import gamedata.XMLCreator;
import gameengine.controller.HighScoresKeeper;
import gameplayer.view.HighScoreScreen;
import gui.view.Screen;
import javafx.stage.Stage;

public class HighScoresController implements IHighScoresController {

	private static final String HIGH_SCORES_FILE = "src/resources/highScores.xml";

	private String myGameFile;
	private File myFile;
	private Screen myScreen;

	public HighScoresController(String gameFile, Screen screen) {
		this.myGameFile = gameFile;
		myFile = new File(HIGH_SCORES_FILE);
		this.myScreen = screen;
	}

	public HighScoresController(String gameFile) {
		this.myGameFile = gameFile;
		myFile = new File(HIGH_SCORES_FILE);
	}

	@Override
	public Map<String, Integer> getGameHighScores() {
		if (getAllGameScores().get(myGameFile) == null) {
			return new HashMap<String, Integer>();
		}
		return getAllGameScores().get(myGameFile);
	}

	@Override
	public Map<String, Map<String, Integer>> getAllGameScores() {
		HighScoresParser scoresParser = new HighScoresParser();
		try {
			return scoresParser.getHighScoreInfo(myFile);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			e.printStackTrace();
			this.myScreen.showError(e.getMessage());
			return null;
		}
	}

	@Override
	public void clearHighScores() {
		HighScoresKeeper newKeeper = new HighScoresKeeper(getAllGameScores());
		newKeeper.clearGameScores(myGameFile);
		//HighScoresCreator scoresCreator = new HighScoresCreator();
		XMLCreator scoresCreator = new XMLCreator();
		try {
			scoresCreator.saveGame(newKeeper, myFile);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void saveHighScore(int score, String player) {
		HighScoresKeeper updatedKeeper = new HighScoresKeeper(getAllGameScores());
		updatedKeeper.addScore(myGameFile, player, score);
		//HighScoresCreator scoresCreator = new HighScoresCreator();
		XMLCreator scoresCreator = new XMLCreator();
		try {
			scoresCreator.saveGame(updatedKeeper, myFile);
		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getGameFile() {
		return this.myGameFile;
	}

}