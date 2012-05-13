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
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

/**
 * @author till
 * 
 */
public class ParserTest
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		ANTLRStringStream ss = new ANTLRStringStream("((5) + 6) * 7");
		UtilitiesLexer lexer = new UtilitiesLexer(ss);
		CommonTokenStream ts = new CommonTokenStream(lexer);
		UtilitiesParser parser = new UtilitiesParser(ts);

		try
		{
			UtilitiesParser.utility_return r = parser.utility();
			CommonTree tree = (CommonTree)r.getTree();

			System.out.println(tree.toStringTree());
		} catch (RecognitionException e)
		{
			System.err.println("Caught error: "+ e.toString());;
		}
	}

}
