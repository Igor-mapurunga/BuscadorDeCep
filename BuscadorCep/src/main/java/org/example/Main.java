package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner leitura = new Scanner(System.in);
        System.out.println("Digite um CEP para ser consultado: ");
        var busca = leitura.nextLine();
        String endereco = "https://viacep.com.br/ws/" + busca + "/json/";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        System.out.println(json);


        catalogoDeEnderecos(busca, json);
    }

    private static void catalogoDeEnderecos(String busca, String json) {
        String nomeArquivo = "endereco_" + busca + ".json";

        try (FileWriter fileWriter = new FileWriter(nomeArquivo)) {
            fileWriter.write(json);
            System.out.println("Arquivo JSON criado com sucesso: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao escrever o arquivo JSON: " + e.getMessage());
        }
    }
}