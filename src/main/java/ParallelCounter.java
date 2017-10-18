import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ParallelCounter {
    public Map<Character, Long> countSymbolsSequnetially(Stream<String> strings) {
        return strings.flatMap(s -> s.chars().mapToObj(ch -> (char)ch)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public List<Character> flatSymbols(Stream<String> strings) {
        List<Character> res = strings.flatMap(s -> s.chars().mapToObj(ch -> (char)ch)).collect(Collectors.toList());

        return res;
    }

    public Map<Character, Long> countSymbolsInStringCollectManually(String s) {
        Map<Character, Long> res = s.chars().mapToObj(value -> (char)value).collect(
                HashMap::new, (r, ch) -> r.merge(ch, 1L, Long::sum), HashMap::putAll);

        return res;
    }

    public Map<Character, Long> countSymbolsInStringGroupingBy(String s) {
        Map<Character, Long> res = IntStream.range(0, s.length()).mapToObj(i -> s.charAt(i))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return res;
    }

    public static void main(String[] args) {
        ParallelCounter counter = new ParallelCounter();

        try (Stream<String> strs = Files.lines(Paths.get("test-simple.txt"))) {
            Map<Character, Long> res = counter.countSymbolsSequnetially(strs);

            List<Map.Entry<Character, Long>> entries = res.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toList());

            System.out.println(entries);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
