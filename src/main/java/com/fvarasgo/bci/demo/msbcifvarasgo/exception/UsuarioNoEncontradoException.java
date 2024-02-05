package com.fvarasgo.bci.demo.msbcifvarasgo.exception;

public class UsuarioNoEncontradoException extends RuntimeException {


	public UsuarioNoEncontradoException(String id) {
		super(id);
	}

	private static final long serialVersionUID = 1L;

}
