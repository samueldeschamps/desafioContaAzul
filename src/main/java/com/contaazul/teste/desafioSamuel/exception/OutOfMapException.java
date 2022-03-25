package com.contaazul.teste.desafioSamuel.exception;

import com.contaazul.teste.desafioSamuel.model.Direction;

public class OutOfMapException extends Exception {

    public OutOfMapException(int x, int y, Direction dir) {
        super(String.format("Robô saiu do mapa em: (%d, %d, %c)", x, y, dir.id));
    }
}
