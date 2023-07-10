package model;
public abstract class HistoricalEntity {
	// Inheritance rules 
	private int id;
	private String name;
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
	public HistoricalEntity() {
		
	}
	public HistoricalEntity(int id, String name) {
		this.id = id;
		this.name = name;
	}
	public abstract void save();
	
	public boolean isMatch(Integer id){
        if (id == null) return false;
        return this.getId() == id;
    }
	
	public boolean isMatch(String name){
        if (this.name != null){
            return this.name.toLowerCase().contains(name.toLowerCase());
        }
        return false;
    }
	
	public boolean compareName(String name) {
		if(this.getName().trim().equalsIgnoreCase(name)) {
			return true;
		}
		return false;
	}
}
