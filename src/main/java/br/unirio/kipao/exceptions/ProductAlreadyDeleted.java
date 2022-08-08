package br.unirio.kipao.exceptions;

public class ProductAlreadyDeleted extends Exception {

	private static final long serialVersionUID = 5328481226072174421L;

	public ProductAlreadyDeleted() {
		super("Esse produto já foi deletado ou não consta na base de dados");
	}

}
