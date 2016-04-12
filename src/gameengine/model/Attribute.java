package gameengine.model;

import com.thoughtworks.xstream.annotations.XStreamOmitField;

import gameengine.model.Actions.Action;
import gameengine.model.Triggers.AttributeType;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Attribute {
	
	private int myValue;
	private int myTriggerValue;
    private AttributeType myType;
    private Action myAction;

    public Attribute(AttributeType type, int initialValue, int triggerValue, Action action) {
    	myValue = initialValue;
    	myTriggerValue = triggerValue;
	    setMyType(type);
        setMyAction(action);
    }

    public void changeAttribute(int change) {
    	myValue += change;
        if(myValue == myTriggerValue){
        	myAction.perform();
        }
    }

	public int getMyValue() {
		return myValue;
	}

	public void setMyValue(int myValue) {
		this.myValue = myValue;
	}

	public int getMyTriggerValue() {
		return myTriggerValue;
	}

	public void setMyTriggerValue(int myTriggerValue) {
		this.myTriggerValue = myTriggerValue;
	}

	public AttributeType getMyType() {
		return myType;
	}

	public void setMyType(AttributeType myType) {
		this.myType = myType;
	}

	public Action getMyAction() {
		return myAction;
	}

	public void setMyAction(Action myAction) {
		this.myAction = myAction;
	}
}
