package compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tokenizer {
    
    public static final List<String> keywords = List.of("Funcionalidade", "Cenário", "Passo", "Dado", "Quando", "Então", "Texto");
    public static final List<Character> symbols = List.of(':');

    public Tokenizer(){ }

    public List<Token> tokenize(String code){
        code += " ";
        int length = code.length();
        List<Token> tokens = new ArrayList<>();

        for (int current = 0; current < length; current++) {

            if(code.charAt(current) == '#'){ 
                current++;                          
                while(current < length){          
                    if((code.charAt(current) == '\n')){
                        current++;
                        break;
                    }
                    current++;
                }
                current = current-1;
            }

            //adicionando as keywords a lista de tokens
            for (String keyword : keywords) {
                if (code.startsWith(keyword, current)) {
                    int keywordLength = keyword.length();
                    if (!Character.isLetterOrDigit(code.charAt(current + keywordLength))) {
                        tokens.add(new Token(TokenType.KEYWORD, keyword));
                        current += keywordLength;
                        continue;
                    }
                }
            }

            if (symbols.contains(code.charAt(current))) {
                tokens.add(new Token(TokenType.SYMBOL, code.charAt(current) + ""));
                continue;
            }
        
            // 
            if (Character.isLetter(code.charAt(current))) {
                int textStart = current;
                int textEnd = textStart + 1;

                while (Character.isLetterOrDigit(code.charAt(current)) || code.charAt(current)==' ' || code.charAt(current)=='"' ){
                    if((code.charAt(current) == '\n')|| current == length -1 ){
                        break;
                    } 
                    current = textEnd++;
                } 
                tokens.add(new Token(TokenType.TEXTO, code.substring(textStart, textEnd - 1)));
                current = textEnd - 2;
                continue;
            }
        }    
        return tokens;
    }

    public enum TokenType {
        SYMBOL("symbol"), KEYWORD("keyword"), TEXTO("texto");
        public final String tag;
        TokenType(String type) {
            this.tag = type;
        }
    } 
    public static class Token {
        public final TokenType type;
        public final String lexeme;

        public Token(TokenType type, String lexeme) {
            this.type = type;
            this.lexeme = lexeme;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Token token = (Token) o;
            return type == token.type &&
                   Objects.equals(lexeme, token.lexeme);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, lexeme);
        }

        @Override
        public String toString() {
            return "Token{" +
                   "type=" + type +
                   ", lexeme='" + lexeme + '\'' +
                   '}';
        }
    }


}