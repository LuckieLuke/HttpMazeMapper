import java.io.FileNotFoundException;

public class Main {

    private static final int mapNumber = 1;

    public static void main(String[] args) {
        /*Parser p = new Parser();
        String s = "10x5";
        int[] size = p.parseSize(s);

        for(int i: size)
            System.out.println(i);

        String pos = "\"down\": \"+\",\n \"left\": \"0\",\n \"right\": \"+\",\n \"up\": \"0\",\n ";
        boolean[] posibilities = p.parsePossibilities(pos);

        for(boolean b: posibilities)
            System.out.println(b);*/
        Robot r = new Robot(1, 1);
        r.move(2);
        r.print();
    }

    public static void save(Maze maze, String path) throws FileNotFoundException { //jeśli nie będziemy tu nic do końca roboty dopisywać to trza to wrzucić do maina i wsio
        MazeFile.saveToFile(maze, path);
    }

    public static void upload(String path) { //to samo co wyżej
        MazeFile.upload(path, mapNumber);
    }
}
