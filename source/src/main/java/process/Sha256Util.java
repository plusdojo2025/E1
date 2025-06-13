package process;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Sha256Util {
	 /**
     * 文字列をSHA-256ハッシュに変換します。
     *
     * @param originalString 変換する元の文字列
     * @return SHA-256ハッシュ化された文字列（16進数形式）、またはnull（エラー発生時）
     */
    public static String hashString(String originalString) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            // SHA-256アルゴリズムが見つからない場合（通常は発生しない）
            e.printStackTrace();
            return null;
        }
    }
}
