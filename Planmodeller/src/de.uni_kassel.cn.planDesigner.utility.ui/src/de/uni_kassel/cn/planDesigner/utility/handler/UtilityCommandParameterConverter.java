package de.uni_kassel.cn.planDesigner.utility.handler;

import org.eclipse.core.commands.AbstractParameterValueConverter;
import org.eclipse.core.commands.ParameterValueConversionException;

public class UtilityCommandParameterConverter extends
		AbstractParameterValueConverter
{

	public UtilityCommandParameterConverter()
	{
	}

	@Override
	public Object convertToObject(String parameterValue)
			throws ParameterValueConversionException
	{
		System.out.println("converting into Long");
		return null;
	}

	@Override
	public String convertToString(Object parameterValue)
			throws ParameterValueConversionException
	{
		// TODO Auto-generated method stub
		return null;
	}

}
