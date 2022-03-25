package com.contaazul.teste.desafioSamuel;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DesafioSamuelApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    // Testes sugeridos:

    @Test
    public void movRotacaoDireita() throws Exception {
        mockMvc.perform(post("/rest/mars/MMRMMRMM")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("(2, 0, S)")));
    }

    @Test
    public void movParaEsquerda() throws Exception {
        mockMvc.perform(post("/rest/mars/MML")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("(0, 2, W)")));
    }

    @Test
    public void repetMovParaEsquerda() throws Exception {
        mockMvc.perform(post("/rest/mars/MML")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("(0, 2, W)")));
    }

    @Test
    public void comandoInvalido1() throws Exception {
        // Comando desconhecido: A
        mockMvc.perform(post("/rest/mars/AAA")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void posicaoInvalida1() throws Exception {
        // Robô saiu do mapa em: (0, 6, N)
        mockMvc.perform(post("/rest/mars/MMMMMMMMMMMMMMMMMMMMMMMM")).andDo(print()).andExpect(status().isBadRequest());
    }

    // Testando exatamente os limites do mapa:

    @Test
    public void posicaoInvalidaXMin() throws Exception {
        // Robô saiu do mapa em: (-1, 0, W)
        mockMvc.perform(post("/rest/mars/LM")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void posicaoInvalidaXMax() throws Exception {
        // Robô saiu do mapa em: (6, 0, W)
        mockMvc.perform(post("/rest/mars/RMMMMMM")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void posicaoInvalidaYMin() throws Exception {
        // Robô saiu do mapa em: (0, -1, W)
        mockMvc.perform(post("/rest/mars/LLM")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void posicaoInvalidaYMax() throws Exception {
        // Robô saiu do mapa em: (0, 6, W)
        mockMvc.perform(post("/rest/mars/MMMMMM")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void posicaoInvalidaDepoisDeRodarXMin() throws Exception {
        // Robô saiu do mapa em: (0, -1, W)
        mockMvc.perform(post("/rest/mars/MMMMMRMMMMMRMMMMMRMMMMMM")).andDo(print()).andExpect(status().isBadRequest());
    }

    @Test
    public void posicaoInvalidaDepoisDeIrRetrocederIr() throws Exception {
        // Robô saiu do mapa em: (0, 6, W)
        mockMvc.perform(post("/rest/mars/MMMMMRRMMLLMMM")).andDo(print()).andExpect(status().isBadRequest());
    }

    // Testa ausência de comandos:

    @Test
    public void ausenciaDeComandos() throws Exception {
        // Erro 404 Not Found, pois não dá match com o path param.
        mockMvc.perform(post("/rest/mars/")).andDo(print()).andExpect(status().isNotFound());
    }

    // Testa caminhos válidos por dentro do mapa:

    @Test
    public void contornaPerimetroGirandoDireitaVoltaInicio() throws Exception {
        mockMvc.perform(post("/rest/mars/MMMMMRMMMMMRMMMMMRMMMMM")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("(0, 0, W)")));
    }

    @Test
    public void contornaPerimetroGirandoEsquerdaVoltaInicio() throws Exception {
        mockMvc.perform(post("/rest/mars/RMMMMMLMMMMMLMMMMMLMMMMM")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("(0, 0, S)")));
    }

    @Test
    public void avancaVoltaMuitasVezesViraDireita() throws Exception {
        mockMvc.perform(post("/rest/mars/MMMMMRRMMMMMLLMMMMMRRMMMMMLLMMMMMLLMMMMMRRMMMMMLLMMMMMRRMMMMMRMMMMM")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("(5, 5, E)")));
    }

    @Test
    public void movimentoEmS() throws Exception {
        mockMvc.perform(post("/rest/mars/RMMLMMLMMRMMR")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(equalTo("(0, 4, E)")));
    }

}
