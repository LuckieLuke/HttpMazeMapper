public class Mapper {

    private HTTPConnector http;
    private Robot walle;
    private Maze maze;

    private final int LEFT = 1;
    private final int UP = 2;
    private final int RIGHT = -1;
    private final int DOWN = -2;

    public Mapper() {
    }

    public void mapFromHttp(String uId, int mapId) {
        http = new HTTPConnector(uId, mapId);
        maze = new Maze(http.getSize());
        walle = new Robot(http.getStartPosition());

        int direction;
        boolean[] possibilities;

        possibilities = get();
        maze.setPossibilitiesChars(walle.getX(), walle.getY(), possibilities);
        direction = choose();
        if(maze.getSum() == 0)
            return;
        move(direction);

/*        while(!walle.isAtStartPosition() && maze.areAnyHashesAround(walle.getx(), walle.getY())){

        }*/





/*
        robimy get, choose, move, push i na stos
        while(!isRobocikNaStarcie && !s.isEmpty() && areHaszeWokółStart)
            get
            choose
            checkSum
            check
            move
            push
        finishMaze
*/

    }

    public void mapFromArray() {

    }

    public boolean[] get() {                                            //bool[4] ← 0-left, 1-up, 2-right, 3-down - wysyła zapytanie GET po  http i korzysta z parsera, żeby wyciągnąć info, zamienia pole na 0
        maze.setChar(walle.getX(), walle.getY(), '0');              // to możnaby jakoś usprawnić żeby nie pobierać oddzielnie x i y z robocika
        return http.getPossibilities();
    }

    public int choose() {
        int result = 0;
        int[] pos = new int[]{walle.getX(), walle.getY()};
        if(maze.areAnyHashesAround(pos[0], pos[1])) {
            if (maze.getChar(pos[0], pos[1]+1) == '#')
                result = 3;
            if(maze.getChar(pos[0]+1, pos[1]) == '#')
                result = 2;
            if(maze.getChar(pos[0], pos[1]-1) == '#')
                result = 1;
            if(maze.getChar(pos[0]-1, pos[1]) == '#')
                result = 0;
        }
        else {
            result = -walle.pop();
        }
        return result;
    }

    public void move(int direction) {
        switch(direction) {
            case LEFT:
                maze.setChar(walle.getX()-1, walle.getY(), '0');
                break;
            case UP:
                maze.setChar(walle.getX(), walle.getY()-1, '0');
                break;
            case RIGHT:
                maze.setChar(walle.getX()+1, walle.getY(), '0');
                break;
            case DOWN:
                maze.setChar(walle.getX(), walle.getY()+1, '0');
                break;
        }
        walle.move(direction);
    }

    public boolean[] getFromArray() {
        return null;
    }

    public void finishMaze() { }  //lecim po całym labiryncie, zamieniając '1' na '+' i jeśli x ^ y % 2 == 0 to buildAWall i ustal czy ściana, czy nie ściana

    public boolean buildAWall(int x, int y) { return false; }

    public boolean check(int x, int y) { return false; } //sprawdza czy zna wszystkich sąsiadów pola [x, y]
}
