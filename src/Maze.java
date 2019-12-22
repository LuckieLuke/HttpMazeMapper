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

    public int getSum() {
        int sum = 0;
        for(int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (maze[i][j] > '0')
                    sum++;
            }
        }
        return sum;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
