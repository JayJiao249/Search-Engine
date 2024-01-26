/**
 * Author Jay Jiao
 * Modify by Lin
 * TODO : get the keywords from sentence/query. Get the similar meaning words
 *
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

public final class Tokenizer {
    // Avoid those words to be keywords
    public static final String[] stopWords = {"a", "is", "!", ".", "?", ":", ",", ";", "=", "\\", "[", "]", "{", "}", "'",
            "\"", "about", "all", "also", "and", "i", "as", "at", "be", "because", "but", "by", "can", "come",
            "could", "day", "do", "even", "find", "first", "for", "from", "get", "give", "go", "have", "he",
            "her", "here", "him", "his", "how", "I", "if", "in", "into", "it", "its", "just", "know", "like",
            "look", "make", "man", "many", "me", "more", "my", "new", "no", "not", "now", "of", "on", "one",
            "only", "or", "other", "our", "out", "people", "say", "see", "she", "so", "some", "take", "tell",
            "than", "that", "the", "their", "them", "then", "there", "these", "they", "thing", "think", "this",
            "those", "time", "to", "up", "use", "very", "want", "way", "we", "well", "what", "when", "which",
            "who", "will", "with", "would", "year", "you", "your"};

    // The suffixes of the word, the purpose is get similar meaning words.
    public static final String[] suffixes = {"s", "es", "en", "ed", "ing", "er", "est", "age", "al",
            "ance", "dom", "ee", "ence", "er", "ery", "ication", "ism", "ist", "ty", "ment", "ness",
            "or", "sion", "tion", "hood", "ship", "ify", "ize", "ise", "able", "al", "ful", "ible", "ic",
            "ical", "ish", "less", "ly", "ous", "y", "ward", "wise", "ways"};

    //Get the keywords of sentence/query
    public static ArrayList<String> Token(String sentence) {
        sentence = sentence.toLowerCase();
        if (sentence.contains("conferences organised by the association for computational linguistics")){
            sentence = sentence.replace("conferences organised by the association for computational linguistics", "acl");
        }
        else if (sentence.contains("international conference on machine learning")){
            sentence = sentence.replace("international conference on machine learning", "icml");
        }
        else if (sentence.contains("neural information processing systems")){
            sentence = sentence.replace("neural information processing systems", "neurips");
        }
        else if (sentence.contains("International Conference on Computer Vision")){
            sentence = sentence.replace("International Conference on Computer Vision", "iccv");
        }

        ArrayList<String> keywords = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(sentence);
        while (stringTokenizer.hasMoreTokens()) {
            boolean stop = false;
            String token = stringTokenizer.nextToken();
            for (String stopWord : stopWords) {
                // check and remove stop words
                if (stopWord.equals(token)) {
                    stop = true;
                    break;
                }
            }
            if ((!stop) && (!(keywords.contains(token)))) {
                keywords.add(token);
            }
        }
        return keywords;
    }

    //get similar meaning words
    public static ArrayList<String> getSimilarWords(String word) {
        ArrayList<String> simWords = new ArrayList<>();
        String origin = null;
        for (String suffix : suffixes) {
            if (word.endsWith(suffix)) {
                origin = word.substring(0, word.lastIndexOf(suffix));
                break;
            }
        }
        for(String suffix: suffixes){
            simWords.add(origin+suffix);
        }
        return simWords;
    }
}
