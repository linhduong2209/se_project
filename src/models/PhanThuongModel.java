package models;

public class PhanThuongModel {
	private int ID;
	private String tenThuong;
	private int idDsThuong;
	private int idNguoiNhan;
	private String thanhTich;
	private int giaTriThuong;

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getTenThuong() {
		return tenThuong;
	}

	public void setTenThuong(String tenThuong) {
		this.tenThuong = tenThuong;
	}

	public String getThanhTich() {
		return thanhTich;
	}

	public void setThanhTich(String thanhTich) {
		this.thanhTich = thanhTich;
	}

	public int getGiaTriThuong() {
		return giaTriThuong;
	}

	public void setGiaTriThuong(int giaTriThuong) {
		this.giaTriThuong = giaTriThuong;
	}

	public int getIdDsThuong() {
		return idDsThuong;
	}

	public void setIdDsThuong(int idDsThuong) {
		this.idDsThuong = idDsThuong;
	}

	public int getIdNguoiNhan() {
		return idNguoiNhan;
	}

	public void setIdNguoiNhan(int idNguoiNhan) {
		this.idNguoiNhan = idNguoiNhan;
	}
}