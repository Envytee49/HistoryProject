package application.author;

import java.util.ArrayList;

public class MemberList {
	private ArrayList<Member> listOfMembers;
	public static final int CAPACITY = 6;
	
	public MemberList() {
		this.listOfMembers= new ArrayList<Member>(CAPACITY);
	}
	
	public void addMember(Member person) {
		this.listOfMembers.add(person);
	}
	
	public Member isMatch(String code) {
		try {

			for(Member person: this.listOfMembers) {
				if(person.getCode().contentEquals(code)) {
					return person;
				}
			}
			return null;
		}catch(NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
}
