public class Mapper {

    HTTPConnector http;
    Robot walle;
    Maze maze;

    public Mapper() {
    }

    public void mapFromHttp(String uId, int mapId) {
        http = new HTTPConnector(uId, mapId);
        maze = new Maze(http.getSize());
        int direction;
        int[] choice;


        get();
        choice = choose();
        direction = choice[0];
        walle.move(direction, http);

        while(!walle.isStackEmpty() && !walle.isAtStartPosition()){

        }
    }

    public void mapFromArray() {

    }

    public boolean[] get() { //bool[4] ← 0-left, 1-up, 2-right, 3-down - wysyła zapytanie GET po  http i korzysta z parsera, żeby wyciągnąć info, zamienia pole na 0
        maze.setChar(walle.getX(), walle.getY(), '0'); // to możnaby jakoś usprawnić żeby nie pobierać oddzielnie x i y z robocika
        return new boolean[4];
    }

    public boolean[] getFromArray() {
        return null;
    }

    public int[] choose() { //int[2] ← od lewej zgodnie ze wskazówkami zegara
        return null;
    }

    public void finishMaze() { //lecim po całym labiryncie, zamieniając '1' na '+' i jeśli x ^ y % 2 == 0 to buildAWall i ustal czy ściana, czy nie ściana

    }

    public boolean buildAWall(int x, int y) {
        return false;
    }

    public boolean check(int x, int y) { //sprawdza czy zna wszystkich sąsiadów pola [x, y]
        return false;
    }
}
