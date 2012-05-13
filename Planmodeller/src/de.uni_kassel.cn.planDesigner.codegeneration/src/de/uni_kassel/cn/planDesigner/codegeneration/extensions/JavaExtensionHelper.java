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
package de.uni_kassel.cn.planDesigner.codegeneration.extensions;

import de.uni_kassel.cn.alica.UtilityModes;

/**
 * @author till
 *
 */
public class JavaExtensionHelper {
	public static int getMaxModeValue()
	{
		return UtilityModes.MAX_VALUE;
	}

	public static int getMinModeValue()
	{
		return UtilityModes.MIN_VALUE;
	}

	public static int getDcModeValue()
	{
		return UtilityModes.DC_VALUE;
	}

	public static int getAvgModeValue()
	{
		return UtilityModes.AVG_VALUE;
	}
}
