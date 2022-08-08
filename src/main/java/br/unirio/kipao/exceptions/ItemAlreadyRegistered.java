package br.unirio.kipao.exceptions;

public class ItemAlreadyRegistered extends Exception {

	private static final long serialVersionUID = 5280566917490217293L;
	
	public ItemAlreadyRegistered() {
		super("Um item com esse nome já foi cadastrado");
	}

}
