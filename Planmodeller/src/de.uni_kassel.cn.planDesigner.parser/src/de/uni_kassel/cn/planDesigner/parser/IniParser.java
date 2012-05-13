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
package de.uni_kassel.cn.planDesigner.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.ini4j.Ini;
import org.ini4j.InvalidIniFormatException;
import org.osgi.framework.Bundle;


/**
 * @author till
 * 
 */
public class IniParser implements ITranslationMapProvider
{

	private HashMap<String, Entity> entityMap;

	private Ini ini;

	private IniParser()
	{
		Bundle b = ParserActivator.getDefault().getBundle();
		URL baseURL = b.getEntry("/inis/test.ini");

		URL fileURL;
		try
		{
			fileURL = FileLocator.toFileURL(baseURL);
		} catch (IOException e1)
		{
			e1.printStackTrace();
			return;
		}

		String file = new Path(fileURL.getPath()).toOSString();

		ini = new Ini();
		try
		{
			ini.load(new FileReader(file));
		} catch (InvalidIniFormatException e)
		{
			ini = null;
			e.printStackTrace();
			return;
		} catch (FileNotFoundException e)
		{
			ini = null;
			e.printStackTrace();
			return;
		} catch (IOException e)
		{
			ini = null;
			e.printStackTrace();
			return;
		}

		parseIni();
	}

	private static IniParser instance = null;

	public static IniParser get()
	{
		if (instance == null)
		{
			instance = new IniParser();
		}

		return instance;
	}

	/**
	 * 
	 */
	private void parseIni()
	{
		if (ini == null)
		{
			return;
		}

		try
		{
			parseAliases();
			parseMethods();
			parseShortcuts();
		} catch (MalformedIniException e)
		{
			e.printStackTrace();
		}

		needsResolving = true;
		resolveLinks();
	}

	private boolean needsResolving;
	
	private void resolveLinks()
	{
		if(needsResolving)
		{
			needsResolving = false;
			
			HashMap<String, Entity> keyMap = getKeyMap();
			List<String> list = new LinkedList<String>(keyMap.keySet());
			for(String key : list)
			{
				Entity e = keyMap.get(key);
				
				// data
				if(keyMap.containsKey(e.getData()))
				{
					e = keyMap.get(e.getData());
					needsResolving = true;
				}
			}
		}
		
//		resolveLinks();
	}

	/**
	 * 
	 */
	private void parseMethods() throws MalformedIniException
	{
		Ini.Section sec = ini.get("Methods");

		if (sec != null)
		{
			parseSection(sec, true);
		}
	}

	/**
	 * @param sec
	 */
	private void parseShortcuts() throws MalformedIniException
	{
		Ini.Section sec = ini.get("Shortcuts");

		if (sec != null)
		{
			parseSection(sec, true);
		}
	}

	/**
	 * @param sec
	 */
	private void parseAliases() throws MalformedIniException
	{
		Ini.Section sec = ini.get("Aliases");

		if (sec != null)
		{
			parseSection(sec, false);
		}
	}

	/**
	 * @param sec
	 * @throws MalformedIniException
	 */
	private void parseSection(Ini.Section sec, boolean typedKey) throws MalformedIniException
	{
		for (String key : sec.keySet())
		{
			String signature = sec.get(key);

			String retVal = null;
			String term = null;
			SortedMap<String, String> vars = new TreeMap<String, String>();
			if (typedKey)
			{
				int colonIndex = key.lastIndexOf(':');
				if (colonIndex == -1 && (key.split(":").length < 2))
				{
					throw new MalformedIniException("No term name and/or return value specified: " + key + " = " + signature);
				}

				retVal = key.substring(colonIndex + 1);
				int openBracketIndex = key.indexOf('(');
				int closeBracketIndex = key.indexOf(')');
				if ((openBracketIndex == -1 && closeBracketIndex != -1) || (openBracketIndex != -1 && closeBracketIndex == -1))
				{
					throw new MalformedIniException("No matching bracket: " + key + " = " + signature);
				}

				if (openBracketIndex == -1)
				{
					term = key.substring(0, colonIndex);
				} else
				{
					term = key.substring(0, openBracketIndex);

					String varsString = key.substring(openBracketIndex + 1, closeBracketIndex);
					for (String varString : varsString.split(","))
					{
						int varColonIndex = varString.trim().indexOf(':');
						if (varColonIndex == -1)
						{
							throw new MalformedIniException("No type for variable: " + varString.trim() + " in: " + key + " = " + signature);
						}

						vars.put(varString.trim().substring(0, varColonIndex), varString.trim().substring(varColonIndex + 1));
					}
				}
			} else
			{
				term = key;
			}

			String data = null;
			Map<String, String> flags = Collections.emptyMap();
			int openBracesIndex = signature.indexOf('{');
			int closeBracesIndex = signature.indexOf('}');
			if ((openBracesIndex == -1 && closeBracesIndex != -1) || (openBracesIndex != -1 && closeBracesIndex == -1))
			{
				throw new MalformedIniException("No matching flag braces: " + key + " = " + signature);
			}

			if (openBracesIndex == -1)
			{
				data = signature;
			} else
			{
				data = signature.substring(0, openBracesIndex);

				String flagsString = signature.substring(openBracesIndex + 1, signature.length() - 1);

				flags = new HashMap<String, String>();
				for (String flag : flagsString.split(";"))
				{
					int flagColonIndex = flag.indexOf(':');
					String flagTerm = null;
					String flagData = null;

					if (flagColonIndex == -1)
					{
						flagTerm = flag.trim();
					} else
					{
						flagTerm = flag.substring(0, flagColonIndex).trim();
						flagData = flag.substring(flagColonIndex + 1, flag.length()).trim();
					}

					flags.put(flagTerm, flagData);
				}
			}

			if (typedKey)
			{
				if (term == null || retVal == null || data == null)
				{
					throw new MalformedIniException("FOOOOOOOOOOBAR in: " + key + " = " + signature);
				}
			} else
			{
				if (term == null || data == null)
				{
					throw new MalformedIniException("FOOOOOOOOOOBAR in: " + key + " = " + signature);
				}
			}

			getKeyMap().put(term, new Entity(retVal, vars, data, flags));
		}
	}

	public HashMap<String, Entity> getKeyMap()
	{
		if (this.entityMap == null)
		{
			this.entityMap = new HashMap<String, Entity>();
		}

		return this.entityMap;
	}

	public Entity getValue(String text)
	{
		return getKeyMap().get(text);
	}

	public boolean isInMap(String text)
	{
		return getKeyMap().containsKey(text);
	}

}
