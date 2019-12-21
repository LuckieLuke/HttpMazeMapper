public class Maze {

    private char[][] maze;
    private int height;
    private int width;

    public Maze(int x, int y) {
        maze = new char[y][x];
        height = maze.length;
        width = maze[0].length;
    }

    public char getChar(int x, int y) {
        return maze[y][x];
    }

    public void setChar(int x, int y, char c) {
        maze[y][x] = c;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
