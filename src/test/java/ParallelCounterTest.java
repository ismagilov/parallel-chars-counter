import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class ParallelCounterTest {
    @Test
    void testCountSymbolsInStringCollectManually() {
        ParallelCounter counter = new ParallelCounter();

        Map<Character, Long> res = counter.countSymbolsInStringCollectManually("aaa-bbb-rrr-ttt");

        Truth.assertThat(res).hasSize(5);
        Truth.assertThat(res).containsEntry('a', 3L);
        Truth.assertThat(res).containsEntry('b', 3L);
        Truth.assertThat(res).containsEntry('r', 3L);
        Truth.assertThat(res).containsEntry('t', 3L);
        Truth.assertThat(res).containsEntry('-', 3L);
    }

    @Test
    void testCountSymbolsInStringCollectWithGroupingBy() {
        ParallelCounter counter = new ParallelCounter();

        Map<Character, Long> res = counter.countSymbolsInStringGroupingBy("aaa-bbb-rrr-ttt-xxx");

        Truth.assertThat(res).hasSize(6);
        Truth.assertThat(res).containsEntry('a', 3L);
        Truth.assertThat(res).containsEntry('b', 3L);
        Truth.assertThat(res).containsEntry('r', 3L);
        Truth.assertThat(res).containsEntry('t', 3L);
        Truth.assertThat(res).containsEntry('x', 3L);
        Truth.assertThat(res).containsEntry('-', 4L);
    }

    @Test
    void testFlatAllStrings() {
        ParallelCounter counter = new ParallelCounter();

        List<Character> res = counter.flatSymbols(Stream.of("aaa", "bbb", "rrr"));

        Truth.assertThat(res).hasSize(9);
        Truth.assertThat(res).containsAllOf('a', 'b', 'r');
        Truth.assertThat(res).containsExactly('a', 'a', 'a', 'b', 'b', 'b', 'r', 'r', 'r');
    }

    @Test
    void testSequentialCount() {
        ParallelCounter counter = new ParallelCounter();

        try (Stream<String> strings = Files.lines(Paths.get("test-10Mb.txt"))) {
            Map<Character, Long> res = counter.countSymbolsSequnetially(strings);
            System.out.println(res);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
