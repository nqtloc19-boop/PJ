# Module 5 - Quản lý lịch sử đặt phòng

## Mục tiêu
Module 5 là lớp điều phối tập trung, tái sử dụng các đối tượng của Module 1–4 để theo dõi toàn bộ vòng đời đặt phòng và lưu lịch sử sau khi khách trả phòng hoặc hủy đặt phòng.

## Thành phần chính
- `QuanLyLichSuDatPhong`: quản lý danh sách bản ghi lịch sử.
- `BanGhiLichSu`: liên kết `PhieuDatPhong`, `ChiTietHoaDon` và `HoaDon` trong cùng một bản ghi.
- `TrangThai`: `DAT_MOI`, `DANG_O`, `DA_TRA_PHONG`, `DA_HUY`.

## Chức năng
1. Ghi nhận phiếu đặt phòng và chống trùng mã phiếu.
2. Tra cứu theo mã phiếu, mã phòng hoặc CCCD khách hàng.
3. Gọi thêm dịch vụ từ Module 3 và đồng bộ vào hóa đơn.
4. Lập hóa đơn tổng hợp tiền phòng, dịch vụ, giảm giá và VAT thông qua Module 4.
5. Cập nhật nhận phòng, thanh toán - trả phòng và giải phóng phòng về trạng thái `Trong`.
6. Hủy đặt phòng và giải phóng phòng.
7. Hiển thị lịch sử và tính doanh thu của các hóa đơn đã thanh toán.

## Menu hợp nhất
Module 5 là điểm vào duy nhất của project và cung cấp 3 nhóm chức năng:

1. Quản lý khách hàng.
2. Quản lý phòng.
3. Đặt phòng, dịch vụ, thanh toán và lịch sử.

Toàn bộ lớp Java dùng package chung `hotelmanagement`.

## Biên dịch và chạy thử
Từ thư mục gốc của dự án:

```text
javac -encoding UTF-8 -d build $(find . -name "*.java")
java -cp build hotelmanagement.QuanLyLichSuDatPhong
```

Trên Windows PowerShell, có thể biên dịch bằng:

```powershell
javac -encoding UTF-8 -d build (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })
java -cp build hotelmanagement.QuanLyLichSuDatPhong
```

Thư mục `build` chứa class đã biên dịch theo đúng cấu trúc package. Có thể chạy menu bằng `java -cp build hotelmanagement.QuanLyLichSuDatPhong`.
