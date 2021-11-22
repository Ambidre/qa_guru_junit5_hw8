package gmail.anastasiacoder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SearchTest extends TestBase {

    //1.ValueSource
    @ValueSource(strings = {"Dr. Stone", "Shingeki no Kyojin"})
    @Tag("blocker")
    @DisplayName("Поиск на myanimelist.net")
    @ParameterizedTest(name = "Поиск аниме {0} и проверка отображения текста {0}")
    void animeSearchByValueTest(String searchQuery) {
        open("https://myanimelist.net/");
        $("#topSearchText").setValue(searchQuery).pressEnter();
        $$(".hoverinfo_trigger.fw-b").find(text(searchQuery)).shouldBe(visible);
    }

    //2.CsvSource
    @CsvSource(value = {
            "Dr. Stone| Dr. Stone: Stone Wars",
            "Shingeki no Kyojin| Shingeki no Kyojin: The Final Season Part 2"
    },
            delimiter = '|')
    @Tag("blocker")
    @DisplayName("Поиск на myanimelist.net")
    @ParameterizedTest(name = "Поиск на myanimelist.net аниме {0} и проверка отображения текста {1}")
    void animeSearchByCsvTest(String searchQuery, String expectedResult) {
        open("https://myanimelist.net/");
        $("#topSearchText").setValue(searchQuery).pressEnter();
        $$(".hoverinfo_trigger.fw-b").find(text(expectedResult)).shouldBe(visible);
    }

    //3.EnumSource
    @EnumSource(SearchQuery.class)
    @Tag("blocker")
    @DisplayName("Поиск на myanimelist.net")
    @ParameterizedTest(name = "Поиск на myanimelist.net аниме {0}")
    void animeSearchWithEnumTest(SearchQuery searchQuery) {
        open("https://myanimelist.net/");
        $("#topSearchText").setValue(searchQuery.name()).pressEnter();
        $$(".hoverinfo_trigger.fw-b").find(text(searchQuery.name())).shouldBe(visible);
    }

    static Stream<Arguments> animeSearchWithMethodSourceTest() {
        return Stream.of(
                Arguments.of("Shingeki no Kyojin", "Shingeki"),
                Arguments.of("Dr. Stone", "Stone")
        );
    }

    //4.MethodSource
    @MethodSource
    @Tag("blocker")
    @DisplayName("Поиск на myanimelist.net")
    @ParameterizedTest(name = "Поиск на myanimelist.net аниме {0} и проверка отображения текста {1}")
    void animeSearchWithMethodSourceTest(String searchQuery, String expectedResult) {
        open("https://myanimelist.net/");
        $("#topSearchText").setValue(searchQuery).pressEnter();
        $$(".hoverinfo_trigger.fw-b").find(text(expectedResult)).shouldBe(visible);
    }
}