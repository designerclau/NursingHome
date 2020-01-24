package nursinghome;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Claudinea de Almeida
 */
public class NextKind {
    private int nextId;
    private String nextName;
    private String nextPhone;
    private String nextEmail;
    private String nextAddress;
    private int residentId;
    
    
    public NextKind(){}

    public NextKind(int nextId, String nextName, String nextPhone, String nextEmail, String nextAddress, int residentId) {
        this.nextId = nextId;
        this.nextName = nextName;
        this.nextPhone = nextPhone;
        this.nextEmail = nextEmail;
        this.nextAddress = nextAddress;
        this.residentId = residentId;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }

    public String getNextName() {
        return nextName;
    }

    public void setNextName(String nextName) {
        this.nextName = nextName;
    }

    public String getNextPhone() {
        return nextPhone;
    }

    public void setNextPhone(String nextPhone) {
        this.nextPhone = nextPhone;
    }

    public String getNextEmail() {
        return nextEmail;
    }

    public void setNextEmail(String nextEmail) {
        this.nextEmail = nextEmail;
    }

    public String getNextAddress() {
        return nextAddress;
    }

    public void setNextAddress(String nextAddress) {
        this.nextAddress = nextAddress;
    }

    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId = residentId;
    }

    @Override
    public String toString() {
        return "NextKind{" + "nextId=" + nextId + ", nextName=" + nextName + ", nextPhone=" + nextPhone + ", nextEmail=" + nextEmail + ", nextAddress=" + nextAddress + ", residentId=" + residentId + '}';
    }
    
    
    
}
