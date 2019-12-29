public class Maze {

    private char[][] maze;
    private int height;
    private int width;

    public Maze(int x, int y) {
        maze = new char[y][x];
        height = maze.length;
        width = maze[0].length;
    }

    public Maze(int[] size) {
        maze = new char[size[0]][size[1]];
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

    public void setPossibilitiesChars(int x, int y, boolean[] possibilities) {
        for(int i = 0; i < 4; i++){
            switch (i){
                case 0:
                    maze[x-1][y] = possibilities[0] ? '#' : '+';
                    break;
                case 1:
                    maze[x][y-1] = possibilities[1] ? '#' : '+';
                    break;
                case 2:
                    maze[x+1][y] = possibilities[2] ? '#' : '+';
                    break;
                case 3:
                    maze[x][y+1] = possibilities[3] ? '#' : '+';
                    break;
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
