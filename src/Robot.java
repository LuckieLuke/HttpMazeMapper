import java.util.ArrayList;

public class Robot {

    private Stack s;
    private int[] startPosition;
    private int x;
    private int y;

    private final int LEFT = 1;
    private final int UP = 2;
    private final int RIGHT = -1;
    private final int DOWN = -2;

    public Robot(int[] startPosition) {
        this.startPosition = startPosition;
        x = startPosition[0];
        y = startPosition[1];
        s = new Stack();
    }

    public void move(int direction, HTTPConnector http) {

        http.move("left");

    }

    public boolean isAtStartPosition(){
        return x == startPosition[0] && y == startPosition[1];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isStackEmpty() {
        return s.isEmpty();
    }

    public void push(int direction) {
        s.push(direction);
    }

    public int pop() {
        return s.pop();
    }

    public boolean areAnyHashesAround() {
        return false;
    }

    public void print() {
        System.out.println("Position: " + x + ", " + y);
        System.out.print("Stack: ");
        s.printStack();
    }

    private static class Stack {
        ArrayList<Integer> s;

        Stack() {
            s = new ArrayList<>();
        }

        void push(Integer x) {
            s.add(s.size(), x);
        }

        int pop() {
            return s.remove(s.size()-1);
        }

        boolean isEmpty() {
            return s.size() == 0;
        }

        void printStack() {
            for(Integer i: s)
                System.out.print(i + " ");
            System.out.println();
        }
    }
}
