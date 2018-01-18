/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.List;

/**
 *
 * @author Amira Kotb
 */
public class Pars {

    public Pars() {
    }
    
    
    public void print(List<TokenBuffer> list , List<String> lex){
        System.out.println("Line     Lexem     TokenType");
        for(int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getLine()+"            "+list.get(i).getToken());
            
        }
        
    }
    
}
