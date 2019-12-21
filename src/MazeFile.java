import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MazeFile {

    public static final String uid = "cośtrzapodać";

    public static void saveToFile(Maze maze, String path) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(path);

        for(int i = 0; i < maze.getHeight(); i++) {
            for(int j = 0; j < maze.getWidth(); j++) {
                pw.append(maze.getChar(i, j));
            }
            pw.append('\n');
        }

        pw.close();
    }

    public static void upload(String path, int nr) {

    }

}
