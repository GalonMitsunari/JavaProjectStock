package model;

public class Rack {
	private int id;
	private String reference;
	private int capaciteMax;
	private String description;
	private String emplacement;

	public Rack(int id, String reference, int capaciteMax, String description, String emplacement) {
		this.setId(id);
		this.setReference(reference);
		this.setCapaciteMax(capaciteMax);
		this.setDescription(description);
		this.setEmplacement(emplacement);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public int getCapaciteMax() {
		return capaciteMax;
	}

	public void setCapaciteMax(int capaciteMax) {
		this.capaciteMax = capaciteMax;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

}
