package application.controller;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableHelper {
	public static <T> ObservableList<T> getObservableList(ArrayList<T> arrayList){
		ObservableList<T> list = 
				FXCollections.observableArrayList(arrayList);
		return list;
	}
}
