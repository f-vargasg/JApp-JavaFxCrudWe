
package com.fvgprinc.app.crudfx.dl;

import com.fvgprinc.app.crudfx.be.StudentBe;
import com.fvgprinc.app.crudfx.globals.GlobalConstants;
import com.fvgprinc.tools.db.DIContainer;
import com.fvgprinc.tools.db.Mapper;
import com.fvgprinc.tools.db.ParamAction;
import com.fvgprinc.tools.string.MyCommonString;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private final String WHERE_BY_PK = " where " + " id = ?";    
    private final String INSERT_STM = "insert into " + TABLE_NAME + " ("
            + " firstname,middlename,lastname ) values ("
            + "?,?,?)";
    private final String UPDATE_STM = "update " + TABLE_NAME + " set "
            + "firstname = ? ,\n"
            + "middlename = ? ,\n"
            + "lastname = ?  "
            + " where  id = ?";

   private final String UPDATE_BY_PK_STM = "update " + TABLE_NAME + " set "
            + "firstname = ? ,\n"
            + "middlename = ? ,\n"
            + "lastname = ?  "
            + WHERE_BY_PK;    
    private final String DELETE_STM = "delete from " + TABLE_NAME;

    private final String DELETE_BY_PK_STM = DELETE_STM + WHERE_BY_PK;

    private final String FIND_BY_PK_STM = SELECT_STM + WHERE_BY_PK;

    public StudentDl() {
        this.dm = DIContainer.getInstance().getDataManager(GlobalConstants.SQLITECONN2);
    }

    @Override
    public void insert(ArrayList<ParamAction> paramDLs) throws SQLException {
        doStatement(INSERT_STM, paramDLs);
    }

    @Override
    public void update(ArrayList<ParamAction> paramDLs) throws SQLException {
        doStatement(UPDATE_BY_PK_STM, paramDLs);
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
    public Object doFind(ArrayList<ParamAction> keyFiedls) throws SQLException {
        StudentBe studentBe = null;
        String wSql = FIND_BY_PK_STM;
        try ( Connection conn = dm.getConnectioin();  PreparedStatement stm = conn.prepareStatement(wSql)) {
            this.setParamPreparedStm(stm, keyFiedls);
            try ( ResultSet rs = stm.executeQuery();) {
                if (rs.next()) {
                    studentBe = (StudentBe) load(rs);
                }
            }
        }
        return studentBe;
    }

    public ArrayList<StudentBe> listar(ArrayList<ParamAction> params) throws SQLException {
        ArrayList<StudentBe> lstRes = new ArrayList<>();
        String condSql = ParamAction.queryCond(params);
        String sqlStm = SELECT_STM + (condSql.length() > 0 ? " WHERE " : MyCommonString.EMPTYSTR) + condSql;
        try ( Connection conn = dm.getConnectioin();  PreparedStatement ps = conn.prepareStatement(sqlStm)) {
            this.setParamPreparedStm(ps, params);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    StudentBe ub = (StudentBe) doLoad(rs);
                    lstRes.add(ub);
                }
            }
        }
        return lstRes;
    }
}
