package com.onndoo.booker.exceptions;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;

public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

	   private ExceptionHandlerFactory parent;

	   // this injection handles jsf
	   public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
	    this.parent = parent;
	   }
	  
	    @Override
	    public ExceptionHandler getExceptionHandler() {
	        
	        ExceptionHandler handler = new CustomExceptionHandler(parent.getExceptionHandler());
	        
	        return handler;
	    }
	    
	}