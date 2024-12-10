package main;

import entity.Vector2;

public class Objective {
	
	public enum OBJECTIVE_TYPE {
		MAIN,
		SIDE
	}
	
	private String objective;
	private OBJECTIVE_TYPE type;
	private String identifier;
	private Boolean finished = false;
	public Vector2 position;
	private int mapIndex;
	
	public Objective(String obj, OBJECTIVE_TYPE type, int map, int x, int y, String identifier) {
		this.objective = obj;
		this.type = type;
		this.position = new Vector2(x, y);
		this.setMapIndex(map);
		this.setIdentifier(identifier);
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public OBJECTIVE_TYPE getType() {
		return type;
	}

	public void setType(OBJECTIVE_TYPE type) {
		this.type = type;
	}

	public Boolean getFinished() {
		return finished;
	}

	public void setFinished(Boolean finished) {
		this.finished = finished;
	}

	public int getMapIndex() {
		return mapIndex;
	}

	public void setMapIndex(int mapIndex) {
		this.mapIndex = mapIndex;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
