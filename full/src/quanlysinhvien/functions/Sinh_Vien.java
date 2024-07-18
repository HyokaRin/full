package quanlysinhvien.functions;
import database.myConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Sinh_Vien {
    Connection con = myConnection.getConnection();
    PreparedStatement ps;
    
    public int getMax(){
        int stt = 0;
            Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(stt) from sinh_vien");
            while (rs.next()) {
                stt = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sinh_Vien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stt + 1;
    }
    
    //Insert data vào database
    public void insert (int stt, String MSSV, String hoTen, String ngaySinh, String gioiTinh, String danToc, String Khoa, 
                        String Email, String namBatDau, String namKetThuc, String diaChi1, String diaChi2)
    {
        String sql = "insert into sinh_vien values (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            ps = con.prepareStatement(sql);  
            ps.setInt(1, stt);
            ps.setString(2, MSSV);
            ps.setString(3, hoTen);
            ps.setString(4, ngaySinh);
            ps.setString(5, gioiTinh);
            ps.setString(6, danToc);
            ps.setString(7, Khoa);
            ps.setString(8, Email);
            ps.setString(9, namBatDau);
            ps.setString(10, namKetThuc);
            ps.setString(11, diaChi1);
            ps.setString(12, diaChi2);  
            if (ps.executeUpdate()>0) {                              
            }
        } catch (Exception ex) {
            Logger.getLogger(Sinh_Vien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //CẬP NHẬT DANH SÁCH
    public void capnhat (int stt, String MSSV, String hoTen, String ngaySinh, String gioiTinh, String danToc, String Khoa, 
                        String Email, String namBatDau, String namKetThuc, String diaChi1, String diaChi2)
    {
        String sql = "update sinh_vien set mssv=?, ho_ten=?, ngay_sinh=?, gioi_tinh=?, dan_toc=?, khoa=?, email=?, "
                + "nam_bat_dau=?, nam_ket_thuc=?, dia_chi_nha_1=?, dia_chi_nha_2=? where stt=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, MSSV);
            ps.setString(2, hoTen);
            ps.setString(3, ngaySinh);
            ps.setString(4, gioiTinh);
            ps.setString(5, danToc);
            ps.setString(6, Khoa);
            ps.setString(7, Email);
            ps.setString(8, namBatDau);
            ps.setString(9, namKetThuc);
            ps.setString(10, diaChi1);
            ps.setString(11, diaChi2);
            ps.setInt(12, stt);      
            if (ps.executeUpdate()>0) {
                JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sinh_Vien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void xoa(int stt) {
        int chacChua = JOptionPane.showConfirmDialog(null, "Xóa hết thông tin đó!", "Chắc chưa!", JOptionPane.OK_CANCEL_OPTION, 0);
        if (chacChua == JOptionPane.OK_OPTION) {
            try {
                ps = con.prepareStatement("delete from sinh_vien where stt=?");
                ps.setInt(1, stt);
                if (ps.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Đã xóa thành công!");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Sinh_Vien.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    //KIỂM TRA TRÙNG LẶP TRƯỜNG EMAIL
    public boolean kiemTraTonTaiEmail (String Email){
        try {            
            //TẠO CÂU LỆNH TRUY VẤN ĐẾN DATABASE CHỌN(SLECT) TẤT CẢ(*) THÔNG TIN Ở TABLE SINH_VIEN TẠI TRƯỜNG EMAIL = ? SẼ ĐƯỢC THAY BẰNG GIÁ TRỊ THỰC TẾ
            ps = con.prepareStatement("select * from sinh_vien where Email = ?");
            
            //ĐẶT GIÁ TRỊ CỦA THAM SỐ THÀNH GIÁ TRỊ CỦA BIẾN EMAIL ([1] LÀ VỊ TRÍ ĐẦU CỦA [?])
            ps.setString(1, Email);
            
            //THỰC THI CÂU LỆNH
            ResultSet rs = ps.executeQuery();
            
            //TRẢ LẠI KẾT QUẢ [NEXT()] DUYỆT TÌM CÁC DÒNG XEM CÓ DATA NÀO TỒN TẠI HAY KHÔNG 
            //NẾU TRẢ VỀ TRUE THÌ ĐÃ CÓ, KHÔNG THÌ FALSE
            if (rs.next()) 
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sinh_Vien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean kiemTraTonTaiSTT (int stt){
        try {            
            //TẠO CÂU LỆNH TRUY VẤN ĐẾN DATABASE CHỌN(SLECT) TẤT CẢ(*) THÔNG TIN Ở TABLE SINH_VIEN TẠI TRƯỜNG STT = ? SẼ ĐƯỢC THAY BẰNG GIÁ TRỊ THỰC TẾ
            ps = con.prepareStatement("select * from sinh_vien where stt = ?");
            ps.setInt(1, stt);
            ResultSet rs = ps.executeQuery();            
            if (rs.next()) 
            {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sinh_Vien.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //HIỂN THỊ DATABASE LÊN TABLE 
    public void hienThiDatabaseSinhVien(JTable bang, String searchValue) {
        String sql = "select * from sinh_vien where concat (stt,ho_ten,Email)like ? order by stt asc";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchValue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) bang.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[12];
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
                model.addRow(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Sinh_Vien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}

