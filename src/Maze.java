public class Maze {

    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, -1, 0, 1};

    private char[][] maze;
    private int height;
    private int width;

    public int getMazeSum() {
        return mazeSum;
    }

    private int mazeSum;

    public Maze(int[] size) {
        maze = new char[size[1]][size[0]];
        height = maze.length;
        width = maze[0].length;

        mazeSum = (height / 2 - 1) * (width / 2 - 1);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || j == 0 || i == height - 1 || j == width - 1)
                    maze[i][j] = '+';
                else
                    maze[i][j] = '1';
            }
        }
    }

    public char getChar(int x, int y) {
        return maze[y][x];
    }

    public void setChar(int x, int y, char c) {
        maze[y][x] = c;
    }

    public int getActualSum() {
        int sum = 0;
        for (int i = 1; i < height - 1; i++) {
            for (int j = 1; j < width - 1; j++) {
                if (maze[i][j] > '0')
                    sum++;
            }
        }
        return sum;
    }

    public void setPossibilitiesChars(int x, int y, boolean[] possibilities) {
        for (int i = 0; i < dx.length; i++) {
            if (maze[y + dy[i]][x + dx[i]] == '1') {
                maze[y + dy[i]][x + dx[i]] = possibilities[i] ? '#' : '+';
                if (maze[y + dy[i]][x + dx[i]] == '#')
                    maze[y + 2 * dy[i]][x + 2 * dx[i]] = '#';
            }
        }
    }

    public void setZerosAround(int x, int y) {
        for (int i = 0; i < dx.length; i++) {
            if (maze[y + dy[i]][x + dx[i]] == '#') {
                setChar(x + dx[i], y + dy[i], '0');
            }
        }
    }

    public boolean areAnyCharsAround(int x, int y, char c) {
        return maze[y][x - 1] == c || maze[y][x + 1] == c || maze[y - 1][x] == c || maze[y + 1][x] == c;
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void printMaze() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }

    public void printMaze(Robot walle) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j == walle.getPosition()[0] && i == walle.getPosition()[1]) {
                    System.out.print("\u001B[34m" + "■" + "\u001B[0m");
                    ;
                } else {
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
    }
}
