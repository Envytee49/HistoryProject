package model;


import java.util.HashMap;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import database.*;

public class HistoricalFigure extends HistoricalEntity{
	
	private String name;
    private String born;
    private String died;
    private String workTime;
    
    private HashMap<String, Integer> era = new HashMap<>();
    private HashMap<String, Integer> father = new HashMap<>();
    private HashMap<String, Integer> mother = new HashMap<>();
    private HashMap<String, Integer> precededBy = new HashMap<>();
    private HashMap<String, Integer> succeededBy = new HashMap<>();
    private HistoricalFigures figure = new HistoricalFigures();
    
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
        
        
        this.name = name;
        this.born = birth;
        this.died = died;
        this.workTime = workTime;
        this.era.put(era, null);
        this.father.put(father, null);
        this.mother.put(mother, null);
        this.precededBy.put(preceded, null);
        this.succeededBy.put(succeeded, null);
        this.id = HistoricalFigures.collection.getId();
        HistoricalFigures.collection.add(this);
        //setId(countFigures);
        
        //HistoricalFigures.collection.add(this); ???
    }
    
    
    public String getBorn() {
        return born;
    }

    public String getWorkTime() {
        return workTime;
    }

    public String getName() {
        return name;
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
    
    public void setBorn(String born) {
        this.born = born;
    }

    public void setDied(String died) {
        this.died = died;
    }


    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public void setName(String name) {
        this.name = name;
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
    
    
    
    public void save(){
        figure.writeJSON(this);
    }
    
    public String getEraKey() {
    	Set<String> keys = era.keySet();
    	StringJoiner joiner = new StringJoiner(",");
    	
		for(String key: keys) {
			joiner.add(key);
		}
		String eraList = joiner.toString();
		return eraList;
		
    }
    
    public String getFatherKey() {
    	Set<String> keys = father.keySet();
    	StringJoiner joiner = new StringJoiner(",");
    	
		for(String key: keys) {
			joiner.add(key);
		}
		String fatherList = joiner.toString();
		return fatherList;
    }
    
    public String getMotherKey() {
    	Set<String> keys = mother.keySet();
    	StringJoiner joiner = new StringJoiner(",");
    	
		for(String key: keys) {
			joiner.add(key);
		}
		String motherList = joiner.toString();
		return motherList;
    }
    
    public String getPrecededByKey() {
    	Set<String> keys = precededBy.keySet();
    	StringJoiner joiner = new StringJoiner(",");
    	
		for(String key: keys) {
			joiner.add(key);
		}
		String precededByList = joiner.toString();
		return precededByList;
    }
    
    public int getSucceededByValue(String key) {
        return (int)succeededBy.get(key);
    }
    
    public int getPrecededByValue(String key) {
        return precededBy.get(key);
    }
    
    public int getFatherValue(String key) {
        return father.get(key);
    }
    
    public int getMotherValue(String key) {
        return mother.get(key);
    }
    
    public int getEraValue(String key) {
        return era.get(key);
    }
    
    public String getSucceededByKey() {
    	Set<String> keys = succeededBy.keySet();
    	StringJoiner joiner = new StringJoiner(",");
    	
		for(String key: keys) {
			joiner.add(key);
		}
		String succeededByList = joiner.toString();
		return succeededByList;
    }

}
