package model;

public class Rack {
	private int id;
	private String reference;
	private int capaciteMax;
	private String description;
	private String emplacement;

	public Rack(int id, String reference, int capaciteMax, String description, String emplacement) {
		this.id = id;
		this.reference = reference;
		this.capaciteMax = capaciteMax;
		this.description = description;
		this.emplacement = emplacement;
	}

	public int getId() {
		return id;
	}

	public String getReference() {
		return reference;
	}

	public int getCapaciteMax() {
		return capaciteMax;
	}

	public String getDescription() {
		return description;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public void setCapaciteMax(int capaciteMax) {
		this.capaciteMax = capaciteMax;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}
}
