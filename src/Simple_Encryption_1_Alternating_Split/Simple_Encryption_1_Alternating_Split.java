package Simple_Encryption_1_Alternating_Split;

/**
 * Algorithm of encryption:
 * - shift to the left for one element;
 * - mirror swap of elements;
 * - divide on 2 equal parts and do mirror swap of parts. If length is odd, leave central element on its place;
 */
public class Simple_Encryption_1_Alternating_Split {

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
        var shiftedLeft = shiftLeft(text);
        var mirrored = mirrorSwap(shiftedLeft);

        return mirrorSwapParts(mirrored);
    }

    private static String decrypt(String text) {
        String partMirrored = mirrorSwapParts(text);
        String mirrored = mirrorSwap(partMirrored);

        return shiftRight(mirrored);
    }

    private static boolean isValid(String text, int n) {
        return text != null && text.length() > 1 && n > 0;
    }

    private static String shiftLeft(String source) {
        return source.substring(1) + source.charAt(0);
    }

    private static String shiftRight(String source) {
        return source.charAt(source.length() - 1) + source.substring(0, source.length() - 1);
    }

    private static String mirrorSwap(String source) {
        char[] array = source.toCharArray();
        for (int i = 0; i < (array.length / 2); i++) {
            char b = array[array.length - i - 1];
            array[array.length - i - 1] = array[i];
            array[i] = b;
        }

        return String.valueOf(array);
    }

    private static String mirrorSwapParts(String text) {
        String left = text.substring(0, text.length() / 2);
        String right = text.substring(text.length() / 2 + text.length() % 2);
        String center = "";
        if (text.length() % 2 == 1) {
            center = String.valueOf(text.charAt(text.length() / 2));
        }

        return mirrorSwap(left) + center + mirrorSwap(right);
    }
}
