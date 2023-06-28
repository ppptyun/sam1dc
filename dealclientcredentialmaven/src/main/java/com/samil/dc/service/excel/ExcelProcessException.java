package com.samil.dc.service.excel;

public class ExcelProcessException extends Exception{

	private static final long serialVersionUID = -8533434983408994377L;

	public ExcelProcessException(String message) {
		super(message);
	}
	public ExcelProcessException(String msg, Exception e)
    {
        super(msg, e);
    }

    public ExcelProcessException(String msg, Throwable t)
    {
        super(msg, t);
    }
}
