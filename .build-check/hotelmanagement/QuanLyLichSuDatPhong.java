package hotelmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Module 5 - Quan ly tap trung thong tin dat phong va lich su luu tru.
 * Module nay ket hop cac doi tuong tu Module 1, 2, 3 va 4.
 */
public class QuanLyLichSuDatPhong {

	public enum TrangThai {
		DAT_MOI("Dat moi"),
		DANG_O("Dang o"),
		DA_TRA_PHONG("Da tra phong"),
		DA_HUY("Da huy");

		private final String tenHienThi;

		TrangThai(String tenHienThi) {
			this.tenHienThi = tenHienThi;
		}

		public String getTenHienThi() {
			return tenHienThi;
		}
	}

	/** Má»™t báº£n ghi lÃ  má»™t phiáº¿u Ä'áº·t phÃ²ng xuyÃªn suá»'t vÃ²ng Ä'á»i cá»§a nÃ³. */
	public static class BanGhiLichSu {
		private final PhieuDatPhong phieu;
		private final ChiTietHoaDon chiTietDichVu;
		private HoaDon hoaDon;
		private TrangThai trangThai;
		private String phuongThucThanhToan;

		private BanGhiLichSu(PhieuDatPhong phieu) {
			this.phieu = phieu;
			this.chiTietDichVu = new ChiTietHoaDon(phieu);
			this.trangThai = TrangThai.DAT_MOI;
		}

		public PhieuDatPhong getPhieu() {
			return phieu;
		}

		public ChiTietHoaDon getChiTietDichVu() {
			return chiTietDichVu;
		}

		public HoaDon getHoaDon() {
			return hoaDon;
		}

		public TrangThai getTrangThai() {
			return trangThai;
		}

		public String getPhuongThucThanhToan() {
			return phuongThucThanhToan;
		}
	}

	private static final DateTimeFormatter DINH_DANG_NGAY = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	private final List<BanGhiLichSu> lichSu = new ArrayList<>();

	public List<BanGhiLichSu> getLichSu() {
		return Collections.unmodifiableList(lichSu);
	}

	/** Ghi nháº­n má»™t phiáº¿u Ä'áº·t phÃ²ng má»›i vÃ o lá»‹ch sá»­. */
	public boolean themPhieuDatPhong(PhieuDatPhong phieu) {
		if (phieu == null || phieu.getMaPhieu() == null || timTheoMaPhieu(phieu.getMaPhieu()) != null) {
			return false;
		}
		lichSu.add(new BanGhiLichSu(phieu));
		return true;
	}

	public BanGhiLichSu timTheoMaPhieu(String maPhieu) {
		if (maPhieu == null) {
			return null;
		}
		for (BanGhiLichSu banGhi : lichSu) {
			if (maPhieu.trim().equalsIgnoreCase(banGhi.phieu.getMaPhieu())) {
				return banGhi;
			}
		}
		return null;
	}

	public BanGhiLichSu timTheoMaPhong(String maPhong) {
		if (maPhong == null) {
			return null;
		}
		for (BanGhiLichSu banGhi : lichSu) {
			if (maPhong.trim().equalsIgnoreCase(banGhi.phieu.getMaPhong())) {
				return banGhi;
			}
		}
		return null;
	}

	public List<BanGhiLichSu> timTheoCCCD(String cccd) {
		List<BanGhiLichSu> ketQua = new ArrayList<>();
		if (cccd == null) {
			return ketQua;
		}
		for (BanGhiLichSu banGhi : lichSu) {
			KHACHHANG khach = banGhi.phieu.getKhachHang();
			if (khach != null && cccd.trim().equalsIgnoreCase(khach.getCCCD())) {
				ketQua.add(banGhi);
			}
		}
		return ketQua;
	}

	/** ThÃªm dá»‹ch vá»¥ Module 3 vÃ o phiáº¿u vÃ  Ä'á»"ng bá»™ hÃ³a Ä'Æ¡n náº¿u Ä'Ã£ láº­p. */
	public boolean themDichVu(String maPhieu, String maDichVu, int soLuong) {
		BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
		return banGhi != null && banGhi.chiTietDichVu.themDichVu(maDichVu, soLuong);
	}

	public HoaDon taoHoaDon(String maPhieu, double giamGia) {
		BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
		if (banGhi == null) {
			return null;
		}
		if (banGhi.hoaDon == null) {
			String maHoaDon = "HD-" + banGhi.phieu.getMaPhieu();
			banGhi.hoaDon = new HoaDon(maHoaDon, ngayHienTai(), banGhi.phieu, banGhi.chiTietDichVu, giamGia);
		} else {
			banGhi.hoaDon.setChiTiet(banGhi.chiTietDichVu);
			banGhi.hoaDon.capNhatHoaDon();
		}
		return banGhi.hoaDon;
	}

	public boolean batDauLuuTru(String maPhieu) {
		BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
		if (banGhi == null || banGhi.trangThai == TrangThai.DA_HUY
				|| banGhi.trangThai == TrangThai.DA_TRA_PHONG) {
			return false;
		}
		banGhi.trangThai = TrangThai.DANG_O;
		if (banGhi.phieu.getPhong() != null) {
			banGhi.phieu.getPhong().setTrangThai("Dang o");
		}
		return true;
	}

	/** Thanh toÃ¡n vÃ  tráº£ phÃ²ng, Ä'á»"ng thá»i chá»'t tráº¡ng thÃ¡i trong lá»‹ch sá»­. */
	public boolean thanhToanVaTraPhong(String maPhieu, String phuongThucThanhToan) {
		BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
		if (banGhi == null || banGhi.trangThai == TrangThai.DA_HUY
				|| banGhi.trangThai == TrangThai.DA_TRA_PHONG) {
			return false;
		}
		HoaDon hoaDon = taoHoaDon(maPhieu, 0.0);
		hoaDon.setDaThanhToan(true);
		banGhi.phuongThucThanhToan = (phuongThucThanhToan == null || phuongThucThanhToan.trim().isEmpty())
				? "Khong xac dinh" : phuongThucThanhToan.trim();
		banGhi.trangThai = TrangThai.DA_TRA_PHONG;
		if (banGhi.phieu.getPhong() != null) {
			banGhi.phieu.getPhong().setTrangThai("Trong");
		}
		return true;
	}

	public boolean huyDatPhong(String maPhieu) {
		BanGhiLichSu banGhi = timTheoMaPhieu(maPhieu);
		if (banGhi == null || banGhi.trangThai == TrangThai.DA_TRA_PHONG) {
			return false;
		}
		banGhi.trangThai = TrangThai.DA_HUY;
		if (banGhi.phieu.getPhong() != null) {
			banGhi.phieu.getPhong().setTrangThai("Trong");
		}
		return true;
	}

	public double tinhDoanhThuDaThanhToan() {
		double tong = 0.0;
		for (BanGhiLichSu banGhi : lichSu) {
			if (banGhi.hoaDon != null && banGhi.hoaDon.isDaThanhToan()) {
				tong += banGhi.hoaDon.getTongTienPhong();
			}
		}
		return tong;
	}

	public void hienThiLichSu() {
		System.out.println("\n================ LICH SU DAT PHONG - MODULE 5 ================");
		if (lichSu.isEmpty()) {
			System.out.println("Chua co du lieu lich su.");
			return;
		}
		System.out.printf("%-10s | %-20s | %-10s | %-14s | %-16s | %-16s%n",
				"MA PHIEU", "KHACH HANG", "MA PHONG", "TRANG THAI", "TONG TIEN", "THANH TOAN");
		for (BanGhiLichSu banGhi : lichSu) {
			String tenKhach = banGhi.phieu.getKhachHang() == null ? "N/A"
					: banGhi.phieu.getKhachHang().gethoten();
			String tongTien = banGhi.hoaDon == null ? "Chua lap" : String.format("%,.0f VND", banGhi.hoaDon.getTongTienPhong());
			String thanhToan = banGhi.hoaDon != null && banGhi.hoaDon.isDaThanhToan() ? "Da thanh toan" : "Chua thanh toan";
			System.out.printf("%-10s | %-20s | %-10s | %-14s | %-16s | %-16s%n",
					banGhi.phieu.getMaPhieu(), tenKhach, banGhi.phieu.getMaPhong(),
					banGhi.trangThai.getTenHienThi(), tongTien, thanhToan);
		}
	}

	private String ngayHienTai() {
		return LocalDate.now().format(DINH_DANG_NGAY);
	}

	private final List<KHACHHANG> danhSachKhachHang = new ArrayList<>();
	private final List<Phong> danhSachPhong = new ArrayList<>();

	/** Menu duy nhất của toàn bộ project. */
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
			System.out.println(themDichVu(nhapChuoi(sc, "Ma phieu: "), nhapChuoi(sc, "Ma dich vu: "), nhapSo(sc, "So luong: ")) ? "Da them dich vu." : "Khong them duoc dich vu.");
			break;
		case 3:
			System.out.println(batDauLuuTru(nhapChuoi(sc, "Ma phieu: ")) ? "Da nhan phong." : "Khong the nhan phong.");
			break;
		case 4:
			String maPhieu = nhapChuoi(sc, "Ma phieu: ");
			System.out.println(thanhToanVaTraPhong(maPhieu, nhapChuoi(sc, "Phuong thuc thanh toan: ")) ? "Da thanh toan va tra phong." : "Khong the thanh toan.");
			break;
		case 5:
			System.out.println(huyDatPhong(nhapChuoi(sc, "Ma phieu: ")) ? "Da huy dat phong." : "Khong the huy.");
			break;
		case 6:
			hienThiLichSu();
			System.out.printf("Doanh thu da thanh toan: %,.0f VND%n", tinhDoanhThuDaThanhToan());
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
			System.out.println(themPhieuDatPhong(phieu) ? "Tao phieu dat phong thanh cong." : "Ma phieu da ton tai.");
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
		new QuanLyLichSuDatPhong().chayMenu();
	}
}