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
public class ResidentHistory extends Resident{
    private Resident resident;
    private String history;
    private String presentCondition;
    private String diet;
    private String fluid;
    private int assistanceof;
    private String mobility;
    private String action;
    private NextKind nextkind;
    
    
    public ResidentHistory(){}

    public ResidentHistory(Resident resident,String history, String presentCondition, String diet, String fluid, int assistanceof, String mobility, String action, NextKind nextkind) {
        this.resident=resident;
        this.history = history;
        this.presentCondition = presentCondition;
        this.diet = diet;
        this.fluid = fluid;
        this.assistanceof = assistanceof;
        this.mobility = mobility;
        this.action = action;
        this.nextkind = nextkind;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getPresentCondition() {
        return presentCondition;
    }

    public void setPresentCondition(String presentCondition) {
        this.presentCondition = presentCondition;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getFluid() {
        return fluid;
    }

    public void setFluid(String fluid) {
        this.fluid = fluid;
    }

    public int getAssistanceof() {
        return assistanceof;
    }

    public void setAssistanceof(int assistanceof) {
        this.assistanceof = assistanceof;
    }

    public String getMobility() {
        return mobility;
    }

    public void setMobility(String mobility) {
        this.mobility = mobility;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public NextKind getNextkind() {
        return nextkind;
    }

    public void setNextkind(NextKind nextkind) {
        this.nextkind = nextkind;
    }


    
    @Override
    public String toString() {
        return "ResidentHistory{" + "resident=" + resident + ", history=" + history + ", presentCondition=" + presentCondition + ", diet=" + diet + ", fluid=" + fluid + ", assistanceof=" + assistanceof + ", mobility=" + mobility + ", action=" + action + ", nextkind=" + nextkind + '}';
    }

   
}
