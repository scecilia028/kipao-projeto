package br.unirio.kipao.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Iterables;
import com.google.maps.errors.NotFoundException;

import br.unirio.kipao.model.Customer;
import br.unirio.kipao.model.Item;
import br.unirio.kipao.model.Order;
import br.unirio.kipao.model.Product;
import br.unirio.kipao.repository.ItemRepository;
import br.unirio.kipao.repository.OrderRepository;
import br.unirio.kipao.repository.ProductRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	public Iterable<Item> getItems() {
		return itemRepository.findAll();
	}

	/** 
	 * @param item nome do produto
	 * @param customer usuario logado
	 * @return
	 * @throws NotFoundException 
	 */
	public Item saveItem(Item item, Customer customer) throws NotFoundException {
		if (!Iterables.isEmpty(productRepository.findByName("Pao de hamburguer")) && 
				orderRepository.findByCustomerId(customer.getId()) != null) {
			
			ArrayList<Order> order = orderRepository.findByCustomerId(customer.getId());
			item.setOrder(order.get(0));
			
			ArrayList<Product> product = productRepository.findByName("Pao de hamburguer");  
			item.setProduct(product.get(0));
			
			Item savedItem = itemRepository.save(item);
			itemRepository.refresh(savedItem);
			
			return savedItem;
		}
		else throw new NotFoundException("Não foi possível encontrar o produto.");
	}

	public Item deleteItem(Item item) {
		return itemRepository.findById(item.getId()).get();
	}

}
