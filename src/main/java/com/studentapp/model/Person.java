// CODE BY VÕ CAO MINH - SE203168
package com.studentapp.model;

public abstract class Person {
    protected String fullName;

    public Person(String fullName) {
        this.fullName = fullName;
    }
    // SE203168

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public abstract String getDescription();
}