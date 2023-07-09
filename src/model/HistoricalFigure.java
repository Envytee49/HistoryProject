package model;


import java.util.HashMap;
import database.*;

public class HistoricalFigure extends HistoricalEntity{
    private String born;
    private String died;
    private String workTime;   
    private HashMap<String, Integer> era;
    private HashMap<String, Integer> father;
    private HashMap<String, Integer> mother;
    private HashMap<String, Integer> precededBy;
    private HashMap<String, Integer> succeededBy;

    public String getBorn() {
        return born;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getDied() {
        return died;
    }
    
    public HashMap<String, Integer> getEra() {
        return era;
    }

    public HashMap<String, Integer> getFather() {
        return father;
    }

    public HashMap<String, Integer> getMother() {
        return mother;
    }

    public HashMap<String, Integer> getPrecededBy() {
        return precededBy;
    }

    public HashMap<String, Integer> getSucceededBy() {
        return succeededBy;
    }
    
    public void setEra(String name, Integer id) {
        this.era.put(name, id);
    }

    public void setFather(String name, Integer id) {
        this.father.put(name, id);
    }

    public void setMother(String name, Integer id) {
        this.mother.put(name, id);
    }

    public void setPrecededBy(String name, Integer id) {
        this.precededBy.put(name, id);
    }

    public void setSucceededBy(String name, Integer id) {
        this.succeededBy.put(name, id);
    }
    
    //Constructors
    public HistoricalFigure(){}
    public HistoricalFigure(
            String name,
            String birth,
            String died,         
            String workTime,
            String era,
            String father,
            String mother,
            String preceded,
            String succeeded
            
            
    ) {  
    	this.era = new HashMap<>();
    	this.father = new HashMap<>();
    	this.mother = new HashMap<>();
    	this.precededBy = new HashMap<>();
    	this.succeededBy = new HashMap<>();
        this.setName(name);
        this.born = birth;
        this.died = died;
        this.workTime = workTime;
        this.era.put(era, null);
        this.father.put(father, null);
        this.mother.put(mother, null);
        this.precededBy.put(preceded, null);
        this.succeededBy.put(succeeded, null);
        this.setId(HistoricalFigures.collection.getId());
        HistoricalFigures.collection.add(this);
    }
    public void save(){
        HistoricalFigures.writeJSON(this);
    }

}
