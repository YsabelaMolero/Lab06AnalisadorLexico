import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Arquivo {

    public static String Read(String Caminho) {

        // Essa string conteudo vai guardar o código todo sem comentários.
        String conteudo = "";
        try {
            FileReader arq = new FileReader(Caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";

            // posicaoAsterico é uma variável que vai pegar a posição dos asteriscos em um
            // comentário em bloco
            // A posicaoAsterisco precisa ter um valor inicial para funcionar e esse valor
            // tem que ser -1.

            int posicaoAsterisco = -1;
            try {
                linha = lerArq.readLine();
                while (linha != null) {

                    // Vou andar de caractere em caractere. Se eu perceber que tem comentário, não
                    // vou adicionar a linha ao conteudo.
                    for (int i = 0; i < linha.length(); i++) {
                        // Ignora a linha se for comentário em bloco (/* ***** )
                        if (linha.charAt(i) == '/' && linha.charAt(i + 1) == '*') {
                            // O comentário em bloco terá várias linhas com o asterisco na mesma posição.
                            // Vamos guardar essa posição.
                            posicaoAsterisco = i + 1;
                            continue;
                        }
                        // Ignora a linha se for comentário em linha (//)
                        else if (linha.charAt(i) == '/' && linha.charAt(i + 1) == '/') {
                            continue;
                        }
                        // Ignora a linha se for final de comentário em bloco (*/).
                        else if (linha.charAt(i) == '*' && linha.charAt(i + 1) == '/') {
                            // O comentário em bloco acabou, não precisa mais ficar guardando a posição do
                            // asterisco. O valor agora é -1. (Não existe índice -1).
                            posicaoAsterisco = -1;
                            continue;
                        }

                        // Se o char atual está na mesma posição que o asterisco do comentário em bloco
                        // e o comentário em bloco ainda não acabou, ignore a linha.
                        // Mas como eu sei se o comentário em bloco ainda não acabou? Simples, o valor
                        // da posicaoAsterisco será diferente de -1 enquanto tiver comentário.
                        else if (i == posicaoAsterisco) {
                            continue;
                        }

                        // A linha não é comentário
                        else {
                            // Salva essa linha
                            conteudo += linha + "\n";
                            linha = lerArq.readLine();
                        }
                    }
                }
                arq.close();
                return conteudo;
            } catch (IOException ex) {
                System.out.println("Erro: Não foi possível ler o arquivo!");
                return "";
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: Arquivo não encontrado!");
            return "";
        }
    }

    public static boolean Write(String Caminho, String Texto) {
        try {
            FileWriter arq = new FileWriter(Caminho);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(Texto);
            gravarArq.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static List<String> retornaLinhas(String Caminho) {
        // String conteudo = "";
        List<String> linhas = new ArrayList<>();
        try {
            FileReader arq = new FileReader(Caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";
            int posicaoAsterisco = -1;
            try {
                linha = lerArq.readLine();
                while (linha != null) {

                    // Vou andar de caractere em caractere. Se eu perceber que tem comentário, não
                    // vou adicionar a linha ao conteudo.
                    for (int i = 0; i < linha.length(); i++) {
                        // Ignora a linha se for comentário em bloco (/* ***** )
                        if (linha.charAt(i) == '/' && linha.charAt(i + 1) == '*') {
                            // O comentário em bloco terá várias linhas com o asterisco na mesma posição.
                            // Vamos guardar essa posição.
                            posicaoAsterisco = i + 1;
                            continue;
                        }
                        // Ignora a linha se for comentário em linha (//)
                        else if (linha.charAt(i) == '/' && linha.charAt(i + 1) == '/') {
                            continue;
                        }
                        // Ignora a linha se for final de comentário em bloco (*/).
                        else if (linha.charAt(i) == '*' && linha.charAt(i + 1) == '/') {
                            // O comentário em bloco acabou, não precisa mais ficar guardando a posição do
                            // asterisco. O valor agora é -1. (Não existe índice -1).
                            posicaoAsterisco = -1;
                            continue;
                        }

                        // Se o char atual está na mesma posição que o asterisco do comentário em bloco
                        // e o comentário em bloco ainda não acabou, ignore a linha.
                        // Mas como eu sei se o comentário em bloco ainda não acabou? Simples, o valor
                        // da posicaoAsterisco será diferente de -1 enquanto tiver comentário.
                        else if (i == posicaoAsterisco) {
                            continue;
                        }

                        // Linha vazia
                        else if (linha == "") {
                            continue;
                        }

                        else {

                            linhas.add(linha);
                            linha = lerArq.readLine();
                        }
                    }
                    arq.close();

                }
            }

            catch (IOException ex) {
                System.out.println("Erro: Não foi possível ler o arquivo!");

            }
        }

        catch (FileNotFoundException ex) {
            System.out.println("Erro: Arquivo não encontrado!");

        }
        return linhas;
    }
}
