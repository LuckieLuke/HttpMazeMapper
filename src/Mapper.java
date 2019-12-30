public class Mapper {

    HTTPConnector http;
    Robot walle;
    Maze maze;

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
        int[] choice;
        boolean[] possibilities;
        boolean isPopFromStack;


        possibilities = get();
        maze.setPossibilitiesChars(walle.getX(), walle.getY(), possibilities);
        choice = choose(possibilities);
        direction = choice[0];
        isPopFromStack = choice[1] == 1 ? true : false;
        walle.move(direction, http);

        while(!walle.isStackEmpty() && !walle.isAtStartPosition() && walle.areAnyHashesAround()){
/*            get();
            choose();*/
        }



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

    public int[] choose(boolean[] possibilities) //int[2] ← od lewej zgodnie ze wskazówkami zegara
    {

        return null;
    }

    public boolean[] getFromArray() {
        return null;
    }

    public void finishMaze() { }  //lecim po całym labiryncie, zamieniając '1' na '+' i jeśli x ^ y % 2 == 0 to buildAWall i ustal czy ściana, czy nie ściana

    public boolean buildAWall(int x, int y) { return false; }

    public boolean check(int x, int y) { return false; } //sprawdza czy zna wszystkich sąsiadów pola [x, y]
}
