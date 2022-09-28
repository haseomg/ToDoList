package com.example.todolist;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class AddItem implements Serializable {

    String toDoList; // addWrite 할 일 적는 텍스트뷰
//    String changeText; // 레이아웃 누르면 addText -> ChangeText로 에딧텍스트에서 텍스트뷰로 변경
    boolean isSelected; // 체크박스

//    public ArrayList<AddItem> getAddItems() {
//        return addItems;
//    }
//
//    public void setAddItems(ArrayList<AddItem> addItems) {
//        this.addItems = addItems;
//    }

//    public String getChangeText() {
//        return changeText;
//    }

//    public void setChangeText(String changeText) {
//        this.changeText = changeText;
//    }

//    ArrayList<AddItem> addItems;

    public boolean isSelected() { // getSelected
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public AddItem() {
        toDoList = "";
        Log.i("아이템 생성자의 toDoList"," + isSelected(체크박스)" + getToDoList() + " / " + isSelected());
    }



//    public AddItem(String toDoList) {
//        this.toDoList = toDoList;
//    }
//
//    public AddItem(boolean isSelected) {
//        this.isSelected = isSelected;
//    }
//
//    public AddItem(int position, AddItem addItem) {
//        addItems.add(position, addItem);
//    }

//    public AddItem(String toDoList, boolean isSelected) {
//        this.toDoList = toDoList;
//        this.isSelected = isSelected;
//    }

    public AddItem(String toDoList) {
        this.toDoList = toDoList;
    }

    public String getToDoList() {
        return toDoList;
    }

    public void setToDoList(String toDoList) {
        this.toDoList = toDoList;
    }


}
