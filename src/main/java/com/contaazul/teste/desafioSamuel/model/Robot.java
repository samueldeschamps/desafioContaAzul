package com.contaazul.teste.desafioSamuel.model;

import com.contaazul.teste.desafioSamuel.exception.OutOfMapException;
import org.springframework.lang.NonNull;

import java.util.List;

public class Robot {

    // Limites do Mapa:
    private static final int MIN_X = 0;
    private static final int MIN_Y = 0;
    private static final int MAX_X = 5;
    private static final int MAX_Y = 5;

    private int x;
    private int y;
    private Direction dir;

    public Robot(int x, int y, @NonNull Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public String position() {
        return String.format("(%d, %d, %c)", x, y, dir.id);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                '}';
    }

    public void execute(@NonNull List<Command> commands) throws OutOfMapException {
        for (Command cmd : commands) {
            switch (cmd) {
                case LEFT:
                    rotateLeft();
                    break;
                case RIGHT:
                    rotateRight();
                    break;
                case MOVE:
                    moveAhead();
                    break;
            }
        }
    }

    public void rotateRight() {
        dir = dir.toRight();
    }

    public void rotateLeft() {
        dir = dir.toLeft();
    }

    public void moveAhead() throws OutOfMapException {
        switch (dir) {
            case NORTH:
                y++;
                break;
            case EAST:
                x++;
                break;
            case SOUTH:
                y--;
                break;
            case WEST:
                x--;
                break;
        }
        if (x < MIN_X || x > MAX_X || y < MIN_Y || y > MAX_Y) {
            throw new OutOfMapException(x, y, dir);
        }
    }

}
