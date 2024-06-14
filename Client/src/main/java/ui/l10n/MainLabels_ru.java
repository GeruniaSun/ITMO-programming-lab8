package ui.l10n;

import java.util.ListResourceBundle;

public class MainLabels_ru extends ListResourceBundle {
    public Object[][] getContents() { return contents; }
    private final Object[][] contents = {
            {"exitButton", "Выход"},
            {"visButton", "Визуализация"},
            {"langButton", "Язык"},
            {"helpButton", "Помощь"},
            {"sortButton", "Столбец сортировки"},
            {"filterButton", "Выберите фильтр типа"},
            {"nameCol", "Имя"},
            {"dateCol", "Дата"},
            {"priceCol", "Цена"},
            {"typeCol", "Тип"},
            {"venueCol", "Место проведения"},
            {"capCol", "Вместимость"},
            {"addressCol", "Адрес"},
            {"authorCol", "Автор"}
    };
}
