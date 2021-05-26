package models;

import java.util.List;

public class DSThuongModel {
    private int ID;
    private String tenDS;
    private int namHoc;
    private int tongChiPhi;
    private List<PhanThuongModel> listThuong;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNamHoc() {
        return namHoc;
    }

    public void setNamHoc(int namHoc) {
        this.namHoc = namHoc;
    }

    public int getTongChiPhi() {
        return tongChiPhi;
    }

    public void setTongChiPhi(int tongChiPhi) {
        this.tongChiPhi = tongChiPhi;
    }

	public String getTenDS() {
		return tenDS;
	}

	public void setTenDS(String tenDS) {
		this.tenDS = tenDS;
	}

	public List<PhanThuongModel> getListThuong() {
		return listThuong;
	}

	public void setListThuong(List<PhanThuongModel> listThuong) {
		this.listThuong = listThuong;
	}
}