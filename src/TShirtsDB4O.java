import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import Entities.Article;
import Entities.CreditCard;
import Entities.Customer;
import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import Entities.Order;
import com.db4o.query.Predicate;

/**
 * @author Joan Anton Perez Branya
 * @since 19/02/2017
 *
 */

public class TShirtsDB4O {
	public static ArrayList<Order> orders;
	static ObjectContainer db;
	

	/**
	 * Implement TODO methods and run to test
	 * 
	 * @param args
	 *            no args
	 * @throws IOException
	 *             in order to read files
	 * @throws ParseException
	 *             in order to parse data formats
	 */
	public static void main(String[] args) throws IOException, ParseException {
		TShirtsDB4O TSM = new TShirtsDB4O();
		FileAccessor fa = new FileAccessor();
		fa.readArticlesFile("articles.csv");
		fa.readCreditCardsFile("creditCards.csv");
		fa.readCustomersFile("customers.csv");
		fa.readOrdersFile("orders.csv");
		fa.readOrderDetailsFile("orderDetails.csv");
		orders = fa.orders;
		try {

			File file = new File("orders.db");
			String fullPath = file.getAbsolutePath();
			db = Db4o.openFile(fullPath);

			TSM.addOrders();//HECHO
			TSM.listOrders();//HECHO
			TSM.listArticles();//HECHP
			TSM.addArticle(7, "CALCETINES EJECUTIVOS 'JACKSON 3PK'", "gris", "45", 18.00f);//hecho
			TSM.updatePriceArticle(7, 12.00f);//heccho
			TSM.llistaArticlesByName("CALCETINES EJECUTIVOS 'JACKSON 3PK'");//Hecho
			TSM.deletingArticlesByName("POLO BÁSICO 'MANIA'");//Hecho
			TSM.deleteArticleById(7);//hecho
			TSM.listArticles();//hecho
			TSM.listCustomers();//hecho
			TSM.changeCreditCardToCustomer(1);//hecho
			TSM.listCustomers();//hecho
			TSM.llistaCustomerByName("Laura");//hecho
			TSM.showOrdersByCustomerName("Laura");//hecho
			TSM.showCreditCardByCustomerName("Laura");//hecho
			TSM.deleteCustomerbyId(2);//hecho
			TSM.retrieveOrderContentById_Order(2);//hecho
			TSM.deleteOrderContentById_Order(2);//hecho
			TSM.retrieveOrderContentById_Order(2);//hecho
			TSM.listCustomers();//hecho
			TSM.listOrders();//hecho

		} finally {
			// close database
			db.close();
		}
	}
	/** Method to add all orders from ArrayList and store them into database */
	public void addOrders() {
		System.out.println("\nAgregando ordenes\n");
		for (int i = 0; i < orders.size(); i++) {
			System.out.println(orders.get(i).toString());
			db.store(orders.get(i));

		}
		// TODO Auto-generated method stub

	}

	/** Method to list all Orders from the database */
	public void listOrders() {
		System.out.println("\nListando ordenes\n");
		ObjectSet<Entities.Order> result = db.queryByExample(new Entities.Order());
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}
		// TODO Auto-generated method stub

	}
	/** Method to list all Articles from the database */
	public void listArticles() {
		System.out.println("\nListando Articles\n");
		ObjectSet<Entities.Article> result = db.queryByExample(new Entities.Article());
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}	// TODO Auto-generated method stub

	}

	public void addArticle(int idArticle, String name, String colour, String size, float recommendedPrice) {
		System.out.println("\nAñadiendo Articles\n");

		Article article = new Article(idArticle, name, colour, size, recommendedPrice);
		System.out.println(article.toString());
		db.store(article);

	}


	public void deleteArticleById(int articleID) {
		System.out.println("\nBorrando Article per ID\n");
		ObjectSet<Entities.Article> result = db.queryByExample(new Entities.Article(articleID, null, null, null,0));
		while(result.hasNext()){
			db.delete(result.next());
		}

	}
	public void updatePriceArticle(int idArticle, float newPrice) {
		System.out.println("\nCambiando Precio Article\n");
	ObjectSet<Entities.Article> result = db.queryByExample(new Entities.Article(idArticle, null, null, null, 0));
	Article article = (Article) result.next();
	deleteArticleById(idArticle);
	article.setRecommendedPrice(newPrice);
	db.store(article);
	}

	/** Method to list Articles from the database using their name */
	public void llistaArticlesByName(String articleName) {
		System.out.println("\nListando Articles por el nombre\n");

		ObjectSet<Entities.Article> result = db.queryByExample(new Entities.Article(0, articleName, null, null, 0 ));
		System.out.println("Obteniendo el articulo x");
		while(result.hasNext()){
			System.out.println(result.next());
		}

		// TODO Auto-generated method stub

	}


	public void deletingArticlesByName(String articleName) {
		// TODO Auto-generated method stub
		System.out.println("\nBorrando Articles por el nombre\n");
		ObjectSet<Entities.Article> result = db.queryByExample(new Entities.Article(0, articleName, null, null, 0 ));
		System.out.println("Borrando el articulo x");
		while(result.hasNext()){
			db.delete(result.next());
		}

	}
	/** Method to list all Customers from the database */
	public void listCustomers() {

		// TODO Auto-generated method stub
		System.out.println("Listando Customers");
		ObjectSet<Entities.Customer> result = db.queryByExample(new Entities.Customer());
		System.out.println(result.size());
		while (result.hasNext()) {
			System.out.println(result.next());
		}
	}





	public void changeCreditCardToCustomer(int idCustomer) {
		System.out.println("\nCambiar el numero de la targeta al costumer por el ID\n");
		int[] credito = new int[16];
		for (int j = 0; j < 16; j++) {
			credito[j] = ((int) Math.random()*9+1);
		}
		String r = "" + credito[0] + credito[1] + credito[2] + credito[3] + credito[4] + credito[5] + credito[6] + credito[7] + credito[8] + credito[9] + credito[10] + credito[11] + credito[12] + credito[13] + credito[14] + credito[15];
		CreditCard creditCard = new CreditCard(r,"" + credito[2] + credito[5] + credito[12],((int) Math.random()*12+1), ((int) Math.random()*10+20));
		db.store(creditCard);
		ObjectSet<Customer> result = db.queryByExample(new Customer(idCustomer,null,null,null,null,null));
		result.get(0).setCreditCard(creditCard);
	}

	/** Method to list Customers from the database using their name */
	public void llistaCustomerByName(String name) {
		// TODO Auto-generated method stub

		ObjectSet<Entities.Customer> result = db.queryByExample(new Entities.Customer(0, name, null, null,null, null ));
		System.out.println("Obteniendo el customer x");
		while(result.hasNext()){
			System.out.println(result.next());
		}

	}


	/**
	 * Delete Order and its orderdetails using idOrder
	 * 
	 * @param
	 *            idOrder
	 */
	public void deleteOrderContentById_Order(int idOrder) {
		// TODO Auto-generated method stub
		System.out.println("\nBorrando Order\n");
		ObjectSet<Entities.Order> result = db.queryByExample(new Entities.Order(idOrder,null, null, null, null));
		while(result.hasNext()){
			db.delete(result.next());
		}
	}

	public void retrieveOrderContentById_Order(int idOrder) {
		// TODO Auto-generated method stub

		ObjectSet<Entities.Order> result = db.queryByExample(new Entities.Order(idOrder,null, null, null, null));
		if (result.hasNext()){
			System.out.println(result.next());
		}
	}


	public void deleteCustomerbyId(int idCustomer) {
		// TODO Auto-generated method stub
		System.out.println("\nBorrando custormer por el ID\n");
		ObjectSet<Entities.Customer> result = db.queryByExample(new Entities.Customer(idCustomer,null, null, null, null, null));
		while(result.hasNext()){
			db.delete(result.next());
		}
	}



	public void showCreditCardByCustomerName(String name) {
		// TODO Auto-generated method stub

		ObjectSet<Entities.Customer> result = db.queryByExample(new Customer());
		System.out.println("Obteniendo la credit card de x");
		while(result.hasNext()){
			Customer customer = result.next();
			if (customer.getName().equals(name)){
				System.out.println(customer.getCreditCard());
			}
		}

	}

	/**
	 * Method to list Oders and orderdetails from the database using the
	 * customer name
	 */
	public void showOrdersByCustomerName(String string) {
		// TODO Auto-generated method stub
		List<Order> orders = db.query(new Predicate<Order>() {
			public boolean match(Order order) {
				return order.getCustomer().getName().compareTo(string) == 0;
			}
		});
		System.out.println("mostrar orders por customer name");
		for (Order o: orders) {
			System.out.println(o.toString());
		}
	}

	/** delete all objects from the whole database */
	public void clearDatabase() {
		// TODO Auto-generated method stub
		ObjectSet<Entities.Article> resultArticle = db.queryByExample(new Entities.Article(0,null, null, null, 0));
		while(resultArticle.hasNext()){
			db.delete(resultArticle.next());
		}
		ObjectSet<Entities.CreditCard> resultCreditCard = db.queryByExample(new Entities.CreditCard(null, null, 0, 0));
		while(resultCreditCard.hasNext()){
			db.delete(resultCreditCard.next());
		}
		ObjectSet<Entities.Customer> resultCustomer = db.queryByExample(new Entities.Customer(0,null, null, null, null, null));
		while(resultCustomer.hasNext()){
			db.delete(resultCustomer.next());
		}
		ObjectSet<Entities.Order> resultOrder = db.queryByExample(new Entities.Order(0,null, null, null, null));
		while(resultOrder.hasNext()){
			db.delete(resultOrder.next());
		}
	}
}

