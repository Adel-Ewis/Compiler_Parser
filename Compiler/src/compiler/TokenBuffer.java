package compiler;


public class TokenBuffer {
    private int line;
    private String token;

    public TokenBuffer(String CurrentToken) {
        this.token = CurrentToken;
    }
    
    public TokenBuffer(int line, String token) {
        this.line = line;
        this.token = token;
    }

    public int getLine() {
        return line;
    }

    public String getToken() {
        return token;
    }
    
    
    
    
    
    
}
