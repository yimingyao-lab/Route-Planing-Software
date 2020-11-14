
public class Position {
	int index = 0;
	private String Name;
	private int X;
	private int Y;

	public Position(String name, int x, int y, int index) {
		super();
		this.index = index;
		this.Name = name;
		this.X = x;
		this.Y = y;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

}
