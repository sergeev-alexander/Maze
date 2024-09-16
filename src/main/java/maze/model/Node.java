package maze.model;

public class Node {

    private Point point;
    private int distance;

    public Node(Point point, int distance) {
        this.point = point;
        this.distance = distance;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Node(" + point + ", distance : " + distance + ")";
    }
}
