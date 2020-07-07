package compiler;

import java.util.LinkedList;
import java.util.List;

import compiler.Tokenizer.Token;
import compiler.Tokenizer.TokenType;
import static compiler.Tokenizer.TokenType.*;

public class Analyzer {
    public static final Token FUNCIONALIDADE = new Token(KEYWORD, "Funcionalidade");
    public static final Token CENARIO = new Token(KEYWORD, "Cenário");
    public static final Token PASSO = new Token(KEYWORD, "Passo");
    public static final Token DADO = new Token(KEYWORD, "Dado");
    public static final Token QUANDO = new Token(KEYWORD, "Quando");
    public static final Token ENTAO = new Token(KEYWORD, "Então");
    public static final Token TWO_DOTS = new Token(SYMBOL, ":");


    private final LinkedList<Token> tokens;

    public Analyzer(List<Token> tokens) {
        this.tokens = new LinkedList<>(tokens);
    }

    public void parse() {
        compileFuncionalidade();
        if (!tokens.isEmpty()) {
            throw new RuntimeException("Erro: tokens remancescentes após a compilação da classe: " + tokens.toString());
        }
    }

    public Token consume(Token expected) {
        Token nextToken = tokens.removeFirst();
        if (!nextToken.equals(expected)) {
            throw new RuntimeException("Erro: era esperado o token " + expected + " mas encontrei o token " + nextToken);
        }
        return nextToken;
    }

    public void consume(TokenType expected) {
        Token nextToken = tokens.removeFirst();
        if (nextToken.type != expected) {
            throw new RuntimeException("Erro: era esperado o tipo de token " + expected + " mas encontrei o tipo de token " + nextToken.type);
        }
    }

    public Token lookAhead(int i) {
        return tokens.get(i);
    }

    private void compileFuncionalidade(){
        //Funcionalidade → 'Funcionalidade' ':' Texto Cenário+
        consume(FUNCIONALIDADE);
        consume(TWO_DOTS);
        consume(TEXTO);
        //Cenário → 'Cenário' ':' Texto Passo+
        compileCenario();  
    }

    private void compileCenario(){
        
        //Cenário → 'Cenário' ':' Texto Passo+
        consume(CENARIO);
        consume(TWO_DOTS);
        consume(TEXTO);
        compilePasso();
        if(!tokens.isEmpty() && tokens.peek().equals(CENARIO)){
            compileCenario();
        }
    }

    private void compilePasso(){
        //Passo → ('Dado' | 'Quando' | 'Então') Texto
        if (tokens.peek().equals(DADO)){
            consume(DADO);
            consume(TEXTO);
        }
        if (tokens.peek().equals(QUANDO)){
            consume(QUANDO);
            consume(TEXTO);
        }
        if(tokens.peek().equals(ENTAO)){
            consume(ENTAO);
            consume(TEXTO);
        }

    }
       
}