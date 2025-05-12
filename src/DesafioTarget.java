import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DesafioTarget {
    public static void main(String[] args) throws IOException {
        System.out.println("Testes Desenvolvedor 2");
        Soma();
        ValorFibonacci();
        Faturamento();
        FaturamentoPorEstado();
        InverterPalavras();


    }


    //-----------------------------------------------
    // Soma:
    private static void Soma() {
        int INDICE = 13, SOMA = 0, K = 0;
        while (K < INDICE) {
            SOMA = SOMA + (++K);
        }
        System.out.println("-----------------------------------------");
        System.out.println("Valor da variável SOMA: " + SOMA);
    }
    //-----------------------------------------------


    //-----------------------------------------------
    //ValorFibonacci();
    private static void ValorFibonacci() {

        int valorAtual = 0, valorAnterior = 1;
        boolean numeroFazParte = false;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite um número: ");
        int numeroInformado = scanner.nextInt();

        while (valorAtual <= numeroInformado) {
            if (valorAtual == numeroInformado) {
                numeroFazParte = true;
                break;
            }

            int proximoValor = valorAnterior + valorAtual;
            valorAnterior = valorAtual;
            valorAtual = proximoValor;
        }

        if (numeroFazParte) {
            System.out.println("Este número: " + numeroInformado + " pertence à Sequência!!!");
        } else {
            System.out.println("Este número: " + numeroInformado + " não  pertence à Sequência!!!");
        }

    }
    //-----------------------------------------------


    //-----------------------------------------------
    // Faturamento:
    private static void Faturamento() throws IOException {
        String json = LeitorJson.readJson("./src/dados.json");

        json = json.trim().substring(1, json.length() - 1);
        String[] objetos = json.split("\\},\\s*\\{");

        ArrayList<DadosJson> lista = new ArrayList<>();

        for (String obj : objetos) {
            obj = obj.replace("{", "").replace("}", "");

            String[] campos = obj.split(",");
            int dia = 0;
            double valor = 0;

            for (String campo : campos) {
                String[] partes = campo.split(":");
                String chave = partes[0].replace("\"", "").trim();
                String valorStr = partes[1].trim();

                if (chave.equals("dia")) {
                    dia = Integer.parseInt(valorStr);
                } else if (chave.equals("valor")) {
                    valor = Double.parseDouble(valorStr);
                }
            }

            lista.add(new DadosJson(dia, valor));
        }

        DadosJson[] vetor = lista.toArray(new DadosJson[0]);
// testando:
//        for (DadosJson d : vetor) {
//            System.out.printf("Dia %02d - Valor: R$ %.2f%n", d.getDia(), d.getValor());
//        }

        double menor = Double.MAX_VALUE, maior = Double.MIN_VALUE, soma = 0.0;
        int diasComFaturamento = 0;

        for (DadosJson d : vetor) {
            if (d.getValor() > 0) {
                if (d.getValor() < menor) menor = d.getValor();
                if (d.getValor() > maior) maior = d.getValor();
                soma += d.getValor();
                diasComFaturamento++;
            }
        }

        double media = soma / diasComFaturamento;
        int diasAcimaDaMedia = 0;

        for (DadosJson d : vetor) {
            if (d.getValor() > media) {
                diasAcimaDaMedia++;
            }
        }

        System.out.println("\n--- Análise de Faturamento ---");
        System.out.printf(">> Menor faturamento (excluindo dias zerados): R$ %.2f%n", menor);
        System.out.printf(">> Maior faturamento: R$ %.2f%n", maior);
        System.out.printf(">> Média mensal (excluindo zeros): R$ %.2f%n", media);
        System.out.printf(">> Dias com faturamento acima da média: %d dia(s)%n", diasAcimaDaMedia);


    }

    public abstract class LeitorJson {
        public static String readJson(String path) throws IOException {
            String json = String.join(" ",
                    Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8)
            );
            return json;
        }
    }

    //-----------------------------------------------
    static class DadosJson {
        private int dia;
        private double valor;

        public DadosJson(int dia, double valor) {
            this.dia = dia;
            this.valor = valor;
        }

        public int getDia() {
            return dia;
        }

        public double getValor() {
            return valor;
        }
    }
    //-----------------------------------------------


    //-----------------------------------------------
    // FaturamentoPorEstado
    private static void FaturamentoPorEstado() {
        double sp = 67836.43;
        double rj = 36678.66;
        double mg = 29229.88;
        double es = 27165.48;
        double outros = 19849.53;
        double total = sp + rj + mg + es + outros;

        System.out.println("\n--- Análise de Faturamento por Estados ---");
        System.out.println(">> SP: " + ((sp / total) * 100) + "%");
        System.out.println(">> RJ: " + ((rj / total) * 100) + "%");
        System.out.println(">> MG: " + ((mg / total) * 100) + "%");
        System.out.println(">> ES: " + ((es / total) * 100) + "%");
        System.out.println(">> Outros: " + ((outros / total)) * 100 + "%");
    }
    //-----------------------------------------------


    //-----------------------------------------------
    // InverterPalavras
    private static void InverterPalavras() {
        String palavra = "Teste para Desenvolvedor de Sistemas Jr. - São Paulo";
        String inverso = "";

        for (int i = palavra.length() - 1; i >= 0; i--) {
            inverso += palavra.charAt(i);
        }
        System.out.println("\n--- Palavra Invertida ---");
        System.out.println(inverso);
    }
    //-----------------------------------------------



}
