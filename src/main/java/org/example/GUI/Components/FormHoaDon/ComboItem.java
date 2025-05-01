package org.example.GUI.Components.FormHoaDon;

public class ComboItem {

    private String displayValue;
    private String logicValue;

    public ComboItem(String displayValue, String logicValue){
        this.displayValue = displayValue;
        this.logicValue = logicValue;
    }


    @Override
    public String toString(){
        return displayValue;
    }

    public String getLogicValue(){
        return logicValue;
    }
}
