package models;

public class PhanQuaModel {
    private int ID;
    private String tenQua;
    private int idDsQua;
    private int idNguoiNhan;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenQua() {
		return tenQua;
	}

	public void setTenQua(String tenQua) {
		this.tenQua = tenQua;
	}

    public int getIdDsQua() {
        return idDsQua;
    }

    public void setIdDsQua(int idDsQua) {
        this.idDsQua = idDsQua;
    }

    public int getIdNguoiNhan() {
        return idNguoiNhan;
    }

    public void setIdNguoiNhan(int idNguoiNhan) {
        this.idNguoiNhan = idNguoiNhan;
    }
}
