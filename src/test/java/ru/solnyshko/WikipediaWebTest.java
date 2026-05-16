package ru.solnyshko;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.solnyshko.data.Language;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WikipediaWebTest {


    @EnumSource(Language.class)
    @ParameterizedTest
    @DisplayName("При смене языка на главной странице wikipedia.org должен меняться язык")
    void wikipediaSiteShouldDisplayCorrectText(Language language) {
        open("https://www.wikipedia.org/");
        $("#js-link-box-" + language.name().toLowerCase()).click();
        $("#content").shouldHave(text(language.description));
    }


    static Stream<Arguments> wikipediaSiteShouldDisplayCorrectButtons() {
        return Stream.of(
                Arguments.of(Language.EN, List.of("Main Page", "Talk", "Read", "View source", "View history") ),
                Arguments.of(Language.RU, List.of("Заглавная", "Обсуждение", "Читать", "Просмотр кода", "История") )
        );
    }

    @MethodSource
    @ParameterizedTest
    @DisplayName("При смене языка на wikipedia.org должны отображаться кнопки на соответствующем языке")
    void wikipediaSiteShouldDisplayCorrectButtons(Language language, List<String> expectedButtons) {
        open("https://www.wikipedia.org/");
        $("#js-link-box-" + language.name().toLowerCase()).click();
        $$(".vector-page-toolbar-container a").filter(visible).shouldHave(texts(expectedButtons));
        $("#content").shouldHave(text(language.description));
    }


    @CsvSource(value = {
            "Kotlin, kotlinlang.org",
            "Junit, junit.org"
    })
    @ParameterizedTest(name = "Для поискового запроса {0} на странице должен быть указан сайт {1}")
    @Tag("BLOCKER")
    @DisplayName("Результат поиска на wikipedia.org должен содержать ожидаемый URL")
    void searchResultsShouldContainExpectedUrl(String searchQuery, String expectedAuthor) {
        open("https://www.wikipedia.org/");
        $("#searchInput").val(searchQuery).pressEnter();
        $("#content").shouldHave(text(expectedAuthor));
    }


}
