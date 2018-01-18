package compiler;


public class Token {
    
    public String type[] = {"Type", "int" , "float" ,"void" , "binary"};
    public String conditionOperator[] = {"Condition_Op", "and" , "or" , "not"};
    public String comparisonOperator[] = {"Comparison_Op", "<" , ">" ,"<=" , ">=" , "==" ,"!=" };
    public String number[] = {"Number", "0" , "1" ,"2" , "3" , "4" ,"5" , "6" , "7" ,"8" , "9" };
    public String addOp[] = {"Add_Op" , "+" , "-"};
    public String mulOp[] = {"Mul_Op" , "*" , "/"};
    public String specialchar[] = {";" , "." , "," , "{" , "}" , "(" , ")" , "=" , "while" , "if" , "return" ,
           "break" , "read" , "write" , "continue" , "using" , "STR" , "class" , "program" , "end"};
   
    
    
    
    public Token() {
        
    }
    
    
    
}
