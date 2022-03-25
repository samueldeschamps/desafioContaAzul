package com.contaazul.teste.desafioSamuel.model;

public enum Direction {

    NORTH('N'), EAST('E'), SOUTH('S'), WEST('W');

    public final char id;

    private Direction(char id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public static Direction fromId(char id) {
        for (Direction cmd : values()) {
            if (cmd.id == id) {
                return cmd;
            }
        }
        return null;
    }

    public Direction toRight() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
            default:
                return NORTH;
        }
    }

    public Direction toLeft() {
        switch (this) {
            case NORTH:
                return WEST;
            case WEST:
                return SOUTH;
            case SOUTH:
                return EAST;
            case EAST:
            default:
                return NORTH;
        }
    }
}
