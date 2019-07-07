package br.com.fiap.springdatajpa.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import br.com.fiap.springdatajpa.advice.ResponseError;
import br.com.fiap.springdatajpa.model.Category;
import br.com.fiap.springdatajpa.model.Inventory;
import br.com.fiap.springdatajpa.model.SalesOrder;
import br.com.fiap.springdatajpa.model.SalesOrderItem;
import br.com.fiap.springdatajpa.repository.CategoryRepository;
import br.com.fiap.springdatajpa.repository.InventoryRepository;
import br.com.fiap.springdatajpa.repository.SalesOrderRepository;
import br.com.fiap.springdatajpa.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.springdatajpa.model.Product;
import br.com.fiap.springdatajpa.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;
	private SalesOrderRepository salesOrderRepository;
	private InventoryRepository inventoryRepository;

	@Autowired
	public ProductServiceImpl(final ProductRepository productRepository,
							  final CategoryRepository categoryRepository,
							  final SalesOrderRepository salesOrderRepository,
							  final InventoryRepository inventoryRepository){
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
		this.salesOrderRepository = salesOrderRepository;
		this.inventoryRepository = inventoryRepository;
	};

	@Override
	public List<Product> getAllProduct() {
		List<Product> product = StreamSupport.stream(productRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return product;
	}

	@Override
	public Product getProductById(Integer id) {
		return productRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));
	}

	@Override
	public Product createProduct(Product product) {
		List<Integer> ids = new ArrayList<>();
		product.getCategories().stream().forEach(category -> ids.add(category.getId()));
		Optional<List<Category>> categories = categoryRepository.findByIdIn(ids);

		if (!categories.isPresent()) {
			throw new ResponseError(HttpStatus.CONFLICT, "Categorias não encontradas.");
		} else {
			if (categories.get().size() != ids.size()){
				throw new ResponseError(HttpStatus.CONFLICT, "Nem todas as categorias foram encontradas.");
			}
		}

		return productRepository.save(product);
	}

	@Override
	public void updateProduct(Product product) {
		Product storedProduct = productRepository.findById(product.getId()).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));

		List<Integer> ids = new ArrayList<>();
		product.getCategories().stream().forEach(category -> ids.add(category.getId()));
		Optional<List<Category>> categories = categoryRepository.findByIdIn(ids);

		if (!categories.isPresent()) {
			throw new ResponseError(HttpStatus.CONFLICT, "Categorias não encontradas.");
		} else {
			if (categories.get().size() != ids.size()){
				throw new ResponseError(HttpStatus.CONFLICT, "Nem todas as categorias foram encontradas.");
			}
		}

		storedProduct.setName(product.getName());
		storedProduct.setDescription(product.getDescription());
		storedProduct.setPrice(product.getPrice());
		storedProduct.setCategories(product.getCategories());

		productRepository.save(storedProduct);
	}

	@Override
	public void deleteProduct(Integer id) {
		Product product = productRepository.findById(id).orElseThrow(() ->
				new ResponseError(HttpStatus.NOT_FOUND, "Produto não encontrado"));

		if (inventoryRepository.findByProductId(product.getId()).isPresent()) {
			throw new ResponseError(HttpStatus.PRECONDITION_FAILED, "O produto informado possui estoque associado.");
		}

		List<SalesOrder> salesOrders = (List<SalesOrder>) salesOrderRepository.findAll();

		for (SalesOrder salesOrder : salesOrders) {
			for (SalesOrderItem salesOrderItem : salesOrder.getItens()) {
				if (salesOrderItem.getId().getProduct().getId().equals(product.getId()))
					throw new ResponseError(HttpStatus.PRECONDITION_FAILED, "O produto informado possui ao menos um pedido de venda associado.");
			}
		}

		productRepository.delete(product);
		
	}

}
