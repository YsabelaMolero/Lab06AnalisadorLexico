import java.util.ArrayList;
import java.util.List;


public class analisadorlexico {

    // string para armazenar todo o conteúdo javascript recebido por arquivo-texto.
    static String javascriptCode;

    // Lista que vai armazenar tokens de um código javascript e fazer a análise
    // léxica.
    // Análise léxica é verificar se tal palavra faz parte de tal linguagem.
    static List<String> listaDeTokens = new ArrayList<>();

    // Lista que vai armazenar cada linha do arquivo javascript para quebrá-las em
    // tokens (palavras ou símbolos).
    static List<String> listaDeLinhas = new ArrayList<>();

    // Lista para guardar o token e o que ele é
    static List<IdentificaToken> listaDeTokenETipo = new ArrayList<>();

    // Um array que armazena keywords do javascript
    static String[] keywords = { "abstract", "arguments", "await", "boolean", "break", "byte", "case", "catch", "char",
            "class", "const", "continue", "debugger", "default", "delete", "do", "double", "else", "enum", "eval",
            "export", "extends", "false", "final", "finally", "float", "for", "function", "goto", "if", "implements",
            "import", "in", "instanceof", "int", "interface", "let", "long", "native", "new", "null", "package",
            "private", "protected", "public", "return", "short", "static", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "true", "try", "typeof", "var", "void", "volatile", "while", "with",
            "yield" };

    // Um array que armazena operadores do javascript
    static String[] operators = { "+", "=", "-", "-=", "+=", "*", "/", "*=", "/=", "%", "==", "===", ">", "<", ">=",
            "<=", "=<", "=>", "^", "!=", "!", "||", "&&" };

    // Um array que armazena outros símbolos (tokens) permitidos no javascript.
    static String[] otherSymbols = { "{", "}", "(", ")", "[", "]", ";", ".", "," };

    /// O analisador léxico vai ler o arquivo onde está o código javascript com
    /// jQuery
    // E vai atribuir cada linha a uma lista de linhas
    // E vai separar cada palavra (ou símbolo) na linha e vai adicionar cada uma em
    /// uma lista de tokens.
    // Lembrando que o token é o elemento mais simples do código-fonte, o menor
    /// elemento.
    static void leArquivo() {
        javascriptCode = Arquivo.Read("javascriptCode.txt"); // Atribuindo todo o código-fonte javascript à string
                                                             // javascriptCode
        listaDeLinhas = Arquivo.retornaLinhas("javascriptCode.txt"); // Atribuindo cada linha do código a uma lista de
                                                                     // linhas

        String token = ""; // String para pegar um token de uma linha e guardá-la numa lista de tokens.

        // Para cada linha do arquivo javascript
        for (String linha : listaDeLinhas) {
            // Eu vou andar de char em char até essa linha acabar
            for (int i = 0; i < linha.length(); i++) {
                // Se o char for um símbolo aceitável pelo javascript
                if (linha.charAt(i) == '(' || linha.charAt(i) == ')' || linha.charAt(i) == '{' || linha.charAt(i) == '}'
                        || linha.charAt(i) == '[' || linha.charAt(i) == ']' || linha.charAt(i) == ';'
                        || linha.charAt(i) == '.' || linha.charAt(i) == ',') {
                    if (token != "") {
                        listaDeTokens.add(token); // Guarda o token que tem agora, caso haja
                        token = "";
                    }
                    token += linha.charAt(i);
                    listaDeTokens.add(token); // E guarda esse símbolo
                    token = "";
                    continue; // Tem que ter esse continue para não adicionar o mesmo token mais de uma vez.
                }

                // Se o char não for um espaço e nem um tab
                if (linha.charAt(i) != ' ' && linha.charAt(i) != '\t') {
                    // Concatena o char à variável token até que o token esteja completo
                    token += linha.charAt(i);
                }

                // Se o char atual for um espaço ou um tab
                else if (linha.charAt(i) == ' ' || linha.charAt(i) == '\t') {
                    // O token está completo, então adicione esse token na lista de tokens.
                    listaDeTokens.add(token);

                    // Agora esvazie o token para continuar a operação.
                    token = "";
                    // O que separa o token é o espaço ou tab.
                }

            }
        }

    }
    // Escrevendo uma tabela de lexemas num arquivo-texto .lex
    static void gravaTabelaLex(){
        String conteudo = "";
        for (IdentificaToken tokenTipo : listaDeTokenETipo) {
            conteudo += tokenTipo.getToken() + ' ' + '§' + ' ' + tokenTipo.getTipo();
        }

        Arquivo.Write("Lexemas.lex", conteudo);
    }

    /// Esse método atribui os tipos de tokens para cada token
    static void analisaTokens() {
        // boolean codigoValido = false;
        // Eu preciso desse indice para ter acesso ao token anterior e posterior durante
        // um laço de repetição. O indice é útil para analisar nome de função.
        int indice = 0;

        for (String token : listaDeTokens) {
            // Analisa se o token é uma palavra reservada
            for (String palavra : keywords) {
                if (token == palavra) {
                    IdentificaToken t = new IdentificaToken();
                    t.setToken(token);
                    t.setTipo("Palavra reservada.");
                    listaDeTokenETipo.add(t);
                }
            }
            // Analisa se o token é um operador
            for (String operador : operators) {
                if (token == operador) {
                    IdentificaToken t = new IdentificaToken();
                    t.setToken(token);
                    t.setTipo("Operador.");
                    listaDeTokenETipo.add(t);
                }
            }
            // Analisa de o token é um símbolo que a linguagem aceita
            for (String simbolo : otherSymbols) {
                if (token == simbolo) {
                    IdentificaToken t = new IdentificaToken();
                    t.setToken(token);
                    t.setTipo("Outro simbolo.");
                    listaDeTokenETipo.add(t);
                }
            }

            // Se o token anterior é a keyword function
            if (listaDeTokens.get(indice - 1) == "function") {
                // Se os tokens posteriores são parênteses
                if (listaDeTokens.get(indice + 1) == "(" && listaDeTokens.get(indice + 2) == ")") {
                    // Se o token atual começa com uma letra
                    if (token.toLowerCase().charAt(0) == 'a' || token.toLowerCase().charAt(0) == 'b'
                            || token.toLowerCase().charAt(0) == 'c' || token.toLowerCase().charAt(0) == 'd'
                            || token.toLowerCase().charAt(0) == 'e' || token.toLowerCase().charAt(0) == 'f'
                            || token.toLowerCase().charAt(0) == 'g' || token.toLowerCase().charAt(0) == 'h'
                            || token.toLowerCase().charAt(0) == 'i' || token.toLowerCase().charAt(0) == 'j'
                            || token.toLowerCase().charAt(0) == 'k' || token.toLowerCase().charAt(0) == 'l'
                            || token.toLowerCase().charAt(0) == 'm' || token.toLowerCase().charAt(0) == 'n'
                            || token.toLowerCase().charAt(0) == 'o' || token.toLowerCase().charAt(0) == 'p'
                            || token.toLowerCase().charAt(0) == 'q' || token.toLowerCase().charAt(0) == 'r'
                            || token.toLowerCase().charAt(0) == 's' || token.toLowerCase().charAt(0) == 't'
                            || token.toLowerCase().charAt(0) == 'u' || token.toLowerCase().charAt(0) == 'v'
                            || token.toLowerCase().charAt(0) == 'w' || token.toLowerCase().charAt(0) == 'x'
                            || token.toLowerCase().charAt(0) == 'y' || token.toLowerCase().charAt(0) == 'z') {
                        IdentificaToken t = new IdentificaToken();
                        t.setTipo(token);
                        // Então esse token é o nome de uma função.
                        t.setTipo("Nome de função.");
                        listaDeTokenETipo.add(t);
                    }
                }
            }

            // Vou utilizar as funções tryParse para ver se o token é um int, double, float
            // ou long.
            else if (tryParseInt(token) == true && tryParseDouble(token) == false && tryParseLong(token) == false
                    && tryParseFloat(token) == false) {
                IdentificaToken t = new IdentificaToken();
                t.setToken(token);
                t.setTipo("Inteiro.");
                listaDeTokenETipo.add(t);
            }

            else if (tryParseDouble(token) == true) {
                IdentificaToken t = new IdentificaToken();
                t.setToken(token);
                t.setTipo("Double.");
                listaDeTokenETipo.add(t);
            }

            else if (tryParseFloat(token) == true) {
                IdentificaToken t = new IdentificaToken();
                t.setToken(token);
                t.setTipo("Float.");
                listaDeTokenETipo.add(t);
            }

            else if (tryParseLong(token) == true) {
                IdentificaToken t = new IdentificaToken();
                t.setToken(token);
                t.setTipo("Long.");
                listaDeTokenETipo.add(t);
            }

            // Se o token não começa com um número
            else if (token.charAt(0) != '0' && token.charAt(0) != '1' && token.charAt(0) != '2'
                    && token.charAt(0) != '3' && token.charAt(0) != '4' && token.charAt(0) != '5'
                    && token.charAt(0) != '6' && token.charAt(0) != '7' && token.charAt(0) != '8'
                    && token.charAt(0) != '9') {
                IdentificaToken t = new IdentificaToken();
                t.setToken(token);
                t.setTipo("Variável.");
                listaDeTokenETipo.add(t);
            }

            else{
                System.out.println("O codigo nao eh valido para javascript. Um token esta fora do ''alfabeto'' do javascript.");
            }

            indice++;
        }

    }

    // O Java não tem os métodos TryParse como no C#, então... :

    /// Tenta converter para int
    static boolean tryParseInt(String value) {
        try {
            int n = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /// Tenta converter para double
    static boolean tryParseDouble(String value) {
        try {
            Double n = Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /// Tenta converter para float
    static boolean tryParseFloat(String value) {
        try {
            Float n = Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean tryParseLong(String value) {
        try {
            long n = Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        leArquivo();
        analisaTokens();;
        gravaTabelaLex();;
    }

}