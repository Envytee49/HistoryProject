package model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.Eras;

public class Era extends HistoricalEntity {

        private String belongsToTimestamp;
        private String homeland;
        private String founder;
        private String locationOfCapital;
        private String time;
        private String overview;
        private Map<String, Integer> listOfKings = new HashMap<>();
        private Eras era = new Eras();
        /* Getters */
        public String getBelongsToTimestamp() {
            return belongsToTimestamp;
        }

        public String getHomeland() {
            return homeland;
        }

        public String getFounder() {
            return founder;
        }

        public String getLocationOfCapital() {
            return locationOfCapital;
        }

        public String getTime() {
            return time;
        }

        public String getOverview() {
            return overview;
        }

        public Map<String, Integer> getListOfKings() {
            return listOfKings;
        }

        /* Setters */
        public void setBelongsToTimestamp(String belongsToTimestamp) {
            this.belongsToTimestamp = belongsToTimestamp;
        }

        public void setHomeland(String homeland) {
            this.homeland = homeland;
        }

        public void setFounder(String founder) {
            this.founder = founder;
        }

        public void setLocationOfCapital(String locationOfCapital) {
            this.locationOfCapital = locationOfCapital;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setListOfKings(String name, Integer id) {
            this.listOfKings.put(name, id);
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        /* Constructors */


        public Era(
                String name,
                String timestamp,
                String hometown,
                String founder,
                String locationOfCapital,
                String time,
                String overview,
                ArrayList<String> listOfKings
        ) {
            this.name = name;
            this.id = Eras.collection.getId();
            this.belongsToTimestamp = timestamp;
            this.homeland = hometown;
            this.founder = founder;
            this.locationOfCapital = locationOfCapital;
            this.time = time;
            this.overview = overview;
            for (String king : listOfKings){
                this.listOfKings.put(king, null);
            }
            Eras.collection.add(this);
        }
        public Era() {}
        public void save(){
            era.writeJSON(this);
        }

		

    }
