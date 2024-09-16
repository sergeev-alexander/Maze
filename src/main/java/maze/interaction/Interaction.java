package maze.interaction;

import maze.model.Point;
import maze.service.Service;

import java.util.Scanner;

public class Interaction {

    private final Scanner scanner = new Scanner(System.in);

    private int height;
    private int width;
    private Point start;
    private Point finish;

    public void explore() {
        int[][] maze = printMenu();
        Service service = new Service(height, width, start, finish);
        System.out.println(service.explore(maze));
    }

    private int[][] printMenu() {
        int[][] maze;
        while (true) {
            System.out.println("""
                    Enter the size (height and width) of the maze separated by a space
                    then enter the maze itself line by line!
                    
                    whitespace -> road
                    # -> wall
                    S -> start
                    F -> finish""");
            String line = scanner.nextLine();
            try {
                height = Integer.parseInt(line.split(" ")[0]);
                width = Integer.parseInt(line.split(" ")[1]);
                maze = new int[height][width];
                for (int i = 0; i < height; i++) {
                    int[] arr = new int[width];
                    String[] row = scanner.nextLine().split("");
                    for (int j = 0; j < width; j++) {
                        arr[j] = switch (row[j]) {
                            case " " -> 0;
                            case "#" -> 1;
                            case "S" -> {
                                start = new Point(i, j);
                                yield 0;
                            }
                            case "F" -> {
                                finish = new Point(i, j);
                                yield 0;
                            }
                            default -> throw new IllegalArgumentException();
                        };
                    }
                    maze[i] = arr;
                }
            } catch (Exception ignored) {
                System.out.println("SOMETHING WENT WRONG!\nTRY AGAIN!");
                continue;
            }
            scanner.close();
            return maze;
        }
    }
}
