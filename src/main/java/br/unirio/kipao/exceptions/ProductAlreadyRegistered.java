package br.unirio.kipao.exceptions;

public class ProductAlreadyRegistered extends Exception {

	private static final long serialVersionUID = 5280566917490217293L;
	
	public ProductAlreadyRegistered() {
		super("Um produto com esse nome já foi cadastrado");
	}

}
