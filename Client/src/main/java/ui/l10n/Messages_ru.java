package ui.l10n;

import java.util.ListResourceBundle;

public class Messages_ru extends ListResourceBundle {
    public Object[][] getContents() { return contents; }
    private final Object[][] contents = {
            {"""
                        info - выводит информацию о коллекции
                        show - выводит все элементы коллекции
                        add - добавляет элемент в коллекцию
                        print_descending - выводит все элементы коллекции в порядке убывания
                        filter_by_type type - выводит все элементы коллекции типа type
                        count_greater_than_type type - выводит количество элементов коллекции, значение поля type которых больше заданного
                        clear - удаляет все элементы из коллекции
                        remove_by_id id - удаляет из коллекции элемент с айди id
                        remove_greater {element} - удаляет из коллекции все элементы, меньшие, чем заданный
                        remove_lower {element} - удаляет из коллекции все элементы, большие, чем заданный
                        add_if_max {element} - добавляет элемент в коллекцию, если он превышает максимальный
                        update id {element} - обновляет поля элемента по его id
                        exit - завершает работу приложения
                        execute_script filename - выполняет команды из файла filename""", """
                        info - выводит информацию о коллекции
                        show - выводит все элементы коллекции
                        add - добавляет элемент в коллекцию
                        print_descending - выводит все элементы коллекции в порядке убывания
                        filter_by_type type - выводит все элементы коллекции типа type
                        count_greater_than_type type - выводит количество элементов коллекции, значение поля type которых больше заданного
                        clear - удаляет все элементы из коллекции
                        remove_by_id id - удаляет из коллекции элемент с айди id
                        remove_greater {element} - удаляет из коллекции все элементы, меньшие, чем заданный
                        remove_lower {element} - удаляет из коллекции все элементы, большие, чем заданный
                        add_if_max {element} - добавляет элемент в коллекцию, если он превышает максимальный
                        update id {element} - обновляет поля элемента по его id
                        exit - завершает работу приложения
                        execute_script filename - выполняет команды из файла filename"""}

    };
}
