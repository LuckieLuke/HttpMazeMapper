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

    public boolean isLastOperationPop() {
        return lastOperationPop;
    }

    private boolean lastOperationPop;

    public Robot(int[] startPosition) {
        this.startPosition = startPosition;
        x = startPosition[0];
        y = startPosition[1];
        s = new Stack();
        lastOperationPop = false;
    }

    public void move(int direction) {
        switch(direction) {
            case LEFT:
                x -= 2;
                break;
            case UP:
                y -= 2;
                break;
            case RIGHT:
                x += 2;
                break;
            case DOWN:
                y += 2;
                break;
        }
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

    public void push(int direction) {
        s.push(direction);
    }

    public int pop() {
        return s.pop();
    }

    public void print() {
        System.out.println("Position: " + x + ", " + y);
        System.out.print("Stack: ");
        s.printStack();
    }

    public boolean isStackEmpty(){
        return s.isEmpty();
    }

    public void setLastOperationPop(boolean lastOperationPop){
        this.lastOperationPop = lastOperationPop;
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
