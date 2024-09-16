package maze.service;

import maze.model.Node;
import maze.model.Point;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Service {

    // Stores the moves of the directions of adjacent cells
    private static final int[] columnDirections = {0, -1, 1, 0};
    private static final int[] rowDirections = {-1, 0, 0, 1};

    private final int ROW;
    private final int COLUMN;

    private final Point start;
    private final Point finish;

    public Service(int ROW, int COLUMN, Point start, Point finish) {
        this.ROW = ROW;
        this.COLUMN = COLUMN;
        this.start = start;
        this.finish = finish;
    }

    // Check if the given cell is valid or not
    private boolean isValid(int row, int column) {
        return (row >= 0) && (column >= 0) && (row < ROW) && (column < COLUMN);
    }

    // Function to find the shortest path from the start to finish in the given matrix
    public String explore(int[][] matrix) {

        // Stores the distances for each cell from the start cell
        int[][] distances = new int[ROW][COLUMN];
        // Fill the distances arrays with -1
        for (int[] dd : distances)
            Arrays.fill(dd, -1);

        // Distance of source cell is 0
        distances[start.x()][start.y()] = 0;

        // Initialize a visited array
        boolean[][] visited = new boolean[ROW][COLUMN];

        // Mark source cell as visited
        visited[start.x()][start.y()] = true;

        // Create a queue for BFS
        ArrayDeque<Node> queue = new ArrayDeque<>();

        // Distance of source cell is 0
        Node startNode = new Node(start, 0);

        // Enqueue source cell
        queue.addLast(startNode);

        // Iterate until queue is not empty
        while (!queue.isEmpty()) {

            // Deque front of the queue
            Node current = queue.removeFirst();
            Point point = current.point();

            // If the finish cell is reached, then find the path
            if (point.x() == finish.x() && point.y() == finish.y()) {
                return restorePath(distances, current);
            }

            // Explore all adjacent directions
            for (int i = 0; i < 4; i++) {
                int row = point.x() + rowDirections[i];
                int column = point.y() + columnDirections[i];

                // If the current cell is valid cell and can be traversed
                if (isValid(row, column) && (matrix[row][column] == 0) && !visited[row][column]) {

                    // Mark the adjacent cells as visited
                    visited[row][column] = true;

                    // Enqueue the adjacent cells
                    Node adjacentCell = new Node(new Point(row, column), current.distance() + 1);
                    queue.addLast(adjacentCell);

                    // Update the distances of the adjacent cells
                    distances[row][column] = current.distance() + 1;
                }
            }
        }
        return "NO SOLUTION!";
    }

    public String restorePath(int[][] distances, Node current) {
        int x = current.point().x();
        int y = current.point().y();
        int distance = current.distance();
        Point point = current.point();

        // Assign the distances of destination to the distances matrix
        distances[point.x()][point.y()] = distance;

        // Stores the shortest path
        StringBuilder path = new StringBuilder();

        // Iterate until start is reached
        while (x != start.x() || y != start.y()) {

            // Append D
            if (x > 0 && distances[x - 1][y] == distance - 1) {
                path.append('D');
                x--;
            }

            // Append U
            if (x < ROW - 1 && distances[x + 1][y] == distance - 1) {
                path.append('U');
                x++;
            }

            // Append R
            if (y > 0 && distances[x][y - 1] == distance - 1) {
                path.append('R');
                y--;
            }

            // Append L
            if (y < COLUMN - 1 && distances[x][y + 1] == distance - 1) {
                path.append('L');
                y++;
            }
            distance--;
        }

        // Print reverse the backtracked path
        for (int i = path.length() - 1; i >= 0; --i)
            System.out.print(path.charAt(i));

        return path.reverse().toString();
    }

}
