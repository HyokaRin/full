/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package quanlysinhvien;

import quanlysinhvien.functions.kiemTraNam;
import quanlysinhvien.functions.Diem_So;
import quanlysinhvien.functions.Hoc_Ky;
import quanlysinhvien.functions.Sinh_Vien;
import quanlysinhvien.functions.Tong_Ket;
import java.awt.Color;
import java.awt.Image;
import java.awt.print.PrinterException;
import java.io.File;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.regex.*;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Thinh Le
 */
public class giaoDienGV extends javax.swing.JFrame {

    Hoc_Ky hocky = new Hoc_Ky();
    Sinh_Vien sinhvien = new Sinh_Vien();
    Diem_So diemso = new Diem_So();
    Tong_Ket tongket = new Tong_Ket();
    NumberFormat nf = NumberFormat.getInstance();
    private int thongTinCot;
    private DefaultTableModel model;
    private String imgPath;

    public giaoDienGV() {
        initComponents();
        init();
    }

    public void init() {
        xemDanhSachHocKy();
        xemDanhSachSV();
        xemDanhSachDiemSo();
        jTextField1_SinhVien_Stt.setText(String.valueOf(sinhvien.getMax()));
        jTextField1_HocKy_STT.setText(String.valueOf(hocky.getMax()));
        jTextField2_DiemSo_STT.setText(String.valueOf(diemso.getMax()));
    }

    private void xemDanhSachSV() {
        sinhvien.hienThiDatabaseSinhVien(jTable1_SinhVien_bangThongTin, "");
        model = (DefaultTableModel) jTable1_SinhVien_bangThongTin.getModel();
    }

    private void xemDanhSachHocKy() {
        hocky.hienThiDatabaseHocKy(jTable2_HocKy_bangSinhVien, "");
        model = (DefaultTableModel) jTable2_HocKy_bangSinhVien.getModel();
    }

    private void xemDanhSachDiemSo() {
        diemso.hienThiDatabaseDiemSo(jTable4_DiemSo_bangDiem, "");
        model = (DefaultTableModel) jTable4_DiemSo_bangDiem.getModel();
    }

    public boolean kiemTraField() {
        //KIỂM TRA MÃ SỐ SINH VIÊN
        String MSSV = jTextField1_SinhVien_MSSV.getText(); //Khởi tạo đối tượng MSSV
        String pattern = "\\d{2}DH\\d{6}"; //Regex kiểm tra định dạng của mã số sinh viên
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(MSSV); //Tạo matcher m và link pattern vào mssv thông qua r.matcher(...)
        if (jTextField1_SinhVien_MSSV.getText().isEmpty()) //Kiểm tra xem mã số sinh viên có trống hay không 
        {
            JOptionPane.showMessageDialog(this, "Chưa điền mã số sinh viên!");
            return false;
        }
        if (!m.matches()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng định dạng xxDHxxxxxx");
            return false;
        }

        //KIỂM TRA HỌ VÀ TÊN
        if (jTextField2_SinhVien_hoVaTen.getText().isEmpty()) //Kiểm tra xem họ và tên có trống hay không 
        {
            JOptionPane.showMessageDialog(this, "Chưa điền họ và tên!");
            return false;
        }

        //KIỂM TRA NGÀY SINH
        if (jDateChooser1__SinhVien_ngaySinh.getDate() == null) //Kiểm tra xem ngày sinh có trống hay không 
        {
            JOptionPane.showMessageDialog(this, "Chưa điền ngày sinh!");
            return false;
        }
        if (jDateChooser1__SinhVien_ngaySinh.getDate().compareTo(new Date()) > 0) //Kiểm tra xem học sinh có du hành thời gian hay không :))) 
        {
            JOptionPane.showMessageDialog(this, "Vui lòng không chọn ngày sinh trong tương lai!");
            return false;
        }

        //KIỂM TRA DÂN TỘC
        if (jTextField4_SinhVien_danToc.getText().isEmpty()) //Kiểm tra xem dân tộc có trống hay không 
        {
            JOptionPane.showMessageDialog(this, "Chưa điền dân tộc!");
            return false;
        }

        //KIỂM TRA KHOA
        if (jTextField5_SinhVien_Khoa.getText().isEmpty()) //Kiểm tra xem thông tin khoa có trống hay không 
        {
            JOptionPane.showMessageDialog(this, "Chưa điền thông tin khoa!");
            return false;
        }

        String checkMail = jTextField2_SinhVien_Email.getText();
        String mailPattern = MSSV + "+@st.huflit.edu.vn";
        Pattern mailPat = Pattern.compile(mailPattern);
        Matcher mail = mailPat.matcher(checkMail);
        if (checkMail.isEmpty()) //Kiểm tra xem thông tin khoa có trống hay không 
        {
            JOptionPane.showMessageDialog(this, "Chưa điền email!");
            return false;
        }
        if (!mail.matches()) {
            JOptionPane.showMessageDialog(this, "Mail phải theo định dạng [MSSV+@st.huflit.edu.vn]!");
            return false;
        }

        //KIỂM TRA NĂM BẮT ĐẦU & KIỂM TRA NĂM KẾT THÚC
        String namBatDau = jTextField6_SinhVien_namBatDau.getText();
        String namKetThuc = jTextField7_SinhVien_namKetThuc.getText();
        boolean kiemtra = kiemTraNam.KiemTraNam(namBatDau, namKetThuc);
        if (!kiemtra) {
            return false;
        }

        //KIỂM TRA ĐỊA CHỈ NHÀ
        if (jTextField9_SinhVien_diaChiNha1.getText().isEmpty()) //Kiểm tra xem địa chỉ nhà có trống hay không 
        {
            JOptionPane.showMessageDialog(this, "Chưa điền địa chỉ!");
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        roundPanel1 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel9 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        roundPanel3 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel6 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1_SinhVien_MSSV = new javax.swing.JTextField();
        jTextField2_SinhVien_hoVaTen = new javax.swing.JTextField();
        jTextField5_SinhVien_Khoa = new javax.swing.JTextField();
        jTextField6_SinhVien_namBatDau = new javax.swing.JTextField();
        jComboBox1_SinhVien_gioiTinh = new javax.swing.JComboBox<>();
        jTextField4_SinhVien_danToc = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField7_SinhVien_namKetThuc = new javax.swing.JTextField();
        jTextField9_SinhVien_diaChiNha1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jDateChooser1__SinhVien_ngaySinh = new com.toedter.calendar.JDateChooser();
        jTextField1_SinhVien_Stt = new javax.swing.JTextField();
        jTextField2_SinhVien_Email = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        roundPanel7 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel22 = new javax.swing.JLabel();
        jTextField2_SinhVien_diaChiNha2 = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        roundPanel8 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel9 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel12 = new javax.swing.JLabel();
        jButton2_SinhVien_timKiem = new javax.swing.JButton();
        jTextField3_SinhVien_timKiem = new javax.swing.JTextField();
        jButton3_SinhVien_Refresh = new javax.swing.JButton();
        roundPanel11 = new quanlysinhvien.customizedShapes.RoundPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1_SinhVien_bangThongTin = new javax.swing.JTable();
        roundPanel12 = new quanlysinhvien.customizedShapes.RoundPanel();
        jButton9_SinhVien_Them = new javax.swing.JButton();
        Button_SinhVien_Exit = new javax.swing.JButton();
        jButton5_SinhVien_capNhat = new javax.swing.JButton();
        jButton7_SinhVien_Clear = new javax.swing.JButton();
        jButton6_SinhVien_Xoa = new javax.swing.JButton();
        jButton1_SinhVien_xuatFile = new javax.swing.JButton();
        roundPanel2 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel10 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel19 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel23 = new javax.swing.JLabel();
        jButton10_HocKy_timSV_Refresh = new javax.swing.JButton();
        jButton11_HocKy_timSV_timKiem = new javax.swing.JButton();
        jTextField12_HocKy_timSV = new javax.swing.JTextField();
        roundPanel20 = new quanlysinhvien.customizedShapes.RoundPanel();
        jButton12_HocKy_Luu = new javax.swing.JButton();
        jButton13_HocKy_Export = new javax.swing.JButton();
        jButton14_HocKy_Clear = new javax.swing.JButton();
        Button_HocKy_Exit = new javax.swing.JButton();
        roundPanel21 = new quanlysinhvien.customizedShapes.RoundPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2_HocKy_bangSinhVien = new javax.swing.JTable();
        roundPanel13 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel18 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField8_HocKy_MSSV_timKiem = new javax.swing.JTextField();
        jButton4_HocKy_timKiem = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField10_HocKy_hoTen = new javax.swing.JTextField();
        jTextField11_HocKy_Khoa = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox2_HocKy_hocKy = new javax.swing.JComboBox<>();
        jComboBox3_HocKy_Mon1 = new javax.swing.JComboBox<>();
        jComboBox4_HocKy_Mon2 = new javax.swing.JComboBox<>();
        jComboBox5_HocKy_Mon3 = new javax.swing.JComboBox<>();
        jComboBox6_HocKy_Mon4 = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextField1_HocKy_STT = new javax.swing.JTextField();
        jTextField1_HocKy_MSSV = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        roundPanel4 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel14 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel22 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel24 = new javax.swing.JLabel();
        jTextField13_DiemSo_MSSV_timKiem = new javax.swing.JTextField();
        jButton15_DiemSo_timKiem = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jTextField1_DiemSo_MSSV_hocKy = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jTextField14_DiemSo_hoTen = new javax.swing.JTextField();
        jTextField15_DiemSo_Khoa = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField16_DiemSo_Mon1 = new javax.swing.JTextField();
        jTextField17_DiemSo_Mon2 = new javax.swing.JTextField();
        jTextField18_DiemSo_Mon3 = new javax.swing.JTextField();
        jTextField19_DiemSo_Mon4 = new javax.swing.JTextField();
        jTextField21_DiemSo_diemMon1 = new javax.swing.JTextField();
        jTextField22_DiemSo_diemMon2 = new javax.swing.JTextField();
        jTextField23_DiemSo_diemMon3 = new javax.swing.JTextField();
        jTextField24_DiemSo_diemMon4 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jTextField1_DiemSo_MSSV = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jTextField2_DiemSo_STT = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jTextField1_DiemSo_hocKy = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        roundPanel16 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel24 = new quanlysinhvien.customizedShapes.RoundPanel();
        jButton18_DiemSo_Luu = new javax.swing.JButton();
        jButton20_DiemSo_capNhat = new javax.swing.JButton();
        jButton21_DiemSo_Export = new javax.swing.JButton();
        jButton22_DiemSo_Clear = new javax.swing.JButton();
        Button_DiemSo_Exit = new javax.swing.JButton();
        roundPanel25 = new quanlysinhvien.customizedShapes.RoundPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4_DiemSo_bangDiem = new javax.swing.JTable();
        roundPanel23 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel36 = new javax.swing.JLabel();
        jButton17_DiemSo_timSV_Refresh = new javax.swing.JButton();
        jButton19_DiemSo_timSV_timKiem = new javax.swing.JButton();
        jTextField28_DiemSo_timSV = new javax.swing.JTextField();
        roundPanel5 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel15 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel29 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel34 = new javax.swing.JLabel();
        jTextField26_TongKet_MSSV_timKiem = new javax.swing.JTextField();
        jButton16_TongKet_MSSV_timKiem = new javax.swing.JButton();
        roundPanel26 = new quanlysinhvien.customizedShapes.RoundPanel();
        jLabel40_TongKet_He10 = new javax.swing.JLabel();
        jLabel40_TongKet_He4 = new javax.swing.JLabel();
        jLabel40_TongKet_tongMon = new javax.swing.JLabel();
        jLabel40_TongKet_tongTinChi = new javax.swing.JLabel();
        roundPanel17 = new quanlysinhvien.customizedShapes.RoundPanel();
        roundPanel28 = new quanlysinhvien.customizedShapes.RoundPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3_TongKet_bangTongKet = new javax.swing.JTable();
        roundPanel30 = new quanlysinhvien.customizedShapes.RoundPanel();
        jButton23_TongKet_Export = new javax.swing.JButton();
        jButton24_TongKet_Clear = new javax.swing.JButton();
        Button_DiemSo_Exit1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(238, 236, 236));

        roundPanel1.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel1.setRoundBottomLeft(20);
        roundPanel1.setRoundBottomRight(20);
        roundPanel1.setRoundTopLeft(20);
        roundPanel1.setRoundTopRight(20);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("CHƯƠNG TRÌNH QUẢN LÝ ĐIỂM SINH VIÊN - NHÓM 16 ");

        javax.swing.GroupLayout roundPanel1Layout = new javax.swing.GroupLayout(roundPanel1);
        roundPanel1.setLayout(roundPanel1Layout);
        roundPanel1Layout.setHorizontalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel1Layout.setVerticalGroup(
            roundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTabbedPane1.setName(""); // NOI18N

        roundPanel3.setBackground(new java.awt.Color(230, 228, 228));
        roundPanel3.setRoundBottomLeft(15);
        roundPanel3.setRoundBottomRight(15);

        roundPanel6.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel6.setRoundBottomLeft(15);
        roundPanel6.setRoundBottomRight(15);
        roundPanel6.setRoundTopLeft(15);
        roundPanel6.setRoundTopRight(15);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("MSSV");

        jTextField1_SinhVien_MSSV.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTextField2_SinhVien_hoVaTen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTextField5_SinhVien_Khoa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTextField6_SinhVien_namBatDau.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField6_SinhVien_namBatDau.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField6_SinhVien_namBatDauKeyTyped(evt);
            }
        });

        jComboBox1_SinhVien_gioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        jTextField4_SinhVien_danToc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Họ và tên");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Ngày sinh");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Giới tính");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Dân tộc");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Khoa ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Năm bắt đầu");

        jTextField7_SinhVien_namKetThuc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField7_SinhVien_namKetThuc.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField7_SinhVien_namKetThucKeyTyped(evt);
            }
        });

        jTextField9_SinhVien_diaChiNha1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Năm kết thúc");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Địa chỉ 1");

        jTextField1_SinhVien_Stt.setEditable(false);
        jTextField1_SinhVien_Stt.setBackground(new java.awt.Color(218, 218, 218));
        jTextField1_SinhVien_Stt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField1_SinhVien_Stt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1_SinhVien_Stt.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField1_SinhVien_Stt.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextField1_SinhVien_Stt.setFocusable(false);

        jTextField2_SinhVien_Email.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Email");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(0, 2));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 321, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        roundPanel7.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel7.setRoundTopLeft(15);
        roundPanel7.setRoundTopRight(15);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(68, 85, 105));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Thông tin sinh viên");

        javax.swing.GroupLayout roundPanel7Layout = new javax.swing.GroupLayout(roundPanel7);
        roundPanel7.setLayout(roundPanel7Layout);
        roundPanel7Layout.setHorizontalGroup(
            roundPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        roundPanel7Layout.setVerticalGroup(
            roundPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel7Layout.createSequentialGroup()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTextField2_SinhVien_diaChiNha2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Địa chỉ 2");

        javax.swing.GroupLayout roundPanel6Layout = new javax.swing.GroupLayout(roundPanel6);
        roundPanel6.setLayout(roundPanel6Layout);
        roundPanel6Layout.setHorizontalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                    .addComponent(roundPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(72, 72, 72)
                        .addComponent(jTextField1_SinhVien_MSSV)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1_SinhVien_Stt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(44, 44, 44)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2_SinhVien_hoVaTen)
                            .addComponent(jDateChooser1__SinhVien_ngaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4))
                        .addGap(21, 21, 21)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4_SinhVien_danToc, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(roundPanel6Layout.createSequentialGroup()
                                .addComponent(jComboBox1_SinhVien_gioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField5_SinhVien_Khoa, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextField7_SinhVien_namKetThuc)))
                    .addGroup(roundPanel6Layout.createSequentialGroup()
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11))
                        .addGap(23, 23, 23)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6_SinhVien_namBatDau)
                            .addComponent(jTextField2_SinhVien_Email)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel33))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2_SinhVien_diaChiNha2)
                            .addComponent(jTextField9_SinhVien_diaChiNha1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))))
                .addContainerGap())
        );
        roundPanel6Layout.setVerticalGroup(
            roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1_SinhVien_MSSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1_SinhVien_Stt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2_SinhVien_hoVaTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jDateChooser1__SinhVien_ngaySinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1_SinhVien_gioiTinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4_SinhVien_danToc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5_SinhVien_Khoa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField2_SinhVien_Email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6_SinhVien_namBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField7_SinhVien_namKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField9_SinhVien_diaChiNha1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2_SinhVien_diaChiNha2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        roundPanel8.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel8.setRoundBottomLeft(15);
        roundPanel8.setRoundBottomRight(15);
        roundPanel8.setRoundTopLeft(15);
        roundPanel8.setRoundTopRight(15);

        roundPanel9.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel9.setPreferredSize(new java.awt.Dimension(501, 51));
        roundPanel9.setRoundBottomLeft(15);
        roundPanel9.setRoundBottomRight(15);
        roundPanel9.setRoundTopLeft(15);
        roundPanel9.setRoundTopRight(15);

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Tìm sinh viên");

        jButton2_SinhVien_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton2_SinhVien_timKiem.setForeground(new java.awt.Color(68, 85, 105));
        jButton2_SinhVien_timKiem.setText("Tìm kiếm");
        jButton2_SinhVien_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_SinhVien_timKiemActionPerformed(evt);
            }
        });

        jTextField3_SinhVien_timKiem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton3_SinhVien_Refresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton3_SinhVien_Refresh.setForeground(new java.awt.Color(68, 85, 105));
        jButton3_SinhVien_Refresh.setText("Refresh");
        jButton3_SinhVien_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_SinhVien_RefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel9Layout = new javax.swing.GroupLayout(roundPanel9);
        roundPanel9.setLayout(roundPanel9Layout);
        roundPanel9Layout.setHorizontalGroup(
            roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel9Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jTextField3_SinhVien_timKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton2_SinhVien_timKiem)
                .addGap(18, 18, 18)
                .addComponent(jButton3_SinhVien_Refresh)
                .addGap(31, 31, 31))
        );
        roundPanel9Layout.setVerticalGroup(
            roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel9Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3_SinhVien_Refresh)
                        .addComponent(jButton2_SinhVien_timKiem)
                        .addComponent(jTextField3_SinhVien_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        roundPanel11.setRoundBottomLeft(15);
        roundPanel11.setRoundBottomRight(15);
        roundPanel11.setRoundTopLeft(15);
        roundPanel11.setRoundTopRight(15);

        jTable1_SinhVien_bangThongTin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(68, 85, 105), 1, true));
        jTable1_SinhVien_bangThongTin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MSSV", "Họ và tên", "Ngày sinh", "Giới tính", "Dân tộc", "Khoa", "Email", "Năm bắt đầu", "Năm kết thúc", "Địa chỉ 1", "Địa chỉ 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1_SinhVien_bangThongTin.setGridColor(new java.awt.Color(218, 216, 216));
        jTable1_SinhVien_bangThongTin.setShowGrid(false);
        jTable1_SinhVien_bangThongTin.setShowHorizontalLines(true);
        jTable1_SinhVien_bangThongTin.setShowVerticalLines(true);
        jTable1_SinhVien_bangThongTin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1_SinhVien_bangThongTinMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1_SinhVien_bangThongTin);
        if (jTable1_SinhVien_bangThongTin.getColumnModel().getColumnCount() > 0) {
            jTable1_SinhVien_bangThongTin.getColumnModel().getColumn(0).setPreferredWidth(30);
            jTable1_SinhVien_bangThongTin.getColumnModel().getColumn(1).setPreferredWidth(82);
        }

        javax.swing.GroupLayout roundPanel11Layout = new javax.swing.GroupLayout(roundPanel11);
        roundPanel11.setLayout(roundPanel11Layout);
        roundPanel11Layout.setHorizontalGroup(
            roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        roundPanel11Layout.setVerticalGroup(
            roundPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundPanel12.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel12.setRoundBottomLeft(15);
        roundPanel12.setRoundBottomRight(15);
        roundPanel12.setRoundTopLeft(15);
        roundPanel12.setRoundTopRight(15);

        jButton9_SinhVien_Them.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton9_SinhVien_Them.setForeground(new java.awt.Color(68, 85, 105));
        jButton9_SinhVien_Them.setText("Thêm");
        jButton9_SinhVien_Them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9_SinhVien_ThemActionPerformed(evt);
            }
        });

        Button_SinhVien_Exit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Button_SinhVien_Exit.setForeground(new java.awt.Color(68, 85, 105));
        Button_SinhVien_Exit.setText("Exit");
        Button_SinhVien_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_SinhVien_ExitActionPerformed(evt);
            }
        });

        jButton5_SinhVien_capNhat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton5_SinhVien_capNhat.setForeground(new java.awt.Color(68, 85, 105));
        jButton5_SinhVien_capNhat.setText("Cập nhật");
        jButton5_SinhVien_capNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5_SinhVien_capNhatActionPerformed(evt);
            }
        });

        jButton7_SinhVien_Clear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton7_SinhVien_Clear.setForeground(new java.awt.Color(68, 85, 105));
        jButton7_SinhVien_Clear.setText("Clear");
        jButton7_SinhVien_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7_SinhVien_ClearActionPerformed(evt);
            }
        });

        jButton6_SinhVien_Xoa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton6_SinhVien_Xoa.setForeground(new java.awt.Color(68, 85, 105));
        jButton6_SinhVien_Xoa.setText("Xóa");
        jButton6_SinhVien_Xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6_SinhVien_XoaActionPerformed(evt);
            }
        });

        jButton1_SinhVien_xuatFile.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton1_SinhVien_xuatFile.setForeground(new java.awt.Color(68, 85, 105));
        jButton1_SinhVien_xuatFile.setText("Export");
        jButton1_SinhVien_xuatFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_SinhVien_xuatFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel12Layout = new javax.swing.GroupLayout(roundPanel12);
        roundPanel12.setLayout(roundPanel12Layout);
        roundPanel12Layout.setHorizontalGroup(
            roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel12Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jButton9_SinhVien_Them, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jButton5_SinhVien_capNhat, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jButton6_SinhVien_Xoa, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1_SinhVien_xuatFile, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(jButton7_SinhVien_Clear, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addComponent(Button_SinhVien_Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(30, 30, 30))
        );

        roundPanel12Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {Button_SinhVien_Exit, jButton1_SinhVien_xuatFile, jButton5_SinhVien_capNhat, jButton6_SinhVien_Xoa, jButton7_SinhVien_Clear, jButton9_SinhVien_Them});

        roundPanel12Layout.setVerticalGroup(
            roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton9_SinhVien_Them, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Button_SinhVien_Exit)
                    .addComponent(jButton5_SinhVien_capNhat)
                    .addComponent(jButton7_SinhVien_Clear)
                    .addComponent(jButton6_SinhVien_Xoa)
                    .addComponent(jButton1_SinhVien_xuatFile))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundPanel8Layout = new javax.swing.GroupLayout(roundPanel8);
        roundPanel8.setLayout(roundPanel8Layout);
        roundPanel8Layout.setHorizontalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
                    .addComponent(roundPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel8Layout.setVerticalGroup(
            roundPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel3Layout = new javax.swing.GroupLayout(roundPanel3);
        roundPanel3.setLayout(roundPanel3Layout);
        roundPanel3Layout.setHorizontalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel3Layout.setVerticalGroup(
            roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Sinh Viên", roundPanel3);

        roundPanel2.setBackground(new java.awt.Color(230, 228, 228));
        roundPanel2.setRoundBottomLeft(15);
        roundPanel2.setRoundBottomRight(15);

        roundPanel10.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel10.setRoundBottomLeft(15);
        roundPanel10.setRoundBottomRight(15);
        roundPanel10.setRoundTopLeft(15);
        roundPanel10.setRoundTopRight(15);

        roundPanel19.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel19.setPreferredSize(new java.awt.Dimension(918, 51));
        roundPanel19.setRoundBottomLeft(15);
        roundPanel19.setRoundBottomRight(15);
        roundPanel19.setRoundTopLeft(15);
        roundPanel19.setRoundTopRight(15);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Tìm sinh viên");

        jButton10_HocKy_timSV_Refresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton10_HocKy_timSV_Refresh.setForeground(new java.awt.Color(68, 85, 105));
        jButton10_HocKy_timSV_Refresh.setText("Refresh");
        jButton10_HocKy_timSV_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10_HocKy_timSV_RefreshActionPerformed(evt);
            }
        });

        jButton11_HocKy_timSV_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton11_HocKy_timSV_timKiem.setForeground(new java.awt.Color(68, 85, 105));
        jButton11_HocKy_timSV_timKiem.setText("Tìm kiếm");
        jButton11_HocKy_timSV_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11_HocKy_timSV_timKiemActionPerformed(evt);
            }
        });

        jTextField12_HocKy_timSV.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout roundPanel19Layout = new javax.swing.GroupLayout(roundPanel19);
        roundPanel19.setLayout(roundPanel19Layout);
        roundPanel19Layout.setHorizontalGroup(
            roundPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel19Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel23)
                .addGap(18, 18, 18)
                .addComponent(jTextField12_HocKy_timSV)
                .addGap(18, 18, 18)
                .addComponent(jButton11_HocKy_timSV_timKiem)
                .addGap(18, 18, 18)
                .addComponent(jButton10_HocKy_timSV_Refresh)
                .addGap(31, 31, 31))
        );
        roundPanel19Layout.setVerticalGroup(
            roundPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel19Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(roundPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton10_HocKy_timSV_Refresh)
                        .addComponent(jButton11_HocKy_timSV_timKiem)
                        .addComponent(jTextField12_HocKy_timSV, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        roundPanel20.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel20.setRoundBottomLeft(15);
        roundPanel20.setRoundBottomRight(15);
        roundPanel20.setRoundTopLeft(15);
        roundPanel20.setRoundTopRight(15);

        jButton12_HocKy_Luu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton12_HocKy_Luu.setForeground(new java.awt.Color(68, 85, 105));
        jButton12_HocKy_Luu.setText("Lưu");
        jButton12_HocKy_Luu.setMaximumSize(new java.awt.Dimension(78, 32));
        jButton12_HocKy_Luu.setMinimumSize(new java.awt.Dimension(78, 32));
        jButton12_HocKy_Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12_HocKy_LuuActionPerformed(evt);
            }
        });

        jButton13_HocKy_Export.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton13_HocKy_Export.setForeground(new java.awt.Color(68, 85, 105));
        jButton13_HocKy_Export.setText("Export");
        jButton13_HocKy_Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13_HocKy_ExportActionPerformed(evt);
            }
        });

        jButton14_HocKy_Clear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton14_HocKy_Clear.setForeground(new java.awt.Color(68, 85, 105));
        jButton14_HocKy_Clear.setText("Clear");
        jButton14_HocKy_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14_HocKy_ClearActionPerformed(evt);
            }
        });

        Button_HocKy_Exit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Button_HocKy_Exit.setForeground(new java.awt.Color(68, 85, 105));
        Button_HocKy_Exit.setText("Exit");
        Button_HocKy_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_HocKy_ExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel20Layout = new javax.swing.GroupLayout(roundPanel20);
        roundPanel20.setLayout(roundPanel20Layout);
        roundPanel20Layout.setHorizontalGroup(
            roundPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel20Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jButton12_HocKy_Luu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jButton13_HocKy_Export, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jButton14_HocKy_Clear, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(Button_HocKy_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        roundPanel20Layout.setVerticalGroup(
            roundPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13_HocKy_Export, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton14_HocKy_Clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton12_HocKy_Luu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_HocKy_Exit))
                .addContainerGap())
        );

        roundPanel21.setRoundBottomLeft(15);
        roundPanel21.setRoundBottomRight(15);
        roundPanel21.setRoundTopLeft(15);
        roundPanel21.setRoundTopRight(15);

        jTable2_HocKy_bangSinhVien.setAutoCreateRowSorter(true);
        jTable2_HocKy_bangSinhVien.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(68, 85, 105), 1, true));
        jTable2_HocKy_bangSinhVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn thứ 1", "Môn thứ 2", "Môn thứ 3", "Môn thứ 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2_HocKy_bangSinhVien.setGridColor(new java.awt.Color(218, 216, 216));
        jTable2_HocKy_bangSinhVien.setShowGrid(true);
        jTable2_HocKy_bangSinhVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2_HocKy_bangSinhVienMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2_HocKy_bangSinhVien);

        javax.swing.GroupLayout roundPanel21Layout = new javax.swing.GroupLayout(roundPanel21);
        roundPanel21.setLayout(roundPanel21Layout);
        roundPanel21Layout.setHorizontalGroup(
            roundPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        roundPanel21Layout.setVerticalGroup(
            roundPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel10Layout = new javax.swing.GroupLayout(roundPanel10);
        roundPanel10.setLayout(roundPanel10Layout);
        roundPanel10Layout.setHorizontalGroup(
            roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(roundPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE)
                    .addComponent(roundPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel10Layout.setVerticalGroup(
            roundPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        roundPanel13.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel13.setRoundBottomLeft(15);
        roundPanel13.setRoundBottomRight(15);
        roundPanel13.setRoundTopLeft(15);
        roundPanel13.setRoundTopRight(15);

        roundPanel18.setBackground(new java.awt.Color(88, 110, 135));
        roundPanel18.setRoundBottomLeft(15);
        roundPanel18.setRoundBottomRight(15);
        roundPanel18.setRoundTopLeft(15);
        roundPanel18.setRoundTopRight(15);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Mã số sinh viên");

        jTextField8_HocKy_MSSV_timKiem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jButton4_HocKy_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton4_HocKy_timKiem.setForeground(new java.awt.Color(68, 85, 105));
        jButton4_HocKy_timKiem.setText("Tìm kiếm");
        jButton4_HocKy_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4_HocKy_timKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel18Layout = new javax.swing.GroupLayout(roundPanel18);
        roundPanel18.setLayout(roundPanel18Layout);
        roundPanel18Layout.setHorizontalGroup(
            roundPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundPanel18Layout.createSequentialGroup()
                        .addComponent(jTextField8_HocKy_MSSV_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4_HocKy_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        roundPanel18Layout.setVerticalGroup(
            roundPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8_HocKy_MSSV_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4_HocKy_timKiem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Họ và tên");

        jTextField10_HocKy_hoTen.setEditable(false);
        jTextField10_HocKy_hoTen.setBackground(new java.awt.Color(229, 229, 229));
        jTextField10_HocKy_hoTen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField10_HocKy_hoTen.setFocusable(false);

        jTextField11_HocKy_Khoa.setEditable(false);
        jTextField11_HocKy_Khoa.setBackground(new java.awt.Color(229, 229, 229));
        jTextField11_HocKy_Khoa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField11_HocKy_Khoa.setFocusable(false);

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Khoa");

        jPanel5.setPreferredSize(new java.awt.Dimension(0, 2));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jComboBox2_HocKy_hocKy.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));

        jComboBox3_HocKy_Mon1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cơ sở dữ liệu ", "Mạng máy tính", "Hệ điều hành", "Lập trình web", " ", "Dẫn luận ngôn ngữ", "Nghe – Nói tiếng Anh 1", "Nghe – Nói tiếng Anh 2 ", "Nghe-Ghi chú tiếng Anh" }));

        jComboBox4_HocKy_Mon2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mạng máy tính", "Lập trình mạng", "Hệ điều hành mạng", "An ninh mạng", " ", "Nói trước công chúng ", "Đọc tiếng Anh 1 ", "Đọc tiếng Anh 2 ", "Đọc báo chí" }));

        jComboBox5_HocKy_Mon3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lập trình an toàn", "Bảo mật người dùng cuối", "Mạng không dây", "Điện toán đám mây", " ", "Viết tiếng Anh 1", "Viết tiếng Anh 2", "Viết tiếng Anh 3 ", "Soạn thảo văn bản tiếng Anh" }));

        jComboBox6_HocKy_Mon4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kỹ thuật mật mã", "Điều tra tấn công", "Penetration testing", "Lập trình an toàn", " ", "Văn minh Anh ", "Ngữ âm - Âm vị tiếng Anh ", "Cú pháp học", "Hình thái học" }));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Học kỳ");

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Môn thứ 1");

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Môn thứ 2");

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Môn thứ 3");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Môn thứ 4");

        jTextField1_HocKy_STT.setEditable(false);
        jTextField1_HocKy_STT.setBackground(new java.awt.Color(229, 229, 229));
        jTextField1_HocKy_STT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField1_HocKy_STT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1_HocKy_STT.setAlignmentX(10.0F);
        jTextField1_HocKy_STT.setAlignmentY(10.0F);
        jTextField1_HocKy_STT.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField1_HocKy_STT.setFocusable(false);

        jTextField1_HocKy_MSSV.setEditable(false);
        jTextField1_HocKy_MSSV.setBackground(new java.awt.Color(229, 229, 229));
        jTextField1_HocKy_MSSV.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField1_HocKy_MSSV.setFocusable(false);
        jTextField1_HocKy_MSSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1_HocKy_MSSVActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("MSSV");

        jLabel38.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Thông tin sinh viên");

        javax.swing.GroupLayout roundPanel13Layout = new javax.swing.GroupLayout(roundPanel13);
        roundPanel13.setLayout(roundPanel13Layout);
        roundPanel13Layout.setHorizontalGroup(
            roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel13Layout.createSequentialGroup()
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel13Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(roundPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox2_HocKy_hocKy, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox3_HocKy_Mon1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4_HocKy_Mon2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox5_HocKy_Mon3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox6_HocKy_Mon4, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(roundPanel13Layout.createSequentialGroup()
                                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roundPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(39, 39, 39))
                                    .addGroup(roundPanel13Layout.createSequentialGroup()
                                        .addComponent(jLabel13)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField11_HocKy_Khoa)
                                    .addGroup(roundPanel13Layout.createSequentialGroup()
                                        .addComponent(jTextField10_HocKy_hoTen, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1_HocKy_STT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel13Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField1_HocKy_MSSV, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        roundPanel13Layout.setVerticalGroup(
            roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField10_HocKy_hoTen)
                    .addComponent(jTextField1_HocKy_STT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1_HocKy_MSSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35))
                .addGap(20, 20, 20)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField11_HocKy_Khoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2_HocKy_hocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(20, 20, 20)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3_HocKy_Mon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(20, 20, 20)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox4_HocKy_Mon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(20, 20, 20)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox5_HocKy_Mon3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20))
                .addGap(20, 20, 20)
                .addGroup(roundPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox6_HocKy_Mon4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addGap(357, 357, 357))
        );

        javax.swing.GroupLayout roundPanel2Layout = new javax.swing.GroupLayout(roundPanel2);
        roundPanel2.setLayout(roundPanel2Layout);
        roundPanel2Layout.setHorizontalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel2Layout.setVerticalGroup(
            roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 551, Short.MAX_VALUE)
                    .addComponent(roundPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Học kỳ", roundPanel2);

        roundPanel4.setBackground(new java.awt.Color(230, 228, 228));
        roundPanel4.setRoundBottomLeft(15);
        roundPanel4.setRoundBottomRight(15);

        roundPanel14.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel14.setRoundBottomLeft(15);
        roundPanel14.setRoundBottomRight(15);
        roundPanel14.setRoundTopLeft(15);
        roundPanel14.setRoundTopRight(15);

        roundPanel22.setBackground(new java.awt.Color(88, 110, 135));
        roundPanel22.setRoundBottomLeft(15);
        roundPanel22.setRoundBottomRight(15);
        roundPanel22.setRoundTopLeft(15);
        roundPanel22.setRoundTopRight(15);

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Mã số sinh viên");

        jTextField13_DiemSo_MSSV_timKiem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField13_DiemSo_MSSV_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13_DiemSo_MSSV_timKiemActionPerformed(evt);
            }
        });

        jButton15_DiemSo_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton15_DiemSo_timKiem.setForeground(new java.awt.Color(68, 85, 105));
        jButton15_DiemSo_timKiem.setText("Tìm kiếm");
        jButton15_DiemSo_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15_DiemSo_timKiemActionPerformed(evt);
            }
        });

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Học kỳ");

        jTextField1_DiemSo_MSSV_hocKy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField1_DiemSo_MSSV_hocKy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1_DiemSo_MSSV_hocKyKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout roundPanel22Layout = new javax.swing.GroupLayout(roundPanel22);
        roundPanel22.setLayout(roundPanel22Layout);
        roundPanel22Layout.setHorizontalGroup(
            roundPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(roundPanel22Layout.createSequentialGroup()
                        .addGroup(roundPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, roundPanel22Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField1_DiemSo_MSSV_hocKy))
                            .addComponent(jTextField13_DiemSo_MSSV_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jButton15_DiemSo_timKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel22Layout.setVerticalGroup(
            roundPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundPanel22Layout.createSequentialGroup()
                        .addComponent(jTextField13_DiemSo_MSSV_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jTextField1_DiemSo_MSSV_hocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton15_DiemSo_timKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Thông tin sinh viên");

        jTextField14_DiemSo_hoTen.setEditable(false);
        jTextField14_DiemSo_hoTen.setBackground(new java.awt.Color(229, 229, 229));
        jTextField14_DiemSo_hoTen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField14_DiemSo_hoTen.setFocusable(false);

        jTextField15_DiemSo_Khoa.setEditable(false);
        jTextField15_DiemSo_Khoa.setBackground(new java.awt.Color(229, 229, 229));
        jTextField15_DiemSo_Khoa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField15_DiemSo_Khoa.setFocusable(false);

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Họ và tên");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Khoa");

        jTextField16_DiemSo_Mon1.setEditable(false);
        jTextField16_DiemSo_Mon1.setBackground(new java.awt.Color(229, 229, 229));
        jTextField16_DiemSo_Mon1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField16_DiemSo_Mon1.setFocusable(false);

        jTextField17_DiemSo_Mon2.setEditable(false);
        jTextField17_DiemSo_Mon2.setBackground(new java.awt.Color(229, 229, 229));
        jTextField17_DiemSo_Mon2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField17_DiemSo_Mon2.setFocusable(false);

        jTextField18_DiemSo_Mon3.setEditable(false);
        jTextField18_DiemSo_Mon3.setBackground(new java.awt.Color(229, 229, 229));
        jTextField18_DiemSo_Mon3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField18_DiemSo_Mon3.setFocusable(false);

        jTextField19_DiemSo_Mon4.setEditable(false);
        jTextField19_DiemSo_Mon4.setBackground(new java.awt.Color(229, 229, 229));
        jTextField19_DiemSo_Mon4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField19_DiemSo_Mon4.setFocusable(false);

        jTextField21_DiemSo_diemMon1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField21_DiemSo_diemMon1.setText("0.0");
        jTextField21_DiemSo_diemMon1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTextField22_DiemSo_diemMon2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField22_DiemSo_diemMon2.setText("0.0");
        jTextField22_DiemSo_diemMon2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTextField23_DiemSo_diemMon3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField23_DiemSo_diemMon3.setText("0.0");
        jTextField23_DiemSo_diemMon3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jTextField24_DiemSo_diemMon4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField24_DiemSo_diemMon4.setText("0.0");
        jTextField24_DiemSo_diemMon4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Môn thứ 1");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Môn thứ 2");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Môn thứ 3");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Môn thứ 4");

        jTextField1_DiemSo_MSSV.setEditable(false);
        jTextField1_DiemSo_MSSV.setBackground(new java.awt.Color(229, 229, 229));
        jTextField1_DiemSo_MSSV.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField1_DiemSo_MSSV.setFocusable(false);

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("MSSV");

        jTextField2_DiemSo_STT.setEditable(false);
        jTextField2_DiemSo_STT.setBackground(new java.awt.Color(229, 229, 229));
        jTextField2_DiemSo_STT.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTextField2_DiemSo_STT.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2_DiemSo_STT.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField2_DiemSo_STT.setFocusable(false);

        jPanel6.setPreferredSize(new java.awt.Dimension(0, 2));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 2, Short.MAX_VALUE)
        );

        jTextField1_DiemSo_hocKy.setBackground(new java.awt.Color(229, 229, 229));
        jTextField1_DiemSo_hocKy.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField1_DiemSo_hocKy.setFocusable(false);

        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Học kỳ");

        javax.swing.GroupLayout roundPanel14Layout = new javax.swing.GroupLayout(roundPanel14);
        roundPanel14.setLayout(roundPanel14Layout);
        roundPanel14Layout.setHorizontalGroup(
            roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel14Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                    .addGroup(roundPanel14Layout.createSequentialGroup()
                        .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel37)
                            .addComponent(jLabel39))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1_DiemSo_MSSV)
                            .addComponent(jTextField15_DiemSo_Khoa)
                            .addGroup(roundPanel14Layout.createSequentialGroup()
                                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jTextField19_DiemSo_Mon4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                                    .addComponent(jTextField18_DiemSo_Mon3, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField17_DiemSo_Mon2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField16_DiemSo_Mon1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField22_DiemSo_diemMon2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel14Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField23_DiemSo_diemMon3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField24_DiemSo_diemMon4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField21_DiemSo_diemMon1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(roundPanel14Layout.createSequentialGroup()
                                .addComponent(jTextField14_DiemSo_hoTen)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2_DiemSo_STT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextField1_DiemSo_hocKy))))
                .addContainerGap())
        );
        roundPanel14Layout.setVerticalGroup(
            roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField14_DiemSo_hoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField2_DiemSo_STT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1_DiemSo_MSSV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37))
                .addGap(20, 20, 20)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField15_DiemSo_Khoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28))
                .addGap(20, 20, 20)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1_DiemSo_hocKy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField16_DiemSo_Mon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField21_DiemSo_diemMon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29))
                .addGap(20, 20, 20)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField17_DiemSo_Mon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField22_DiemSo_diemMon2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(20, 20, 20)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField18_DiemSo_Mon3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField23_DiemSo_diemMon3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(20, 20, 20)
                .addGroup(roundPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField19_DiemSo_Mon4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField24_DiemSo_diemMon4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addContainerGap(83, Short.MAX_VALUE))
        );

        roundPanel16.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel16.setRoundBottomLeft(15);
        roundPanel16.setRoundBottomRight(15);
        roundPanel16.setRoundTopLeft(15);
        roundPanel16.setRoundTopRight(15);

        roundPanel24.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel24.setRoundBottomLeft(15);
        roundPanel24.setRoundBottomRight(15);
        roundPanel24.setRoundTopLeft(15);
        roundPanel24.setRoundTopRight(15);

        jButton18_DiemSo_Luu.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton18_DiemSo_Luu.setForeground(new java.awt.Color(68, 85, 105));
        jButton18_DiemSo_Luu.setText("Lưu");
        jButton18_DiemSo_Luu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18_DiemSo_LuuActionPerformed(evt);
            }
        });

        jButton20_DiemSo_capNhat.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton20_DiemSo_capNhat.setForeground(new java.awt.Color(68, 85, 105));
        jButton20_DiemSo_capNhat.setText("Cập nhật");
        jButton20_DiemSo_capNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20_DiemSo_capNhatActionPerformed(evt);
            }
        });

        jButton21_DiemSo_Export.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton21_DiemSo_Export.setForeground(new java.awt.Color(68, 85, 105));
        jButton21_DiemSo_Export.setText("Export");
        jButton21_DiemSo_Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21_DiemSo_ExportActionPerformed(evt);
            }
        });

        jButton22_DiemSo_Clear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton22_DiemSo_Clear.setForeground(new java.awt.Color(68, 85, 105));
        jButton22_DiemSo_Clear.setText("Clear");
        jButton22_DiemSo_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22_DiemSo_ClearActionPerformed(evt);
            }
        });

        Button_DiemSo_Exit.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Button_DiemSo_Exit.setForeground(new java.awt.Color(68, 85, 105));
        Button_DiemSo_Exit.setText("Exit");
        Button_DiemSo_Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_DiemSo_ExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel24Layout = new javax.swing.GroupLayout(roundPanel24);
        roundPanel24.setLayout(roundPanel24Layout);
        roundPanel24Layout.setHorizontalGroup(
            roundPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel24Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jButton18_DiemSo_Luu, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jButton20_DiemSo_capNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jButton21_DiemSo_Export, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(jButton22_DiemSo_Clear, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(Button_DiemSo_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        roundPanel24Layout.setVerticalGroup(
            roundPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton18_DiemSo_Luu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton20_DiemSo_capNhat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton21_DiemSo_Export)
                    .addComponent(jButton22_DiemSo_Clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_DiemSo_Exit))
                .addContainerGap())
        );

        roundPanel25.setRoundBottomLeft(15);
        roundPanel25.setRoundBottomRight(15);
        roundPanel25.setRoundTopLeft(15);
        roundPanel25.setRoundTopRight(15);

        jTable4_DiemSo_bangDiem.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(68, 85, 105), 1, true));
        jTable4_DiemSo_bangDiem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn 1", "Điểm 1", "Môn 2", "Điểm 2", "Môn 3", "Điểm 3", "Môn 4", "Điểm 4", "Trung bình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable4_DiemSo_bangDiem.setGridColor(new java.awt.Color(218, 216, 216));
        jTable4_DiemSo_bangDiem.setShowGrid(true);
        jTable4_DiemSo_bangDiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable4_DiemSo_bangDiemMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable4_DiemSo_bangDiem);

        javax.swing.GroupLayout roundPanel25Layout = new javax.swing.GroupLayout(roundPanel25);
        roundPanel25.setLayout(roundPanel25Layout);
        roundPanel25Layout.setHorizontalGroup(
            roundPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4)
                .addContainerGap())
        );
        roundPanel25Layout.setVerticalGroup(
            roundPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel25Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundPanel23.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel23.setPreferredSize(new java.awt.Dimension(918, 51));
        roundPanel23.setRoundBottomLeft(15);
        roundPanel23.setRoundBottomRight(15);
        roundPanel23.setRoundTopLeft(15);
        roundPanel23.setRoundTopRight(15);

        jLabel36.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Tìm sinh viên");

        jButton17_DiemSo_timSV_Refresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton17_DiemSo_timSV_Refresh.setForeground(new java.awt.Color(68, 85, 105));
        jButton17_DiemSo_timSV_Refresh.setText("Refresh");
        jButton17_DiemSo_timSV_Refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17_DiemSo_timSV_RefreshActionPerformed(evt);
            }
        });

        jButton19_DiemSo_timSV_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton19_DiemSo_timSV_timKiem.setForeground(new java.awt.Color(68, 85, 105));
        jButton19_DiemSo_timSV_timKiem.setText("Tìm kiếm");
        jButton19_DiemSo_timSV_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19_DiemSo_timSV_timKiemActionPerformed(evt);
            }
        });

        jTextField28_DiemSo_timSV.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField28_DiemSo_timSV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField28_DiemSo_timSVActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel23Layout = new javax.swing.GroupLayout(roundPanel23);
        roundPanel23.setLayout(roundPanel23Layout);
        roundPanel23Layout.setHorizontalGroup(
            roundPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel23Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel36)
                .addGap(18, 18, 18)
                .addComponent(jTextField28_DiemSo_timSV)
                .addGap(18, 18, 18)
                .addComponent(jButton19_DiemSo_timSV_timKiem)
                .addGap(18, 18, 18)
                .addComponent(jButton17_DiemSo_timSV_Refresh)
                .addGap(31, 31, 31))
        );
        roundPanel23Layout.setVerticalGroup(
            roundPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel23Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(roundPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton17_DiemSo_timSV_Refresh)
                        .addComponent(jButton19_DiemSo_timSV_timKiem)
                        .addComponent(jTextField28_DiemSo_timSV, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundPanel16Layout = new javax.swing.GroupLayout(roundPanel16);
        roundPanel16.setLayout(roundPanel16Layout);
        roundPanel16Layout.setHorizontalGroup(
            roundPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, 928, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel16Layout.setVerticalGroup(
            roundPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel4Layout = new javax.swing.GroupLayout(roundPanel4);
        roundPanel4.setLayout(roundPanel4Layout);
        roundPanel4Layout.setHorizontalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel4Layout.setVerticalGroup(
            roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Điểm số", roundPanel4);

        roundPanel5.setBackground(new java.awt.Color(230, 228, 228));
        roundPanel5.setRoundBottomLeft(15);
        roundPanel5.setRoundBottomRight(15);

        roundPanel15.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel15.setRoundBottomLeft(15);
        roundPanel15.setRoundBottomRight(15);
        roundPanel15.setRoundTopLeft(15);
        roundPanel15.setRoundTopRight(15);

        roundPanel29.setBackground(new java.awt.Color(88, 110, 135));
        roundPanel29.setRoundBottomLeft(15);
        roundPanel29.setRoundBottomRight(15);
        roundPanel29.setRoundTopLeft(15);
        roundPanel29.setRoundTopRight(15);

        jLabel34.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("Mã số sinh viên");

        jTextField26_TongKet_MSSV_timKiem.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField26_TongKet_MSSV_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField26_TongKet_MSSV_timKiemActionPerformed(evt);
            }
        });

        jButton16_TongKet_MSSV_timKiem.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton16_TongKet_MSSV_timKiem.setForeground(new java.awt.Color(68, 85, 105));
        jButton16_TongKet_MSSV_timKiem.setText("Tìm kiếm");
        jButton16_TongKet_MSSV_timKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16_TongKet_MSSV_timKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel29Layout = new javax.swing.GroupLayout(roundPanel29);
        roundPanel29.setLayout(roundPanel29Layout);
        roundPanel29Layout.setHorizontalGroup(
            roundPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roundPanel29Layout.createSequentialGroup()
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(roundPanel29Layout.createSequentialGroup()
                        .addComponent(jTextField26_TongKet_MSSV_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton16_TongKet_MSSV_timKiem, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel29Layout.setVerticalGroup(
            roundPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel29Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField26_TongKet_MSSV_timKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton16_TongKet_MSSV_timKiem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel26.setRoundBottomLeft(15);
        roundPanel26.setRoundBottomRight(15);
        roundPanel26.setRoundTopLeft(15);
        roundPanel26.setRoundTopRight(15);

        jLabel40_TongKet_He10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40_TongKet_He10.setText("Điểm trung bình hệ 10: ");

        jLabel40_TongKet_He4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40_TongKet_He4.setText("Điểm trung bình hệ 4: ");

        jLabel40_TongKet_tongMon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40_TongKet_tongMon.setText("Tổng số môn đã học:");

        jLabel40_TongKet_tongTinChi.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel40_TongKet_tongTinChi.setText("Số tín chỉ đã đạt:");

        javax.swing.GroupLayout roundPanel26Layout = new javax.swing.GroupLayout(roundPanel26);
        roundPanel26.setLayout(roundPanel26Layout);
        roundPanel26Layout.setHorizontalGroup(
            roundPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40_TongKet_He10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel26Layout.createSequentialGroup()
                        .addGroup(roundPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel40_TongKet_He4)
                            .addComponent(jLabel40_TongKet_tongMon)
                            .addComponent(jLabel40_TongKet_tongTinChi))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        roundPanel26Layout.setVerticalGroup(
            roundPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel40_TongKet_He10)
                .addGap(18, 18, 18)
                .addComponent(jLabel40_TongKet_He4)
                .addGap(18, 18, 18)
                .addComponent(jLabel40_TongKet_tongMon)
                .addGap(18, 18, 18)
                .addComponent(jLabel40_TongKet_tongTinChi)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundPanel15Layout = new javax.swing.GroupLayout(roundPanel15);
        roundPanel15.setLayout(roundPanel15Layout);
        roundPanel15Layout.setHorizontalGroup(
            roundPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel15Layout.setVerticalGroup(
            roundPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        roundPanel17.setBackground(new java.awt.Color(255, 255, 255));
        roundPanel17.setRoundBottomLeft(15);
        roundPanel17.setRoundBottomRight(15);
        roundPanel17.setRoundTopLeft(15);
        roundPanel17.setRoundTopRight(15);

        roundPanel28.setRoundBottomLeft(15);
        roundPanel28.setRoundBottomRight(15);
        roundPanel28.setRoundTopLeft(15);
        roundPanel28.setRoundTopRight(15);

        jTable3_TongKet_bangTongKet.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(68, 85, 105), 1, true));
        jTable3_TongKet_bangTongKet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn 1 ", "Điểm 1", "Môn 2", "Điểm 2", "Môn 3", "Điểm 3", "Môn 4", "Điểm 4", "Trung bình"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3_TongKet_bangTongKet.setGridColor(new java.awt.Color(218, 216, 216));
        jTable3_TongKet_bangTongKet.setShowGrid(true);
        jScrollPane3.setViewportView(jTable3_TongKet_bangTongKet);
        if (jTable3_TongKet_bangTongKet.getColumnModel().getColumnCount() > 0) {
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(6).setResizable(false);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(6).setPreferredWidth(40);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(8).setResizable(false);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(8).setPreferredWidth(40);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(10).setResizable(false);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(10).setPreferredWidth(40);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(12).setResizable(false);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(12).setPreferredWidth(40);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(13).setResizable(false);
            jTable3_TongKet_bangTongKet.getColumnModel().getColumn(13).setPreferredWidth(45);
        }

        javax.swing.GroupLayout roundPanel28Layout = new javax.swing.GroupLayout(roundPanel28);
        roundPanel28.setLayout(roundPanel28Layout);
        roundPanel28Layout.setHorizontalGroup(
            roundPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        roundPanel28Layout.setVerticalGroup(
            roundPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundPanel30.setBackground(new java.awt.Color(68, 85, 105));
        roundPanel30.setRoundBottomLeft(15);
        roundPanel30.setRoundBottomRight(15);
        roundPanel30.setRoundTopLeft(15);
        roundPanel30.setRoundTopRight(15);

        jButton23_TongKet_Export.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton23_TongKet_Export.setForeground(new java.awt.Color(68, 85, 105));
        jButton23_TongKet_Export.setText("Export");
        jButton23_TongKet_Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23_TongKet_ExportActionPerformed(evt);
            }
        });

        jButton24_TongKet_Clear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton24_TongKet_Clear.setForeground(new java.awt.Color(68, 85, 105));
        jButton24_TongKet_Clear.setText("Clear");
        jButton24_TongKet_Clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24_TongKet_ClearActionPerformed(evt);
            }
        });

        Button_DiemSo_Exit1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        Button_DiemSo_Exit1.setForeground(new java.awt.Color(68, 85, 105));
        Button_DiemSo_Exit1.setText("Exit");
        Button_DiemSo_Exit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Button_DiemSo_Exit1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout roundPanel30Layout = new javax.swing.GroupLayout(roundPanel30);
        roundPanel30.setLayout(roundPanel30Layout);
        roundPanel30Layout.setHorizontalGroup(
            roundPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel30Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jButton23_TongKet_Export, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addComponent(jButton24_TongKet_Clear, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addComponent(Button_DiemSo_Exit1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100))
        );
        roundPanel30Layout.setVerticalGroup(
            roundPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton23_TongKet_Export, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton24_TongKet_Clear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Button_DiemSo_Exit1))
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel17Layout = new javax.swing.GroupLayout(roundPanel17);
        roundPanel17.setLayout(roundPanel17Layout);
        roundPanel17Layout.setHorizontalGroup(
            roundPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundPanel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        roundPanel17Layout.setVerticalGroup(
            roundPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout roundPanel5Layout = new javax.swing.GroupLayout(roundPanel5);
        roundPanel5.setLayout(roundPanel5Layout);
        roundPanel5Layout.setHorizontalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(roundPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        roundPanel5Layout.setVerticalGroup(
            roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roundPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(roundPanel5Layout.createSequentialGroup()
                        .addComponent(roundPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Điểm tổng kết", roundPanel5);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(roundPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addGap(5, 5, 5))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleDescription("Sinh Viên");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton10_HocKy_timSV_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10_HocKy_timSV_RefreshActionPerformed
        jTable2_HocKy_bangSinhVien.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn thứ 1", "Môn thứ 2", "Môn thứ 3", "Môn thứ 4"}));
        hocky.hienThiDatabaseHocKy(jTable2_HocKy_bangSinhVien, "");
        jTextField12_HocKy_timSV.setText(null);
    }//GEN-LAST:event_jButton10_HocKy_timSV_RefreshActionPerformed

    private void jButton17_DiemSo_timSV_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17_DiemSo_timSV_RefreshActionPerformed
        jTable4_DiemSo_bangDiem.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn thứ 1", "Điểm 1", "Môn thứ 2", "Điểm 2",
            "Môn thứ 3", "Điểm 3", "Môn thứ 4", "Điểm 4", "Trung bình"}));
        diemso.hienThiDatabaseDiemSo(jTable4_DiemSo_bangDiem, "");
        jTextField28_DiemSo_timSV.setText(null);
    }//GEN-LAST:event_jButton17_DiemSo_timSV_RefreshActionPerformed

    private void Button_SinhVien_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_SinhVien_ExitActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0)//0=yes,1=no:
        {
            this.dispose();
        }
    }//GEN-LAST:event_Button_SinhVien_ExitActionPerformed

    private void jButton7_SinhVien_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7_SinhVien_ClearActionPerformed
        clearStudent();
    }//GEN-LAST:event_jButton7_SinhVien_ClearActionPerformed

    private void jButton9_SinhVien_ThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9_SinhVien_ThemActionPerformed
        if (kiemTraField()) {
            //NẾU KIEMTRATONTAIEMAIL = FALSE THÌ KHÔNG TỒN TẠI MAIL TRONG DATABASE, SAU ĐÓ TIẾN HÀNH CHÈN DATA VÀO
            if (!sinhvien.kiemTraTonTaiEmail(jTextField2_SinhVien_Email.getText())) {
                int stt = sinhvien.getMax();
                String mSSV = jTextField1_SinhVien_MSSV.getText();
                String hoTen = jTextField2_SinhVien_hoVaTen.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String ngaySinh = dateFormat.format(jDateChooser1__SinhVien_ngaySinh.getDate());
                String gioiTinh = jComboBox1_SinhVien_gioiTinh.getSelectedItem().toString();
                String danToc = jTextField4_SinhVien_danToc.getText();
                String khoa = jTextField5_SinhVien_Khoa.getText();
                String email = jTextField2_SinhVien_Email.getText();
                String namBatDau = jTextField6_SinhVien_namBatDau.getText();
                String namKetThuc = jTextField7_SinhVien_namKetThuc.getText();
                String diaChiNha1 = jTextField9_SinhVien_diaChiNha1.getText();
                String diaChiNha2 = jTextField2_SinhVien_diaChiNha2.getText();
                sinhvien.insert(stt, mSSV, hoTen, ngaySinh, gioiTinh, danToc, khoa, email, namBatDau, namKetThuc, diaChiNha1, diaChiNha2);
                jTable1_SinhVien_bangThongTin.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Ngày sinh", "Giới tính", "Dân tộc", "Khoa", "Email", "Năm bắt đầu", "Năm kết thúc", "Địa chỉ 1", "Địa chỉ 2"}));
                sinhvien.hienThiDatabaseSinhVien(jTable1_SinhVien_bangThongTin, "");
                clearStudent();
            } else {
                JOptionPane.showMessageDialog(this, "Mail sinh viên đã tồn tại!");
            }
        }
    }//GEN-LAST:event_jButton9_SinhVien_ThemActionPerformed

    private void jTextField6_SinhVien_namBatDauKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField6_SinhVien_namBatDauKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField6_SinhVien_namBatDauKeyTyped

    private void jTextField7_SinhVien_namKetThucKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7_SinhVien_namKetThucKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField7_SinhVien_namKetThucKeyTyped

    private void jButton1_SinhVien_xuatFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_SinhVien_xuatFileActionPerformed
        try {
            MessageFormat header = new MessageFormat("Thông tin sinh viên");
            MessageFormat footer = new MessageFormat("Page{0,number,integer}");
            jTable1_SinhVien_bangThongTin.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(giaoDienGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1_SinhVien_xuatFileActionPerformed

    private void jTable1_SinhVien_bangThongTinMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1_SinhVien_bangThongTinMouseClicked
        model = (DefaultTableModel) jTable1_SinhVien_bangThongTin.getModel();
        thongTinCot = jTable1_SinhVien_bangThongTin.getSelectedRow();
        jTextField1_SinhVien_Stt.setText(model.getValueAt(thongTinCot, 0).toString());
        jTextField1_SinhVien_MSSV.setText(model.getValueAt(thongTinCot, 1).toString());
        jTextField2_SinhVien_hoVaTen.setText(model.getValueAt(thongTinCot, 2).toString());

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(model.getValueAt(thongTinCot, 3).toString());
            jDateChooser1__SinhVien_ngaySinh.setDate(date);
        } catch (ParseException ex) {
            Logger.getLogger(giaoDienGV.class.getName()).log(Level.SEVERE, null, ex);
        }

        String gioitinh = model.getValueAt(thongTinCot, 4).toString();
        if (gioitinh.equals("Nam")) {
            jComboBox1_SinhVien_gioiTinh.setSelectedIndex(0);
        } else {
            jComboBox1_SinhVien_gioiTinh.setSelectedIndex(1);
        }

        jTextField4_SinhVien_danToc.setText(model.getValueAt(thongTinCot, 5).toString());
        jTextField5_SinhVien_Khoa.setText(model.getValueAt(thongTinCot, 6).toString());
        jTextField2_SinhVien_Email.setText(model.getValueAt(thongTinCot, 7).toString());
        jTextField6_SinhVien_namBatDau.setText(model.getValueAt(thongTinCot, 8).toString());
        jTextField7_SinhVien_namKetThuc.setText(model.getValueAt(thongTinCot, 9).toString());
        jTextField9_SinhVien_diaChiNha1.setText(model.getValueAt(thongTinCot, 10).toString());
        jTextField2_SinhVien_diaChiNha2.setText(model.getValueAt(thongTinCot, 11).toString());
    }//GEN-LAST:event_jTable1_SinhVien_bangThongTinMouseClicked

    private void jButton5_SinhVien_capNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5_SinhVien_capNhatActionPerformed
        if (kiemTraField()) {
            int stt = Integer.parseInt(jTextField1_SinhVien_Stt.getText());
            if (sinhvien.kiemTraTonTaiSTT(stt)) {
                if (!kiemTraEmailSauCapNhat()) {
                    String mSSV = jTextField1_SinhVien_MSSV.getText();
                    String hoTen = jTextField2_SinhVien_hoVaTen.getText();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String ngaySinh = dateFormat.format(jDateChooser1__SinhVien_ngaySinh.getDate());
                    String gioiTinh = jComboBox1_SinhVien_gioiTinh.getSelectedItem().toString();
                    String danToc = jTextField4_SinhVien_danToc.getText();
                    String khoa = jTextField5_SinhVien_Khoa.getText();
                    String email = jTextField2_SinhVien_Email.getText();
                    String namBatDau = jTextField6_SinhVien_namBatDau.getText();
                    String namKetThuc = jTextField7_SinhVien_namKetThuc.getText();
                    String diaChiNha1 = jTextField9_SinhVien_diaChiNha1.getText();
                    String diaChiNha2 = jTextField2_SinhVien_diaChiNha2.getText();
                    sinhvien.capnhat(stt, mSSV, hoTen, ngaySinh, gioiTinh, danToc, khoa, email, namBatDau, namKetThuc, diaChiNha1, diaChiNha2);
                    jTable1_SinhVien_bangThongTin.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Ngày sinh", "Giới tính", "Dân tộc", "Khoa", "Email",
                        "Năm bắt đầu", "Năm kết thúc", "Địa chỉ 1", "Địa chỉ 2"}));
                    sinhvien.hienThiDatabaseSinhVien(jTable1_SinhVien_bangThongTin, "");
                    clearStudent();
                }
            } else {
                JOptionPane.showMessageDialog(this, "STT không tồn tại");
            }
        }
    }//GEN-LAST:event_jButton5_SinhVien_capNhatActionPerformed

    private void jButton6_SinhVien_XoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6_SinhVien_XoaActionPerformed
        int stt = Integer.parseInt(jTextField1_SinhVien_Stt.getText());
        if (sinhvien.kiemTraTonTaiSTT(stt)) {
            sinhvien.xoa(stt);
            jTable1_SinhVien_bangThongTin.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Ngày sinh", "Giới tính", "Dân tộc", "Khoa", "Email",
                "Năm bắt đầu", "Năm kết thúc", "Địa chỉ 1", "Địa chỉ 2"}));
            sinhvien.hienThiDatabaseSinhVien(jTable1_SinhVien_bangThongTin, "");
            clearStudent();
        } else {
            JOptionPane.showMessageDialog(this, "Không tồn tại!");
        }
    }//GEN-LAST:event_jButton6_SinhVien_XoaActionPerformed

    private void jButton2_SinhVien_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2_SinhVien_timKiemActionPerformed
        //String MSSV = jTextField1_SinhVien_MSSV.getText(); //Khởi tạo đối tượng MSSV
        //String pattern = "\\d{2}DH\\d{6}"; //Regex kiểm tra định dạng của mã số sinh viên
        //Pattern r = Pattern.compile(pattern);
        //Matcher m = r.matcher(MSSV);
        if (jTextField3_SinhVien_timKiem.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin sinh viên");
        } //if (!m.matches()) {            
        //        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã số sinh viên");                                   
        //}
        else {
            jTable1_SinhVien_bangThongTin.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Ngày sinh", "Giới tính", "Dân tộc", "Khoa", "Email",
                "Năm bắt đầu", "Năm kết thúc", "Địa chỉ 1", "Địa chỉ 2"}));
            sinhvien.hienThiDatabaseSinhVien(jTable1_SinhVien_bangThongTin, jTextField3_SinhVien_timKiem.getText());
        }
    }//GEN-LAST:event_jButton2_SinhVien_timKiemActionPerformed

    private void jButton3_SinhVien_RefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3_SinhVien_RefreshActionPerformed
        jTable1_SinhVien_bangThongTin.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Ngày sinh", "Giới tính", "Dân tộc", "Khoa", "Email",
            "Năm bắt đầu", "Năm kết thúc", "Địa chỉ 1", "Địa chỉ 2"}));
        sinhvien.hienThiDatabaseSinhVien(jTable1_SinhVien_bangThongTin, "");
        jTextField3_SinhVien_timKiem.setText(null);
    }//GEN-LAST:event_jButton3_SinhVien_RefreshActionPerformed

    private void jButton14_HocKy_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14_HocKy_ClearActionPerformed
        clearHocKy();
    }//GEN-LAST:event_jButton14_HocKy_ClearActionPerformed

    private void jButton12_HocKy_LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12_HocKy_LuuActionPerformed
        if (jTextField1_HocKy_MSSV.getText().isEmpty() || jComboBox2_HocKy_hocKy.getItemCount() == 0) {
            JOptionPane.showMessageDialog(this, "Something is missing!");
        } else {
            int stt = hocky.getMax();
            String hoTen = jTextField10_HocKy_hoTen.getText().toString();
            String MSSV = jTextField1_HocKy_MSSV.getText().toString();
            String Khoa = jTextField11_HocKy_Khoa.getText().toString();
            int hocKy = Integer.parseInt(jComboBox2_HocKy_hocKy.getSelectedItem().toString());
            String Mon1 = jComboBox3_HocKy_Mon1.getSelectedItem().toString();
            String Mon2 = jComboBox4_HocKy_Mon2.getSelectedItem().toString();
            String Mon3 = jComboBox5_HocKy_Mon3.getSelectedItem().toString();
            String Mon4 = jComboBox6_HocKy_Mon4.getSelectedItem().toString();
            if (hocky.kiemTraTonTaiHocKy(MSSV, hocKy)) {
                JOptionPane.showMessageDialog(this, "Học kỳ đã tồn tại: " + hocKy);
            } else {
                if (hocky.kiemTraTonTaiHocPhan(MSSV, "mon_1", Mon1)) {
                    JOptionPane.showMessageDialog(this, "Học phần này đã được chọn rồi: " + Mon1);
                } else {
                    if (hocky.kiemTraTonTaiHocPhan(MSSV, "mon_2", Mon2)) {
                        JOptionPane.showMessageDialog(this, "Học phần này đã được chọn rồi: " + Mon2);
                    } else {
                        if (hocky.kiemTraTonTaiHocPhan(MSSV, "mon_3", Mon3)) {
                            JOptionPane.showMessageDialog(this, "Học phần này đã được chọn rồi: " + Mon3);
                        } else {
                            if (hocky.kiemTraTonTaiHocPhan(MSSV, "mon_4", Mon4)) {
                                JOptionPane.showMessageDialog(this, "Học phần này đã được chọn rồi: " + Mon4);
                            } else {
                                hocky.insert(stt, MSSV, hoTen, Khoa, hocKy, Mon1, Mon2, Mon3, Mon4);
                                jTable2_HocKy_bangSinhVien.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn thứ 1", "Môn thứ 2", "Môn thứ 3", "Môn thứ 4"}));
                                hocky.hienThiDatabaseHocKy(jTable2_HocKy_bangSinhVien, "");
                                clearHocKy();
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton12_HocKy_LuuActionPerformed

    private void jButton4_HocKy_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4_HocKy_timKiemActionPerformed
        String mssv = jTextField8_HocKy_MSSV_timKiem.getText();

        // Kiểm tra nếu MSSV rỗng
        if (mssv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã số sinh viên!");
            return;
        }

        // Kiểm tra định dạng của MSSV
        String regex = "^[0-9]{2}DH[0-9]{6}$";
        if (!mssv.matches(regex)) {
            JOptionPane.showMessageDialog(this, "Mã số sinh viên không đúng định dạng (xxDHxxxxxx)!");
            return;
        }

        // Tìm kiếm thông tin sinh viên
        if (hocky.getMSSV(mssv)) {
            jComboBox2_HocKy_hocKy.setSelectedIndex(-1);
            int hocKy = hocky.tinhHocKy(mssv);
            if (hocKy >= 0) {
                jComboBox2_HocKy_hocKy.removeAllItems();
                for (int i = 1; i <= hocKy + 1; i++) {
                    jComboBox2_HocKy_hocKy.addItem(i + "");
                }
            }
        }
    }//GEN-LAST:event_jButton4_HocKy_timKiemActionPerformed

    private void jTextField1_HocKy_MSSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1_HocKy_MSSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1_HocKy_MSSVActionPerformed

    private void jButton11_HocKy_timSV_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11_HocKy_timSV_timKiemActionPerformed
        //String MSSV = jTextField1_SinhVien_MSSV.getText(); //Khởi tạo đối tượng MSSV
        //String pattern = "\\d{2}DH\\d{6}"; //Regex kiểm tra định dạng của mã số sinh viên
        //Pattern r = Pattern.compile(pattern);
        //Matcher m = r.matcher(MSSV);
        if (jTextField12_HocKy_timSV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin sinh viên");
        } //if (!m.matches()) {            
        //        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã số sinh viên");                                   
        //}
        else {
            jTable2_HocKy_bangSinhVien.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn thứ 1", "Môn thứ 2", "Môn thứ 3", "Môn thứ 4"}));
            hocky.hienThiDatabaseHocKy(jTable2_HocKy_bangSinhVien, jTextField12_HocKy_timSV.getText());
        }
    }//GEN-LAST:event_jButton11_HocKy_timSV_timKiemActionPerformed

    private void jButton13_HocKy_ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13_HocKy_ExportActionPerformed
        try {
            MessageFormat header = new MessageFormat("Học phần của sinh viên");
            MessageFormat footer = new MessageFormat("Page{0,number,integer}");
            jTable2_HocKy_bangSinhVien.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(giaoDienGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton13_HocKy_ExportActionPerformed

    private void Button_HocKy_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_HocKy_ExitActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0)//0=yes,1=no:
        {
            this.dispose();
        }
    }//GEN-LAST:event_Button_HocKy_ExitActionPerformed

    private void Button_DiemSo_ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_DiemSo_ExitActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0)//0=yes,1=no:
        {
            this.dispose();
        }
    }//GEN-LAST:event_Button_DiemSo_ExitActionPerformed

    private void Button_DiemSo_Exit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Button_DiemSo_Exit1ActionPerformed
        int a = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát?", "Select", JOptionPane.YES_NO_OPTION);
        if (a == 0)//0=yes,1=no:
        {
            this.dispose();
        }
    }//GEN-LAST:event_Button_DiemSo_Exit1ActionPerformed

    private void jButton22_DiemSo_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22_DiemSo_ClearActionPerformed
        clearDiemSo();
    }//GEN-LAST:event_jButton22_DiemSo_ClearActionPerformed

    private void jButton21_DiemSo_ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21_DiemSo_ExportActionPerformed
        try {
            MessageFormat header = new MessageFormat("Điểm số của sinh viên");
            MessageFormat footer = new MessageFormat("Page{0,number,integer}");
            jTable4_DiemSo_bangDiem.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(giaoDienGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton21_DiemSo_ExportActionPerformed

    private void jButton23_TongKet_ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23_TongKet_ExportActionPerformed
        try {
            MessageFormat header = new MessageFormat("Bảng điểm     MSSV "+jTextField26_TongKet_MSSV_timKiem.getText());
            MessageFormat footer = new MessageFormat("Page{0,number,integer}");
            jTable3_TongKet_bangTongKet.print(JTable.PrintMode.FIT_WIDTH, header, footer);
        } catch (PrinterException ex) {
            Logger.getLogger(giaoDienGV.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton23_TongKet_ExportActionPerformed

    private void jButton24_TongKet_ClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24_TongKet_ClearActionPerformed
        clearDiemTong();
    }//GEN-LAST:event_jButton24_TongKet_ClearActionPerformed

    private void jTable2_HocKy_bangSinhVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2_HocKy_bangSinhVienMouseClicked
        model = (DefaultTableModel) jTable2_HocKy_bangSinhVien.getModel();
        thongTinCot = jTable2_HocKy_bangSinhVien.getSelectedRow();
        jTextField1_HocKy_STT.setText(model.getValueAt(thongTinCot, 0).toString());
        jTextField1_HocKy_MSSV.setText(model.getValueAt(thongTinCot, 1).toString());
        jTextField10_HocKy_hoTen.setText(model.getValueAt(thongTinCot, 2).toString());
        jTextField11_HocKy_Khoa.setText(model.getValueAt(thongTinCot, 3).toString());
    }//GEN-LAST:event_jTable2_HocKy_bangSinhVienMouseClicked

    private void jButton15_DiemSo_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15_DiemSo_timKiemActionPerformed
        if (jTextField13_DiemSo_MSSV_timKiem.getText().isEmpty() || jTextField1_DiemSo_MSSV_hocKy.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập MSSV và chọn học kỳ");
        } else {
            String mssv = jTextField13_DiemSo_MSSV_timKiem.getText();
            int HocKy = Integer.parseInt(jTextField1_DiemSo_MSSV_hocKy.getText());
            diemso.getMSSV(mssv, HocKy);
        }
    }//GEN-LAST:event_jButton15_DiemSo_timKiemActionPerformed

    private void jTextField13_DiemSo_MSSV_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField13_DiemSo_MSSV_timKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField13_DiemSo_MSSV_timKiemActionPerformed

    private void jButton18_DiemSo_LuuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18_DiemSo_LuuActionPerformed
        if (!jTextField13_DiemSo_MSSV_timKiem.getText().isEmpty()) {
            if (!diemso.kiemTraTonTaiSTT(Integer.parseInt(jTextField2_DiemSo_STT.getText()))) {
                String hoten = jTextField14_DiemSo_hoTen.getText();
                String khoa = jTextField15_DiemSo_Khoa.getText();
                String mssv = jTextField1_DiemSo_MSSV.getText();
                int hocky = Integer.parseInt(jTextField1_DiemSo_hocKy.getText());
                if (!diemso.kiemTraTonTaiMSSVHocKy(mssv, hocky)) {
                    if (kiemTraChuSo(jTextField21_DiemSo_diemMon1.getText()) && kiemTraChuSo(jTextField22_DiemSo_diemMon2.getText())
                            && kiemTraChuSo(jTextField23_DiemSo_diemMon3.getText()) && kiemTraChuSo(jTextField24_DiemSo_diemMon4.getText())) {
                        int stt = diemso.getMax();
                        String mon1 = jTextField16_DiemSo_Mon1.getText();
                        String mon2 = jTextField17_DiemSo_Mon2.getText();
                        String mon3 = jTextField18_DiemSo_Mon3.getText();
                        String mon4 = jTextField19_DiemSo_Mon4.getText();
                        double diem1 = parseDoubleFromText(jTextField21_DiemSo_diemMon1.getText());
                        double diem2 = parseDoubleFromText(jTextField22_DiemSo_diemMon2.getText());
                        double diem3 = parseDoubleFromText(jTextField23_DiemSo_diemMon3.getText());
                        double diem4 = parseDoubleFromText(jTextField24_DiemSo_diemMon4.getText());
                        double trungBinh = (diem1 + diem2 + diem3 + diem4) / 4;
                        nf.setMaximumIntegerDigits(2);
                        diemso.insert(stt, mssv, hoten, khoa, hocky, mon1, mon2, mon3, mon4, diem1, diem2, diem3, diem4, parseDoubleFromText(nf.format(trungBinh)));
                        jTable4_DiemSo_bangDiem.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn thứ 1", "Điểm 1", "Môn thứ 2", "Điểm 2",
                            "Môn thứ 3", "Điểm 3", "Môn thứ 4", "Điểm 4", "Trung bình"}));
                        diemso.hienThiDatabaseDiemSo(jTable4_DiemSo_bangDiem, "");
                        clearDiemSo();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Học kỳ: [" + hocky + "] đã có kết quả rồi!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Điểm đã tồn tại!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng tìm sinh viên!");
        }
    }//GEN-LAST:event_jButton18_DiemSo_LuuActionPerformed
    private double parseDoubleFromText(String text) throws NumberFormatException {
        // Thay thế dấu phẩy bằng dấu chấm để đảm bảo định dạng đúng
        text = text.replace(',', '.');
        return Double.parseDouble(text);
    }

    private boolean kiemTraChuSo(String s) {
        try {
            double d = Double.parseDouble(s);
            if (d >= 0.0 && d <= 10.0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập điểm từ 0.0 đến 10!");
                return false;
            }
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        return false;
    }

    private void jTextField1_DiemSo_MSSV_hocKyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1_DiemSo_MSSV_hocKyKeyTyped
        if (!Character.isDigit(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField1_DiemSo_MSSV_hocKyKeyTyped

    private void jTable4_DiemSo_bangDiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable4_DiemSo_bangDiemMouseClicked
        model = (DefaultTableModel) jTable4_DiemSo_bangDiem.getModel();
        thongTinCot = jTable4_DiemSo_bangDiem.getSelectedRow();
        jTextField2_DiemSo_STT.setText(model.getValueAt(thongTinCot, 0).toString());
        jTextField1_DiemSo_MSSV.setText(model.getValueAt(thongTinCot, 1).toString());
        jTextField14_DiemSo_hoTen.setText(model.getValueAt(thongTinCot, 2).toString());
        jTextField15_DiemSo_Khoa.setText(model.getValueAt(thongTinCot, 3).toString());
        jTextField1_DiemSo_hocKy.setText(model.getValueAt(thongTinCot, 4).toString());
        jTextField16_DiemSo_Mon1.setText(model.getValueAt(thongTinCot, 5).toString());
        jTextField21_DiemSo_diemMon1.setText(model.getValueAt(thongTinCot, 6).toString());
        jTextField17_DiemSo_Mon2.setText(model.getValueAt(thongTinCot, 7).toString());
        jTextField22_DiemSo_diemMon2.setText(model.getValueAt(thongTinCot, 8).toString());
        jTextField18_DiemSo_Mon3.setText(model.getValueAt(thongTinCot, 9).toString());
        jTextField23_DiemSo_diemMon3.setText(model.getValueAt(thongTinCot, 10).toString());
        jTextField19_DiemSo_Mon4.setText(model.getValueAt(thongTinCot, 11).toString());
        jTextField24_DiemSo_diemMon4.setText(model.getValueAt(thongTinCot, 12).toString());
    }//GEN-LAST:event_jTable4_DiemSo_bangDiemMouseClicked

    private void jButton20_DiemSo_capNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20_DiemSo_capNhatActionPerformed
        int stt = Integer.parseInt(jTextField2_DiemSo_STT.getText());
        if (diemso.kiemTraTonTaiSTT(stt)) {
            double diem1 = parseDoubleFromText(jTextField21_DiemSo_diemMon1.getText());
            double diem2 = parseDoubleFromText(jTextField22_DiemSo_diemMon2.getText());
            double diem3 = parseDoubleFromText(jTextField23_DiemSo_diemMon3.getText());
            double diem4 = parseDoubleFromText(jTextField24_DiemSo_diemMon4.getText());
            double trungBinh = (diem1 + diem2 + diem3 + diem4) / 4;
            nf.setMaximumIntegerDigits(2);
            diemso.capnhat(stt, diem1, diem2, diem3, diem4, parseDoubleFromText(nf.format(trungBinh)));
            jTable4_DiemSo_bangDiem.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn thứ 1", "Điểm 1", "Môn thứ 2", "Điểm 2",
                "Môn thứ 3", "Điểm 3", "Môn thứ 4", "Điểm 4", "Trung bình"}));
            diemso.hienThiDatabaseDiemSo(jTable4_DiemSo_bangDiem, "");
            clearDiemSo();
        } else {
            JOptionPane.showMessageDialog(this, "Điểm không tồn tại!");
        }
    }//GEN-LAST:event_jButton20_DiemSo_capNhatActionPerformed

    private void jButton19_DiemSo_timSV_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19_DiemSo_timSV_timKiemActionPerformed
        //String MSSV = jTextField1_SinhVien_MSSV.getText(); //Khởi tạo đối tượng MSSV
        //String pattern = "\\d{2}DH\\d{6}"; //Regex kiểm tra định dạng của mã số sinh viên
        //Pattern r = Pattern.compile(pattern);
        //Matcher m = r.matcher(MSSV);
        if (jTextField28_DiemSo_timSV.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mssv sinh viên");
        } //if (!m.matches()) {            
        //        JOptionPane.showMessageDialog(this, "Vui lòng nhập mã số sinh viên");                                   
        //}
        else {
            jTable4_DiemSo_bangDiem.setModel(new DefaultTableModel(null, new Object[]{"STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn 1", "Điểm 1", "Môn 2", "Điểm 2",
                "Môn 3", "Điểm 3", "Môn 4", "Điểm 4", "Trung bình"}));
            diemso.hienThiDatabaseDiemSo(jTable4_DiemSo_bangDiem, jTextField28_DiemSo_timSV.getText());
        }
    }//GEN-LAST:event_jButton19_DiemSo_timSV_timKiemActionPerformed

    private void jButton16_TongKet_MSSV_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16_TongKet_MSSV_timKiemActionPerformed
        String MSSV = jTextField26_TongKet_MSSV_timKiem.getText(); //Khởi tạo đối tượng MSSV
        String pattern = "\\d{2}DH\\d{6}"; //Regex kiểm tra định dạng của mã số sinh viên
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(MSSV);
        if (!m.matches()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã số sinh viên đúng định dạng xxDHxxxxxx");
        } else {
            if (tongket.kiemTraTonTaiMSSV(MSSV)) {
                jTable3_TongKet_bangTongKet.setModel(new DefaultTableModel(null, new Object[]{
                    "STT", "MSSV", "Họ và tên", "Khoa", "Học kỳ", "Môn 1", "Điểm 1",
                    "Môn 2", "Điểm 2", "Môn 3", "Điểm 3", "Môn 4", "Điểm 4", "Trung bình"}));
                tongket.hienThiDatabaseTongKet(jTable3_TongKet_bangTongKet, MSSV);

                double TB10 = tongket.tinhTB10(MSSV);
                String TB10Str = String.format("%.2f", TB10);
                jLabel40_TongKet_He10.setText("Điểm trung bình hệ 10: " + TB10Str);

                double TB4 = tongket.chuyenDoiHe4(TB10);
                String TB4Str = String.format("%.2f", TB4);
                jLabel40_TongKet_He4.setText("Điểm trung bình hệ 4: " + TB4Str);

                int tongMon = tongket.tinhTongMon(MSSV);
                jLabel40_TongKet_tongMon.setText("Số môn đã học: " + tongMon);

                int tongTinChi = tongket.tinhTongTinChi(MSSV);
                jLabel40_TongKet_tongTinChi.setText("Tổng tín chỉ đã học: " + tongTinChi);
            } else {
                JOptionPane.showMessageDialog(this, "Mã số sinh viên không tồn tại!");
            }
        }
    }//GEN-LAST:event_jButton16_TongKet_MSSV_timKiemActionPerformed

    private void jTextField26_TongKet_MSSV_timKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField26_TongKet_MSSV_timKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField26_TongKet_MSSV_timKiemActionPerformed

    private void jTextField28_DiemSo_timSVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField28_DiemSo_timSVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField28_DiemSo_timSVActionPerformed

    //KIỂM TRA EMAIL VÀ MSSV SAU KHI CẬP NHẬT
    public boolean kiemTraEmailSauCapNhat() {
        String mailCapNhat = jTextField2_SinhVien_Email.getText();
        String oldMail = model.getValueAt(thongTinCot, 7).toString();
        if (mailCapNhat.equals(oldMail)) {
            return false;
        } else {
            //NẾU MAIL CẬP NHẬT KHÔNG PHẢI LÀ MAIL CŨ THÌ KÉO HÀM [kiemtratontaimail] RA CHECK
            //HÀM CÁC HÀM [kiemtratontai] LUÔN TRẢ VỀ KẾT QUẢ = TRUE (ĐÃ CÓ DATABASE TRÊN HỆ THỐNG)
            if (!mailCapNhat.equals(oldMail)) {
                boolean x = sinhvien.kiemTraTonTaiEmail(mailCapNhat);
                if (x) {
                    JOptionPane.showMessageDialog(this, "Mail đã tồn tại!");
                }
                return x;
            }
        }
        return false;
    }

    private void clearStudent() {
        jTextField1_SinhVien_Stt.setText(String.valueOf(sinhvien.getMax()));
        jTextField1_SinhVien_MSSV.setText(null);
        jTextField2_SinhVien_hoVaTen.setText(null);
        jDateChooser1__SinhVien_ngaySinh.setDate(null);
        jComboBox1_SinhVien_gioiTinh.setSelectedIndex(0);
        jTextField4_SinhVien_danToc.setText(null);
        jTextField5_SinhVien_Khoa.setText(null);
        jTextField2_SinhVien_Email.setText(null);
        jTextField6_SinhVien_namBatDau.setText(null);
        jTextField7_SinhVien_namKetThuc.setText(null);
        jTextField9_SinhVien_diaChiNha1.setText(null);
        jTextField2_SinhVien_diaChiNha2.setText(null);
    }

    private void clearHocKy() {
        jTextField1_HocKy_STT.setText(String.valueOf(hocky.getMax()));
        jTextField10_HocKy_hoTen.setText(null);
        jTextField11_HocKy_Khoa.setText(null);
        jTextField1_HocKy_MSSV.setText(null);
        jTextField8_HocKy_MSSV_timKiem.setText(null);
        jComboBox2_HocKy_hocKy.setSelectedIndex(-1);
        jComboBox3_HocKy_Mon1.setSelectedIndex(-1);
        jComboBox4_HocKy_Mon2.setSelectedIndex(-1);
        jComboBox5_HocKy_Mon3.setSelectedIndex(-1);
        jComboBox6_HocKy_Mon4.setSelectedIndex(-1);
        jTable2_HocKy_bangSinhVien.clearSelection();
    }

    private void clearDiemSo() {
        jTextField2_DiemSo_STT.setText(String.valueOf(diemso.getMax()));
        jTextField13_DiemSo_MSSV_timKiem.setText(null);
        jTextField1_DiemSo_MSSV_hocKy.setText(null);
        jTextField1_DiemSo_MSSV.setText(null);
        jTextField14_DiemSo_hoTen.setText(null);
        jTextField1_DiemSo_hocKy.setText(null);
        jTextField15_DiemSo_Khoa.setText(null);
        jTextField16_DiemSo_Mon1.setText(null);
        jTextField17_DiemSo_Mon2.setText(null);
        jTextField18_DiemSo_Mon3.setText(null);
        jTextField19_DiemSo_Mon4.setText(null);
        jTextField21_DiemSo_diemMon1.setText("0.0");
        jTextField22_DiemSo_diemMon2.setText("0.0");
        jTextField23_DiemSo_diemMon3.setText("0.0");
        jTextField24_DiemSo_diemMon4.setText("0.0");
        jTable4_DiemSo_bangDiem.clearSelection();
    }

    private void clearDiemTong() {
        jTable3_TongKet_bangTongKet.clearSelection();
        jLabel40_TongKet_He10.setText("Trung bình hệ 10: 0.0");
        jTextField26_TongKet_MSSV_timKiem.setText(null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(giaoDienGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(giaoDienGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(giaoDienGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(giaoDienGV.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new giaoDienGV().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Button_DiemSo_Exit;
    private javax.swing.JButton Button_DiemSo_Exit1;
    private javax.swing.JButton Button_HocKy_Exit;
    private javax.swing.JButton Button_SinhVien_Exit;
    private javax.swing.JButton jButton10_HocKy_timSV_Refresh;
    private javax.swing.JButton jButton11_HocKy_timSV_timKiem;
    private javax.swing.JButton jButton12_HocKy_Luu;
    private javax.swing.JButton jButton13_HocKy_Export;
    private javax.swing.JButton jButton14_HocKy_Clear;
    private javax.swing.JButton jButton15_DiemSo_timKiem;
    private javax.swing.JButton jButton16_TongKet_MSSV_timKiem;
    private javax.swing.JButton jButton17_DiemSo_timSV_Refresh;
    private javax.swing.JButton jButton18_DiemSo_Luu;
    private javax.swing.JButton jButton19_DiemSo_timSV_timKiem;
    private javax.swing.JButton jButton1_SinhVien_xuatFile;
    private javax.swing.JButton jButton20_DiemSo_capNhat;
    private javax.swing.JButton jButton21_DiemSo_Export;
    private javax.swing.JButton jButton22_DiemSo_Clear;
    private javax.swing.JButton jButton23_TongKet_Export;
    private javax.swing.JButton jButton24_TongKet_Clear;
    private javax.swing.JButton jButton2_SinhVien_timKiem;
    private javax.swing.JButton jButton3_SinhVien_Refresh;
    private javax.swing.JButton jButton4_HocKy_timKiem;
    private javax.swing.JButton jButton5_SinhVien_capNhat;
    private javax.swing.JButton jButton6_SinhVien_Xoa;
    private javax.swing.JButton jButton7_SinhVien_Clear;
    private javax.swing.JButton jButton9_SinhVien_Them;
    private javax.swing.JComboBox<String> jComboBox1_SinhVien_gioiTinh;
    private javax.swing.JComboBox<String> jComboBox2_HocKy_hocKy;
    private javax.swing.JComboBox<String> jComboBox3_HocKy_Mon1;
    private javax.swing.JComboBox<String> jComboBox4_HocKy_Mon2;
    private javax.swing.JComboBox<String> jComboBox5_HocKy_Mon3;
    private javax.swing.JComboBox<String> jComboBox6_HocKy_Mon4;
    private com.toedter.calendar.JDateChooser jDateChooser1__SinhVien_ngaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40_TongKet_He10;
    private javax.swing.JLabel jLabel40_TongKet_He4;
    private javax.swing.JLabel jLabel40_TongKet_tongMon;
    private javax.swing.JLabel jLabel40_TongKet_tongTinChi;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1_SinhVien_bangThongTin;
    private javax.swing.JTable jTable2_HocKy_bangSinhVien;
    private javax.swing.JTable jTable3_TongKet_bangTongKet;
    private javax.swing.JTable jTable4_DiemSo_bangDiem;
    public static javax.swing.JTextField jTextField10_HocKy_hoTen;
    public static javax.swing.JTextField jTextField11_HocKy_Khoa;
    private javax.swing.JTextField jTextField12_HocKy_timSV;
    private javax.swing.JTextField jTextField13_DiemSo_MSSV_timKiem;
    public static javax.swing.JTextField jTextField14_DiemSo_hoTen;
    public static javax.swing.JTextField jTextField15_DiemSo_Khoa;
    public static javax.swing.JTextField jTextField16_DiemSo_Mon1;
    public static javax.swing.JTextField jTextField17_DiemSo_Mon2;
    public static javax.swing.JTextField jTextField18_DiemSo_Mon3;
    public static javax.swing.JTextField jTextField19_DiemSo_Mon4;
    public static javax.swing.JTextField jTextField1_DiemSo_MSSV;
    public static javax.swing.JTextField jTextField1_DiemSo_MSSV_hocKy;
    public static javax.swing.JTextField jTextField1_DiemSo_hocKy;
    public static javax.swing.JTextField jTextField1_HocKy_MSSV;
    public static javax.swing.JTextField jTextField1_HocKy_STT;
    private javax.swing.JTextField jTextField1_SinhVien_MSSV;
    private javax.swing.JTextField jTextField1_SinhVien_Stt;
    private javax.swing.JTextField jTextField21_DiemSo_diemMon1;
    private javax.swing.JTextField jTextField22_DiemSo_diemMon2;
    private javax.swing.JTextField jTextField23_DiemSo_diemMon3;
    private javax.swing.JTextField jTextField24_DiemSo_diemMon4;
    private javax.swing.JTextField jTextField26_TongKet_MSSV_timKiem;
    private javax.swing.JTextField jTextField28_DiemSo_timSV;
    public static javax.swing.JTextField jTextField2_DiemSo_STT;
    private javax.swing.JTextField jTextField2_SinhVien_Email;
    private javax.swing.JTextField jTextField2_SinhVien_diaChiNha2;
    private javax.swing.JTextField jTextField2_SinhVien_hoVaTen;
    private javax.swing.JTextField jTextField3_SinhVien_timKiem;
    private javax.swing.JTextField jTextField4_SinhVien_danToc;
    private javax.swing.JTextField jTextField5_SinhVien_Khoa;
    private javax.swing.JTextField jTextField6_SinhVien_namBatDau;
    private javax.swing.JTextField jTextField7_SinhVien_namKetThuc;
    private javax.swing.JTextField jTextField8_HocKy_MSSV_timKiem;
    private javax.swing.JTextField jTextField9_SinhVien_diaChiNha1;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel1;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel10;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel11;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel12;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel13;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel14;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel15;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel16;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel17;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel18;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel19;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel2;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel20;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel21;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel22;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel23;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel24;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel25;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel26;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel28;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel29;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel3;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel30;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel4;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel5;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel6;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel7;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel8;
    private quanlysinhvien.customizedShapes.RoundPanel roundPanel9;
    // End of variables declaration//GEN-END:variables
}
