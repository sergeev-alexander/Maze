package maze.service;

import maze.model.Point;
import maze.model.Node;

import java.util.ArrayDeque;
import java.util.Arrays;

public class Service {

    private static final int[] columnDirections = {0, -1, 1, 0};
    private static final int[] rowDirections = {-1, 0, 0, 1};

    private int ROW;
    private int COLUMN;
    // Stores the moves of the directions of adjacent cells

    private Point start;
    private Point finish;

    // Keeps track of whether finish is reached or not
    private boolean isFinishReached = false;

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
        distances[start.getX()][start.getY()] = 0;

        // Initialize a visited array
        boolean[][] visited = new boolean[ROW][COLUMN];

        // Mark source cell as visited
        visited[start.getX()][start.getY()] = true;

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
            Point point = current.getPoint();

            // If the finish cell is reached, then find the path
            if (point.getX() == finish.getX() && point.getY() == finish.getY()) {
                return restorePath(distances, current);
            }

            // Explore all adjacent directions
            for (int i = 0; i < 4; i++) {
                int row = point.getX() + rowDirections[i];
                int column = point.getY() + columnDirections[i];

                // If the current cell is valid cell and can be traversed
                if (isValid(row, column) && (matrix[row][column] == 0) && !visited[row][column]) {

                    // Mark the adjacent cells as visited
                    visited[row][column] = true;

                    // Enqueue the adjacent cells
                    Node adjacentCell = new Node(new Point(row, column), current.getDistance() + 1);
                    queue.addLast(adjacentCell);

                    // Update the distances of the adjacent cells
                    distances[row][column] = current.getDistance() + 1;
                }
            }
        }
        return "NO SOLUTION!";
    }

    public String restorePath(int[][] distances, Node current) {
        int x = current.getPoint().getX();
        int y = current.getPoint().getY();
        int distance = current.getDistance();
        Point point = current.getPoint();

        // Assign the distances of destination to the distances matrix
        distances[point.getX()][point.getY()] = distance;

        // Stores the shortest path
        StringBuilder path = new StringBuilder();

        // Iterate until start is reached
        while (x != start.getX() || y != start.getY()) {

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
