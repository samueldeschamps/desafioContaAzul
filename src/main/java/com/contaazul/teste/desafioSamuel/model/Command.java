package com.contaazul.teste.desafioSamuel.model;

public enum Command {

    MOVE('M'), LEFT('L'), RIGHT('R');

    private char id;

    private Command(char id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public static Command fromId(char id) {
        for (Command cmd : values()) {
            if (cmd.id == id) {
                return cmd;
            }
        }
        return null;
    }
}
