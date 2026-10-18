package br.com.fiap.bank.plataforma.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class CoreBancarioClientService {

    private final RestClient restClient;

    public CoreBancarioClientService(@Value("${core.bancario.url:http://localhost:8080/api/v1}") String baseUrl) {
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    /**
     * Consulta informacoes da conta do cliente no Core Bancario via RestClient nativo do Spring.
     * Caso o Core Bancario esteja indisponivel ou o cliente nao tenha conta, aplica dados padrao de fallback.
     */
    public Map<String, Object> consultarContaCliente(String cpf) {
        try {
            return restClient.get()
                    .uri("/contas/cliente/{cpf}", cpf)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            System.err.println("[CORE BANCARIO CLIENT] Core Bancario offline ou conta nao encontrada para CPF: " + cpf
                    + ". Usando dados padrao.");
            return Map.of(
                    "statusConta", "ATIVA",
                    "saldo", 12500.00,
                    "agencia", "0001",
                    "numeroConta", "12345-6");
        }
    }
}
