package sample;

import javafx.beans.property.*;

public class Student
{
	private SimpleDoubleProperty mark = new SimpleDoubleProperty();
	private SimpleStringProperty firstName = new SimpleStringProperty();
	private SimpleStringProperty lastName = new SimpleStringProperty();
	private SimpleIntegerProperty age = new SimpleIntegerProperty();


	public double getMark()
	{
		return this.mark.get();
	}
	public DoubleProperty getMarkProp()
	{
		return this.mark;
	}

	public void setMark( double mark ){ this.mark.set(mark); }
	
	public String getFirstName()
	{
		return this.firstName.get();
	}
	public StringProperty getFirstNameProp()
	{
		return this.firstName;
	}

	public void setFirstName( String firstName )
	{
		this.firstName.set(firstName);
	}
	
	public String getLastName()
	{
		return this.lastName.get();
	}
	public StringProperty getLastNameProp()
	{
		return this.lastName;
	}

	public void setLastName( String lastName )
	{
		this.lastName.set(lastName);
	}

	public int getAge()
	{
		return this.age.get();
	}
	public IntegerProperty getAgeProp()
	{
		return this.age;
	}
	
	public void setAge( int age )
	{
		this.age.set(age);
	}

	Student(String firstName, String lastName, double mark, int age){
		setFirstName(firstName);
		setLastName(lastName);
		setMark(mark);
		setAge(age);
	}
	Student(){}

	public void print(){
		System.out.println(this.getMark() + " " + this.getFirstName() + " " + this.getLastName() + " " + this.getAge());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Student student = (Student) o;

		if (Double.compare(student.getMark(), getMark()) != 0) return false;
		if (getAge() != student.getAge()) return false;
		if (!getFirstName().equals(student.getFirstName())) return false;
		return getLastName().equals(student.getLastName());
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(getMark());
		result = (int) (temp ^ (temp >>> 32));
		result = 31 * result + getFirstName().hashCode();
		result = 31 * result + getLastName().hashCode();
		result = 31 * result + getAge();
		return result;
	}
}
