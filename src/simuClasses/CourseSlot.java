package simuClasses;

import java.util.LinkedList;
import java.util.List;

public class CourseSlot {
	private int TimeSlot;
	private Course myCourse;
	private int maxStuds;
	private List<Student> myStuds = new LinkedList<Student>();
	private List<StudentPair> myPairs;
	
	public CourseSlot(Course c, int slotNo, int max){
		this.myCourse = c;
		this.TimeSlot = slotNo;
		this.maxStuds = max;
	}
	
	public List<Student> getStudents(){
		return myStuds;
	}
	
	public void addStudent(Student s){
		myStuds.add(s);
	}

	public int getTimeSlot() {
		return TimeSlot;
	}
	
	public int getMax(){
		return maxStuds;
	}

	public void setTimeSlot(int timeSlot) {
		TimeSlot = timeSlot;
	}
	
	public Course getCourse(){
		return myCourse;
	}
	
	public boolean isFilled(){
		return myStuds.size() == maxStuds;
	}
	
	public void initializePairings(HappinessList HL){
		myPairs = new LinkedList<StudentPair>();
		List<Student> tempStuds = new LinkedList<Student>();
		tempStuds.addAll(myStuds);
		
		while(!tempStuds.isEmpty()){
			StudentPair tempPair = new StudentPair();
			tempPair.setStud1(tempStuds.remove(0));
			
			if(!tempStuds.isEmpty()){
				tempPair.setStud2(tempStuds.remove(tempStuds.size()-1));
				tempPair.setOurHappiness(HL.getHpById(tempPair.getStud1().getID(), tempPair.getStud2().getID()).getHappiness());
			}else{
				tempPair.setStud2(null);
				tempPair.setOurHappiness(0.5);
			}
			myPairs.add(tempPair);
		}
	}
	
	public double getHappiness(){
		double ret = 0;
		for(StudentPair sp : myPairs){
			ret += sp.getOurHappiness();
		}
		return ret;
	}
}
