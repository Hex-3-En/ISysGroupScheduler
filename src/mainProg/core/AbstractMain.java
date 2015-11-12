package mainProg.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import simuClasses.*;

public abstract class AbstractMain {
	
	private String[] courseNames = {"A","B","C"};
	private int[] courseChances = {80,90,85};
	private int OVERLAP = 2;
	private int STUDENTS = 80;
	private int MAXSTUDSPERCOURSESLOT = 12;
	private int BLOCKCOUNT = 30;
//--------------------------------------------------------
	
	private List<Course> COURSES = new LinkedList<Course>();
	private StudCollection studsToManage = new StudCollection();
	//private static TimeTable resultTable;
	private TimeTable emptyTable = null;
	private List<Integer> usedSlots = new LinkedList<Integer>();
	private Random myRandom = new Random();
	
//--------------------------------------------------------
	
	public void init(){
		generateCourses();
		generateStudents();
		createEmptyTable();
	}
	
	private void createEmptyTable(){
		for(Course c : COURSES){
			if(emptyTable == null){
				createFirstEmptyTable(c);
			}else{
				createEmptyTable(c);
			}
		}
	}
	
	public TimeTable getEmptyTable(){
		return emptyTable;
	}
	
	private void createEmptyTable(Course c){
		int slotCount = (c.getStudents().size() / MAXSTUDSPERCOURSESLOT) + ((c.getStudents().size() % MAXSTUDSPERCOURSESLOT == 0) ? 0 : 1);
		
		int newSlotNo = myRandom.nextInt(BLOCKCOUNT);
		
		for(int i = 0; i < slotCount; i++){
			if(i < OVERLAP){
				while(!usedSlots.contains(newSlotNo)){
					newSlotNo = myRandom.nextInt(BLOCKCOUNT);
				}
				c.addSlot(new CourseSlot(c, newSlotNo));
			}else{
				while(usedSlots.contains(newSlotNo)){
					newSlotNo = myRandom.nextInt(BLOCKCOUNT);
				}
				
				c.addSlot(new CourseSlot(c, newSlotNo));
				usedSlots.add(newSlotNo);
			}
		}
		emptyTable.addAllCourseSlots(c.getMySlots());
	}
	
	private void createFirstEmptyTable(Course c){
		emptyTable = new TimeTable();
		int slotCount = (c.getStudents().size() / MAXSTUDSPERCOURSESLOT) + ((c.getStudents().size() % MAXSTUDSPERCOURSESLOT == 0) ? 0 : 1);
		int newSlotNo;
		for(int i = 0; i < slotCount; i++){
			newSlotNo = myRandom.nextInt(BLOCKCOUNT);
			c.addSlot(new CourseSlot(c, newSlotNo));
			usedSlots.add(newSlotNo);
			
			while(usedSlots.contains(newSlotNo)){
				newSlotNo = myRandom.nextInt(BLOCKCOUNT);
			}
			
			c.addSlot(new CourseSlot(c, newSlotNo));
			usedSlots.add(newSlotNo);
		}
		emptyTable.addAllCourseSlots(c.getMySlots());
	}
	
	private void generateCourses(){
		for(int i = 0; i < courseNames.length; i++){
			COURSES.add(new Course(courseNames[i]));
			COURSES.get(i).setChance(courseChances[i]);
		}
	}
	
	private void generateStudents(){
				
		for(int i = 0; i < STUDENTS; i++){
			Student newStudent = new Student(i);			
			for(Course c : COURSES){
				if(myRandom.nextInt(100) <= c.getChance()){
					newStudent.addCourse(c);
					c.addStudent(newStudent);
				}
			}
			studsToManage.addStudent(newStudent);
		}
	}
	
	

}