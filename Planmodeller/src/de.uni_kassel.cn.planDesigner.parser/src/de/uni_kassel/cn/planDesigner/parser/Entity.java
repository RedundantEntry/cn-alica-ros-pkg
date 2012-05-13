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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author till
 *
 */
public class Entity
{
	private String returnVal;

	private Map<String, String> variables;

	private List<String> vars;

	private String data;

	private Map<String, String> flags;

	public Entity(String returnVal, Map<String, String> variables, String data, Map<String, String> flags)
	{
		this.data = data;
		this.flags = flags;
		this.returnVal = returnVal;
		this.variables = variables;
	}
	
	@Override
	public String toString()
	{
		StringBuffer buf = new StringBuffer();

		buf.append("(");
		for(String key : variables.keySet())
		{
			buf.append(key+":"+variables.get(key));
		}
		buf.append(")");
		
		buf.append(":"+returnVal+"::");
		
		buf.append(data);
		
		buf.append("{");
		for(String key : flags.keySet())
		{
			buf.append(key+":"+flags.get(key));
		}
		buf.append("}");
		
		return buf.toString();
	}

	public String assemble()
	{
		String retVal = data;
		
		if(vars != null)
		{
			List<String> keyList = new LinkedList<String>(variables.keySet());
			for(String var : keyList)
			{
				retVal = retVal.replace(var, vars.get(keyList.indexOf(var)));
			}
		}
		else
		{
//			retVal = null;
		}
		
		return retVal;
	}

	public void setVars(List<String> vars)
	{
		if(variables == null || (vars.size() != variables.size()))
		{
			return; // TODO: maybe throw exception...
		}
		
		this.vars = vars;
	}

	public boolean needsArgs()
	{
		return variables.size() > 0 ? true : false;
	}
	
	public void clear()
	{
		this.vars = null;
	}

	public String getData()
	{
		return data;
	}

	public String getReturnVal()
	{
		return returnVal;
	}

	public Map<String, String> getVariables()
	{
		return variables;
	}
}
