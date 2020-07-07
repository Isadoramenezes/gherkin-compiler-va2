package compiler;

import compiler.Tokenizer.Token;
import org.junit.jupiter.api.Test;
import java.util.List;
import static compiler.Tokenizer.TokenType.*;

public class TokenizerTest {
    
    @Test
    public void testeTokenFuncionalidade() {
        final Tokenizer tokenizer = new Tokenizer();
        final String code = "Funcionalidade: abcd";
        final List<Token> tokens = tokenizer.tokenize(code);

        assert tokens.size() == 3;
        assert tokens.get(0).type == KEYWORD;
        assert tokens.get(1).type == SYMBOL;

    }

    @Test
    public void testeTokenCenario() {
        final Tokenizer tokenizer = new Tokenizer();
        final String code = "Cenário: teste dois \n";
        final List<Token> tokens = tokenizer.tokenize(code);

        assert tokens.size() == 3;
        assert tokens.get(0).type == KEYWORD;
        assert tokens.get(1).type == SYMBOL;
        assert tokens.get(2).type == TEXTO;
    }

    @Test
    public void testeTokenCompleto() {
        final Tokenizer tokenizer = new Tokenizer();
        final String code = "Funcionalidade: Postagem no blog\n"+
        "Cenário: O Estrongildo faz uma postagem no seu blog \n"+
        "Dado que estou logado como o Estrongildo \n"+
        "Quando eu tentar realizar uma postagem com o título \"BDD is awesome \" \n"+
        "Então eu devo ver a mensagem \"Postagem feita com sucesso\"";

        final List<Token> tokens = tokenizer.tokenize(code);

        assert tokens.size() == 12;
        //TODO colocar condições para os tokens
    }
    
    @Test
    public void testeComentario() {
        final Tokenizer tokenizer = new Tokenizer();
        final String code = "Cenário: abc def \n"+
        "# eu sou um comentario \n"+
        "Dado ghi";
        final List<Token> tokens = tokenizer.tokenize(code);

        assert tokens.size() == 5;
        assert tokens.get(0).type == KEYWORD;
        assert tokens.get(1).type == SYMBOL;
        assert tokens.get(2).type == TEXTO;
        assert tokens.get(3).type == KEYWORD;
        assert tokens.get(4).type == TEXTO;
    }
}