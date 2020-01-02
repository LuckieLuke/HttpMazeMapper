import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class MazeFile {

    public static void saveToFile(Maze maze, String path) throws FileNotFoundException {
        PrintWriter pw = new PrintWriter(path);

        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                pw.append(maze.getChar(j, i));
            }
            if (i != maze.getHeight() - 1)
                pw.append('\n');
        }
        pw.close();
    }

}
