package br.unirio.kipao.exceptions;

public class OrderNotExists extends Exception {

	private static final long serialVersionUID = 7008586869155311029L;
	
	public OrderNotExists() {
		super("O pedido não consta na base de dados");
	}

}
