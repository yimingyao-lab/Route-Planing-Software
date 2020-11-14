
public class SearchPath {	
	Position from;
	Position to;
	private double length;

	public SearchPath(Position from, Position to) {
		super();
		this.from = from;
		this.to = to;
		this.length = Math.sqrt((from.getX() - to.getX()) * (from.getX() - to.getX())
				+ (from.getY() - to.getY()) * (from.getY() - to.getY()));
	}

	public Position getFrom() {
		return from;
	}

	public void setFrom(Position from) {
		this.from = from;
	}

	public Position getTo() {
		return to;
	}

	public void setTo(Position to) {
		this.to = to;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

}
