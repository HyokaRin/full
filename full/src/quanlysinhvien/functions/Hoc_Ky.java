package quanlysinhvien.functions;

import quanlysinhvien.functions.Sinh_Vien;
import database.myConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SNIHostName;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import quanlysinhvien.giaoDienGV;

public class Hoc_Ky {
    Connection con = myConnection.getConnection();
    PreparedStatement ps;
    public int getMax(){
        int stt = 0;
            Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(stt) from hoc_ky");
            while (rs.next()) {
                stt = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hoc_Ky.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stt + 1;
    }
    
    public boolean getMSSV(String mssv){
        try {
            ps = con.prepareStatement("select * from sinh_vien where mssv = ?");
            ps.setString(1, mssv);
            ResultSet rs = ps.executeQuery();            
            if (rs.next()) {
                giaoDienGV.jTextField1_HocKy_STT.setText(String.valueOf(rs.getInt("stt")));
                giaoDienGV.jTextField10_HocKy_hoTen.setText(rs.getString("ho_ten"));
                giaoDienGV.jTextField11_HocKy_Khoa.setText(rs.getString("khoa"));
                giaoDienGV.jTextField1_HocKy_MSSV.setText(rs.getString("mssv"));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Sinh viên không tồn tại!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hoc_Ky.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int tinhHocKy(String mssv) {
        int tong = 0;
        try {
            ps = con.prepareStatement("select count(*) as 'total' from hoc_ky where mssv=?");
            ps.setString(1, mssv);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tong = rs.getInt(1);
            }
            if (tong == 8) {
                JOptionPane.showMessageDialog(null, "Sinh viên đã hoàn thành các khóa!");
                return -1;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hoc_Ky.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tong;
    }
    
    public void insert(int stt, String MSSV, String hoTen, String khoa, int hocKy, String mon1, String mon2, String mon3, String mon4)
    {
        String sql = "insert into hoc_ky values (?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);  
            ps.setInt(1, stt);
            ps.setString(2, MSSV);
            ps.setString(3, hoTen);
            ps.setString(4, khoa);
            ps.setInt(5, hocKy);
            ps.setString(6, mon1);
            ps.setString(7, mon2);
            ps.setString(8, mon3);
            ps.setString(9, mon4); 
            if (ps.executeUpdate()>0) {                         
            }
        } catch (Exception ex) {
            Logger.getLogger(Sinh_Vien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //HIỂN THỊ DATABASE LÊN TABLE 
    public void hienThiDatabaseHocKy(JTable bang, String searchValue) {
        String sql = "select * from hoc_ky where concat (stt,hoc_ky,mssv)like ? order by stt asc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) bang.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[9];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                row[5] = rs.getString(6);
                row[6] = rs.getString(7);
                row[7] = rs.getString(8);
                row[8] = rs.getString(9);
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hoc_Ky.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean kiemTraTonTaiHocKy (String mssv, int hocKy){
        try {
            ps = con.prepareStatement("select * from hoc_ky where mssv = ? and hoc_ky = ?");
            ps.setString(1, mssv);
            ps.setInt(2, hocKy);
            ResultSet rs = ps.executeQuery();            
            if (rs.next()) 
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hoc_Ky.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean kiemTraTonTaiHocPhan(String mssv, String hocPhanSo, String hocPhan) {
        String sql = "select * from hoc_ky where mssv=? and " + hocPhanSo + " = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, mssv);
            ps.setString(2, hocPhan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Hoc_Ky.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
