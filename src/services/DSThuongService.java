package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.DSThuongModel;
import models.PhanThuongModel;

public class DSThuongService {
	// Them moi ds
	public boolean addDS(DSThuongModel ds) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO ds_thuong(tenDs, namHoc, tongChiPhi) values (?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, ds.getTenDS());
		preparedStatement.setInt(2, ds.getNamHoc());
		preparedStatement.setInt(3, ds.getTongChiPhi());

		preparedStatement.executeUpdate();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		if (rs.next()) {
			int genID = rs.getInt(1);
			String sql = "INSERT INTO ds_thuong(idDsThuong, tenThuong, idNguoiNhan, giaTriThuong, thanhTich)"
					+ " values (?, ?, ?, ?, ?)";
			PreparedStatement preStatement = connection.prepareStatement(sql);
			ds.getListThuong().forEach((PhanThuongModel q) -> {
				try {
					preStatement.setString(1, q.getTenThuong());
					preStatement.setInt(2, genID);
					preStatement.setInt(3, q.getIdNguoiNhan());
					preStatement.setInt(4, q.getGiaTriThuong());
					preStatement.setString(5, q.getThanhTich());

					preStatement.executeUpdate();
				} catch (SQLException ex) {
					Logger.getLogger(DSThuongService.class.getName()).log(Level.SEVERE, null, ex);
				}

			});
			preStatement.close();
		}
		preparedStatement.close();
		connection.close();
		return true;
	}

	// Lay thong tin 1 ds
	public DSThuongModel getDSThuong(int id) {
		DSThuongModel ds = new DSThuongModel();

		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			String query = "SELECT * FROM ds_thuong WHERE ID = " + id;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			ds.setID(rs.getInt("ID"));
			ds.setTenDS(rs.getString("tenDS"));
			ds.setNamHoc(rs.getInt("namHoc"));
			ds.setTongChiPhi(rs.getInt("tongChiPhi"));

			try {
				String sql = "SELECT * FROM phan_thuong WHERE idDsThuong =" + rs.getInt(1);
				PreparedStatement prst = connection.prepareStatement(sql);
				ResultSet rs_1 = prst.executeQuery();
				List<PhanThuongModel> listThuong = new ArrayList<>();
				while (rs_1.next()) {
					PhanThuongModel q = new PhanThuongModel();
					q.setID(rs_1.getInt("ID"));
					q.setTenThuong(rs_1.getString("tenThuong"));
					q.setIdNguoiNhan(rs_1.getInt("idNguoiNhan"));
					q.setGiaTriThuong(rs_1.getInt("giaTriThuong"));
					q.setThanhTich(rs_1.getString("thanhTich"));
					q.setIdDsThuong(rs.getInt(1));
					listThuong.add(q);
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ds;
	}

	// Lay list ds
	public List<DSThuongModel> getListDSThuong() {
		List<DSThuongModel> list = new ArrayList<>();

		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			String query = "SELECT * FROM ds_thuong";
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSThuongModel temp = new DSThuongModel();
				temp = this.getDSThuong(rs.getInt(1));
				list.add(temp);
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	// Cap nhat DS da chon
	public boolean updateDSThuong(int id, List<PhanThuongModel> listUpdate, List<PhanThuongModel> listNew)
			throws ClassNotFoundException, SQLException {

		try {
			Connection connection = MysqlConnection.getMysqlConnection();

			// Them moi phan thuong trong ds
			if (listNew != null) {
				String sql = "INSERT INTO phan_thuong(tenThuong, idDsThuong, idNguoiNhan, giaTriThuong, thanhTich)"
						+ " values (?, ?, ?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				listNew.forEach((PhanThuongModel q) -> {
					try {
						preparedStatement.setString(1, q.getTenThuong());
						preparedStatement.setInt(2, q.getIdDsThuong());
						preparedStatement.setInt(3, q.getIdNguoiNhan());

						preparedStatement.executeUpdate();
					} catch (SQLException ex) {
						Logger.getLogger(DSThuongService.class.getName()).log(Level.SEVERE, null, ex);
					}

				});
				preparedStatement.close();
			}

			// chinh sua thong tin da co trong ds
			if (listUpdate != null) {
				String sql_1 = "UPDATE phan_thuong SET tenThuong = ?, idNguoiNhan = ?, giaTriThuong = ?, thanhTich = ? WHERE ID ="
						+ id;
				PreparedStatement preStatement = connection.prepareStatement(sql_1);
				listNew.forEach((PhanThuongModel q) -> {
					try {
						preStatement.setString(1, q.getTenThuong());
						preStatement.setInt(2, q.getIdNguoiNhan());
						preStatement.setInt(3, q.getGiaTriThuong());
						preStatement.setString(4, q.getThanhTich());

						preStatement.executeUpdate();
					} catch (SQLException ex) {
						Logger.getLogger(DSThuongService.class.getName()).log(Level.SEVERE, null, ex);
					}
				});
				preStatement.close();
			}
			connection.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return true;
	}

	// Thong ke
	// Theo thoi gian
	public List<DSThuongModel> filtDSThuong(int from, int to) {
		List<DSThuongModel> list = new ArrayList<>();
		String query = "SELECT * from ds_thuong WHERE YEAR(thoiGian) >= " + from + "AND YEAR(thoiGian) <= " + to
				+ "ORDER BY thoiGian DESC";
		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSThuongModel temp = new DSThuongModel();
				temp = this.getDSThuong(rs.getInt(1));
				list.add(temp);
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	// Theo Ho
	public List<DSThuongModel> filtDSThuong(int id) {
		List<DSThuongModel> list = new ArrayList<>();
		String query = "SELECT * from ds_thuong ds INNER JOIN " + "thanh_vien_ho tv ON ds.idNguoiNhan = tv.idNhanKhau "
				+ " WHERE tv.idHoKhau = " + id;
		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSThuongModel temp = new DSThuongModel();
				temp = this.getDSThuong(rs.getInt(1));
				list.add(temp);
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

}

