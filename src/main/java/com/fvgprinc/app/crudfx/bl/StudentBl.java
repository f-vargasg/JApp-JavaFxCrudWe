/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fvgprinc.app.crudfx.bl;

import com.fvgprinc.app.crudfx.be.StudentBe;
import com.fvgprinc.app.crudfx.dl.StudentDl;
import com.fvgprinc.tools.db.ParamAction;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author garfi
 */
public class StudentBl {

    private StudentDl studentDl;

    public StudentBl() {
        studentDl = new StudentDl();
    }

    public void add(StudentBe studentBe) {
        try {
            ArrayList<ParamAction> lst = new ArrayList<>();
            lst.add(new ParamAction(ParamAction.JavaTypes.STRING, studentBe.getFirstName()));
            lst.add(new ParamAction(ParamAction.JavaTypes.STRING, studentBe.getMiddleName()));
            lst.add(new ParamAction(ParamAction.JavaTypes.STRING, studentBe.getLastName()));

            studentDl.insert(lst);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void update(StudentBe studentBe) {
        try {
            ArrayList<ParamAction> lst = new ArrayList<>();
            lst.add(new ParamAction(ParamAction.JavaTypes.STRING, studentBe.getFirstName()));
            lst.add(new ParamAction(ParamAction.JavaTypes.STRING, studentBe.getMiddleName()));
            lst.add(new ParamAction(ParamAction.JavaTypes.STRING, studentBe.getLastName()));
            lst.add(new ParamAction(ParamAction.JavaTypes.INTEGER, studentBe.getId()));

            studentDl.update(lst);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(StudentBe studentBe) {
        try {
            ArrayList<ParamAction> lst = new ArrayList<>();
            lst.add(new ParamAction(ParamAction.JavaTypes.INTEGER, studentBe.getId()));

            studentDl.delete(lst);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    
    public ObservableList<StudentBe> getList() {
        ObservableList<StudentBe> res = FXCollections.observableArrayList();
        try {
            ArrayList<ParamAction> params = new ArrayList<>();
            ArrayList<StudentBe> studentList = studentDl.listar(params);
            StudentBe sBe;
            for (StudentBe studentBe : studentList) {
                sBe = new StudentBe(studentBe.getId(), studentBe.getFirstName(), studentBe.getMiddleName(), 
                                                    studentBe.getLastName());
                res.add(sBe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}
