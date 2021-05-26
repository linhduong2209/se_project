package models;

import java.sql.Date;
import java.util.List;

public class DSQuaModel {
	private int ID;
	private String tenDS;
	private String tenDip;
	private Date thoiGian;
	private int giaTriQua;
	private int tongChiPhi;
	private List<PhanQuaModel> listQua;

	public String getTenDS() {
		return tenDS;
	}

	public void setTenDS(String tenDS) {
		this.tenDS = tenDS;
	}

	public List<PhanQuaModel> getListQua() {
		return listQua;
	}

	public void setListQua(List<PhanQuaModel> listQua) {
		this.listQua = listQua;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getTenDip() {
		return tenDip;
	}

	public void setTenDip(String tenDip) {
		this.tenDip = tenDip;
	}

	public Date getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(Date thoiGian) {
		this.thoiGian = thoiGian;
	}

	public int getGiaTriQua() {
		return giaTriQua;
	}

	public void setGiaTriQua(int giaTriQua) {
		this.giaTriQua = giaTriQua;
	}

	public int getTongChiPhi() {
		return tongChiPhi;
	}

	public void setTongChiPhi(int tongChiPhi) {
		this.tongChiPhi = tongChiPhi;
	}

}
