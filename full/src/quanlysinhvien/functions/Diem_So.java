/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlysinhvien.functions;

import database.myConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import quanlysinhvien.giaoDienGV;

/**
 *
 * @author Thinh Le
 */
public class Diem_So {
    Connection con = myConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int stt = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(stt) from diem_so");
            while (rs.next()) {
                stt = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stt + 1;
    }
    
    public boolean getMSSV(String mssv, int hocky) {
        try {
            ps = con.prepareStatement("select * from hoc_ky where mssv=? and hoc_ky=?");
            ps.setString(1, mssv);
            ps.setInt(2, hocky);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                giaoDienGV.jTextField14_DiemSo_hoTen.setText(rs.getString("ho_ten")); 
                giaoDienGV.jTextField15_DiemSo_Khoa.setText(rs.getString("khoa"));
                giaoDienGV.jTextField1_DiemSo_MSSV.setText(rs.getString("mssv"));
                giaoDienGV.jTextField1_DiemSo_hocKy.setText(rs.getString("hoc_ky"));
                giaoDienGV.jTextField16_DiemSo_Mon1.setText(rs.getString("mon_1"));
                giaoDienGV.jTextField17_DiemSo_Mon2.setText(rs.getString("mon_2"));
                giaoDienGV.jTextField18_DiemSo_Mon3.setText(rs.getString("mon_3"));
                giaoDienGV.jTextField19_DiemSo_Mon4.setText(rs.getString("mon_4"));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Sinh viên hoặc học kỳ không tồn tại!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void insert(int stt, String MSSV, String hoTen, String khoa, int hocKy, String mon1, String mon2, String mon3, String mon4, double diem1, double diem2, double diem3, double diem4, double trungBinh)
    {
        String sql = "insert into diem_so values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);  
            ps.setInt(1, stt);
            ps.setString(2, MSSV);
            ps.setString(3, hoTen);
            ps.setString(4, khoa);
            ps.setInt(5, hocKy);
            ps.setString(6, mon1);
            ps.setDouble(7, diem1);
            ps.setString(8, mon2);
            ps.setDouble(9, diem2);
            ps.setString(10, mon3);
            ps.setDouble(11, diem3);
            ps.setString(12, mon4); 
            ps.setDouble(13, diem4);
            ps.setDouble(14, trungBinh);
            if (ps.executeUpdate()>0) {                         
            }
        } catch (Exception ex) {
            Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void capnhat (int stt, double diem_1, double diem_2, double diem_3, double diem_4, double trung_binh)
    {
        String sql = "update diem_so set diem_1=?, diem_2=?, diem_3=?, diem_4=?, trung_binh=? where stt=?";
        try {
            ps = con.prepareStatement(sql);            
            ps.setDouble(1, diem_1);
            ps.setDouble(2, diem_2);
            ps.setDouble(3, diem_3);
            ps.setDouble(4, diem_4);
            ps.setDouble(5, trung_binh);
            ps.setInt(6, stt);
            if (ps.executeUpdate()>0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //HIỂN THỊ DATABASE LÊN TABLE 
    public void hienThiDatabaseDiemSo(JTable bang, String searchValue) {
        String sql = "select * from diem_so where concat (stt,hoc_ky,mssv)like ? order by stt asc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) bang.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                row[9] = rs.getString(10);
                row[10] = rs.getString(11);
                row[11] = rs.getString(12);
                row[12] = rs.getString(13);
                row[13] = rs.getString(14);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public boolean kiemTraTonTaiSTT(int stt) {
        try {
            ps = con.prepareStatement("select * from diem_so where stt=?");
            ps.setInt(1, stt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
        
    public boolean kiemTraTonTaiMSSVHocKy(String mssv, int hocky) {
        try {
            ps = con.prepareStatement("select * from diem_so where mssv=? and hoc_ky=?");
            ps.setString(1, mssv);
            ps.setInt(2, hocky);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}

