package maze.model;

public record Node(Point point, int distance) {

    @Override
    public String toString() {
        return "Node(" + point + ", distance : " + distance + ")";
    }
}
