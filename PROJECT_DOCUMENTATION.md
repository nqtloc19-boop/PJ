# Tài liệu dự án Hotel Management

## 1. Phạm vi dự án
Dự án là ứng dụng Java console quản lý các đối tượng, phòng, dịch vụ, đặt phòng, hóa đơn và lịch sử lưu trú của khách sạn. Toàn bộ mã nguồn dùng package chung `hotelmanagement` và được chia theo thư mục chức năng.

## 2. Kiến trúc module

| Module | Trách nhiệm | Lớp tiêu biểu |
|---|---|---|
| Module 1 - Đối tượng cơ bản | Quản lý người, khách hàng, nhân viên và danh sách người | `NGUOI`, `KHACHHANG`, `NHANVIEN` |
| Module 2 - Quản lý phòng | Mô hình hóa phòng, loại phòng, trạng thái và tiện nghi | `Phong`, `PhongStandard`, `PhongVIP`, `TienNghiPhong` |
| Module 3 - Dịch vụ tiện ích | Danh mục và thông tin dịch vụ ăn uống, spa, giải trí, thuê xe | `DichVu`, `DanhSachDichVu` |
| Module 4 - Quản lý đặt phòng | Tạo phiếu, phát sinh dịch vụ, lập hóa đơn, thanh toán và doanh thu | `PhieuDatPhong`, `ChiTietHoaDon`, `HoaDon`, `QuanLyDatPhong` |
| Module 5 - Lịch sử đặt phòng | Điều phối dữ liệu từ các module và lưu lịch sử vòng đời đặt phòng | `QuanLyLichSuDatPhong` |

`ClassDungChung` cung cấp interface nhập/xuất và các tiện ích kiểm tra dữ liệu, thông báo.

## 3. Luồng nghiệp vụ chính

1. Tạo `KHACHHANG` và chọn `Phong` từ Module 1–2.
2. Tạo `PhieuDatPhong`; phòng chuyển sang trạng thái `Da dat`.
3. Đưa phiếu vào Module 5 để bắt đầu một `BanGhiLichSu`.
4. Trong thời gian lưu trú, thêm dịch vụ qua `ChiTietHoaDon` của Module 3–4.
5. Lập `HoaDon` để tính tiền phòng, dịch vụ, giảm giá và VAT.
6. Khi thanh toán, Module 5 đánh dấu hóa đơn đã thanh toán, chuyển bản ghi sang `DA_TRA_PHONG` và phòng về `Trong`.
7. Các bản ghi vẫn giữ trong danh sách lịch sử để tra cứu theo mã phiếu, phòng hoặc CCCD.

## 4. Trạng thái lịch sử

- `DAT_MOI`: phiếu đã được ghi nhận nhưng khách chưa nhận phòng.
- `DANG_O`: khách đang lưu trú.
- `DA_TRA_PHONG`: đã thanh toán và trả phòng.
- `DA_HUY`: đặt phòng bị hủy, phòng được giải phóng.

## 5. Quy ước triển khai

- Mã phiếu là định danh duy nhất trong Module 5.
- Không sao chép khách hàng, phòng hoặc phiếu; bản ghi lịch sử giữ tham chiếu tới các đối tượng hiện có.
- Dịch vụ được cộng dồn theo mã dịch vụ và hóa đơn được cập nhật lại sau khi thay đổi dịch vụ.
- Các lớp mới không phụ thuộc vào thao tác console, nên có thể được gọi từ menu hoặc giao diện khác trong tương lai.

## 6. Menu chạy chính

Chương trình chỉ có một điểm vào:

`hotelmanagement.Main`

Menu gồm đúng ba nhóm chức năng: quản lý khách hàng; quản lý phòng; đặt phòng tích hợp dịch vụ, thanh toán và lịch sử.

## 7. Biên dịch

Biên dịch toàn bộ mã nguồn với UTF-8 vào thư mục `build`, sau đó chạy lớp `hotelmanagement.Main`. Hướng dẫn chi tiết nằm trong `Module5_QuanLyLichSuDatPhong/README.md`.
