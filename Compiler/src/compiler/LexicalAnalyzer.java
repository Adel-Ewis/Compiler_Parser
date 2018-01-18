
package compiler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import parsertest.parser;



public class LexicalAnalyzer {
    
    private TokenBuffer tb = null;
    private String leftFactoryTemp;
    private String append;
    private boolean commentFlag;
    public String common[] = {"<*", "*>" ,"<=" , ">=" , "==" ,"!=" };
    char array[] = {'$', '{', '}', ';', '=', '(', ')', '+', '-', '*', '/', '<', '>', ',','!'};
    boolean ret = false;
    public LinkedList<TokenBuffer> list = new LinkedList<TokenBuffer>();
    private Pars p = new Pars();
    public List<String> lexem = new ArrayList<String>();

   
    public LexicalAnalyzer(){
        this.leftFactoryTemp = "";
        this.append = "";
        this.commentFlag = false;
    }
    
    
    
    
    private boolean match(String str, String[] arrName){
        for( int i=0; i<arrName.length; i++ )
            if( arrName[i].equals(str))
                return true;
            
        
        return false;
    } 
       
    
    
    
    public boolean addToken(String str , int line){
        Token t = new Token();
        boolean b = false;
        int c, check=0, error;
        c = str.charAt(0);
        
        if( this.commentFlag == true ){
            if( str.equals("*") ){
                this.append += str;
            }
           
            else if( str.equals(">") ){
                if( this.append.substring(this.append.length()-1).equals("*") ){
                    this.append += str;
                    this.leftFactoryTemp = "";
                    this.append = "";
                    
                    tb = new TokenBuffer(line , "comment");
                    list.remove(list.size()-1);
                    list.add(tb);
                    this.commentFlag = false;
                    return true;
                }else{
                    this.append += str;
                    tb = new TokenBuffer(line , "INV");
                    return false;
                }
               
            }
            
            else{
                this.append += str;
                tb = new TokenBuffer(line , "INV"); 
                return false;
            }
        }
        
        
        if( this.leftFactoryTemp.equals("") )
        if( str.equals("<") || str.equals("=") || str.equals(">") || str.equals("!") || ( str.equals("*") && this.commentFlag == true ) ){
            this.leftFactoryTemp = str;
            if(this.commentFlag == false){
            b = this.match(str , t.comparisonOperator);
            if(b){ tb = new TokenBuffer(line , t.comparisonOperator[0]); this.lexem.add(this.leftFactoryTemp); list.add(tb); }
            
            b = this.match(str , t.specialchar);
            if(b){ tb = new TokenBuffer(line , str);   }
        
            }
            return true;
        }
        
        
        if( !this.leftFactoryTemp.equals("") ){
            this.append = this.leftFactoryTemp;
            this.append += str;
            b = this.match(this.append, this.common);
            if(b){
                if( this.append.equals("<*") ){
                    tb = new TokenBuffer( line , "INV" );
                    this.commentFlag = true;
                    this.leftFactoryTemp = "";
                    return false;
                }
                
                else{
                    str = this.append;
                    this.leftFactoryTemp = "";
                }
            }
            else
                this.append = "";
        }
        
        
        
        b = this.match(str , t.type);
        if(b){ tb = new TokenBuffer(line , t.type[0]); list.add(tb); this.lexem.add(str); return true;  }
        
        
        
        if( this.leftFactoryTemp.equals("") ){
            b = this.match(str , t.specialchar);
            if(b){ tb = new TokenBuffer(line , str); list.add(tb); 
            this.lexem.add(str);
            if( str.equals("end") ){ 
                p.print(list, lexem);
                parser pr =new parser();
                pr.parse(list);
            }
            
            return true;  
            }
            
        }else{
            b = this.match(this.leftFactoryTemp , t.specialchar);
            if(b){ tb = new TokenBuffer(line , this.leftFactoryTemp); list.add(tb); this.lexem.add(this.leftFactoryTemp); }
            this.leftFactoryTemp = "";
            b = this.match(str , t.specialchar);
            if(b){ tb = new TokenBuffer(line , str); list.add(tb); this.lexem.add(str); }
            else this.addToken(str, line);
            
            return true;
        }
        
        
        
        if( this.leftFactoryTemp.equals("") ){
        b = this.match(str , t.comparisonOperator);
        if(b){ tb = new TokenBuffer(line , t.comparisonOperator[0]); list.add(tb); this.lexem.add(str); return true;  }
        }
        else{
            b = this.match(this.leftFactoryTemp , t.comparisonOperator);
            if(b){ tb = new TokenBuffer(line , t.comparisonOperator[0]); list.add(tb); this.lexem.add(this.leftFactoryTemp); }
            this.leftFactoryTemp = "";
            b = this.match(str , t.comparisonOperator);
            if(b){ tb = new TokenBuffer(line , t.comparisonOperator[0]); list.add(tb); this.lexem.add(str); }
            else this.addToken(str, line);
            
            return true;
        }
        
        
        
        b = this.match(str , t.conditionOperator);
        if(b){ tb = new TokenBuffer(line , t.conditionOperator[0]); list.add(tb); this.lexem.add(str); return true;  }
        
        
        b = this.match(str , t.addOp);
        if(b){ tb = new TokenBuffer(line , t.addOp[0]); list.add(tb); this.lexem.add(str); return true;  }
        
        
        b = this.match(str , t.mulOp);
        if(b){ tb = new TokenBuffer(line , t.mulOp[0]);  list.add(tb); this.lexem.add(str); return true;  }
        
       
        
        
        check = 0;
        for( int i=0; i<str.length(); i++ ){
            b = this.match(String.valueOf(str.charAt(i)) , t.number);
            if(b)check++;
        }
        if( check == str.length() ){
            tb = new TokenBuffer(line , t.number[0]); this.lexem.add(str); list.add(tb); 
            return true;
        }
        
        
        check = 0;
        c = str.charAt(0);
        if( (c>=97 && c<=122) || ( c>=65 && c<=90 ) ){
            for( int i=1; i<str.length(); i++ ){
                c = str.charAt(i);
                if( (c>=97 && c<=122) || ( c>=65 && c<=90 ) || ( c>=48 && c<=57 ) || ( c == 95 ) ) continue;
                check++;
        }
        if( check == 0 ){ tb = new TokenBuffer(line , "ID"); list.add(tb); this.lexem.add(str);  return true;  }
        } 
        
        
        
        error = 0;
        if( str.length()>5 )
            if( str.substring(0,1).equals("\"") && str.substring(str.length()-1).equals("\"")  )
                if( str.substring(str.length()-5 , str.length()-4).equals(".") || str.substring(str.length()-6 , str.length()-5).equals(".") )
                    for( int i=1; i<str.length()-1; i++ ){
                        c = str.charAt(i);
                        if( (c>=97 && c<=122) || ( c>=65 && c<=90 ) || ( c>=48 && c<=57 ) || ( c == 95 ) );
                        else error++;
                    }
       
        if( error == 1 ) 
        { tb = new TokenBuffer(line , "STR"); list.add(tb); this.lexem.add(str);  return true;  }
        
        
        
        if(!b){ tb = new TokenBuffer(line , "INV"); this.lexem.add(str); list.add(tb);  }
            
            
        return false;
        
    }
    
    
    
    
     public boolean Lex(String line, int lineNumber) {
       String temp;
       if(line == null)
            return true;
        
            temp=this.GetLexemes(line,lineNumber);
            Lex(temp,lineNumber);
        return false;
        
    }
    
    
    
      
    public String GetLexemes(String syntax  ,int lineNumber) {
                
       char[] charArray;char lexeme=' ';int e=0;
       List<Character> chars = new ArrayList();
       List<Character> temp = new ArrayList();
       StringBuilder builder = new StringBuilder(chars.size());
       StringBuilder builder1 = new StringBuilder(chars.size());
       if(this.CheckString (syntax)== true){
           ret=this.addToken(syntax, lineNumber);
           
            if(ret==false );
                ///////////////p.print(list);
                //System.out.println("call to parser ");/////////////////////////////***************
           
         return null;
       }
       else{    
       String text ;
       charArray = syntax.toCharArray();
       for( int i=0;i<charArray.length;i++){
        for(int j=0 ;j<array.length ;j++){
         if(charArray[i] == array[j]){
             lexeme=charArray[i];e++;
              break;}
         if(e>0)break;}
         if(e>0)break;}
       
        text = String.copyValueOf(charArray);
        int h=text.indexOf(lexeme);
        for(int j=0 ;j<h ;j++)
        chars.add(charArray[j]) ;
         for(Character ch: chars)
        builder.append(ch);
         if( e!=0 ){
         if(!builder.toString().isEmpty()){
             ret=this.addToken(builder.toString(), lineNumber);
             
              //if(ret==false  ) System.out.println("call to parser ");/////////////////////////////**************************
       
         }//////////////////////////////
         if( lexeme !='$'){
        String string1 = Character.toString(lexeme);
        ret=this.addToken(string1, lineNumber);
        
         //if(ret==false) System.out.println("call to parser ");////////////////////*************************************
       
         }/////////////////////////////////////////
         }
       
       if(charArray.length==0){
           if(ret==false);
              //////////////// p.print(list);
              // System.out.println("call to parser ");///////////////////////////////////////***********
           return null;
       }
       else{    
       for(int l=h+1;l<charArray.length;l++)
           temp.add(charArray[l]);
       for(Character ch: temp)
        builder1.append(ch);
        
       }
/////////////////////////////////////////
       return builder1.toString();        
       }
    }  
    
    
    

    private boolean CheckString (String syntax){
    int e=0;
    for( int i=0;i<syntax.length();i++){
        for(int j=0 ;j<array.length ;j++){
         if(syntax.charAt(i) != array[j]){
             e++;
        }
}
}
    
   if(syntax.length()== (e/array.length) && e!=0)
   return true;
   else
    return false;
}

    
    
}