package gameplayer.view;

import gameengine.controller.Game;
import gameengine.model.AttributeType;
import voogasalad.util.hud.source.*;

public class TLGCSValueFinder implements IValueFinder {

	private Game data; //for other projects, your data will be a different class
	
	@Override
	public Property<?> find(String key) {
		Property<?> ret = null;
		switch (key.toLowerCase()) {
			case "points":
				ret = data.getCurrentLevel().getMainCharacter().getAttribute(AttributeType.POINTS).getProperty();
				break;
			case "health":
				//
				break;
			case "ammo":
				//
				break;
			default:
				ret = new Property<String>("Value Not Found", key);
				break;
		}
		return ret;
	}


	@Override
	public void setDataSource(Object dataSource) throws IllegalArgumentException {
		if (dataSource instanceof Game) {
			this.data = (Game) dataSource;
		} else {
			throw new IllegalArgumentException();
		}
	}

}
