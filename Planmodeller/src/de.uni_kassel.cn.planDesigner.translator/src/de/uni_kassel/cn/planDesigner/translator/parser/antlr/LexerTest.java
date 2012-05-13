// Copyright 2009 Distributed Systems Group, University of Kassel
// This program is distributed under the GNU Lesser General Public License (LGPL).
//
// This file is part of the Carpe Noctem Software Framework.
//
//    The Carpe Noctem Software Framework is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    The Carpe Noctem Software Framework is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
/**
 * 
 */
package de.uni_kassel.cn.planDesigner.translator.parser.antlr;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Token;

/**
 * @author till
 *
 */
public class LexerTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		UtilitiesLexer lexer;
		Token token;

		ANTLRStringStream ss = new ANTLRStringStream("1.5 - Dist(a,       b)");
		lexer  = new UtilitiesLexer(ss);

	    while(true)
	    {
	        token = lexer.nextToken();
	        if(token.getType() == Token.EOF)
	        {
	            break;
	        }

	        System.out.println("Token: ‘" + token.getText() + "‘");
	    }

	}

}
