grammar Utilities;

options {
	language = Java;
	output = AST;
}

@header{
  package de.uni_kassel.cn.planDesigner.translator.parser.antlr;
  
  import de.uni_kassel.cn.planDesigner.translator.parser.antlr.nodes.*;
}

@lexer::header{
  package de.uni_kassel.cn.planDesigner.translator.parser.antlr;
}

utility
  : util
  | EOF  // when an empty string is checked...
  ;

util
//  : ('-'^)? paran_val (OPERATOR^ paran_val)* 
  : ding (OPERATOR^ ding)*
  ;
  
ding
//  : ('-'^)? paran_block
  : val
  | paran_block
  ;
  
paran_block
  : L_PARAN<BlockNode>^ util R_PARAN!
  ;

val
  : number
  | keyword^ (paran_arg_block)?
  ;

paran_arg_block
  : L_PARAN! ding (','! ding)* R_PARAN!
  | L_PARAN! R_PARAN!
  ;

keyword
  : WORD<KeywordNode>
  ;

number
  : NUMBER // INT does not work here... why???
  | INT
  | FLOAT
  ;

NUMBER: '0'..'9';

INT: (NUMBER)+;

FLOAT: INT'.'INT;

WORD: ('a'..'z' | 'A'..'Z' | '_' | NUMBER)+;

L_PARAN: '(';

R_PARAN: ')';

WS: (' ' | '\t' | '\r' | '\n')+ { $channel=HIDDEN; };

OPERATOR: '-' | '+' | '*' | '/';

SEPERATOR: ',';
