/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.fvgprinc.app.crudfx.dl;

import com.fvgprinc.app.crudfx.be.StudentBe;
import com.fvgprinc.app.crudfx.globals.GlobalConstants;
import com.fvgprinc.tools.common.string.MyCommonString;
import com.fvgprinc.tools.db.DIContainer;
import com.fvgprinc.tools.db.Mapper;
import com.fvgprinc.tools.db.ParamAction;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author garfi
 */
public class StudentDl extends Mapper {
    private final String TABLE_NAME = "student";
    private final String SELECT_STM = "Select * "
            + "from " + TABLE_NAME;
    private final String INSERT_STM = "insert into " + TABLE_NAME + " ("
            + " firstname,middlename,lastname ) values ("
            + "?,?,?)";
    private final String UPDATE_STM = "update " + TABLE_NAME + " set "
            + "firstname = ? ,\n"
            + "middlename = ? ,\n"
            + "lastname = ?  "
            + " where  id = ?";
    private final String DELETE_STM = "delete from " + TABLE_NAME;

    private final String DELETE_BY_PK_STM = DELETE_STM + " where "
            + " id = ?";

    private final String FIND_BY_PK_STM = SELECT_STM + " where "
            + " id = ?";

    public StudentDl() {
        this.dm = DIContainer.getInstance().getDataManager(GlobalConstants.MARIADBCONN);
    }

    @Override
    public void insert(ArrayList<ParamAction> paramDLs) throws SQLException {
        doStatement(INSERT_STM, paramDLs);
    }

    @Override
    public void update(ArrayList<ParamAction> paramDLs) throws SQLException {
        doStatement(UPDATE_STM, paramDLs);
    }

    @Override
    public void delete(ArrayList<ParamAction> keyFields) throws SQLException {
        doStatement(DELETE_BY_PK_STM, keyFields);
    }

    @Override
    protected Object doLoad(ResultSet rs) throws SQLException {
        StudentBe studentBe = new StudentBe();

        studentBe.setId(rs.getInt("id"));
        studentBe.setFirstName(rs.getString("firstname"));
        studentBe.setMiddleName(rs.getString("middlename"));
        studentBe.setLastName(rs.getString("lastname"));

        return studentBe;
    }

    @Override
    protected Object doFind(ArrayList<ParamAction> keyFiedls) throws SQLException {
        StudentBe studentBe = null;
        ResultSet res = this.doStmReturnData(FIND_BY_PK_STM, keyFiedls);
        if (res.next()) {
            studentBe = (StudentBe) load(res);
        }
        this.conn.close();
        return studentBe;
    }

    public ArrayList<StudentBe> listar(ArrayList<ParamAction> params) throws SQLException {
        ArrayList<StudentBe> lstRes = new ArrayList<>();
        String condSql = queryCond(params);
        String sqlStm = SELECT_STM + (condSql.length() > 0 ? " WHERE " : MyCommonString.EMPTYSTR)
                + condSql;
        ResultSet rs = this.doStmReturnData(sqlStm, params);
        //PreparedStatement ps = this.doStmReturn(sqlStm, params);
        //ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            StudentBe ub = (StudentBe) doLoad(rs);
            lstRes.add(ub);
        }
        rs.close();
        //ps.close();
        this.conn.close();
        //this.conn.close();
        return lstRes;
    }

}
