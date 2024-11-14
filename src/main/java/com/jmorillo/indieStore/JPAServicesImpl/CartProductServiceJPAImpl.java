package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jmorillo.indieStore.model.CartProduct;
import com.jmorillo.indieStore.services.CartProductService;

@Service
@Transactional
public class CartProductServiceJPAImpl implements CartProductService{
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<CartProduct> obtainAllCartProducts() {
		return entityManager.createQuery("select op from OrderProduct op").getResultList();
	}

}
