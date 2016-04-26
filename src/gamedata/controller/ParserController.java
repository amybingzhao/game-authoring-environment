package gamedata.controller;

import java.io.File;

import gamedata.XMLParser;
import gameengine.controller.Game;
import gui.view.Screen;

public class ParserController implements IParserController {

	private XMLParser myXMLParser;
	private Screen myScreen;

	public ParserController() {
		this.myXMLParser = new XMLParser();

	}

	public ParserController(Screen screen) {
		this.myXMLParser = new XMLParser();
		this.myScreen = screen;
	}

	@Override
	public Game loadForEditing(File file) {
		Game playingGame = loadforPlaying(file);
		if (playingGame == null) {
			return null;
		}
		else {
			File editingFile = new File(playingGame.getInitialGameFile());
			return loadforPlaying(editingFile);
		}
	}

	@Override
	public Game loadforPlaying(File file) {
		Game XMLgame = (Game) this.myXMLParser.load(file);
		if (XMLgame == null) {
			return null;
		}
		else {
			return new Game(XMLgame.getInitialGameFile(), XMLgame.getInfo(), XMLgame.getLevels());
		}
	}

}
