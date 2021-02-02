package core.db;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Medicine {

    int medi_id;
    String medi_name;
    Date medi_expire_date;
    double medi_pu;
    int medi_stock_qte;
    String medi_dci;
    String medi_form;
    double medi_dose;
    int medi_grp;

    public Medicine(int medi_id, String medi_name, Date medi_expire_date, double medi_pu, int medi_stock_qte, String medi_dci, String medi_form, double medi_dose, int medi_grp) {
        this.medi_id = medi_id;
        this.medi_name = medi_name;
        this.medi_expire_date = medi_expire_date;
        this.medi_pu = medi_pu;
        this.medi_stock_qte = medi_stock_qte;
        this.medi_dci = medi_dci;
        this.medi_form = medi_form;
        this.medi_dose = medi_dose;
        this.medi_grp = medi_grp;
    }

    public void setMedi_name(String medi_name) {
        this.medi_name = medi_name;
    }

    public void setMedi_expire_date(Date medi_expire_date) {
        this.medi_expire_date = medi_expire_date;
    }

    public void setMedi_id(int medi_id) {
        this.medi_id = medi_id;
    }

    public int getMedi_grp() {
        return medi_grp;
    }

    public void setMedi_grp(int medi_grp) {
        this.medi_grp = medi_grp;
    }

    public void setMedi_pu(double medi_pu) {
        this.medi_pu = medi_pu;
    }

    public void setMedi_stock_qte(int medi_stock_qte) {
        this.medi_stock_qte = medi_stock_qte;
    }

    public void setMedi_dci(String medi_dci) {
        this.medi_dci = medi_dci;
    }

    public void setMedi_form(String medi_form) {
        this.medi_form = medi_form;
    }

    public void setMedi_dose(double medi_dose) {
        this.medi_dose = medi_dose;
    }

    public int getMedi_id() {
        return medi_id;
    }

    public String getMedi_name() {
        return medi_name;
    }

    public Date getMedi_expire_date() {
        return medi_expire_date;
    }

    public double getMedi_pu() {
        return medi_pu;
    }

    public double getMedi_stock_qte() {
        return medi_stock_qte;
    }

    public String getMedi_dci() {
        return medi_dci;
    }

    public String getMedi_form() {
        return medi_form;
    }

    public double getMedi_dose() {
        return medi_dose;
    }

    public static Medicine getInstance(ResultSet medicineRS) throws SQLException {
        return new Medicine(
                medicineRS.getInt("medi_id"),
                medicineRS.getString("medi_name"),
                medicineRS.getDate("medi_expire_date"),
                medicineRS.getDouble("medi_pu"),
                medicineRS.getInt("medi_stock_qte"),
                medicineRS.getString("medi_dci"),
                medicineRS.getString("medi_form"),
                medicineRS.getDouble("medi_dose"),
                medicineRS.getInt("medi_grp")
        );
    }
}
