import Exceptions.BadRequestException;
import Exceptions.ForbiddenMoveException;
import Exceptions.NotFoundException;

public class Mapper {

    private HTTPConnector http;
    private Robot walle;
    private Maze maze;

    private final int LEFT = 1;
    private final int UP = 2;
    private final int RIGHT = -1;
    private final int DOWN = -2;

    private final int CHOOSE_ERROR = -5;

    public Mapper(HTTPConnector http) {
        this.http = http;
    }

    public Maze mapFromHttp() {
        try {
            maze = new Maze(http.getSize());
            walle = new Robot(http.getStartPosition());
        } catch (NotFoundException e) {
            System.err.println("NotFoundException caught!");
            System.err.println(e.getMessage());
        } catch (BadRequestException e) {
            System.err.println("BadRequestException caught!");
            System.err.println(e.getMessage());
        }

        int direction;
        boolean[] possibilities;

        //to be hidden when executed without reset command
        http.postReset();

        do {

            possibilities = get();
            maze.setPossibilitiesChars(walle.getPosition()[0], walle.getPosition()[1], possibilities);
            direction = choose();

            if (direction == CHOOSE_ERROR)
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
        try {
            System.out.println(http.getMoves());
        } catch (NotFoundException e) {
            System.err.println("NotFoundException caught!");
            System.err.println(e.getMessage());
        } catch (BadRequestException e) {
            System.err.println("BadRequestException caught!");
            System.err.println(e.getMessage());
        }

        return maze;
    }

    public boolean[] get() {
        maze.setChar(walle.getX(), walle.getY(), '0');
        try {
            return http.getPossibilities();
        } catch (BadRequestException e) {
            System.err.println("BadRequestException caught!");
            System.err.println(e.getMessage());
        } catch (NotFoundException e) {
            System.err.println("NotFoundException caught!");
            System.err.println(e.getMessage());
        }
        return new boolean[]{false, false, false, false};
    }

    public int choose() {
        int result = 0;
        int[] pos = walle.getPosition();
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
            return CHOOSE_ERROR;
        } else {
            walle.setLastOperationPop(true);
            result = -walle.pop();
        }
        return result;
    }

    public void move(int direction) {
        int[] pos = walle.getPosition();
        switch (direction) {
            case LEFT:
                maze.setChar(pos[0] - 1, pos[1], '0');
                break;
            case UP:
                maze.setChar(pos[0], pos[1] - 1, '0');
                break;
            case RIGHT:
                maze.setChar(pos[0] + 1, pos[1], '0');
                break;
            case DOWN:
                maze.setChar(pos[0], pos[1] + 1, '0');
                break;
        }
        walle.move(direction);
        try {
            http.postMove(direction);
        } catch (ForbiddenMoveException e) {
            System.err.println("ForbiddenMoveException caught!");
            System.err.println(e.getMessage());
        }
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
        int[] pos = walle.getPosition();
        if (!walle.isLastOperationPop()) {
            switch (direction) {
                case LEFT:
                    result = handleNode(pos[0] - 2, pos[1]);
                    break;
                case UP:
                    result = handleNode(pos[0], pos[1] - 2);
                    break;
                case RIGHT:
                    result = handleNode(pos[0] + 2, pos[1]);
                    break;
                case DOWN:
                    result = handleNode(pos[0], pos[1] + 2);
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