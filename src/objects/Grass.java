package objects;

public class Grass {

	private int x, y, type;

	public Grass(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getType() {
		return this.type;
	}
}