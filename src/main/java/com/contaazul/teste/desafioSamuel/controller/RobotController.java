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

    /*
    Observações:
    - Eu já desenvolvi vários servidores SpringBoot com banco de dados e lógicas de negócio,
    usando as camadas @Service, @Repository, recebendo/enviando TOs em forma de JSON,
    só que neste projeto eu não fiz porque não havia necessidade, era simples demais pra isso;
    - Tenho conhecimento que os @Services são a camada correta pra colocar as lógicas de negócio
    da aplicação, bem como anotar os métodos deles com @Transactional para delimitar o escopo das
    transações e também para delimitar o escopo do EntityManager com relação a lazy loading, etc.
    A transação é iniciada no momento em que uma classe chama um método de *outra classe* anotado
    com @Transactional.
    - Já implementei servidores multi-tenant separados por Schema, rotinas agendadas usando
    @Configuration/@EnableScheduling/@Scheduled/@PostConstruct, request interceptors para criar
    regras que se aplicam a todos os requests e/ou requests filtrados, e também tenho experiência
    com JPA e Hibernate. Já criei rotinas de migração da BD de uma versão para outra.
    - Tenho conhecimento de que em aplicações maiores / mais complexas é interessante
    além de ter os testes em nível externo (web), ter também testes unitários internos.
     */

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
