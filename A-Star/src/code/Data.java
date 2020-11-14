package code;


public class Data {
    public Point point;
	public double g;
	public double h;
    public Data parent;

    public Data(Point p, double g, double h, Data parent) {
        this.point = p;
        this.g = g;
        this.h = h;
        this.parent = parent;
    }

    double f() {
        return g + h;
    }
}
