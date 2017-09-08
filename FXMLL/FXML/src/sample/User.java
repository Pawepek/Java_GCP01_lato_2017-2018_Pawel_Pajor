package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class User {

    private SimpleStringProperty fName = new SimpleStringProperty();
    private SimpleStringProperty fPassword = new SimpleStringProperty();
    private SimpleStringProperty fAge = new SimpleStringProperty();
    private SimpleStringProperty fGender = new SimpleStringProperty();
    private SimpleStringProperty fAddress = new SimpleStringProperty();

    public String getfName() {
        return fName.get();
    }

    public SimpleStringProperty fNameProperty() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName.set(fName);
    }

    public String getfPassword() {
        return fPassword.get();
    }

    public SimpleStringProperty fPasswordProperty() {
        return fPassword;
    }

    public void setfPassword(String fPassword) {
        this.fPassword.set(fPassword);
    }

    public String getfAge() {
        return fAge.get();
    }

    public SimpleStringProperty fAgeProperty() {
        return fAge;
    }

    public void setfAge(String fAge) {
        this.fAge.set(fAge);
    }

    public String getfGender() {
        return fGender.get();
    }

    public SimpleStringProperty fGenderProperty() {
        return fGender;
    }

    public void setfGender(String fGender) {
        this.fGender.set(fGender);
    }

    public String getfAddress() {
        return fAddress.get();
    }

    public SimpleStringProperty fAddressProperty() {
        return fAddress;
    }

    public void setfAddress(String fAddress) {
        this.fAddress.set(fAddress);
    }


}
