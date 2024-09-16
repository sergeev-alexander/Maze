package maze.model;

public record Point(int x, int y) {

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }
}
