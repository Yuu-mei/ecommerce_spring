package com.jmorillo.indieStore.JPAServicesImpl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmorillo.indieStore.SQLConstants.SQLConstants;
import com.jmorillo.indieStore.model.Cart;
import com.jmorillo.indieStore.model.CartProduct;
import com.jmorillo.indieStore.model.Order;
import com.jmorillo.indieStore.model.OrderProduct;
import com.jmorillo.indieStore.model.User;
import com.jmorillo.indieStore.model.dataTypes.OrderSummary;
import com.jmorillo.indieStore.model.orderStatus.OrderStatus;
import com.jmorillo.indieStore.services.CartService;
import com.jmorillo.indieStore.services.OrderService;

@Service
@Transactional
public class OrderServiceJPAImpl implements OrderService{
	
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	CartService cartService;
	
	private Order processOrder(int userID) {
		User u = entityManager.find(User.class, userID);
		Order orderInProgress = null;
		try {
			orderInProgress = (Order) entityManager.createQuery("select o from Order o where o.status = :status and o.user.user_id = :user_id")
			.setParameter("status", OrderStatus.INCOMPLETE.name())
			.setParameter("user_id", u.getUser_id())
			.getSingleResult();
		}catch(NoResultException e) {
			System.out.println("Order does not exist so it will be created");
		}
		
		Order order = null;
		
		if(orderInProgress == null) {
			order = new Order();
			order.setUser(u);
			order.setStatus(OrderStatus.INCOMPLETE.name());
		}else {
			order = orderInProgress;
		}
		
		
		return order;
	}

	@Override
	public void processGeneralUserData(String fullName, String address, String country, String state, String zip_code,
			String phone, int userID) {
		Order order = processOrder(userID);
		order.setFullName(fullName);
		order.setAddress(address);
		order.setCountry(country);
		order.setState(state);
		order.setZip_code(zip_code);
		order.setPhone(phone);
		entityManager.merge(order);
	}

	@Override
	public void processCardData(String cardOwner, String cardNumber, String cardType, String ccv, Date cardExpireDate,
			int userID) {
		Order order = processOrder(userID);
		order.setCardOwner(cardOwner);
		order.setCardNumber(cardNumber);
		order.setCardType(cardType);
		order.setCcv(ccv);
		order.setCardExpireDate(cardExpireDate);
		entityManager.merge(order);
	}

	@Override
	public void processShippingDetails(String shippingDetails, String shippingType, int userID) {
		Order order = processOrder(userID);
		order.setShippingDetails(shippingDetails);
		order.setShippingType(shippingType);
		entityManager.merge(order);
	}

	@Override
	public void confirmOrder(int userID) {
		Order order = processOrder(userID);
		User user = entityManager.find(User.class, userID);
		Cart cart = user.getCart();
		
		// Setting up the OrderProducts
		if(cart!=null && cart.getCartProducts().size() > 0) {
			for (CartProduct cartProduct : cart.getCartProducts()) {
				OrderProduct op = new OrderProduct();
				op.setVideogame(cartProduct.getVideogame());
				op.setQuantity(cartProduct.getQuantity());
				op.setOrder(order);
				
				order.getOrderProducts().add(op);
				
				entityManager.persist(op);
			}
		}
		
		// Delete products from cart
		Query query = entityManager.createNativeQuery(SQLConstants.SQL_DELETE_CART_PRODUCTS);
		query.setParameter("cart_id", cart.getCart_id());
		query.executeUpdate();
		
		//Update the order
		order.setStatus(OrderStatus.COMPLETE.name());
		entityManager.merge(order);
		
	}

	@Override
	public OrderSummary obtainOrderSummary(int userID) {
		OrderSummary orderSummary = new OrderSummary();
		Order order = processOrder(userID);
		
		// Step 1 - General User Data
		orderSummary.setFullName(order.getFullName());
		orderSummary.setAddress(order.getAddress());
		orderSummary.setCountry(order.getCountry());
		orderSummary.setState(order.getState());
		orderSummary.setZip_code(order.getZip_code());
		orderSummary.setPhone(order.getPhone());
		// Step 2 - Card Data
		orderSummary.setCardOwner(order.getCardOwner());
		orderSummary.setCardNumber(order.getCardNumber());
		orderSummary.setCardType(order.getCardType());
		orderSummary.setCcv(order.getCcv());
		orderSummary.setCardExpireDate(order.getCardExpireDate());
		// Step 3 - Shipping Data
		orderSummary.setShippingDetails(order.getShippingDetails());
		orderSummary.setShippingType(order.getShippingType());
		// Step 4 - Products
		orderSummary.setVideogames(cartService.obtainUserCartProducts(userID));
		
		return orderSummary;
	}

	@Override
	public List<Order> obtainAllOrders() {
		return entityManager.createQuery("select o from Order o order by o.order_id desc").getResultList();
	}

	@Override
	public Order obtainOrderById(int order_id) {
		return entityManager.find(Order.class, order_id);
	}

	@Override
	public void editOrderStatus(int order_id, String status) {
		Order o = entityManager.find(Order.class, order_id);
		o.setStatus(status);
		entityManager.merge(o);
	}

	@Override
	public List<OrderProduct> obtainOrderProductsByUser(User user) {
		Query query = entityManager.createQuery("select o from Order o where o.user = :user");
		query.setParameter("user", user);
		Order o = null;
		try {
			o = (Order) query.getSingleResult();
			return o.getOrderProducts();
		}catch(NoResultException e) {
			System.out.println("No order for user: "+user.getUsername());
			return null;
		}
	}

}
