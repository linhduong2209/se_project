package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.DSQuaModel;
import models.PhanQuaModel;

public class DSQuaService {
	// Them moi ds
	public boolean addDS(DSQuaModel ds) throws ClassNotFoundException, SQLException {
		Connection connection = MysqlConnection.getMysqlConnection();
		String query = "INSERT INTO ds_qua(tenDs, tenDip, thoiGian, tongChiPhi, giaTriQua)" + " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setString(1, ds.getTenDS());
		preparedStatement.setString(2, ds.getTenDip());
		preparedStatement.setDate(3, ds.getThoiGian());
		preparedStatement.setInt(4, ds.getTongChiPhi());
		preparedStatement.setInt(5, ds.getGiaTriQua());

		preparedStatement.executeUpdate();
		ResultSet rs = preparedStatement.getGeneratedKeys();
		if (rs.next()) {
			int genID = rs.getInt(1);
			String sql = "INSERT INTO phan_qua(idDsQua, tenQua, idNguoiNhan)" + " values (?, ?, ?)";
			PreparedStatement preStatement = connection.prepareStatement(sql);
			ds.getListQua().forEach((PhanQuaModel q) -> {
				try {
					preStatement.setString(1, q.getTenQua());
					preStatement.setInt(2, genID);
					preStatement.setInt(3, q.getIdNguoiNhan());

					preStatement.executeUpdate();
				} catch (SQLException ex) {
					Logger.getLogger(DSQuaService.class.getName()).log(Level.SEVERE, null, ex);
				}

			});
			preStatement.close();
		}
		preparedStatement.close();
		connection.close();
		return true;
	}

	// Lay ra 1 ds
	public DSQuaModel getDSQua(int id) {
		DSQuaModel ds = new DSQuaModel();

		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			String query = "SELECT * FROM ds_qua WHERE ID = " + id;
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			ds.setID(rs.getInt("ID"));
			ds.setTenDS(rs.getString("tenDS"));
			ds.setTenDip(rs.getString("tenDip"));
			ds.setThoiGian(rs.getDate("thoiGian"));
			ds.setTongChiPhi(rs.getInt("tongChiPhi"));
			ds.setGiaTriQua(rs.getInt("giaTriQua"));

			try {
				String sql = "SELECT * FROM phan_qua WHERE idDsQua =" + rs.getInt(1);
				PreparedStatement prst = connection.prepareStatement(sql);
				ResultSet rs_1 = prst.executeQuery();
				List<PhanQuaModel> listQua = new ArrayList<>();
				while (rs_1.next()) {
					PhanQuaModel q = new PhanQuaModel();
					q.setID(rs_1.getInt("ID"));
					q.setTenQua(rs_1.getString("tenQua"));
					q.setIdNguoiNhan(rs_1.getInt("idNguoiNhan"));
					q.setIdDsQua(rs.getInt(1));
					listQua.add(q);
				}
				prst.close();
			} catch (SQLException ex) {
				Logger.getLogger(DSQuaService.class.getName()).log(Level.SEVERE, null, ex);
			}

			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return ds;
	}

	// Lay ra list ds
	public List<DSQuaModel> getListDSQua() {
		List<DSQuaModel> list = new ArrayList<>();

		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			String query = "SELECT * FROM ds_qua";
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSQuaModel temp = new DSQuaModel();
				temp = this.getDSQua(rs.getInt(1));
				list.add(temp);
			}
			preparedStatement.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	// cap nhat DS
	public boolean updateDSQua(int id, List<PhanQuaModel> listUpdate, List<PhanQuaModel> listNew)
			throws ClassNotFoundException, SQLException {

		try {
			Connection connection = MysqlConnection.getMysqlConnection();

			// Them moi phan qua trong ds
			if (listNew != null) {
				String sql = "INSERT INTO phan_qua(idDsQua, tenQua, idNguoiNhan) values (?, ?, ?)";
				PreparedStatement preparedStatement = connection.prepareStatement(sql);
				listNew.forEach((PhanQuaModel q) -> {
					try {
						preparedStatement.setString(1, q.getTenQua());
						preparedStatement.setInt(2, q.getIdDsQua());
						preparedStatement.setInt(3, q.getIdNguoiNhan());

						preparedStatement.executeUpdate();
					} catch (SQLException ex) {
						Logger.getLogger(DSQuaService.class.getName()).log(Level.SEVERE, null, ex);
					}

				});
				preparedStatement.close();
			}

			// cap nhat thong tin da co trong ds
			if (listUpdate != null) {
				String sql_1 = "UPDATE phan_qua SET tenQua = ?, idNguoiNhan = ? WHERE ID =" + id;
				PreparedStatement preStatement = connection.prepareStatement(sql_1);
				listNew.forEach((PhanQuaModel q) -> {
					try {
						preStatement.setString(1, q.getTenQua());
						preStatement.setInt(2, q.getIdNguoiNhan());

						preStatement.executeUpdate();
					} catch (SQLException ex) {
						Logger.getLogger(DSQuaService.class.getName()).log(Level.SEVERE, null, ex);
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
	public List<DSQuaModel> filtDSQua(int from, int to) {
		List<DSQuaModel> list = new ArrayList<>();
		String query = "SELECT * from ds_qua WHERE YEAR(thoiGian) >= " + from + "AND YEAR(thoiGian) <= " + to
				+ "ORDER BY thoiGian DESC";
		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSQuaModel temp = new DSQuaModel();
				temp = this.getDSQua(rs.getInt(1));
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
	public List<DSQuaModel> filtDSQua(int id) {
		List<DSQuaModel> list = new ArrayList<>();
		String query = "SELECT * from ds_qua ds INNER JOIN"
					+ "thanh_vien_ho tv ON ds.idNguoiNhan = tv.idNhanKhau " 
					+ " WHERE tv.idHoKhau = " +id;
		try {
			Connection connection = MysqlConnection.getMysqlConnection();
			PreparedStatement preparedStatement = (PreparedStatement) connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DSQuaModel temp = new DSQuaModel();
				temp = this.getDSQua(rs.getInt(1));
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