package Simple_Encryption_1_Alternating_Split;

/**
 * Algorithm of encryption:
 * - shift to the left for one element;
 * - mirror swap of elements;
 * - divide on 2 equal parts and do mirror swap of parts. If length is odd, leave central element on its place;
 */
public class Kata {

    public static String encrypt(final String text, final int n) {
        if (!isValid(text, n)) {
            return text;
        }
        String result = text;
        for (int i = 0; i < n; i++) {
            result = encrypt(result);
        }
        return result;
    }

    public static String decrypt(final String encryptedText, final int n) {
        if (!isValid(encryptedText, n)) {
            return encryptedText;
        }
        String result = encryptedText;
        for (int i = 0; i < n; i++) {
            result = decrypt(result);
        }
        return result;
    }

    private static String encrypt(String text) {
        StringBuilder odds = new StringBuilder();
        StringBuilder evens = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (i % 2 == 0) {
                evens.append(text.charAt(i));
            } else {
                odds.append(text.charAt(i));
            }
        }
        return odds.toString() + evens;
    }

    private static String decrypt(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length() / 2; i++) {
            result.append(text.charAt(text.length() / 2 + i));
            result.append(text.charAt(i));
        }
        // should be done for rows with odd length
        if (text.length() % 2 == 1) {
            result.append(text.charAt(text.length() - 1));
        }
        return result.toString();
    }

    private static boolean isValid(String text, int n) {
        return text != null && text.length() > 1 && n > 0;
    }
}
