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
package de.uni_kassel.cn.planDesigner.translator;

import java.util.LinkedList;
import java.util.List;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

import de.uni_kassel.cn.planDesigner.parser.Entity;
import de.uni_kassel.cn.planDesigner.parser.ITranslationMapProvider;
import de.uni_kassel.cn.planDesigner.parser.ParserActivator;
import de.uni_kassel.cn.planDesigner.translator.parser.antlr.UtilitiesLexer;
import de.uni_kassel.cn.planDesigner.translator.parser.antlr.UtilitiesParser;
import de.uni_kassel.cn.planDesigner.translator.parser.antlr.nodes.BlockNode;
import de.uni_kassel.cn.planDesigner.translator.parser.antlr.nodes.KeywordNode;

/**
 * @author till
 * 
 */
public class Translator
{
	private static Translator instance;

	public static Translator getInstance()
	{
		if (instance == null)
		{
			instance = new Translator();
		}

		return instance;
	}

	private static UtilitiesLexer lexer;

	private static CommonTokenStream ts;

	private static UtilitiesParser parser;

	private Translator()
	{
		lexer = new UtilitiesLexer();
		ts = new CommonTokenStream(lexer);
		parser = new UtilitiesParser(ts);
	}

	private static String internalTranslate(String input)
	{
		getInstance();

		String retVal = "";
		lexer.setCharStream(new ANTLRStringStream(input));
		ts = new CommonTokenStream(lexer);
		parser.setTokenStream(ts);

		try
		{
			UtilitiesParser.utility_return utility = parser.utility();

			CommonTree tree = (CommonTree) utility.getTree();
			if (parser.getNumberOfSyntaxErrors() <= 0 && tree.token.getInputStream() != null)
			{
				retVal = translateTree(tree);
				System.out.println("Translation: " + retVal);
			}
		} catch (RecognitionException e)
		{
			e.printStackTrace();
		}

		if (retVal == null)
		{
			retVal = "";
		}

		return retVal;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unused")
	private static void printTree()
	{
		lexer.reset();
		try
		{
			UtilitiesParser.utility_return r = parser.utility();
			System.out.println(((CommonTree) r.getTree()).toStringTree());
		} catch (RecognitionException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param retVal
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String traverseLexer(String retVal)
	{
		try
		{

			Token token = lexer.nextToken();
			while (token.getType() != Token.EOF)
			{
				ITranslationMapProvider p = ParserActivator.getDefault().getParser();
				if (p.isInMap(token.getText()))
				{
					Entity entity = p.getValue(token.getText());
					if (entity.needsArgs())
					{
					}

					if (entity != null)
					{
						retVal += entity.assemble();
					} else
					{
						retVal += token.getText();
					}
				}

				token = lexer.nextToken();
			}
		} catch (Exception e)
		{
			System.err.println("Caught error: " + e.toString());
		}
		return retVal;
	}

	@SuppressWarnings("unchecked")
	public static String translateTree(CommonTree tree) throws RecognitionException
	{
		String retVal = tree.getText();
		ITranslationMapProvider mapProvider = getTranslationMapParser();
		List<CommonTree> children = tree.getChildren();

		if (tree instanceof KeywordNode)
		{
			KeywordNode node = (KeywordNode) tree;
			Entity entity = mapProvider.getValue(node.getText());
			
			// method
			if (mapProvider.isInMap(node.getText()))
			{
				if(entity.needsArgs())
				{
					List<String> childrenList = new LinkedList<String>();
					if (children != null)
					{
						for (CommonTree t : children)
						{
							childrenList.add(translateTree(t));
						}
					}
				
	
					entity.setVars(childrenList);

				}
				
				if (entity != null)
				{
					retVal = entity.assemble();
					entity.clear();
				}
			} 
			
			// constant
			if(retVal == null || !mapProvider.isInMap(node.getText()))
//			if(retVal == null || !mapProvider.isInMap(node.getText()) ||  !entity.needsArgs())
			{
				retVal = tree.getText();
				if (children != null)
				{
					retVal += "(";
					for (CommonTree t : children)
					{
						retVal += translateTree(t);
						retVal += " ,";
					}

					int end = retVal.trim().lastIndexOf(",") - 1;
					retVal = retVal.substring(0, end);
					retVal += ")";
				}
			}
		} else if (children != null)
		{
			if (tree instanceof BlockNode)
			{
				retVal = "(";
				for (CommonTree t : children)
				{
					retVal += translateTree(t);
				}
				retVal += ")";
			} else
			{
				retVal = "";
				for (CommonTree t : children)
				{
					retVal += translateTree(t);
					retVal += " ";
					retVal += tree.getText();
					retVal += " ";
				}

				int end = retVal.trim().lastIndexOf(' ');
				retVal = retVal.substring(0, end);
			}
		}

		return retVal;
	}

	private static ITranslationMapProvider tParser;

	private static ITranslationMapProvider getTranslationMapParser()
	{
		if (tParser == null)
		{
			tParser = ParserActivator.getDefault().getParser();
		}

		return tParser;
	}

	public static String translate(String input)
	{
		return Translator.internalTranslate(input);
	}
}
