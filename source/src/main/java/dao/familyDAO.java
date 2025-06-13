package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.family;

public class familyDAO {
	// 引数で指定されたidpwでログイン成功ならtrueを返す
	public boolean isFamilyOK(family family) {
		Connection conn = null;
		boolean familyResult = false;

		try {
			// JDBCドライバを読み込む
			Class.forName("com.mysql.cj.jdbc.Driver");

			// データベースに接続する
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/e1_db?"
					+ "characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B9&rewriteBatchedStatements=true",
					"root", "password");

			// SELECT文を準備する
			String sql = "SELECT count(*) FROM family WHERE family_id=? AND fami_pass=?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1, family.getFamily_id());
			pStmt.setString(2, family.getFami_pass());

			// SELECT文を実行し、結果表を取得する
			ResultSet rs = pStmt.executeQuery();

			// ファミリーIDとあいことばが一致するユーザーがいれば結果をtrueにする
			rs.next();
			if (rs.getInt("count(*)") == 1) {
				familyResult = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			familyResult = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			familyResult = false;
		} finally {
			// データベースを切断
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					familyResult = false;
				}
			}
		}

		// 結果を返す
		return familyResult;
	}
}
