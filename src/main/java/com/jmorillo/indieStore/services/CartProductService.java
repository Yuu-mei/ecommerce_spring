package com.jmorillo.indieStore.services;

import java.util.List;

import com.jmorillo.indieStore.model.CartProduct;

// This will stay for now even if I haven't used it yet, may come in handy
public interface CartProductService {
	List<CartProduct> obtainAllCartProducts();
}
