package com.contaazul.teste.desafioSamuel.controller;

import com.contaazul.teste.desafioSamuel.exception.BadRequestException;
import com.contaazul.teste.desafioSamuel.exception.OutOfMapException;
import com.contaazul.teste.desafioSamuel.model.Command;
import com.contaazul.teste.desafioSamuel.model.Direction;
import com.contaazul.teste.desafioSamuel.model.Robot;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RobotController {

    @PostMapping("/rest/mars/{command}")
    public @ResponseBody
    String simulatePath(@PathVariable("command") @NonNull String commandStr) {
        List<Command> commands = parseCommands(commandStr);
        Robot robot = new Robot(0, 0, Direction.NORTH);
        try {
            robot.execute(commands);
            return robot.position();
        } catch (OutOfMapException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private List<Command> parseCommands(@NonNull String commandStr) {
        return commandStr.chars().mapToObj((c) -> {
            Command cmd = Command.fromId((char) c);
            if (cmd == null) {
                throw new BadRequestException("Comando desconhecido: " + (char) c);
            }
            return cmd;
        }).collect(Collectors.toList());
    }
}
