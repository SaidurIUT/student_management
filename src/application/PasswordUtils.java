package application;

import java.security.MessageDigest;

public class PasswordUtils {
	public static String getSHA256Hash(String input) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(input.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
