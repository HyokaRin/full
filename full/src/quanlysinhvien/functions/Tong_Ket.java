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
    import javax.swing.JTable;
    import javax.swing.table.DefaultTableModel;

    /**
     *
     * @author Thinh Le
     */
    public class Tong_Ket {

        Connection con = myConnection.getConnection();
        PreparedStatement ps;

        public boolean kiemTraTonTaiMSSV(String mssv) {
            try {
                //TẠO CÂU LỆNH TRUY VẤN ĐẾN DATABASE CHỌN(SLECT) TẤT CẢ(*) THÔNG TIN Ở TABLE SINH_VIEN TẠI TRƯỜNG STT = ? SẼ ĐƯỢC THAY BẰNG GIÁ TRỊ THỰC TẾ
                ps = con.prepareStatement("select * from hoc_ky where mssv = ?");
                ps.setString(1, mssv);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Tong_Ket.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }

        public void hienThiDatabaseTongKet(JTable bang, String mssv) {
            String sql = "select * from diem_so where mssv=?";
            try {
                ps = con.prepareStatement(sql);
                ps.setString(1, mssv);
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

        public double tinhTB10(String mssv) {
            double TB10 = 0.0;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String query = "select avg(trung_binh) from diem_so where mssv = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, mssv);
                rs = ps.executeQuery();
                if (rs.next()) {
                    TB10 = rs.getDouble(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            return TB10;
        }

        public double chuyenDoiHe4(double TB10) {
            if (TB10 >= 8.5) {
                return 4.0;
            } else if (TB10 >= 7.0) {
                return 3.0;
            } else if (TB10 >= 5.5) {
                return 2.0;
            } else if (TB10 >= 4.0) {
                return 1.0;
            } else {
                return 0.0;
            }
        }

        public int tinhTongMon(String mssv) {
            int tongMon = 0;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String query = "select count(*) from diem_so where mssv = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, mssv);
                rs = ps.executeQuery();
                if (rs.next()) {
                    tongMon = rs.getInt(1) * 4; // Mỗi học kỳ có 4 môn
                }
            } catch (SQLException ex) {
                Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            return tongMon;
        }

        public int tinhTongTinChi(String mssv) {
            int tongTinChi = 0;
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String query = "select count(*) from diem_so where mssv = ?";
                ps = con.prepareStatement(query);
                ps.setString(1, mssv);
                rs = ps.executeQuery();
                if (rs.next()) {
                    tongTinChi = rs.getInt(1) * 4 * 4; // Mỗi học kỳ có 4 môn, mỗi môn 4 tín chỉ
                }
            } catch (SQLException ex) {
                Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException e) {
                    Logger.getLogger(Diem_So.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            return tongTinChi;
        }
    }
