package model;
public abstract class HistoricalEntity {
	// Inheritance rules 
	protected int id;
	protected String name;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
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
