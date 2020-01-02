public class Mapper {

    private HTTPConnector http;
    private Robot walle;
    private Maze maze;

    private final int LEFT = 1;
    private final int UP = 2;
    private final int RIGHT = -1;
    private final int DOWN = -2;

    private final int CHOSE_ERROR = -5;

    public Mapper(HTTPConnector http) {
        this.http = http;
    }

    public Maze mapFromHttp() {
        maze = new Maze(http.getSize());
        walle = new Robot(http.getStartPosition());

        int direction;
        boolean[] possibilities;

        http.postReset();

        do {
            possibilities = get();
            maze.setPossibilitiesChars(walle.getX(), walle.getY(), possibilities);
            direction = choose();

            if (direction == CHOSE_ERROR)
                break;
            if (maze.getActualSum() == maze.getMazeSum())
                break;
            if (checkIfNotYetKnown(direction)) {
                move(direction);
                if (!walle.isLastOperationPop())
                    walle.push(direction);
            }
        } while (!(walle.isStackEmpty() && walle.isAtStartPosition() && !maze.areAnyCharsAround(walle.getX(), walle.getY(), '#')));

        finishMaze();
        maze.printMaze();
        System.out.println(http.getMoves());

        return maze;
    }

    public boolean[] get() {
        maze.setChar(walle.getX(), walle.getY(), '0');
        return http.getPossibilities();
    }

    public int choose() {
        int result = 0;
        int[] pos = new int[]{walle.getX(), walle.getY()};
        if (maze.areAnyCharsAround(pos[0], pos[1], '#')) {
            walle.setLastOperationPop(false);

            if (maze.getChar(pos[0], pos[1] + 1) == '#')
                result = -2;
            if (maze.getChar(pos[0] + 1, pos[1]) == '#')
                result = -1;
            if (maze.getChar(pos[0], pos[1] - 1) == '#')
                result = 2;
            if (maze.getChar(pos[0] - 1, pos[1]) == '#')
                result = 1;
        } else if (!(maze.areAnyCharsAround(pos[0], pos[1], '#') || maze.areAnyCharsAround(pos[0], pos[1], '0'))) {
            return CHOSE_ERROR;
        } else {
            walle.setLastOperationPop(true);
            result = -walle.pop();
        }
        return result;
    }

    public void move(int direction) {
        switch (direction) {
            case LEFT:
                maze.setChar(walle.getX() - 1, walle.getY(), '0');
                break;
            case UP:
                maze.setChar(walle.getX(), walle.getY() - 1, '0');
                break;
            case RIGHT:
                maze.setChar(walle.getX() + 1, walle.getY(), '0');
                break;
            case DOWN:
                maze.setChar(walle.getX(), walle.getY() + 1, '0');
                break;
        }
        walle.move(direction);
        http.postMove(direction);
    }

    public void finishMaze() {
        fixAllOnes();

        for (int i = 2; i < maze.getHeight() - 1; i += 2) {
            for (int j = 2; j < maze.getWidth() - 1; j += 2) {
                if (buildAWall(j, i))
                    maze.setChar(j, i, '+');
                else
                    maze.setChar(j, i, '0');
            }
        }
    }

    public void fixAllOnes() {
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                if ((i % 2 != 0 || j % 2 != 0) && maze.getChar(j, i) == '1')
                    maze.setChar(j, i, '+');
                if ((i % 2 != 0 || j % 2 != 0) && maze.getChar(j, i) == '#')
                    maze.setChar(j, i, '0');
            }
        }
    }

    public boolean buildAWall(int x, int y) {
        return maze.areAnyCharsAround(x, y, '+');
    }


    public boolean checkIfNotYetKnown(int direction) {
        boolean result = true;
        if (!walle.isLastOperationPop()) {
            switch (direction) {
                case LEFT:
                    result = handleNode(walle.getX() - 2, walle.getY());
                    break;
                case UP:
                    result = handleNode(walle.getX(), walle.getY() - 2);
                    break;
                case RIGHT:
                    result = handleNode(walle.getX() + 2, walle.getY());
                    break;
                case DOWN:
                    result = handleNode(walle.getX(), walle.getY() + 2);
                    break;
            }
        }
        return result;
    }

    private boolean handleNode(int x, int y) {
        boolean result;
        if (maze.areAnyCharsAround(x, y, '1')) {
            result = true;
        } else {
            result = false;
            maze.setZerosAround(x, y);
        }
        return result;
    }
}