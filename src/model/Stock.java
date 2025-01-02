package model;

public class Stock {
	private int id;
	private String name;
	private int quantity;
	private int categoryId;

	public Stock(int id, String name, int quantity, int categoryId) {
		this.setId(id);
		this.setName(name);
		this.setQuantity(quantity);
		this.setCategoryId(categoryId);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

}
