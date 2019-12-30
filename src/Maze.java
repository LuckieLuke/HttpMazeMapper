public class Maze {

    private final int[] dx = {-1, 0, 1, 0};
    private final int[] dy = {0, -1, 0, 1};

    private char[][] maze;
    private int height;
    private int width;

    public Maze(int x, int y) {
        maze = new char[y][x];
        height = maze.length;
        width = maze[0].length;

        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(i == 0 || j == 0 || i == height-1 || j == width-1)
                    maze[i][j] = '+';
                else
                    maze[i][j] = '1';
            }
        }
    }

    public Maze(int[] size) {
        maze = new char[size[0]][size[1]];
        height = maze.length;
        width = maze[0].length;


        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(i == 0 || j == 0 || i == height-1 || j == width-1)
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

    public int getSum() {
        int sum = 0;
        for(int i = 2; i < height-1; i+=2) {
            for (int j = 2; j < width-1; j+=2) {
                if (maze[i][j] > '0')
                    sum++;
            }
        }
        return sum;
    }

    public void setPossibilitiesChars(int x, int y, boolean[] possibilities) {
        for(int i = 0; i < dx.length; i++) {
            if(maze[y+dy[i]][x+dx[i]] != '1')
                maze[y+dy[i]][x+dx[i]] = possibilities[i] ? '#' : '+';
        }
    }

    public boolean areAnyHashesAround(int x, int y) {
        return maze[y][x-1] == '#' || maze[y][x+1] == '#' || maze[y-1][x] == '#' || maze[y+1][x] == '#';
    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void printMaze() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println();
        }
    }
}
