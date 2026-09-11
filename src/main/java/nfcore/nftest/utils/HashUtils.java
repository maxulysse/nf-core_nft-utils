package nfcore.nftest.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 * Utility methods for computing MD5 hashes.
 */
public final class HashUtils {

  /**
   * Prevents instantiation of this utility class.
   */
  private HashUtils() {
  }

  /**
   * Bit mask used to convert a signed byte to its unsigned representation.
   */
  private static final int BYTE_MASK = 0xff;

  /**
   * Computes an MD5 hash of the given string value.
   *
   * @param value The string to hash.
   * @return The MD5 digest as a lowercase hexadecimal string.
   * @throws NoSuchAlgorithmException If the MD5 algorithm is not available.
   */
  public static String md5Hex(final String value)
      throws NoSuchAlgorithmException {
    final MessageDigest digest = MessageDigest.getInstance("MD5");
    final byte[] hash = digest.digest(
      value.getBytes(StandardCharsets.UTF_8)
    );

    final StringBuilder result = new StringBuilder();
    for (final byte b : hash) {
      result.append(
        String.format(Locale.ROOT, "%02x", b)
      );
    }
    return result.toString();
  }

  /**
   * Computes an MD5 hash from the string representation of each element in
   * a list.
   *
   * @param input The list of objects to include in the MD5 calculation.
   * @return The MD5 digest as a hexadecimal string.
   * @throws UnsupportedEncodingException If UTF-8 encoding is not supported.
   */
  public static String listToMD5(
      final ArrayList<Object> input)
      throws UnsupportedEncodingException {
    try {
      MessageDigest md5 = MessageDigest.getInstance("MD5");
      Iterator<Object> inputIterator = input.iterator();
      while (inputIterator.hasNext()) {
        md5.update(inputIterator.next().toString().getBytes("UTF-8"));
      }
      byte[] digest = md5.digest();

      StringBuilder hexString = new StringBuilder();
      for (byte b : digest) {
        String hex = Integer.toHexString(BYTE_MASK & b);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException("MD5 algorithm not available", e);
    }
  }
}
