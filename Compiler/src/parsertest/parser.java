/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsertest;

import compiler.Token;
import compiler.TokenBuffer;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class parser {
    
  LinkedList<TokenBuffer> tokens;
  TokenBuffer lookahead;  // to point on the top of the list
  Token Token;
  private void error(String message,int lineNumber)
  {
      System.out.println("Line number :"+(lineNumber-1)+" "+message);
      
  }
   public void parse(LinkedList<TokenBuffer> tokens)
   {
        this.tokens = (LinkedList<TokenBuffer>) tokens.clone();
        lookahead = this.tokens.getFirst();
        
        // write the first root or first role
        System.out.println("parse start");
        //Factor();
        //Expression();
      //  Class_Implementation();
       // Statement();

        Program();
       
        
        
        if (!lookahead.getToken().equals("end"))
        {
            error("end not found , found "+lookahead.getToken()+" insted", lookahead.getLine());
        }
        else
            System.out.println("success "+ lookahead.getToken() +" found");
   }
   
     private void nextToken()
  {
      if(!tokens.isEmpty())
        this.tokens.pop();
    // at the end of input we return an epsilon token
    if (tokens.isEmpty())
      lookahead = new TokenBuffer("end");
    else
      lookahead = tokens.getFirst();
   }
    
   
   //we start from the last role
   void F_name()
   {//F_name →STR
       if(lookahead.getToken().equals("STR"))//STR
       {
       nextToken();
       }else {
           error("INV input", lookahead.getLine());
           nextToken();
       }
   }
   
   void using_command()
   {//using_command →using(F_name.txt);
       if(lookahead.getToken().equals("using"))
       {
           nextToken();
       }
       
       if(lookahead.getToken().equals("(")){
               nextToken();
               F_name();
               
           }else{error("( not found", lookahead.getLine());}
       
       if(lookahead.getToken().equals(")"))// )
       {
           nextToken();
       }else{error(") not found", lookahead.getLine());}
       if(lookahead.getToken().equals(";")){// ;
               nextToken();
               
           }else{error("; not found", lookahead.getLine());}
   }
   
   void Comment()
   {//Comment →<* STR *>
       if(lookahead.getToken().equals("comment"))
           nextToken();
   }
   
   void Factor()
   {//Factor→ ID| Number
       if(lookahead.getToken().equals("ID"))
       {
           nextToken();
           
       }else if(lookahead.getToken().equals("Number"))
       {
           nextToken();
             
       }else{error("Number or ID not found", lookahead.getLine());}
   }
   
   void Term1()
   {//term1->Mul_Op Factor term1 | EPSILON
    if(lookahead.getToken().equals(("Mul_Op")))
    {
        nextToken();
        Factor();
        Term1();
        
    }
    
    
   }
   
   void Term()
   {//term->factor term1
       Factor();
       Term1();
   }
   
   void Expression1()
   {//Expression1-> Add_Op Term Expression1 | EPSILON
      if(lookahead.getToken().equals("Add_Op"))
    {
        nextToken();
        Term();
        Expression1();
      }
   }
   
   void Expression()
   {//Expression-> term Expression1
       Term();
       Expression1();
   }
   
   void Continue_statment()
   {
       //Continue _Statement →continue ;
       if(lookahead.getToken().equals("continue"))
           nextToken();
        if (lookahead.getToken().equals(";"))
         nextToken();
        else{error("; not found", lookahead.getLine());}
   }
   
   void Break_statment()
   {
       //Break _Statement→ break ;
        if(lookahead.getToken().equals("break"))
        {  
            nextToken();
        }
        
         if (lookahead.getToken().equals(";"))
            nextToken();
         else{error("; not found", lookahead.getLine());}
             
   }
   
   void While_Statement()
   {
     //  While _Statement → while(Condition _Expression) Block Statements
      if(lookahead.getToken().equals("while")){
          nextToken();
          
          if(lookahead.getToken().equals("("))
          {
               nextToken();
               Condition_Expression();
               if(lookahead.getToken().equals(")"))
                   nextToken();
               else{
                   error(") not found", lookahead.getLine());
               }
          }
          Block_Statements();
          
      }
   }//end of while statment
   
   void  Comparison_Op()
   {
       //Comparison _Op → == | != | > | >= | < | <= 
       if(lookahead.getToken().equals("Comparison_Op")){//com_op==com_op
       nextToken();
   }}
   
  void Condition()
  {
      //Condition→ Expression Comparison _Op Expression
      
          Expression();
          Comparison_Op();
          Expression();
      
  }
  
  void Conditon_Op()
  {
  //Condition _Op → and | or
      if(lookahead.getToken().equals("Condition_Op"))
      {
          nextToken();
      }
      
  }
  
  void Return_Statement1()
  {//Return_Statement1->Expression ; | ;
      if(!lookahead.getToken().equals(";"))
      {
          Expression();
          if(lookahead.getToken().equals(";"))
              nextToken();
          else{error("; not found", lookahead.getLine());}
      }else{
          if(lookahead.getToken().equals(";"))
              nextToken();
          else{error("; not found", lookahead.getLine());}
      }
  }
  
  void Return_Statement()
  {//Return_ Statement->return Return_ Statement1
      if(lookahead.getToken().equals("return"))
          nextToken();
      
      Return_Statement1();
  }
  
  void Condition_Expression1()
  {//Condition_Expression1->condition_op condition | Epslion
      if(lookahead.getToken().equals("Condition_Op"))
      {
          nextToken();
          Condition();
      }
      
  }
  
 void Condition_Expression()
 {//Condition_Expression->condition Condition_Expression1
     Condition();
     Condition_Expression1();
 }
 
 void Block_Statements()
 {//Block Statements→{ statements }
     if(lookahead.getToken().equals("{"))
     {
         nextToken();
     }else{error("{ not found", lookahead.getLine());}
         Statements();
         if(lookahead.getToken().equals("}"))
     {
         nextToken();
     }else{error("} not found", lookahead.getLine());}
     
     
     
 }
 
 void if_statment()
 {
     //If _Statement→ if (Condition _Expression) Block Statements
      if(lookahead.getToken().equals("if"))
      {  nextToken();
      if(lookahead.getToken().equals("("))
      {
          nextToken();
      }else{error("( not found", lookahead.getLine());}
          Condition_Expression();
          if(lookahead.getToken().equals(")"))//el mafrod a3rf el left braket wa el write lwa7dohm m4 fel other
            {
                 nextToken();
            } 
            else{error(") not found", lookahead.getLine());}
         Block_Statements();
      }
      
}
 
 
 void NonEmpty_Argument_List1()
 {//NonEmpty_Argument_List1->,Expression NonEmpty_Argument_List1 | Epslon
    
     if(lookahead.getToken().equals(",")){
     nextToken();
     Expression();
     NonEmpty_Argument_List1();
     
     }
 }
 
 
 void NonEmpty_Argument_List()
 {//NonEmpty_Argument_List->Expression NonEmpty_Argument_List1
     Expression();
     NonEmpty_Argument_List1();
 }
 
 void Argument_List()
 {//Argument_List →empty | NonEmpty_Argument_List
     if(lookahead.getToken().equals("ID")  || lookahead.getToken().equals("Number")){
         NonEmpty_Argument_List();
      
     }
 }
 
 void Func_Call()
 {//Func _Call → ID (Argument_List) ;
     if(lookahead.getToken().equals("ID")){
         nextToken();
     }else{error("ID not found", lookahead.getLine());}
         
        if(lookahead.getToken().equals("("))
        {
            nextToken();
        }else{error("Expected ( not found", lookahead.getLine());}
            Argument_List();

        if(lookahead.getToken().equals(")")){
            nextToken();
        }else{error("Expected ) not found", lookahead.getLine());}

        if(lookahead.getToken().equals(";")){
            nextToken();
        }else{error("Expected ; not found", lookahead.getLine());}
     
 }
 
 void Assignment()
 {//Assignment→ VarDeclaration = Expression;
     VarDeclaration();
     if(lookahead.getToken().equals("="))
     {
         nextToken();
     }else{error("Expected = not found", lookahead.getLine());}
     
        Expression();
//        if(lookahead.getToken().equals(";")){
//            nextToken();
//        }else{error("Expected ; not found", lookahead.getLine());}
     
 }
 
 void Statement()
 {//Statement→Assignment | If _Statement | While _Statement | Return_ Statement | Break_Statement| Continue _Statement | read (ID ); | write (Expression); |
     // ekteb de ya adel 
 if(lookahead.getToken().equals("if"))
     if_statment();
 else if(lookahead.getToken().equals("while"))
     While_Statement();
else if(lookahead.getToken().equals("return"))
     Return_Statement();
else if(lookahead.getToken().equals("break"))
     Break_statment();
else if(lookahead.getToken().equals("continue"))
     Break_statment();
else if(lookahead.getToken().equals("read"))
        {
            nextToken();
            if(lookahead.getToken().equals("(")){
                nextToken();
            }else{error("Expected ( not found", lookahead.getLine());}
            
            if(lookahead.getToken().equals("ID"))
                nextToken();
            else{
                error("Expected ID not found", lookahead.getLine());
            }
            if(lookahead.getToken().equals(")"))
                nextToken();
            else
                error("Expected ) not found", lookahead.getLine());
             if(lookahead.getToken().equals(";"))
                nextToken();
            else
                error("Expected ; not found", lookahead.getLine());
        }
else if(lookahead.getToken().equals("write"))
        {
            nextToken();
            if(lookahead.getToken().equals("("))
                nextToken();
            else{error("Expected ( not found", lookahead.getLine());}
            Expression();
            if(lookahead.getToken().equals(")"))
                nextToken();
            else
                error("Expected ) not found", lookahead.getLine());
             if(lookahead.getToken().equals(";"))
                nextToken();
            else
                error("Expected ; not found", lookahead.getLine());
        }
else if(lookahead.getToken().equals("break"))
     Break_statment();
 else{
    Assignment();
}
 }
 
 void Statements()
 {//Statements→empty | Statement Statements
     
     if(lookahead.getToken().equals("if")||lookahead.getToken().equals("while")||lookahead.getToken().equals("return")||
             lookahead.getToken().equals("break")||lookahead.getToken().equals("continue")||
             lookahead.getToken().equals("read")||lookahead.getToken().equals("write")/*||lookahead.getToken().equals("Type")*/)
            {
                Statement();
                Statements();
            }
 }
 
 void ID_List1()
 {//ID_List->, ID ID_List1 |Epslon
     
         if(lookahead.getToken().equals(","))
         {
             nextToken();
             if(lookahead.getToken().equals("ID"))
             {
                 nextToken();
              ID_List1();}
             else{error("Expected ID not found", lookahead.getLine());}
                
         }
 }

 
 void ID_List()
 {//ID_List-> ID ID_List1
     if(lookahead.getToken().equals("ID"))
     {
         nextToken();
         ID_List1();/*
         if(look)
         if(  nextToken(lookahead.getToken().equals("ID")&&list(1).equals(",")))
         nextToken();
         if(lookahead.getToken().equals(","))
         nextToken();
         if(lookahead.getToken().equals("ID"))
         nextToken();
         */
     }
 }
 
 void VarDeclaration()
 {//VarDeclaration→ empty | Type ID_List ; VarDeclaration
     //VarDeclaration→ {Type ID_List ; }
     if(lookahead.getToken().equals("Type") && !this.tokens.get(2).getToken().equals("("))
     {
         nextToken();
         ID_List();
         if(lookahead.getToken().equals("="))
         Assignment();
         if(lookahead.getToken().equals(";"))
         {
             nextToken();
         }else{error("unfound ;",lookahead.getLine());}
             VarDeclaration();
         
     }
 }
 
 void Non_Empty_List1()
 {//Non_Empty_List1->,Type ID Non_Empty_List1 | Epslon
     
     if(lookahead.getToken().equals(","))
     {
         nextToken();
     
     if(lookahead.getToken().equals("Type"))
     {
         nextToken();
     }else{error("Expected Type not found", lookahead.getLine());}
     if(lookahead.getToken().equals("ID"))
     {
         nextToken();
     }else{error("Expected ID not found", lookahead.getLine());}
         Non_Empty_List1();
     }
}
 
 
 void Non_Empty_List()
 {//Non-Empty List->Type ID Non-Empty List1
     if(lookahead.getToken().equals("Type"))
     {
         nextToken();
         if(lookahead.getToken().equals("ID"))
         {
             nextToken();
         }else{error("Expected ID not found", lookahead.getLine());}
             Non_Empty_List1();
         
     }
 }
 void ParameterList()
 {//ParameterList →empty | void | Non-Empty List
     
         
      if(lookahead.getToken().equals("Type"))
     {
            Non_Empty_List();
     }
 }
 
 void Type()
 {//Type →int | void | binary | float
     if(lookahead.getToken().equals("Type"))
     {
         nextToken();
     }
 }
 
 void Func_Decl()
 {//Func Decl →Type ID (ParameterList)
     if(lookahead.getToken().equals("Type"))
     {
         nextToken();
         if(lookahead.getToken().equals("ID"))
         {
             nextToken();
         }else{error("Expected ID not found", lookahead.getLine());}
         if(lookahead.getToken().equals("("))
         {
             nextToken();
         }else{error("Expected ( not found", lookahead.getLine());}
             ParameterList();
         
         if(lookahead.getToken().equals(")"))
         {
             nextToken();
         }else{error("Expected ) not found", lookahead.getLine());}
     }
 }
 
 void MethodDeclaration1() //8alt hena
 {//MethodDeclaration1->; | {VarDeclaration statments}
     if(lookahead.getToken().equals(";"))
     {
         nextToken();
     }else if(lookahead.getToken().equals("{"))
            {
                nextToken();
                VarDeclaration(); //8alt
                Statements();
                if(lookahead.getToken().equals("}"))
                {
                    nextToken();
                }else{error("Expected } not found", lookahead.getLine());}
            }else{/*error*/}
 }
 
 void MethodDeclaration()//at2ked an deh sa7 34an el mo4kla btdbd2 hna
 {//MethodDeclaration→ Func Decl MethodDeclaration1
     Func_Decl();
     MethodDeclaration1();
 }
 
 void Class_Implementation()
 {/*Class_Implementation→ VarDeclaration Class_Implementation|
MethodDeclaration Class_Implementation | Comment Class_Implementation |
using_command Class_Implementation| Func _Call Class_Implementation |empty*/
     //if(lookahead.getToken().)
     if(((lookahead.getToken().equals("Type")))&&(this.tokens.get(2).getToken().equals("(")) )
     {
         MethodDeclaration();
         Class_Implementation();
     }
     else if ((lookahead.getToken().equals("Type")) )
     {
         VarDeclaration();
         Class_Implementation();
     }
     else if(lookahead.getToken().equals("comment"))
     {
         Comment();
         Class_Implementation();
     }
     else if (lookahead.getToken().equals("using"))
     {
         using_command();
         Class_Implementation();
     }
     else if (lookahead.getToken().equals("ID"))
     {
         Func_Call();
         Class_Implementation();
     }
     
 }
 
 void ClassDeclaration()
 {//ClassDeclaration→ class ID{ Class_Implementation}
     if(lookahead.getToken().equals("class"))
     {
         nextToken();
         if(lookahead.getToken().equals("ID"))
         {
             nextToken();
         }else{error("Expected ID not found", lookahead.getLine());}
         if(lookahead.getToken().equals("{"))
         {
             nextToken();
         }else{error("Expected { not found", lookahead.getLine());}
             Class_Implementation();
             if(lookahead.getToken().equals("}"))
             {
                 nextToken();
             }else {error("Expected } not found", lookahead.getLine());}
         
     }
 }
 
 void Program()
 {//Program → Program ClassDeclaration  End
     if(lookahead.getToken().equals("program"))
     {
         nextToken();
     }else{error("Expected Program not found", lookahead.getLine());}
         ClassDeclaration();
         if(lookahead.getToken().equals("end"))
         {
             nextToken();
         }
     
 }
 //rejwct
}

