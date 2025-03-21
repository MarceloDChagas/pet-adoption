package util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextHighlighter {
    public static String highlightTerm(String text, String term) {
        final String ANSI_BOLD = "\033[1m";
        final String ANSI_RESET = "\033[0m";
        if (term == null || term.trim().isEmpty()) {
            return text;
        }

        Pattern pattern = Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            matcher.appendReplacement(sb, ANSI_BOLD + matcher.group() + ANSI_RESET);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}
