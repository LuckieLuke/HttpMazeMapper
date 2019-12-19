import java.util.ArrayList;

public class Robot {

    private Stack s;
    private int x;
    private int y;

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
        s = new Stack();
    }

    public void move(int direction) {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private static class Stack<Integer> {
        ArrayList<Integer> s;

        Stack() {
            s = new ArrayList<>();
        }

        public void push(Integer x) {
            s.add(s.size(), x);
        }

        public int pop() {
            return (int)s.remove(s.size()-1);
        }
    }
}
