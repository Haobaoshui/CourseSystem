package com.haobaoshui.course.exception;

public class NaturalClassNotExistException extends Exception
{
   

	/**
	 * 
	 */
	private static final long serialVersionUID = 593553535839526239L;

	public NaturalClassNotExistException(String errorMsg)
    {
        super(errorMsg);
    }
}