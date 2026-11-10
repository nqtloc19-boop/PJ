package hotelmanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final QuanLyLichSuDatPhong qlLichSu = new QuanLyLichSuDatPhong();
    private final List<KHACHHANG> danhSachKhachHang = new ArrayList<>();
    private final List<Phong> danhSachPhong = new ArrayList<>();

    public void chayMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n========== HOTEL MANAGEMENT - MODULE 5 ==========");
            System.out.println("1. Quan ly khach hang");
            System.out.println("2. Quan ly phong");
            System.out.println("3. Dat phong - dich vu - thanh toan - lich su");
            System.out.println("0. Thoat");
            int luaChon = nhapSo(sc, "Chon chuc nang: ");
            switch (luaChon) {
                case 1:
                    menuKhachHang(sc);
                    break;
                case 2:
                    menuPhong(sc);
                    break;
                case 3:
                    menuDatPhong(sc);
                    break;
                case 0:
                    System.out.println("Da thoat chuong trinh.");
                    return;
                default:
                    System.out.println("Lua chon khong hop le.");
            }
        }
    }

    private void menuKhachHang(Scanner sc) {
        System.out.println("\n--- QUAN LY KHACH HANG ---");
        System.out.println("1. Them khach hang");
        System.out.println("2. Xem danh sach");
        System.out.println("3. Tim theo CCCD");
        System.out.println("0. Quay lai");
        switch (nhapSo(sc, "Chon: ")) {
            case 1:
                String hoTen = nhapChuoi(sc, "Ho ten: ");
                String cccd = nhapChuoi(sc, "CCCD: ");
                String sdt = nhapChuoi(sc, "So dien thoai: ");
                String diaChi = nhapChuoi(sc, "Dia chi: ");
                String loai = nhapChuoi(sc, "Loai khach (Thuong/VIP/Doanh nghiep): ");
                danhSachKhachHang.add(new KHACHHANG(hoTen, cccd, sdt, diaChi, loai, null));
                System.out.println("Them khach hang thanh cong.");
                break;
            case 2:
                if (danhSachKhachHang.isEmpty()) {
                    System.out.println("Chua co khach hang.");
                }
                for (KHACHHANG khach : danhSachKhachHang) {
                    System.out.println(khach.getCCCD() + " | " + khach.gethoten() + " | " + khach.getSdt() + " | " + khach.getLoaikhach());
                }
                break;
            case 3:
                KHACHHANG khach = timKhachHang(nhapChuoi(sc, "Nhap CCCD: "));
                System.out.println(khach == null ? "Khong tim thay." : khach.gethoten() + " | " + khach.getSdt());
                break;
            default:
                break;
        }
    }

    private void menuPhong(Scanner sc) {
        System.out.println("\n--- QUAN LY PHONG ---");
        System.out.println("1. Them phong Standard");
        System.out.println("2. Them phong VIP");
        System.out.println("3. Xem trang thai phong");
        System.out.println("0. Quay lai");
        switch (nhapSo(sc, "Chon: ")) {
            case 1:
                danhSachPhong.add(new PhongStandard(nhapChuoi(sc, "Ma phong: "), nhapGia(sc), "Trong", false));
                System.out.println("Them phong Standard thanh cong.");
                break;
            case 2:
                danhSachPhong.add(new PhongVIP(nhapChuoi(sc, "Ma phong: "), nhapGia(sc), "Trong", "Khong xac dinh", 0));
                System.out.println("Them phong VIP thanh cong.");
                break;
            case 3:
                if (danhSachPhong.isEmpty()) {
                    System.out.println("Chua co phong.");
                }
                for (Phong phong : danhSachPhong) {
                    System.out.printf("%s | %s | %,.0f VND/dem%n", phong.getMaPhong(), phong.getTrangThai(), phong.tinhGiaPhong());
                }
                break;
            default:
                break;
        }
    }

    private void menuDatPhong(Scanner sc) {
        System.out.println("\n--- DAT PHONG VA LICH SU ---");
        System.out.println("1. Tao phieu dat phong");
        System.out.println("2. Them dich vu");
        System.out.println("3. Nhan phong");
        System.out.println("4. Thanh toan - tra phong");
        System.out.println("5. Huy dat phong");
        System.out.println("6. Xem lich su va doanh thu");
        System.out.println("0. Quay lai");
        switch (nhapSo(sc, "Chon: ")) {
            case 1:
                taoPhieu(sc);
                break;
            case 2:
                System.out.println(qlLichSu.themDichVu(nhapChuoi(sc, "Ma phieu: "), nhapChuoi(sc, "Ma dich vu: "), nhapSo(sc, "So luong: "))
                        ? "Da them dich vu." : "Khong them duoc dich vu.");
                break;
            case 3:
                System.out.println(qlLichSu.batDauLuuTru(nhapChuoi(sc, "Ma phieu: ")) ? "Da nhan phong." : "Khong the nhan phong.");
                break;
            case 4:
                String maPhieu = nhapChuoi(sc, "Ma phieu: ");
                System.out.println(qlLichSu.thanhToanVaTraPhong(maPhieu, nhapChuoi(sc, "Phuong thuc thanh toan: "))
                        ? "Da thanh toan va tra phong." : "Khong the thanh toan.");
                break;
            case 5:
                System.out.println(qlLichSu.huyDatPhong(nhapChuoi(sc, "Ma phieu: ")) ? "Da huy dat phong." : "Khong the huy.");
                break;
            case 6:
                qlLichSu.hienThiLichSu();
                System.out.printf("Doanh thu da thanh toan: %,.0f VND%n", qlLichSu.tinhDoanhThuDaThanhToan());
                break;
            default:
                break;
        }
    }

    private void taoPhieu(Scanner sc) {
        KHACHHANG khach = timKhachHang(nhapChuoi(sc, "CCCD khach hang: "));
        Phong phong = timPhong(nhapChuoi(sc, "Ma phong trong: "));
        if (khach == null || phong == null || !phong.conTrong()) {
            System.out.println("Khach hang khong ton tai hoac phong khong trong.");
            return;
        }
        String ma = nhapChuoi(sc, "Ma phieu: ");
        String ngayDat = nhapChuoi(sc, "Ngay dat (d/M/yyyy): ");
        String ngayNhan = nhapChuoi(sc, "Ngay nhan (d/M/yyyy): ");
        String ngayTra = nhapChuoi(sc, "Ngay tra (d/M/yyyy): ");
        try {
            PhieuDatPhong phieu = new PhieuDatPhong(ma, ngayDat, ngayNhan, ngayTra, khach, phong);
            System.out.println(qlLichSu.themPhieuDatPhong(phieu) ? "Tao phieu dat phong thanh cong." : "Ma phieu da ton tai.");
        } catch (IllegalArgumentException ex) {
            System.out.println("Ngay khong hop le: " + ex.getMessage());
        }
    }

    private KHACHHANG timKhachHang(String cccd) {
        for (KHACHHANG khach : danhSachKhachHang) {
            if (khach.getCCCD().equalsIgnoreCase(cccd)) return khach;
        }
        return null;
    }

    private Phong timPhong(String maPhong) {
        for (Phong phong : danhSachPhong) {
            if (phong.getMaPhong().equalsIgnoreCase(maPhong)) return phong;
        }
        return null;
    }

    private int nhapSo(Scanner sc, String loiNhac) {
        while (true) {
            try {
                System.out.print(loiNhac);
                return Integer.parseInt(sc.nextLine().trim());
            } catch (Exception ex) {
                System.out.println("Vui long nhap so nguyen.");
            }
        }
    }

    private double nhapGia(Scanner sc) {
        while (true) {
            try {
                System.out.print("Gia co ban moi dem: ");
                return Double.parseDouble(sc.nextLine().trim());
            } catch (Exception ex) {
                System.out.println("Vui long nhap gia hop le.");
            }
        }
    }

    private String nhapChuoi(Scanner sc, String loiNhac) {
        while (true) {
            System.out.print(loiNhac);
            String giaTri = sc.nextLine().trim();
            if (!giaTri.isEmpty()) return giaTri;
            System.out.println("Thong tin khong duoc de trong.");
        }
    }

    public static void main(String[] args) {
        new Main().chayMenu();
    }
}