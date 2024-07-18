/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlysinhvien.functions;
import javax.swing.JOptionPane;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class kiemTraNam {

    private static final String YEAR_PATTERN = "^(200[0-9]|201[0-9]|202[0-4])$";
    private static final Pattern pattern = Pattern.compile(YEAR_PATTERN);
    private static final int CURRENT_YEAR = java.time.Year.now().getValue();

    public static boolean KiemTraNam(String namBatDau, String namKetThuc) {
        //Kiểm tra nếu năm bắt đầu trống
        if (namBatDau.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Chưa điền năm bắt đầu!");
            return false;
        }

        // Kiểm tra nếu năm kết thúc trống
        if (namKetThuc.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Chưa điền năm kết thúc!");
            return false;
        }

        // Kiểm tra định dạng của năm bắt đầu
        Matcher matcher = pattern.matcher(namBatDau);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập năm bắt đầu từ 2000-2024!");
            return false;
        }
        int startYear = Integer.parseInt(namBatDau);

        // Kiểm tra năm bắt đầu không lớn hơn năm hiện tại
        if (startYear > CURRENT_YEAR) {
            JOptionPane.showMessageDialog(null, "Năm bắt đầu không thể lớn hơn năm hiện tại!");
            return false;
        }

        // Kiểm tra định dạng của năm kết thúc
        matcher = pattern.matcher(namKetThuc);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập năm kết thúc từ 2000-2024!");
            return false;
        }
        int endYear = Integer.parseInt(namKetThuc);

        // Kiểm tra năm kết thúc không lớn hơn năm hiện tại
        if (endYear > CURRENT_YEAR) {
            JOptionPane.showMessageDialog(null, "Năm kết thúc không thể lớn hơn năm hiện tại!");
            return false;
        }

        // Kiểm tra mối quan hệ giữa năm bắt đầu và năm kết thúc
        if (endYear < startYear) {
            JOptionPane.showMessageDialog(null, "Năm kết thúc không thể nhỏ hơn năm bắt đầu!");
            return false;
        }
        
        if (endYear == startYear) {
            JOptionPane.showMessageDialog(null, "Năm kết thúc không thể bằng năm bắt đầu!");
            return false;
        }
        
        if (endYear != startYear + 4) {
            JOptionPane.showMessageDialog(null, "Năm kết thúc không thể lớn hơn năm bắt đầu quá 4 năm!");
            return false;
        }
        return true;
    }
}

