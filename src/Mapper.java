import Exceptions.BadRequestException;
import Exceptions.ForbiddenMoveException;
import Exceptions.NotFoundException;

class Mapper {

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

    public Maze mapFromHttp() throws NotFoundException, BadRequestException {
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

            if (http.getMoves() > 0 && walle.getStackSize() == 1) {
                int temp = walle.pop();
                if (wellKnownNode(-temp)) {
                    System.out.println("Not pushing back!");
                    walle.changeStartPosition(walle.getPosition());
                } else
                    walle.push(temp);
            }

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

            walle.print();
            walle.printStart();
            maze.printMaze(walle);
            System.out.println();
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

    public boolean wellKnownNode(int direction) {
        boolean result = false;
        switch (direction) {
            case LEFT:
                result = !maze.areAnyCharsAround(walle.getPosition()[0] - 2, walle.getPosition()[1], '#');
                break;
            case UP:
                result = !maze.areAnyCharsAround(walle.getPosition()[0], walle.getPosition()[1] - 2, '#');
                break;
            case RIGHT:
                result = !maze.areAnyCharsAround(walle.getPosition()[0] + 2, walle.getPosition()[1], '#');
                break;
            case DOWN:
                result = !maze.areAnyCharsAround(walle.getPosition()[0], walle.getPosition()[1] + 2, '#');
                break;
        }
        System.out.println("Checking " + walle.getX() + ", " + walle.getY() + " in direction: " + direction);
        return result;
    }

    private boolean[] get() {
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

    private int choose() {
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
        } else if(walle.getStackSize() != 0) {
            walle.setLastOperationPop(true);
            result = -walle.pop();
        } else
            return CHOOSE_ERROR;
        return result;
    }

    private void move(int direction) {
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

    private void finishMaze() {
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

    private void fixAllOnes() {
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                if ((i % 2 != 0 || j % 2 != 0) && maze.getChar(j, i) == '1')
                    maze.setChar(j, i, '+');
                if ((i % 2 != 0 || j % 2 != 0) && maze.getChar(j, i) == '#')
                    maze.setChar(j, i, '0');
            }
        }
    }

    private boolean buildAWall(int x, int y) {
        return maze.areAnyCharsAround(x, y, '+');
    }


    private boolean checkIfNotYetKnown(int direction) {
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