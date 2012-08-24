package org.intracube.api.input;

/*
 * More for this coming soon (will be used for sprite control)
 */
public class Key {

	private String sCharacter;
	private Character cCharacter;
	private Direction direction;
	
	public Key(String character, Direction direction){
		this.sCharacter = character;
		this.direction = direction;
	}
	
	public Key(Character character, Direction direction){
		this.cCharacter = character;
		this.direction = direction;
	}
	
	public String getString(){
		return sCharacter;
	}
	
	public Character getChar(){
		return cCharacter;
	}
	
	public Direction getDirection(){
		return direction; 
	}
}
