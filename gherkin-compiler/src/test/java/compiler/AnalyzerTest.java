package compiler;

import compiler.Tokenizer.Token;
import org.junit.jupiter.api.Test;
import java.util.List;


public class AnalyzerTest {

    @Test
    public void funcionalidadeComUmCenario() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(
            "Funcionalidade: Postagem no blog\n"+
            "Cenário: O Estrongildo faz uma postagem no seu blog \n"+
            "Dado que estou logado como o Estrongildo \n"+
            "Quando eu tentar realizar uma postagem com o título \"BDD is awesome \" \n"+
            "Então eu devo ver a mensagem \"Postagem feita com sucesso\" "
        );

        Analyzer analyzer = new Analyzer(tokens);
        analyzer.parse();
    }

    @Test
    public void funcionalidadeComMaisDeUmCenario() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(
            "Funcionalidade: Postagem no blog\n"+
            "Cenário: O Estrongildo faz uma postagem no seu blog \n"+
            "Dado que estou logado como o Estrongildo \n"+
            "Quando eu tentar realizar uma postagem com o título \"BDD is awesome \" \n"+
            "Então eu devo ver a mensagem \"Postagem feita com sucesso\" " +
            "# O cenário abaixo necessita de dois usuários distintos\n"+
            "Cenário: O Estrongildo tenta excluir uma postagem que não é sua\n"+
            "Dado que estou logado como o Estrongildo\n"+
            "Quando eu tento excluir a postagem \"Java sucks\" do Astroaldo\n"+
            "Então eu devo ver a mensagem \" blog não é o seu!\" "
        );

        Analyzer analyzer = new Analyzer(tokens);
        analyzer.parse();
    }

    @Test
    public void funcionalidadeComCenarioSemTexto() {
        Tokenizer tokenizer = new Tokenizer();
        List<Token> tokens = tokenizer.tokenize(
            "Funcionalidade: Postagem no blog\n"+
            "Cenário:" +
            "Dado que estou logado como o Estrongildo \n"+
            "Quando eu tentar realizar uma postagem com o título \"BDD is awesome \" \n"+
            "Então eu devo ver a mensagem \"Postagem feita com sucesso\" " 
        );

        Analyzer analyzer = new Analyzer(tokens);
        try {
            analyzer.parse();
            assert false;
        } catch (RuntimeException ex) {
            // Ok!
        }
    }
    
}