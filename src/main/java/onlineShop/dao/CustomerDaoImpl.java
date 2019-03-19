package onlineShop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Authorities;
import onlineShop.model.Cart;
import onlineShop.model.Customer;
import onlineShop.model.User;

@Repository //作用于持久层,会被扫描到bean里,具有将数据库操作抛出的原生异常翻译转化为spring持久层的功能
public class CustomerDaoImpl implements CustomerDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	//添加一位顾客的代码
	public void addCustomer(Customer customer){
		customer.getUser().setEnabled(true);
		Authorities authorities = new Authorities();
		authorities.setAuthorities("ROLE_USER");
		authorities.setEmailId(customer.getUser().getEmailId());
		
		Cart cart = new Cart();
		customer.setCart(cart);
		cart.setCustomer(customer);
		
		Session session = null;
		try{
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(authorities);
			session.save(customer);
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			if(session!=null){
				session.close();
			}
		}
	}
	
	//根据顾客姓名找到目标顾客
	public Customer getCustomerByUserName(String userName) {
		Session session = null;
		User user = null;
		try{
			session = sessionFactory.openSession();
			session.beginTransaction();
			//注:userName == emailId
			user = (User)session.createCriteria(User.class).add(Restrictions.eq("emailId", userName)).uniqueResult();
			session.getTransaction().commit();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		if(user!=null) return user.getCustomer();
		return null;
	}
}
